package org.maats.eloserver.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HomeController {

    @GetMapping('/')
    String getHome() {
        return 'ELO Server'
    }

}
