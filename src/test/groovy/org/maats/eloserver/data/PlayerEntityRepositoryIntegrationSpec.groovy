package org.maats.eloserver.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PlayerEntityRepositoryIntegrationSpec extends Specification {

    private static final String DOMAIN = 'PlayerEntityRepositoryIntegrationSpec'

    @Autowired
    private PlayerEntityRepository repository

    def 'test - getAllPlayers'() {
        given:
        def startingPlayers = repository.saveAll([
                new PlayerEntity(domain: DOMAIN, name: '1',),
                new PlayerEntity(domain: DOMAIN, name: '2',),
        ])

        when:
        def result = repository.getAllPlayers(DOMAIN)

        then:
        result
        result.size() == 2
        result.find { it.id == startingPlayers[0].id }
        result.find { it.id == startingPlayers[1].id }

        and:
        repository.deleteAll(repository.getAllPlayers(DOMAIN))
    }

}
