package controllers

import models.Movie
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import persistence.JSONSerializer
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals

class MovieAPITest {

    private var shawshankRedemption: Movie? = null
    private var godfather: Movie? = null
    private var darkKnight: Movie? = null
    private var inception: Movie? = null
    private var interstellar: Movie? = null
    private var populatedMovies: MovieAPI? = MovieAPI(XMLSerializer(File("movies.xml")))
    private var emptyMovies: MovieAPI? = MovieAPI(XMLSerializer(File("movies.xml")))

    @BeforeEach
    fun setup() {
        shawshankRedemption = Movie(0, "Shawshank Redemption", "Thriller", "David", "John cena",false)
        godfather = Movie(1, "God Father", "Drama", "John", "Rock",true)
        darkKnight = Movie(2, "Dark Knight", "Investication Thriller", "Mathew", "Nivin Pauly",false)
        inception = Movie(3, "Inception", "Comedy", "Nolan", "Fernandous",true)
        interstellar = Movie(4, "Interstellar", "Entertainment", "Jospeh", "Christo",false)

        populatedMovies!!.add(shawshankRedemption!!)
        populatedMovies!!.add(godfather!!)
        populatedMovies!!.add(darkKnight!!)
        populatedMovies!!.add(inception!!)
        populatedMovies!!.add(interstellar!!)
    }

    @AfterEach
    fun tearDown() {
        shawshankRedemption = null
        godfather = null
        darkKnight = null
        inception = null
        interstellar = null
        populatedMovies = null
        emptyMovies = null
    }

    @Nested
    inner class AddMovies {

        @Test
        fun `adding a Movie to a populated movie list adds to ArrayList`() {
            val newMovie = Movie(0, "Neram", "Entertainment", "Alphonse", "Nivin Pauly")
            assertEquals(5, populatedMovies!!.numberOfMovies())
            assertTrue(populatedMovies!!.add(newMovie))
            assertEquals(6, populatedMovies!!.numberOfMovies())
            assertEquals(newMovie, populatedMovies!!.findMovie(populatedMovies!!.numberOfMovies() - 1))
        }

        @Test
        fun `adding a Movie to a empty movie list adds to ArrayList`() {
            val newMovie = Movie(0, "Neram", "Entertainment", "Alphonse", "Nivin Pauly")
            assertEquals(0, emptyMovies!!.numberOfMovies())
            assertTrue(emptyMovies!!.add(newMovie))
            assertEquals(1, emptyMovies!!.numberOfMovies())
            assertEquals(newMovie, emptyMovies!!.findMovie(emptyMovies!!.numberOfMovies() - 1))
        }

    }

    @Nested
    inner class ListMovies {
        @Test
        fun `listAllMovies returns No Movies Stored message when ArrayList is empty`() {
            assertEquals(0, emptyMovies!!.numberOfMovies())
            assertTrue(emptyMovies!!.listAllMovies().lowercase().contains("no movies"))
        }

        @Test
        fun `listAllMovies returns Movies when ArrayList has movies stored`() {
            assertEquals(5, populatedMovies!!.numberOfMovies())
            val moviesString = populatedMovies!!.listAllMovies().lowercase()
            assertTrue(moviesString.contains("shawshank redemption"))
            assertTrue(moviesString.contains("god father"))
            assertTrue(moviesString.contains("dark knight"))
            assertTrue(moviesString.contains("inception"))
            assertTrue(moviesString.contains("interstellar"))
        }

        @Test
        fun `listActiveMovies returns no active movies stored when ArrayList is empty`() {
            assertEquals(0, emptyMovies!!.numberOfActiveMovies())
            assertTrue(
                emptyMovies!!.listActiveMovies().lowercase().contains("no active movies")
            )
        }

        @Test
        fun `listActiveMovies returns active movies when ArrayList has active movies stored`() {
            assertEquals(3, populatedMovies!!.numberOfActiveMovies())
            val activeMovieString = populatedMovies!!.listActiveMovies().lowercase()
            assertTrue(activeMovieString.contains("shawshank redemption"))
            assertFalse(activeMovieString.contains("god father"))
            assertTrue(activeMovieString.contains("dark knight"))
            assertFalse(activeMovieString.contains("inception"))
            assertFalse(activeMovieString.contains("interception"))
        }

        @Test
        fun `listArchivedMovies returns no archived movies when ArrayList is empty`() {
            assertEquals(0, emptyMovies!!.numberOfArchivedMovies())
            assertTrue(
                emptyMovies!!.listArchivedMovies().lowercase().contains("no archived movies")
            )
        }

        @Test
        fun `listArchivedMovies returns archived movies when ArrayList has archived movies stored`() {
            assertEquals(2, populatedMovies!!.numberOfArchivedMovies())
            val archivedMoviesString = populatedMovies!!.listArchivedMovies().lowercase()
            assertFalse(archivedMoviesString.contains("shawshank redemption"))
            assertTrue(archivedMoviesString.contains("god father"))
            assertFalse(archivedMoviesString.contains("dark knight"))
            assertTrue(archivedMoviesString.contains("inception"))
            assertFalse(archivedMoviesString.contains("interception"))
        }
    }

