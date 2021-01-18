package org.maats.eloserver.model

import groovy.transform.Canonical
import groovy.transform.ToString

@Canonical
@ToString(includePackage=false, includeNames=true)
class Player {
    String id
    String domain
    String name
    String description
    BigDecimal rating
    int wins
    int losses
}
