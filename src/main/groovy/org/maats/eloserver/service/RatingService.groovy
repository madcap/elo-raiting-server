package org.maats.eloserver.service

import org.maats.eloserver.model.Match
import org.maats.eloserver.model.Player

interface RatingService {

    Set<Player> getAllPlayerRatings(String domain)

    Match getMatch(String domain)

    void reportMatchResult(String matchId, String domain, String winningPlayerId)

}
