package org.maats.eloserver.utility

import org.maats.eloserver.data.PlayerEntity
import org.maats.eloserver.model.Player
import spock.lang.Specification

class PlayerUtilitySpec extends Specification {

    private static final UUID UNIQUE_ID = UUID.randomUUID()

    private PlayerEntity playerEntity = new PlayerEntity(
            id: UNIQUE_ID,
            domain: 'domain',
            name: 'name',
            description: 'description',
            rating: 100.0,
            wins: 1,
            losses: 0,
    )

    private Player player = new Player(
            id: UNIQUE_ID.toString(),
            domain: 'domain',
            name: 'name',
            description: 'description',
            rating: 100.0,
            wins: 1,
            losses: 0,
    )

    def 'test - convertFromEntity'() {
        expect:
        PlayerUtility.convertFromEntity(playerEntity) == player
        PlayerUtility.convertFromEntity(null) == null
    }

    def 'test - convertToEntity'() {
        expect:
        PlayerUtility.convertToEntity(player) == playerEntity
        PlayerUtility.convertToEntity(null) == null
    }

}
