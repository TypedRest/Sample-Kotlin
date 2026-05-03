package net.typedrest.sample

import net.typedrest.endpoints.Endpoint
import net.typedrest.endpoints.generic.ElementEndpoint
import net.typedrest.endpoints.generic.ElementEndpointImpl
import net.typedrest.endpoints.rpc.ActionEndpoint
import net.typedrest.endpoints.rpc.ActionEndpointImpl
import java.net.URI

/**
 * Represents a REST endpoint for a single [Contact].
 */
open class ContactElementEndpoint(referrer: Endpoint, relativeUri: URI)
    : ElementEndpointImpl<Contact>(referrer, relativeUri, Contact::class.java) {

    /**
     * An optional note on the contact.
     */
    val note: ElementEndpoint<Note> =
        ElementEndpointImpl(this, "./note", Note::class.java)

    /**
     * An action for poking the contact.
     */
    val poke: ActionEndpoint =
        ActionEndpointImpl(this, "./poke")
}
