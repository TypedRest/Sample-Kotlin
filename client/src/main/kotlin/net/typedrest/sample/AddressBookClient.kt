package net.typedrest.sample

import net.typedrest.endpoints.EntryEndpoint
import net.typedrest.endpoints.generic.GenericCollectionEndpointImpl
import net.typedrest.serializers.KotlinxJsonSerializer
import okhttp3.OkHttpClient
import java.net.URI

/**
 * Provides a type-safe client for the Address Book REST API.
 */
open class AddressBookClient : EntryEndpoint {

    /**
     * Creates a new Address Book Client.
     *
     * @param uri The base URI of the Address Book API.
     */
    constructor(uri: URI) : super(uri, serializer = KotlinxJsonSerializer())

    /**
     * Creates a new Address Book Client using a custom [OkHttpClient]. This is usually used for testing.
     *
     * @param uri The base URI of the Address Book API.
     * @param httpClient The HTTP client used to communicate with the remote element.
     */
    constructor(uri: URI, httpClient: OkHttpClient) : super(uri, httpClient, KotlinxJsonSerializer())

    /**
     * A collection of contacts in an address book.
     */
    val contacts: GenericCollectionEndpointImpl<Contact, ContactElementEndpoint> =
        GenericCollectionEndpointImpl(this, "contacts", Contact::class.java) { referrer, relativeUri ->
            ContactElementEndpoint(referrer, relativeUri)
        }
}
