package net.typedrest.sample

import org.junit.jupiter.api.Test
import org.mockito.Mockito.verify
import org.mockito.kotlin.whenever
import java.net.URI
import kotlin.test.assertEquals

/**
 * Ensures [AddressBookClient.contacts] and [ContactsController] work together.
 */
class ContactsApiTest : ApiTestBase() {

    @Test
    fun readsAllFromService() {
        val contacts = listOf(
            Contact(id = "1", firstName = "John", lastName = "Smith"),
            Contact(id = "2", firstName = "Jane", lastName = "Doe")
        )
        whenever(serviceMock.readAll()).thenReturn(contacts)

        val result = client.contacts.readAll()

        assertEquals(contacts, result)
    }

    @Test
    fun readsFromService() {
        val contact = Contact(id = "1", firstName = "John", lastName = "Smith")
        whenever(serviceMock.read("1")).thenReturn(contact)

        val result = client.contacts["1"].read()

        assertEquals(contact, result)
    }

    @Test
    fun createsInService() {
        val contactWithoutId = Contact(firstName = "John", lastName = "Smith")
        val contactWithId = Contact(id = "1", firstName = "John", lastName = "Smith")
        whenever(serviceMock.create(contactWithoutId)).thenReturn(contactWithId)

        val result = client.contacts.create(contactWithoutId)

        assertEquals(URI("http://localhost:$port/contacts/1"), result?.uri)
    }

    @Test
    fun updatesInService() {
        val contact = Contact(id = "1", firstName = "John", lastName = "Smith")

        client.contacts["1"].set(contact)

        verify(serviceMock).update(contact)
    }

    @Test
    fun deletesFromService() {
        client.contacts["1"].delete()

        verify(serviceMock).delete("1")
    }

    @Test
    fun readsNoteFromService() {
        val note = Note(content = "my note")
        whenever(serviceMock.readNote("1")).thenReturn(note)

        val result = client.contacts["1"].note.read()

        assertEquals(note, result)
    }

    @Test
    fun setsNoteInService() {
        val note = Note(content = "my note")

        client.contacts["1"].note.set(note)

        verify(serviceMock).setNote("1", note)
    }

    @Test
    fun pokesViaService() {
        client.contacts["1"].poke.invoke()

        verify(serviceMock).poke("1")
    }
}
