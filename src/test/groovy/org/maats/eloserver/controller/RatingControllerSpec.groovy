package org.maats.eloserver.controller

import org.maats.eloserver.model.Match
import org.maats.eloserver.model.Player
import org.maats.eloserver.service.RatingService
import spock.lang.Specification

class RatingControllerSpec extends Specification {

    private static final String DOMAIN = 'DOMAIN99'

    private RatingService mockRatingService = Mock()

    private RatingController controller = new RatingController(ratingService: mockRatingService)

    private Set<Player> players = [
            new Player(name: 'player1'),
    ] as Set

    def 'test - getHome'() {
        expect:
        controller.getHome() == 'ELO Rating Server'
    }

    def 'test - getAllPlayerRatings'() {
        when:
        def results = controller.getAllPlayerRatings(DOMAIN)

        then:
        results == [
                domain: DOMAIN,
                players: players,
        ]

        and:
        1 * mockRatingService.getAllPlayerRatings(DOMAIN) >> players
        0 * _
    }

    def 'test - requestMatch'() {
        given:
        def match = new Match(matchId: 'm1')

        when:
        def result = controller.requestMatch(DOMAIN)

        then:
        result == match

        and:
        1 * mockRatingService.getMatch(DOMAIN) >> match
        0 * _
    }

    def 'test - reportMatchResult'() {
        when:
        def result = controller.reportMatchResult(DOMAIN, 'match1', 'player1')

        then:
        result == [matchReported: 'success']

        and:
        1 * mockRatingService.reportMatchResult('match1', DOMAIN, 'player1')
        0 * _
    }
}
