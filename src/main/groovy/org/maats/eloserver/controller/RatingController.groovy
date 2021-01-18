package org.maats.eloserver.controller

import org.maats.eloserver.model.Match
import org.maats.eloserver.model.Player
import org.maats.eloserver.service.RatingService
import org.springframework.beans.factory.annotation.Autowired
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

    @RequestMapping(value = '/', method = RequestMethod.GET, produces = 'application/json')
    @ResponseBody Map<String, Object> ratingHome() {
        // TODO JM - do something with this
        return [:]
    }

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

    @RequestMapping(value = '/v1/match/domain/{domain}', method = RequestMethod.GET, produces = 'application/json')
    @ResponseBody Match requestMatch(
            @PathVariable("domain") String domain
    ) {
        return ratingService.getMatch(domain)
    }

    @RequestMapping(value = '/v1/match/domain/{domain}/matchId/{matchId}/winningPlayerId/{winningPlayerId}', method = RequestMethod.POST, produces = 'application/json')
    @ResponseBody Map<String, Object> matchResult(
            @PathVariable("domain") String domain,
            @PathVariable("matchId") String matchId,
            @PathVariable("winningPlayerId") String winningPlayerId
    ) {
        ratingService.reportMatchResult(matchId, domain, winningPlayerId)
        return [matchReported: 'success']
    }

}
