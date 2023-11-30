import controllers.MovieAPI
import models.Movie
import models.Review
import persistence.XMLSerializer
import utils.ScannerInput.readNextLine
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextBoolean
import java.io.File
import kotlin.system.exitProcess
private val movieAPI = MovieAPI(XMLSerializer(File("movies.xml")))

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addMovie()
            2 -> listMovies()
            3 -> updateMovie()
            4 -> deleteMovie()
            5 -> archiveMovie()
            6 -> addRatingToMovie()
            7 -> updateReviewsInMovie()
            8 -> deleteReview()
            9 -> searchMovieByGenre()
            10 -> searchMovieByActor()
            11 -> listTopFiveMovies()
            12 -> listTopFiveFavoriteMovies()
            13 -> searchMoviesByRating()
            14 -> searchMovieByUserName()
            15 -> save()
            16 -> load()
            0 -> exitApp()

            -99 -> dummyData()
            else -> println("Invalid menu choice: $option")
        }
    } while (true)
}

fun mainMenu() = readNextInt(
    """ 
         > -----------------------------------------------------  
         > |                 MOVIE REVIEW APP                  |
         > -----------------------------------------------------  
         > | MOVIE MENU                                        |
         > |   1) Add a movie                                  |
         > |   2) List movies                                  |
         > |   3) Update a movie                               |
         > |   4) Delete a movie                               |
         > |   5) Archive a movie                              |
         > -----------------------------------------------------  
         > | REVIEW MENU                                       | 
         > |   6) Add rating to a movie                        |
         > |   7) Update rating on a movie                     |
         > |   8) Delete rating from a movie                   |
         > -----------------------------------------------------  
         > | REPORT MENU FOR MOVIES                            | 
         > |   09) Search for all movies (by movie genre)      |
         > |   10) Search for all movies (by actor name)       |                                
         > |   11) List Top 5 movies( By average Rating)       |
         > |   12) List Top 5 movies (By Favorites)            |
         > -----------------------------------------------------  
         > | REPORT MENU FOR REVIEWS                           |                                
         > |   13) Search for all movies (by movie rating)     |
         > |   14) Search Movie by  reviewer username          |
         > ----------------------------------------------------- 
         > | REPORT MENU FOR REVIEWS                           |                                
         > |   15) Save Movie                                  |
         > |   16) Load Movie                                  |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

fun addMovie() {
    val movieName = readNextLine("Enter the name of movie: ")
    val movieGenre = readNextLine("Enter the genre")
    val movieDirector = readNextLine("Enter director name: ")
    val movieStars = readNextLine("Enter actors name : ")
    val isAdded = movieAPI.add(Movie( movieName= movieName, movieGenre = movieGenre, directorName = movieDirector, stars = movieStars))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Add Failed")
    }
}

fun listMovies() {
    if (movieAPI.numberOfMovies() > 0) {
        val option = readNextInt(
            """
                  > ---------------------------------
                  > |   1) View ALL movies          |
                  > |   2) View ACTIVE movies       |
                  > |   3) View ARCHIVED movies     |
                  > ---------------------------------
         > ==>> """.trimMargin(">")
        )

        when (option) {
            1 -> listAllMovies()
            2 -> listActiveMovies()
            3 -> listArchivedMovies()
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No movies stored")
    }
}

fun updateMovie() {
    listMovies()

    if (movieAPI.numberOfMovies() > 0) {
        val id = readNextInt("Enter the id of the movie to update: ")
        val movie = movieAPI.findMovie(id)

        if (movie != null) {
            val option = readNextInt(
                """
                  > -----------------------------------
                  > |   1) Update Name of the movie   |
                  > |   2) Update Genre               |
                  > |   3) Update director            |
                  > |   4) Update movie actors        |
                  > -----------------------------------
         > ==>> """.trimMargin(">")
            )

            val userInput = when (option) {
                1 -> "Enter the name of the movie: "
                2 -> "Enter the genre of the movie: "
                3 -> "Enter the name of director: "
                4 -> "Enter the name of actors: "
                else -> ""
            }

            val newValue = readNextLine(userInput)

            if (movieAPI.update(id, option, newValue)) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no movies for this index number")
        }
    }
}

fun deleteMovie() {
    listMovies()
    if (movieAPI.numberOfMovies() > 0) {
        val id = readNextInt("Enter the id of the movie to delete: ")
        val movieToDelete = movieAPI.delete(id)
        if (movieToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun archiveMovie() {
    listActiveMovies()
    if (movieAPI.numberOfActiveMovies() > 0) {
        val id = readNextInt("Enter the id of the movie to archive: ")
        if (movieAPI.archiveMovie(id)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}

private fun addRatingToMovie() {
    val movie: Movie? = askUserToChooseActiveMovie()
    if (movie != null) {
        val userName = readNextLine("Enter your name ")
        val movieRating = readNextInt("Enter the rating ")
        val movieReview = readNextLine("Tell me the review about the movie ")
        val isFavorite = readNextBoolean("Mark this movie as Favourite( Type yes or no ) ")
        movie.addRating(Review(name = userName, rating = movieRating, reviewText = movieReview, isFavorite = isFavorite))
        println("Add Successful!")
    }
    else println("Add NOT Successful")

}

fun updateReviewsInMovie() {
    val movie: Movie? = askUserToChooseActiveMovie()
    if (movie != null) {
        val review: Review? = askUserToChooseReview(movie)
        if (review != null) {
            val option = readNextInt(
                """
                  > -----------------------------------
                  > |   1) Update your name           |
                  > |   2) Update rating              |
                  > |   3) Update review text         |
                  > |   4) Update Favorite            |
                  > -----------------------------------
         > ==>> """.trimMargin(">")
            )

            val userInput = when (option) {
                1 -> "Enter your name "
                2 -> "Enter rating: "
                3 -> "Enter review text "
                else -> ""
            }

            val newValue = readNextLine(userInput)

            if (movie.update(review.ratingId, option, newValue)) {
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no reviews for this index number")
        }
    } else {
        println("Invalid Item Id")
    }
}

fun deleteReview() {
    val movie: Movie? = askUserToChooseActiveMovie()
    if (movie != null) {
        val review: Review? = askUserToChooseReview(movie)
        if (review != null) {
            val isDeleted = movie.delete(review.ratingId)
            if (isDeleted) {
                println("Delete Successful!")
            } else {
                println("Delete NOT Successful")
            }
        }
    }
}

//------------------------------------
//MOVIE REPORTS MENU
//------------------------------------

fun searchMovieByGenre(){
        val searchGenre = readNextLine("Enter the genre to search by: ")
        val searchResults = movieAPI.searchByGenre(searchGenre)
        if (searchResults.isEmpty()) {
            println("No movies for this genre stored")
        } else {
            println(searchResults)
        }
}

fun searchMovieByActor(){
    val searchActor = readNextLine("Enter the actor to search by: ")
    val searchResults = movieAPI.searchByActor(searchActor)
    if (searchResults.isEmpty()) {
        println("No movies for searched actor stored")
    } else {
        println(searchResults)
    }
}

fun searchMoviesByRating() {
    val ratingToSearch = readNextInt("Enter the rating to search by: ")
    val searchResults = movieAPI.searchMoviesByRating(ratingToSearch.toDouble())
    if (searchResults.isEmpty()) {
        println("No movies for this rating stored")
    } else {
        println(searchResults)
    }
}

fun searchMovieByUserName() {
    val userToSearch = readNextLine("Enter the name of user to search by: ")
    val searchResults = movieAPI.searchReviewByUser(userToSearch)
    if (searchResults.isEmpty()) {
        println("No review of this user is stored")
    } else {
        println(searchResults)
    }
}





private fun askUserToChooseActiveMovie(): Movie? {
    listActiveMovies()
    if (movieAPI.numberOfActiveMovies() > 0) {
        val movie = movieAPI.findMovie(readNextInt("\nEnter the id of the movie: "))
        if (movie != null) {
            if (movie.isMovieArchived) {
                println("Movie is NOT Active, it is Archived")
            } else {
                return movie
            }
        } else {
            println("Movie id is not valid")
        }
    }
    return null
}

private fun askUserToChooseReview(movie: Movie): Review? {
    if (movie.numberOfRatings() > 0) {
        print(movie.listRatings())
        return movie.findOne(readNextInt("\nEnter the id of the review: "))
    }
    else{
        println ("No items for chosen note")
        return null
    }
}

fun save() {
    try {
        movieAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        movieAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}

fun exitApp(){
    println("Exiting... Bye")
    System.exit(0)
}

fun dummyData() {
   movieAPI.add(Movie( movieName= "neram", movieGenre = "thriller", directorName = "alphonse", stars = "nivin pauly,nazriya"))
    movieAPI.add(Movie( movieName= "premam", movieGenre = "entertainment", directorName = "alphonse", stars = "nivin pauly"))
    movieAPI.add(Movie( movieName= "varathan", movieGenre = "thriller", directorName = "amal neerad", stars = "fahad fasil"))
}
fun listAllMovies() = println(movieAPI.listAllMovies())
fun listActiveMovies() = println(movieAPI.listActiveMovies())

fun listArchivedMovies() = println(movieAPI.listArchivedMovies())
fun listTopFiveMovies() = println(movieAPI.listTopFiveRatedMovies())

fun listTopFiveFavoriteMovies() = println(movieAPI.listTopFiveMoviesByFavorites())