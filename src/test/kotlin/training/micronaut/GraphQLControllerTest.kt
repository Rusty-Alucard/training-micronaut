package training.micronaut

import io.micronaut.core.type.Argument
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

@MicronautTest
class GraphQLControllerTest {

    @Inject
    @field:Client("/")
    lateinit var client: HttpClient

    @Test
    fun testGraphQLController() {
        val query = "{ \"query\": \"{ bookById(id:\\\"book-1\\\") { name, pageCount, author { firstName, lastName} } }\" }"
        val request: HttpRequest<String> = HttpRequest.POST("/graphql", query)

        val res = client.toBlocking().exchange(request, Argument.of(Map::class.java))
        assertEquals(HttpStatus.OK, res.status)
        assertNotNull(res.body())

        val bookInfo = res.getBody(Map::class.java).get()["data"] as Map<*, *>
        val bookById = bookInfo["bookById"] as Map<*, *>?
        assertEquals("Harry Potter and the Philosopher's stone", bookById!!["name"])
        assertEquals(223, bookById["pageCount"])

        val author = bookById["author"] as Map<*, *>?
        assertEquals("Joanne", author!!["firstName"])
        assertEquals("Rowling", author["lastName"])
    }
}
