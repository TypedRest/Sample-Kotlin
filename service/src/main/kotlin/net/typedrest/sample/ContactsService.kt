package net.typedrest.sample

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.Instant

/**
 * Manages contacts in an address book.
 */
interface ContactsService {
    fun readAll(): List<Contact>
    fun read(id: String): Contact
    fun create(contact: Contact): Contact
    fun update(contact: Contact)
    fun delete(id: String)
    fun readNote(id: String): Note
    fun setNote(id: String, note: Note)
    fun poke(id: String)
}

@Service
@Transactional
class ContactsServiceImpl(private val repository: ContactRepository) : ContactsService {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun readAll(): List<Contact> {
        val result = repository.findAll().map { it.toDto() }
        logger.trace("Read all contacts")
        return result
    }

    override fun read(id: String): Contact {
        val element = repository.findById(id).orElseThrow { NoSuchElementException("Contact '$id' not found.") }
        logger.trace("Read contact {}", id)
        return element.toDto()
    }

    override fun create(contact: Contact): Contact {
        val entity = ContactEntity(firstName = contact.firstName, lastName = contact.lastName)
        repository.save(entity)
        logger.debug("Created new contact {}", entity.id)
        return entity.toDto()
    }

    override fun update(contact: Contact) {
        val id = contact.id ?: throw IllegalArgumentException("Contact ID must be set.")
        val entity = repository.findById(id).orElseThrow { NoSuchElementException("Contact '$id' not found.") }
        entity.firstName = contact.firstName
        entity.lastName = contact.lastName
        repository.save(entity)
        logger.debug("Updated contact {}", id)
    }

    override fun delete(id: String) {
        val entity = repository.findById(id).orElseThrow { NoSuchElementException("Contact '$id' not found.") }
        repository.delete(entity)
        logger.debug("Deleted contact {}", id)
    }

    override fun readNote(id: String): Note {
        val entity = repository.findById(id).orElseThrow { NoSuchElementException("Contact '$id' not found.") }
        logger.trace("Read note for contact {}", id)
        return Note(content = entity.note.orEmpty())
    }

    override fun setNote(id: String, note: Note) {
        val entity = repository.findById(id).orElseThrow { NoSuchElementException("Contact '$id' not found.") }
        entity.note = note.content
        repository.save(entity)
        logger.debug("Set note for contact {}", id)
    }

    override fun poke(id: String) {
        val entity = repository.findById(id).orElseThrow { NoSuchElementException("Contact '$id' not found.") }
        entity.pokes.add(PokeEntity(contact = entity, timestamp = Instant.now()))
        repository.save(entity)
        logger.debug("Poked contact {}", id)
    }

    private fun ContactEntity.toDto() = Contact(id = id, firstName = firstName, lastName = lastName)
}
