package org.maats.eloserver

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class ApplicationSpec extends Specification {

    def 'test - app starts up'() {
        expect:
        true
    }

}
