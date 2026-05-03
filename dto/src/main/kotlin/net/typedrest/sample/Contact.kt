package net.typedrest.sample

import kotlinx.serialization.Serializable

/**
 * A contact in an address book.
 */
@Serializable
data class Contact(
    val id: String? = null,
    val firstName: String,
    val lastName: String
)
