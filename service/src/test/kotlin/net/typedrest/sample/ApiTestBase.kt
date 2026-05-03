package net.typedrest.sample

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.bean.override.mockito.MockitoBean
import java.net.URI

/**
 * Sets up an in-memory version of the Spring Boot stack for decoupled testing of controllers and the client library.
 */
@SpringBootTest(
    classes = [Application::class],
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
abstract class ApiTestBase {
    @LocalServerPort
    protected var port: Int = 0

    @MockitoBean
    protected lateinit var serviceMock: ContactsService

    protected val client: AddressBookClient
        get() = AddressBookClient(URI("http://localhost:$port/"))
}
