package org.maats.eloserver.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class PlayerEntityRepositoryIntegrationSpec extends Specification {

    @Autowired
    private PlayerEntityRepository repository

    // TODO JM - more in-depth tests needed, don't rely on pre-created data

    def 'test - get test player'() {
        when:
        def result = repository.getAllPlayers('test')

        then:
        //println result
        result
    }

}
