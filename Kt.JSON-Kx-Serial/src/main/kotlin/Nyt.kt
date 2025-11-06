import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.net.URL

@Serializable
class NytTop(val status: String,
             val last_modified: String,
             val results: NytResult)

@Serializable
class NytResult(val list_name: String,
                val books: List<NytBook>)

@Serializable
class NytBook(val rank: Int,
              @SerialName("primary_isbn13") val isbn: String,
              val publisher: String,
              val title: String,
              val author: String)

fun showNytBestsellers() {
    // ersetzen Sie xxx durch einen eigenen Key, den Sie unter
    // <https://developer.nytimes.com> und
    // <https://developer.nytimes.com/accounts/create>
    // erhalten
    val url = URL("https://api.nytimes.com/svc/books/v3/lists/current/hardcover-fiction.json?api-key=QlgA0FsRxaB2hDP4QGKjmaD2OOCaBrf0")
    val jsondata = url.readText()
    // Json-Objekt erzeugen
    val json = Json { ignoreUnknownKeys = true }
    val bestsellers = json.decodeFromString<NytTop>(jsondata)
    for (b in bestsellers.results.books) {
        println("Rang ${b.rank}: ${b.title}")
        println("  Autor:  ${b.author}")
        println("  Verlag: ${b.publisher}")
        println("  ISBN:   ${b.isbn}")
    }

}