package training.micronaut

import jakarta.inject.Singleton

@Singleton
class DbRepository {

    fun findAllBooks(): List<Book> {
        return books
    }

    fun findAllAuthors(): List<Author> {
        return books.map(Book::author)
    }

    companion object {
        private val books = listOf(
            Book("book-1", "Harry Potter and the Philosopher's stone", 223, Author("Author-1", "Joanne", "Rowling")),
            Book("book-2", "Moby Dick", 635, Author("Author-2", "Herman", "Melville")),
            Book("book-3", "Interview with the vampire", 371, Author("Author-3", "Anne", "Rice"))
        )
    }
}
