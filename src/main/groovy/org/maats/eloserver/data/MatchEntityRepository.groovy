package org.maats.eloserver.data

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchEntityRepository extends JpaRepository<MatchEntity, UUID> {
}
