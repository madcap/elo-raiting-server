package org.maats.eloserver.data

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class MatchEntityRepositorySpec extends Specification {

    @Autowired
    private MatchEntityRepository repository

    // TODO JM - more in-depth tests needed, don't rely on pre-created data

    def 'test - get test player'() {
        when:
        def result = repository.findAll()

        then:
        //println result
        result
    }

}
