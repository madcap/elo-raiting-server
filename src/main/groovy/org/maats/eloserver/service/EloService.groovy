package org.maats.eloserver.service

import org.maats.eloserver.model.Player
import org.maats.eloserver.model.PlayerPair

interface  EloService {

    PlayerPair updateRatings(String domain, Player winningPlayer, Player losingPLayer)

}
