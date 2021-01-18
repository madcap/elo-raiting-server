package org.maats.eloserver.service

import org.maats.eloserver.model.Player
import org.maats.eloserver.model.PlayerPair
import org.springframework.stereotype.Service

@Service
class DefaultEloService implements EloService {

    private static final int K_VALUE = 28

    @Override
    PlayerPair updateRatings(String domain, Player winningPlayer, Player losingPlayer) {
        assert domain
        assert winningPlayer
        assert losingPlayer
        assert winningPlayer.rating
        assert losingPlayer.rating

//        println 'computing new ratings for:'
//        println "winner: $winningPlayer"
//        println "loser:  $losingPlayer"

        Player winner = new Player(
                id: winningPlayer.id,
                domain: winningPlayer.domain,
                name: winningPlayer.name,
                description: winningPlayer.description,
                wins: winningPlayer.wins + 1,
                losses: winningPlayer.losses,
        )
        Player loser = new Player(
                id: losingPlayer.id,
                domain: losingPlayer.domain,
                name: losingPlayer.name,
                description: losingPlayer.description,
                wins: losingPlayer.wins,
                losses: losingPlayer.losses + 1,
        )
        PlayerPair results = new PlayerPair(
                player1: winner,
                player2: loser,
        )

        BigDecimal winnerProbability = getProbability(
                winningPlayer.rating,
                losingPlayer.rating
        )
        //println "winnerProbability = $winnerProbability"

        BigDecimal loserProbability = getProbability(
                losingPlayer.rating,
                winningPlayer.rating
        )
        //println "loserProbability = $loserProbability"

        assert (winnerProbability + loserProbability).round(3) == 1.0, 'rating calculation failed'

        winner.rating = winningPlayer.rating + (K_VALUE * (1 - winnerProbability))
        loser.rating = losingPlayer.rating + (K_VALUE * (0 - loserProbability))

        return results
    }

    private BigDecimal getProbability(BigDecimal rating1, BigDecimal rating2) {
        return 1.0 / (1.0 + Math.pow(10, (rating1.doubleValue() - rating2.doubleValue()) / 400))
    }

}
