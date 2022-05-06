package training.micronaut

import graphql.schema.DataFetcher
import graphql.schema.DataFetchingEnvironment
import jakarta.inject.Singleton
import javax.xml.crypto.Data

@Singleton
class GraphQLDataFetchers(private val dbRepository: DbRepository) {

    fun bookByIdDataFetcher(): DataFetcher<Book> {
        return DataFetcher { environment: DataFetchingEnvironment ->
            val bookId: String = environment.getArgument("id")
            dbRepository.findAllBooks()
                .firstOrNull { book: Book -> (book.id == bookId) }
        }
    }

    fun authorDataFetcher(): DataFetcher<Author> {
        return DataFetcher { environment: DataFetchingEnvironment ->
            val book: Book = environment.getSource()
            val authorBook: Author = book.author
            dbRepository.findAllAuthors()
                .firstOrNull { author: Author -> (author.id == authorBook.id) }
        }
    }
}