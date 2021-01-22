package org.maats.eloserver.controller

import org.maats.eloserver.model.Match
import org.maats.eloserver.model.Player
import org.maats.eloserver.service.RatingService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping('/rating')
class RatingController {

    @Autowired
    private RatingService ratingService

    @CrossOrigin
    @GetMapping('/')
    String getHome() {
        return 'ELO Rating Server'
    }

    @CrossOrigin
    @RequestMapping(value = '/v1/list/domain/{domain}', method = RequestMethod.GET, produces = 'application/json')
    @ResponseBody Map<String, Object> getAllPlayerRatings(
            @PathVariable("domain") String domain
    ) {
        Set<Player> players = ratingService.getAllPlayerRatings(domain)
        return [
                domain: domain,
                players: players,
        ]
    }

    @CrossOrigin
    @RequestMapping(value = '/v1/match/domain/{domain}', method = RequestMethod.GET, produces = 'application/json')
    @ResponseBody Match requestMatch(
            @PathVariable("domain") String domain
    ) {
        return ratingService.getMatch(domain)
    }

    @CrossOrigin
    @RequestMapping(value = '/v1/match/domain/{domain}/matchId/{matchId}/winningPlayerId/{winningPlayerId}', method = RequestMethod.POST, produces = 'application/json')
    @ResponseBody Map<String, Object> reportMatchResult(
            @PathVariable("domain") String domain,
            @PathVariable("matchId") String matchId,
            @PathVariable("winningPlayerId") String winningPlayerId
    ) {
        ratingService.reportMatchResult(matchId, domain, winningPlayerId)
        return [matchReported: 'success']
    }

}
