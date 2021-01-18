package org.maats.eloserver.model

import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includePackage=false, includeNames=true)
class Match {
    String matchId
    String domain
    Player player1
    Player player2
}
