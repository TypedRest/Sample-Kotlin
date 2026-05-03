package net.typedrest.sample

import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

/**
 * Provides access to contacts in an address book.
 */
@RestController
@RequestMapping("/contacts")
class ContactsController(private val service: ContactsService) {

    @GetMapping
    fun readAll(): List<Contact> = service.readAll()

    @GetMapping("/{id}")
    fun read(@PathVariable id: String): Contact = service.read(id)

    @PostMapping
    fun create(@RequestBody @Valid contact: Contact): ResponseEntity<Contact> {
        val result = service.create(contact)
        val location = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(result.id)
            .toUri()
        return ResponseEntity.created(location).body(result)
    }

    @PutMapping("/{id}")
    fun set(@PathVariable id: String, @RequestBody @Valid contact: Contact): ResponseEntity<Void> {
        if (contact.id != id) throw BadRequestException("ID in URI ($id) must match the ID in the body (${contact.id}).")
        service.update(contact)
        return ResponseEntity.noContent().build()
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: String): ResponseEntity<Void> {
        service.delete(id)
        return ResponseEntity.noContent().build()
    }

    @GetMapping("/{id}/note")
    fun readNote(@PathVariable id: String): Note = service.readNote(id)

    @PutMapping("/{id}/note")
    fun setNote(@PathVariable id: String, @RequestBody @Valid note: Note): ResponseEntity<Void> {
        service.setNote(id, note)
        return ResponseEntity.noContent().build()
    }

    @PostMapping("/{id}/poke")
    fun poke(@PathVariable id: String): ResponseEntity<Void> {
        service.poke(id)
        return ResponseEntity.noContent().build()
    }
}
