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
@Table(name = "players", schema = "rating")
class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    UUID id
    String domain
    String name
    String description
    BigDecimal rating
    int wins
    int losses
}