    @Nested
    inner class UpdateMovies {
        @Test
        fun `updating a movie that does not exist returns false`(){
            assertFalse(populatedMovies!!.update(8,4,"fahad"))
            assertFalse(populatedMovies!!.update(-1,2,"fasil"))
            assertFalse(emptyMovies!!.update(0,3,"afcxa"))
        }

        @Test
        fun `updating a movie name that exists` () {

            assertEquals(godfather, populatedMovies!!.findMovie(1))
            assertEquals("God Father", populatedMovies!!.findMovie(1)!!.movieName)

            assertTrue(populatedMovies!!.update(1,1,"The God Father"))
            assertEquals("The God Father", populatedMovies!!.findMovie(1)!!.movieName)
            assertEquals("Drama", populatedMovies!!.findMovie(1)!!.movieGenre)
            assertEquals("John", populatedMovies!!.findMovie(1)!!.directorName)
            assertEquals("Rock", populatedMovies!!.findMovie(1)!!.stars)

        }

        @Test
        fun `updating a movie genre that exists` () {

            assertEquals(godfather, populatedMovies!!.findMovie(1))
            assertEquals("Drama", populatedMovies!!.findMovie(1)!!.movieGenre)

            assertTrue(populatedMovies!!.update(1,2,"Thriller"))
            assertEquals("God Father", populatedMovies!!.findMovie(1)!!.movieName)
            assertEquals("Thriller", populatedMovies!!.findMovie(1)!!.movieGenre)
            assertEquals("John", populatedMovies!!.findMovie(1)!!.directorName)
            assertEquals("Rock", populatedMovies!!.findMovie(1)!!.stars)

        }

        @Test
        fun `updating a movie director name that exists` () {

            assertEquals(godfather, populatedMovies!!.findMovie(1))
            assertEquals("John", populatedMovies!!.findMovie(1)!!.directorName)

            assertTrue(populatedMovies!!.update(1,3,"John David"))
            assertEquals("God Father", populatedMovies!!.findMovie(1)!!.movieName)
            assertEquals("Drama", populatedMovies!!.findMovie(1)!!.movieGenre)
            assertEquals("John David", populatedMovies!!.findMovie(1)!!.directorName)
            assertEquals("Rock", populatedMovies!!.findMovie(1)!!.stars)

        }

        @Test
        fun `updating a movie stars name that exists` () {

            assertEquals(godfather, populatedMovies!!.findMovie(1))
            assertEquals("Rock", populatedMovies!!.findMovie(1)!!.stars)

            assertTrue(populatedMovies!!.update(1,4,"Rocky"))
            assertEquals("God Father", populatedMovies!!.findMovie(1)!!.movieName)
            assertEquals("Drama", populatedMovies!!.findMovie(1)!!.movieGenre)
            assertEquals("John", populatedMovies!!.findMovie(1)!!.directorName)
            assertEquals("Rocky", populatedMovies!!.findMovie(1)!!.stars)

        }

    }

    @Nested
    inner class DeleteMovies {

        @Test
        fun `deleting a Movie that does not exist, returns null`() {
            Assertions.assertNull(emptyMovies!!.delete(0))
            Assertions.assertNull(populatedMovies!!.delete(-1))
            Assertions.assertNull(populatedMovies!!.delete(5))
        }

        @Test
        fun `deleting a Movie that exists delete and returns deleted object`() {
            assertEquals(5, populatedMovies!!.numberOfMovies())
            assertEquals(godfather, populatedMovies!!.delete(1))
            assertEquals(4, populatedMovies!!.numberOfMovies())
            assertEquals(shawshankRedemption, populatedMovies!!.delete(0))
            assertEquals(3, populatedMovies!!.numberOfMovies())
        }
    }

