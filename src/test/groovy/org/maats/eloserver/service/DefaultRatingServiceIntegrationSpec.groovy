package org.maats.eloserver.service

import org.maats.eloserver.data.PlayerEntity
import org.maats.eloserver.data.PlayerEntityRepository
import org.maats.eloserver.model.Match
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class DefaultRatingServiceIntegrationSpec extends Specification {

    private static final String DOMAIN = 'DefaultRatingServiceIntegrationSpec'

    @Autowired private PlayerEntityRepository playerEntityRepository
    @Autowired private RatingService ratingService


    private players = []

    def 'setup'() {
        // load players into player table
        playerEntityRepository.deleteAll(playerEntityRepository.getAllPlayers(DOMAIN))
        playerEntityRepository.saveAll([
                new PlayerEntity(domain: DOMAIN, name: '1', rating: 1500.0, wins: 0, losses: 0,),
                new PlayerEntity(domain: DOMAIN, name: '2', rating: 1500.0, wins: 0, losses: 0,),
                new PlayerEntity(domain: DOMAIN, name: '3', rating: 1500.0, wins: 0, losses: 0,),
                new PlayerEntity(domain: DOMAIN, name: '4', rating: 1500.0, wins: 0, losses: 0,),
                new PlayerEntity(domain: DOMAIN, name: '5', rating: 1500.0, wins: 0, losses: 0,),
                new PlayerEntity(domain: DOMAIN, name: '6', rating: 1500.0, wins: 0, losses: 0,),
                new PlayerEntity(domain: DOMAIN, name: '7', rating: 1500.0, wins: 0, losses: 0,),
                new PlayerEntity(domain: DOMAIN, name: '8', rating: 1500.0, wins: 0, losses: 0,),
        ])

        players = playerEntityRepository.getAllPlayers(DOMAIN)
    }

    def 'cleanup'() {
        playerEntityRepository.deleteAll(playerEntityRepository.getAllPlayers(DOMAIN))
    }

    def 'test - simulate games'() {
        when:
        128.times {
            Match match = ratingService.getMatch(DOMAIN)
            ratingService.reportMatchResult(match.matchId, DOMAIN, match.player1.id)
        }

        then:
        def results = playerEntityRepository.getAllPlayers(DOMAIN)
        results.each {
            //println it
            assert it.wins
            assert it.losses
            assert it.rating != 1500.0
        }

        // TODO JM - assert that all matches got deleted
    }

}
