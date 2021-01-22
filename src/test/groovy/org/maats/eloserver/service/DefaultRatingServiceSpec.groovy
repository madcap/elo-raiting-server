package org.maats.eloserver.service

import org.maats.eloserver.data.MatchEntity
import org.maats.eloserver.data.MatchEntityRepository
import org.maats.eloserver.data.PlayerEntity
import org.maats.eloserver.data.PlayerEntityRepository
import org.maats.eloserver.model.Match
import org.maats.eloserver.model.Player
import org.maats.eloserver.model.PlayerPair
import spock.lang.Specification

class DefaultRatingServiceSpec extends Specification {

    private static final String DOMAIN = 'domain99'

    private EloService mockEloService = Mock()
    private MatchEntityRepository mockMatchEntityRepository = Mock()
    private PlayerEntityRepository mockPlayerEntityRepository = Mock()
    private Random mockRandom = Mock()

    private DefaultRatingService service = new DefaultRatingService(
            eloService: mockEloService,
            matchEntityRepository: mockMatchEntityRepository,
            playerEntityRepository: mockPlayerEntityRepository,
    )

    def 'setup'() {
        service.random = mockRandom
    }


    def 'test - getAllPlayerRatings - invalid input'() {
        when:
        service.getAllPlayerRatings(null)

        then:
        thrown(AssertionError)
    }

    def 'test - getAllPlayerRatings'() {
        given:
        Player player = new Player(name: 'p1',)
        PlayerEntity playerEntity = new PlayerEntity(name: 'p1',)

        when:
        def result = service.getAllPlayerRatings(DOMAIN)

        then:
        result == [player] as Set

        and:
        1 * mockPlayerEntityRepository.getAllPlayers(DOMAIN) >> ([playerEntity] as Set)
        0 * _
    }

    def 'test - getMatch - invalid input'() {
        when:
        service.getMatch(null)

        then:
        thrown(AssertionError)
    }

    def 'test - getMatch - not enough players'() {
        when:
        service.getMatch(DOMAIN)

        then:
        def e = thrown(AssertionError)
        e.message.contains 'domain does not have enough players to create a match'

        and:
        1 * mockPlayerEntityRepository.getAllPlayers(DOMAIN) >> ([new PlayerEntity()] as Set)
        0 * _
    }

    def 'test - getMatch'() {
        given:
        def players = [
                new PlayerEntity(id: UUID.randomUUID(), wins: 8, losses: 8,),
                new PlayerEntity(id: UUID.randomUUID(), wins: 4, losses: 4,),
                new PlayerEntity(id: UUID.randomUUID(), wins: 0, losses: 0,),
        ]
        def unsavedMatch = new MatchEntity(domain: DOMAIN, playerId1: players[2].id.toString(), playerId2: players[1].id.toString(), )
        def savedMatch = new MatchEntity(domain: DOMAIN, playerId1: players[2].id.toString(), playerId2: players[1].id.toString(), id: UUID.randomUUID(), )

        when:
        def result = service.getMatch(DOMAIN)

        then:
        result == new Match(
                matchId: savedMatch.id,
                domain: DOMAIN,
                player1: new Player(
                        id: players[2].id.toString(),
                        wins: 0,
                        losses: 0,
                ),
                player2: new Player(
                        id: players[1].id.toString(),
                        wins: 4,
                        losses: 4,
                ),
        )

        and:
        1 * mockPlayerEntityRepository.getAllPlayers(DOMAIN) >> (players.collect { it } as Set)
        1 * mockRandom.nextInt(2) >> 0
        1 * mockMatchEntityRepository.save(unsavedMatch) >> savedMatch
        0 * _
    }

    def 'test - reportMatchResult - invalid input'() {
        when:
        service.reportMatchResult(matchId, domain, winningPlayerId)

        then:
        thrown(AssertionError)

        where:
        matchId   | domain   | winningPlayerId
        null      | 'domain' | 'winningPlayerId'
        'matchId' | null     | 'winningPlayerId'
        'matchId' | 'domain' | null
    }

