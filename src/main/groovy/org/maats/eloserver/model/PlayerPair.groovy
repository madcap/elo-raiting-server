package org.maats.eloserver.model

import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includePackage=false, includeNames=true)
class PlayerPair {
    Player player1
    Player player2
}
