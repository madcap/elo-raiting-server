package org.maats.eloserver.service

import org.maats.eloserver.model.Player
import spock.lang.Specification

class DefaultEloServiceSpec extends Specification {

    private static final String DOMAIN = 'domain'

    private DefaultEloService service = new DefaultEloService()

    private Player player1 = new Player(name: '1', rating: 1000.0, wins: 0, losses: 0, domain: DOMAIN, description: 'p1',)
    private Player player2 = new Player(name: '2', rating: 1000.0, wins: 0, losses: 0, domain: DOMAIN, description: 'p2',)


    def 'test - updateRatings - invalid input'() {
        when:
        service.updateRatings(domain, winningPlayer, losingPlayer)

        then:
        thrown(AssertionError)

        and:
        0 * _

        where:
        domain | winningPlayer             | losingPlayer
        null   | new Player(rating: 1.0,)  | new Player(rating: 2.0,)
        DOMAIN | null                      | new Player(rating: 2.0,)
        DOMAIN | new Player(rating: 1.0,)  | null
        DOMAIN | new Player(rating: null,) | new Player(rating: 2.0,)
        DOMAIN | new Player(rating: 1.0,)  | new Player(rating: null,)
    }

    def 'test - updateRatings - happy path'() {
        when:
        def results = service.updateRatings(DOMAIN, player1, player2)

        then:
        results
        results.player1
        results.player2

        and:
        results.player1.losses == 0
        results.player1.wins == 1
        results.player2.losses == 1
        results.player2.wins == 0
        results.player1.domain == DOMAIN
        results.player2.domain == DOMAIN
        results.player1.description == player1.description
        results.player2.description == player2.description

        and:
        // since both players have the same starting rating the probability should be 50/50
        results.player1.rating == player1.rating + (DefaultEloService.K_VALUE * 0.5)
        results.player2.rating == player2.rating - (DefaultEloService.K_VALUE * 0.5)

        and:
        0 * _
    }

}