    def 'test - reportMatchResult - player1 wins'() {
        given:
        def winningPlayerId = UUID.randomUUID()
        //println "winningPlayerId = $winningPlayerId"
        def losingPlayerId = UUID.randomUUID()
        //println "losingPlayerId = $losingPlayerId"
        def matchId = UUID.randomUUID()
        def matchEntity = new MatchEntity(id: matchId, domain: DOMAIN, playerId1: winningPlayerId.toString(), playerId2: losingPlayerId.toString(), )
        def winningPlayerEntity = new PlayerEntity(id: winningPlayerId, )
        def losingPlayerEntity = new PlayerEntity(id: losingPlayerId, )
        def updatedPlayers = new PlayerPair(
                player1: new Player(id: winningPlayerId.toString(), ),
                player2: new Player(id: losingPlayerId.toString(), ),
        )

        when:
        service.reportMatchResult(matchId.toString(), DOMAIN, winningPlayerId.toString())

        then:
        1 * mockMatchEntityRepository.findById(matchId) >> Optional.of(matchEntity)
        1 * mockPlayerEntityRepository.findById(winningPlayerId) >> Optional.of(winningPlayerEntity)
        1 * mockPlayerEntityRepository.findById(losingPlayerId) >> Optional.of(losingPlayerEntity)
        1 * mockEloService.updateRatings(DOMAIN, _ as Player, _ as Player) >> { args ->
            assert args[1].id == winningPlayerId.toString()
            assert args[2].id == losingPlayerId.toString()
            return updatedPlayers
        }
        1 * mockPlayerEntityRepository.save(new PlayerEntity(id: winningPlayerId, ))
        1 * mockPlayerEntityRepository.save(new PlayerEntity(id: losingPlayerId, ))
        1 * mockMatchEntityRepository.deleteById(matchId)
        0 * _
    }

    def 'test - reportMatchResult - player2 wins'() {
        given:
        def winningPlayerId = UUID.randomUUID()
        //println "winningPlayerId = $winningPlayerId"
        def losingPlayerId = UUID.randomUUID()
        //println "losingPlayerId = $losingPlayerId"
        def matchId = UUID.randomUUID()
        def matchEntity = new MatchEntity(id: matchId, domain: DOMAIN, playerId1: losingPlayerId.toString(), playerId2: winningPlayerId.toString(), )
        def winningPlayerEntity = new PlayerEntity(id: winningPlayerId, )
        def losingPlayerEntity = new PlayerEntity(id: losingPlayerId, )
        def updatedPlayers = new PlayerPair(
                player1: new Player(id: winningPlayerId.toString(), ),
                player2: new Player(id: losingPlayerId.toString(), ),
        )

        when:
        service.reportMatchResult(matchId.toString(), DOMAIN, winningPlayerId.toString())

        then:
        1 * mockMatchEntityRepository.findById(matchId) >> Optional.of(matchEntity)
        1 * mockPlayerEntityRepository.findById(losingPlayerId) >> Optional.of(losingPlayerEntity)
        1 * mockPlayerEntityRepository.findById(winningPlayerId) >> Optional.of(winningPlayerEntity)
        1 * mockEloService.updateRatings(DOMAIN, _ as Player, _ as Player) >> { args ->
            assert args[1].id == winningPlayerId.toString()
            assert args[2].id == losingPlayerId.toString()
            return updatedPlayers
        }
        1 * mockPlayerEntityRepository.save(new PlayerEntity(id: winningPlayerId, ))
        1 * mockPlayerEntityRepository.save(new PlayerEntity(id: losingPlayerId, ))
        1 * mockMatchEntityRepository.deleteById(matchId)
        0 * _
    }

    def 'test - reportMatchResult - invalid player id'() {
        given:
        def winningPlayerId = UUID.randomUUID()
        def player1Id = UUID.randomUUID()
        def player2Id = UUID.randomUUID()
        def matchId = UUID.randomUUID()
        def matchEntity = new MatchEntity(id: matchId, domain: DOMAIN, playerId1: player1Id.toString(), playerId2: player2Id.toString(), )

        when:
        service.reportMatchResult(matchId.toString(), DOMAIN, winningPlayerId.toString())

        then:
        def e = thrown(AssertionError)
        e.message.contains 'declared winning player was not a valid choice for this match'

        and:
        1 * mockMatchEntityRepository.findById(matchId) >> Optional.of(matchEntity)
        0 * _
    }

}
