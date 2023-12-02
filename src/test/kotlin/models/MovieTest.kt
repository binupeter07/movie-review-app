package model

import controllers.MovieAPI
import models.Movie
import models.Review
import org.junit.jupiter.api.*
import persistence.XMLSerializer
import java.io.File
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlin.test.fail

class MovieTest {

    private var shawshankRedemption: Movie?  = null
    private var godfather: Movie? = null
    private var darkKnight: Movie? = null
    private var inception: Movie? = null
    private var interstellar: Movie? = null
    private var populatedMovies: MovieAPI? = MovieAPI(XMLSerializer(File("movies.xml")))
    private var emptyMovies: MovieAPI? = MovieAPI(XMLSerializer(File("movies.xml")))

    @BeforeEach
    fun setup() {
        shawshankRedemption = Movie(0, "Shawshank Redemption", "Thriller", "David", "John cena",false,
            mutableSetOf(Review(0, "siobhan",8,"Good movie",true), Review(1, "binu",7,"Bad movie",false))
        )
        godfather = Movie(1, "God Father", "Drama", "John", "Nivin",true,
            mutableSetOf(Review(2,"Binu",4,"Not good one",false),Review(0, "siobhan",8,"Good movie",true))
        )
        darkKnight = Movie(2, "Dark Knight", "Investigation Thriller", "Mathew", "Nivin Pauly",false,
            mutableSetOf(Review(3,"Joseph",8,"Great Movie",true),Review(0, "siobhan",8,"Good movie",true))
        )
        inception = Movie(3, "Inception", "Comedy", "Nolan", "Fernandous",true,
            mutableSetOf(Review(3,"Mathew",2,"bad",false),Review(0, "siobhan",8,"Good movie",true))
        )
        interstellar = Movie(4, "Interstellar", "Entertainment", "Jospeh", "Christo",false,
            mutableSetOf(Review(4,"Mickel",8,"Entertaining one",false),Review(0, "siobhan",8,"Good movie",true))
        )

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
    inner class AddRating{
        @Test
        fun `Add rating to a movie that does not exist in movie arraylist`(){

            assertEquals(0,emptyMovies!!.numberOfMovies())
            val result = emptyMovies!!.findMovie(0)?.addRating((Review(0,"binu",5,"grsvc",false)))
            println("result...." + result )
            if (result != null){
                fail()
            }

        }

        @Test
        fun `Add rating to a movie that exist in movie array list` (){
            assertEquals(5,populatedMovies!!.numberOfMovies())
            assertTrue (godfather!!.addRating(Review(0,"binu",8,"Good One",false,)))
            assertEquals(3,populatedMovies!!.findMovie(1)!!.numberOfRatings())
            assertTrue (shawshankRedemption!!.addRating(Review(5,"mathew",5,"Not Good",false)))
            assertEquals(3,populatedMovies!!.findMovie(0)!!.numberOfRatings())
        }
    }

    @Nested
    inner class UpdateRating{

        @Test
        fun `Update a rating of a movie that does not exist in movie array list ` () {
            assertEquals(0,emptyMovies!!.numberOfMovies())
            val result = emptyMovies!!.findMovie(0)?.update(0,1,"James")
            println("result...." + result )
            if (result != null){
                fail()
            }
        }

        @Test
        fun `Update a review of a movie that exist in movie array list ` () {

            assertEquals(godfather, populatedMovies!!.findMovie(1))

            assertTrue { populatedMovies!!.findMovie(0)!!.update(0,1,"James") }
            assertTrue { populatedMovies!!.findMovie(0)!!.update(0,2,4) }
            assertTrue { populatedMovies!!.findMovie(0)!!.update(0,3,"Impressive") }
            assertTrue { populatedMovies!!.findMovie(0)!!.update(0,4,false) }

        }

    }

    @Nested
    inner class DeleteRating{

        @Test
        fun `deleting a Review that does not exist, returns null`() {


            assertEquals(0,emptyMovies!!.numberOfMovies())
            val result = emptyMovies!!.findMovie(0)?.deleteReview(0)
            println("result...." + result )
            if (result != null){
                fail()
            }

        }

        @Test
        fun `deleting a Review that exists delete and returns deleted object`() {
            assertEquals(5, populatedMovies!!.numberOfMovies())
            assertTrue { populatedMovies!!.findMovie(1)!!.deleteReview(2) }
            assertEquals(1, populatedMovies!!.findMovie(1)!!.numberOfRatings())
        }
    }

    @Nested
    inner class SearchMethods {

        @Test
        fun `searchMoviesByGenre returns no movies when no movies with that file exist` (){
            //Searching populated collection for a genre that doesn't exist.
            assertEquals(5, populatedMovies!!.numberOfMovies())
            val searchResults = populatedMovies!!.searchByGenre("No results found")
            assertTrue(searchResults.isEmpty())

            //Searching an empty collection
            assertEquals(0, emptyMovies!!.numberOfMovies())
            assertTrue(emptyMovies!!.searchByGenre("").isEmpty())
        }

        @Test
        fun `searchMoviesByGenre returns movies when movies with that title is exist in array` (){
            assertEquals(5, populatedMovies!!.numberOfMovies())

            //Searching a populated collection for a full genre exist in movie collections
            var searchResults = populatedMovies!!.searchByGenre("Thriller")
            assertTrue(searchResults.contains("Thriller"))
            assertFalse(searchResults.contains("Investigation thriller"))

            //Searching a populated collection for partial genre exist in movie collections
            searchResults = populatedMovies!!.searchByGenre("Thriller")
            assertTrue(searchResults.contains("Investigation Thriller"))
            assertFalse(searchResults.contains("Drama"))

        }

        @Test
        fun `searchMoviesByActors returns no movies when no movies with that file exist` (){
            //Searching populated collection for a genre that doesn't exist.
            assertEquals(5, populatedMovies!!.numberOfMovies())
            val searchResults = populatedMovies!!.searchByActor("No results found")
            assertTrue(searchResults.isEmpty())

            //Searching an empty collection
            assertEquals(0, emptyMovies!!.numberOfMovies())
            assertTrue(emptyMovies!!.searchByActor("").isEmpty())
        }

        @Test
        fun `searchMoviesByActor returns movies when movies with that title is exist in array` (){
            assertEquals(5, populatedMovies!!.numberOfMovies())

            //Searching a populated collection for a full genre exist in movie collections
            var searchResults = populatedMovies!!.searchByActor("Nivin")
            assertTrue(searchResults.contains("Nivin Pauly"))
            assertFalse(searchResults.contains("Nivin pauly"))

            //Searching a populated collection for partial actor name exist in movie collections
            searchResults = populatedMovies!!.searchByActor("Nivin")
            assertTrue(searchResults.contains("Nivin Pauly"))
            assertFalse(searchResults.contains("nivin"))

        }

    }



}