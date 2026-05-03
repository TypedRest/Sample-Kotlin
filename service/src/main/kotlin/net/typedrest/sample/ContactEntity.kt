package net.typedrest.sample

import jakarta.persistence.*
import java.time.Instant
import java.util.UUID

/**
 * A representation of a contact for database storage.
 */
@Entity
@Table(name = "contacts")
class ContactEntity(
    @Id
    var id: String = UUID.randomUUID().toString(),

    @Column(nullable = false)
    var firstName: String = "",

    @Column(nullable = false)
    var lastName: String = "",

    @Column
    var note: String? = null,

    @OneToMany(mappedBy = "contact", cascade = [CascadeType.ALL], orphanRemoval = true)
    var pokes: MutableList<PokeEntity> = mutableListOf()
)

/**
 * A representation of a poke for database storage.
 */
@Entity
@Table(name = "pokes")
class PokeEntity(
    @Id
    var id: String = UUID.randomUUID().toString(),

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id", nullable = false)
    var contact: ContactEntity? = null,

    @Column(nullable = false)
    var timestamp: Instant = Instant.now()
)