    @Nested
    inner class ArchiveMovies {
        @Test
        fun `archiving a movie that does not exist returns false`(){
            assertFalse(populatedMovies!!.archiveMovie(6))
            assertFalse(populatedMovies!!.archiveMovie(-1))
            assertFalse(emptyMovies!!.archiveMovie(0))
        }

        @Test
        fun `archiving an already archived movie returns false`(){
            assertTrue(populatedMovies!!.findMovie(1)!!.isMovieArchived)
            assertFalse(populatedMovies!!.archiveMovie(1))
        }

        @Test
        fun `archiving an active movie that exists returns true and archives`() {
            assertFalse(populatedMovies!!.findMovie(2)!!.isMovieArchived)
            assertTrue(populatedMovies!!.archiveMovie(2))
            assertTrue(populatedMovies!!.findMovie(2)!!.isMovieArchived)
        }
    }

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {

            val storingMovies = MovieAPI(XMLSerializer(File("movies.xml")))
            storingMovies.store()


            val loadedMovies = MovieAPI(XMLSerializer(File("movies.xml")))
            loadedMovies.load()


            assertEquals(0, storingMovies.numberOfMovies())
            assertEquals(0, loadedMovies.numberOfMovies())
            assertEquals(storingMovies.numberOfMovies(), loadedMovies.numberOfMovies())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {

            val storingMovies = MovieAPI(XMLSerializer(File("movies.xml")))
            storingMovies.add(godfather!!)
            storingMovies.add(shawshankRedemption!!)
            storingMovies.add(interstellar!!)
            storingMovies.store()

            val loadedMovies = MovieAPI(XMLSerializer(File("movies.xml")))
            loadedMovies.load()


            assertEquals(3, storingMovies.numberOfMovies())
            assertEquals(3, loadedMovies.numberOfMovies())
            assertEquals(storingMovies.numberOfMovies(), loadedMovies.numberOfMovies())
            assertEquals(storingMovies.findMovie(0), loadedMovies.findMovie(0))
            assertEquals(storingMovies.findMovie(1), loadedMovies.findMovie(1))
            assertEquals(storingMovies.findMovie(2), loadedMovies.findMovie(2))
        }

        @Test
        fun `saving and loading an empty collection in JSON doesn't crash app`() {
            // Saving an empty movies.json file.
            val storingMovies = MovieAPI(JSONSerializer(File("movies.json")))
            storingMovies.store()

            //Loading the empty movies.json file into a new object
            val loadedMovies = MovieAPI(JSONSerializer(File("movies.json")))
            loadedMovies.load()

            //Comparing the source of the movies (storingMovies) with the json loaded movies (loadedMovies)
            assertEquals(0, storingMovies.numberOfMovies())
            assertEquals(0, loadedMovies.numberOfMovies())
            assertEquals(storingMovies.numberOfMovies(), loadedMovies.numberOfMovies())
        }

        @Test
        fun `saving and loading an loaded collection in JSON doesn't loose data`() {
            // Storing 3 Movie to the movies.json file.
            val storingMovies = MovieAPI(JSONSerializer(File("movies.json")))
            storingMovies.add(shawshankRedemption!!)
            storingMovies.add(godfather!!)
            storingMovies.add(interstellar!!)
            storingMovies.store()

            //Loading movies.json into a different collection
            val loadedMovies = MovieAPI(JSONSerializer(File("movies.json")))
            loadedMovies.load()

            //Comparing the source of the movies (storingMovies) with the json loaded movies (loadedMovies)
            assertEquals(3, storingMovies.numberOfMovies())
            assertEquals(3, loadedMovies.numberOfMovies())
            assertEquals(storingMovies.numberOfMovies(), loadedMovies.numberOfMovies())
            assertEquals(storingMovies.findMovie(0), loadedMovies.findMovie(0))
            assertEquals(storingMovies.findMovie(1), loadedMovies.findMovie(1))
            assertEquals(storingMovies.findMovie(2), loadedMovies.findMovie(2))
        }
    }


    @Nested
    inner class SearchMethods {

        @Test
        fun `searchMoviesByActor returns no movies when no movies with that file exist` (){
            //Searching populated collection for a title that doesn't exist.
            assertEquals(5, populatedMovies!!.numberOfMovies())
            val searchResults = populatedMovies!!.searchByActor("No results found")
            assertTrue(searchResults.isEmpty())

            //Searching an empty collection
            assertEquals(0, emptyMovies!!.numberOfMovies())
            assertTrue(emptyMovies!!.searchByActor("").isEmpty())
        }

        @Test
        fun `searchMoviesByActor returns movies when movies with that movie name is exist in array` (){
            assertEquals(5, populatedMovies!!.numberOfMovies())

            //Searching a populated collection for a full title exist in movies collections
            var searchResults = populatedMovies!!.searchByActor("John")
            assertTrue(searchResults.contains("John"))
            assertFalse(searchResults.contains("Nivin Pauly"))

            //Searching a populated collection for partial title exist in movies collections
            searchResults = populatedMovies!!.searchByActor("Nivin")
            assertTrue(searchResults.contains("Nivin Pauly"))
            assertFalse(searchResults.contains("John"))

        }

    }
}