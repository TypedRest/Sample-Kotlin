package net.typedrest.sample

import org.springframework.data.jpa.repository.JpaRepository

interface ContactRepository : JpaRepository<ContactEntity, String>
