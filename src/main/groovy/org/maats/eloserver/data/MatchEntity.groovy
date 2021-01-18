package org.maats.eloserver.data

import groovy.transform.Canonical
import groovy.transform.ToString

import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.Table

@Canonical
@ToString(includePackage=false, includeNames=true)
@Entity
@Table(name = "matches", schema = "rating")
class MatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    UUID id
    String domain
    @Column(name="player_id_1")
    String playerId1
    @Column(name="player_id_2")
    String playerId2
}
