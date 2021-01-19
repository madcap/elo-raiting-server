package org.maats.eloserver.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class MatchEntityRepositorySpec extends Specification {

    @Autowired
    private MatchEntityRepository repository

    def 'test - validate database access'() {
        given:
        def entity = new MatchEntity(
                domain: 'domain',
                playerId1: 'player1',
                playerId2: 'player2',
        )

        when:
        def result = repository.save(entity)


        then:
        result
        result.id

        and:
        repository.deleteById(result.id)
    }

}
