package org.maats.eloserver.controller

import spock.lang.Specification

class HomeControllerSpec extends Specification {

    private HomeController controller = new HomeController()

    def 'test - getHome'() {
        expect:
        controller.getHome() == 'ELO Rating Server'
    }
}
