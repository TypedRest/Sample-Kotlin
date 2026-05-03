package net.typedrest.sample

import kotlinx.serialization.Serializable

/**
 * A note about a specific [Contact].
 */
@Serializable
data class Note(
    val content: String
)
