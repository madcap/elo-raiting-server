package org.maats.eloserver.utility

import org.maats.eloserver.data.PlayerEntity
import org.maats.eloserver.model.Player


final class PlayerUtility {

    private PlayerUtility() { }

    static Player convertFromEntity(PlayerEntity playerEntity) {
        if(playerEntity == null) {
            return null
        }
        return new Player(
                id: playerEntity.id?.toString(),
                domain: playerEntity.domain,
                name: playerEntity.name,
                description: playerEntity.description,
                rating: playerEntity.rating,
                wins: playerEntity.wins,
                losses: playerEntity.losses,
        )
    }

    static PlayerEntity convertToEntity(Player player) {
        if(player == null) {
            return null
        }
        return new PlayerEntity(
                id: player.id ? UUID.fromString(player.id) : null,
                domain: player.domain,
                name: player.name,
                description: player.description,
                rating: player.rating,
                wins: player.wins,
                losses: player.losses,
        )
    }
}
