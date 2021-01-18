package org.maats.eloserver.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface PlayerEntityRepository extends JpaRepository<PlayerEntity, UUID> {

    @Query("select players from PlayerEntity players where players.domain = ?1")
    Set<PlayerEntity> getAllPlayers(String domain)

}
