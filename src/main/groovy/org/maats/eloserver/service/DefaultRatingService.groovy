package org.maats.eloserver.service

import org.maats.eloserver.data.MatchEntity
import org.maats.eloserver.data.MatchEntityRepository
import org.maats.eloserver.data.PlayerEntity
import org.maats.eloserver.data.PlayerEntityRepository
import org.maats.eloserver.model.Match
import org.maats.eloserver.model.Player
import org.maats.eloserver.model.PlayerPair
import org.maats.eloserver.utility.PlayerUtility
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class DefaultRatingService implements RatingService {

    // TODO JM - need to clean up the match database once in a while, otherwise it will fill up over time

    @Autowired
    private EloService eloService

    @Autowired
    private MatchEntityRepository matchEntityRepository

    @Autowired
    private PlayerEntityRepository playerEntityRepository

    private Random random = new Random()

    @Override
    Set<Player> getAllPlayerRatings(String domain) {
        assert domain
        return playerEntityRepository.getAllPlayers(domain).collect { playerEntity ->
            PlayerUtility.convertFromEntity(playerEntity)
        } as Set
    }

    @Override
    Match getMatch(String domain) {
        assert domain

        List<PlayerEntity> allPlayers = playerEntityRepository.getAllPlayers(domain) as List
        int playerCount = allPlayers.size()

        // TODO JM - handle size <= 1 better
        assert playerCount > 1, 'domain does not have enough players to create a match'

        // pick 2 players at random
        int playerIndex1 = random.nextInt(playerCount)
        int playerIndex2 = random.nextInt(playerCount)
        while(playerIndex2 == playerIndex1) {
            // ensure that we don't use the same player for both spots in the match
            playerIndex2 = random.nextInt(playerCount)
        }

        PlayerEntity playerEntity1 = allPlayers[playerIndex1]
        PlayerEntity playerEntity2 = allPlayers[playerIndex2]

        // pick 2 players that have played the least games
//        allPlayers.sort { it.wins + it.losses }
//
//        PlayerEntity playerEntity1 = allPlayers[0]
//        PlayerEntity playerEntity2 = allPlayers[1]

        MatchEntity matchEntity = matchEntityRepository.save(new MatchEntity(
                domain: domain,
                playerId1: playerEntity1.id.toString(),
                playerId2: playerEntity2.id.toString(),
        ))

        Match match = new Match(
                player1: PlayerUtility.convertFromEntity(playerEntity1),
                player2: PlayerUtility.convertFromEntity(playerEntity2),
                domain: domain,
                matchId: matchEntity.id.toString(),
        )

        return match
    }

    @Override
    void reportMatchResult(String matchId, String domain, String winningPlayerId) {
        assert matchId
        assert domain
        assert winningPlayerId

        MatchEntity matchEntity = matchEntityRepository.findById(UUID.fromString(matchId)).get()

        // TODO JM - handle winner was not a valid choice better
        assert matchEntity.playerId1 == winningPlayerId || matchEntity.playerId2 == winningPlayerId, 'declared winning player was not a valid choice for this match'

        // TODO JM - handle couldn't find matching player entities better
        PlayerEntity playerEntity1 = playerEntityRepository.findById(UUID.fromString(matchEntity.playerId1)).get()
        PlayerEntity playerEntity2 = playerEntityRepository.findById(UUID.fromString(matchEntity.playerId2)).get()


        PlayerPair resultingPlayers
        if (playerEntity1.id.toString() == winningPlayerId) {
            resultingPlayers = eloService.updateRatings(domain, PlayerUtility.convertFromEntity(playerEntity1), PlayerUtility.convertFromEntity(playerEntity2))
        } else {
            resultingPlayers = eloService.updateRatings(domain, PlayerUtility.convertFromEntity(playerEntity2), PlayerUtility.convertFromEntity(playerEntity1))
        }

        playerEntityRepository.save(PlayerUtility.convertToEntity(resultingPlayers.player1))
        playerEntityRepository.save(PlayerUtility.convertToEntity(resultingPlayers.player2))

        matchEntityRepository.deleteById(UUID.fromString(matchId))
    }
}
