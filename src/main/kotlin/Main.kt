import controllers.MovieAPI
import models.Movie
import utils.ScannerInput
import utils.ScannerInput.readNextInt

private val movieAPI = MovieAPI()

fun main() = runMenu()

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1 -> addMovie()
            2 -> listMovies()
            3 -> updateMovie()
            4 -> deleteMovie()
            5 -> archiveMovie()
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
         > | REPORT MENU FOR NOTES                             | 
         > |   10) Search for all movies (by movie genre)     |
         > |   11) .....                                       |
         > |   12) .....                                       |
         > |   13) .....                                       |
         > |   14) .....                                       |
         > -----------------------------------------------------  
         > | REPORT MENU FOR ITEMS                             |                                
         > |   15) Search for all ratings (by movie rating)  |
         > |   16) List TODO Items                             |
         > |   17) .....                                       |
         > |   18) .....                                       |
         > |   19) .....                                       |
         > -----------------------------------------------------  
         > |   0) Exit                                         |
         > -----------------------------------------------------  
         > ==>> """.trimMargin(">")
)

fun addMovie() {
    val movieName = ScannerInput.readNextLine("Enter the name of movie: ")
    val movieGenre = ScannerInput.readNextLine("Enter the genre")
    val movieDirector = ScannerInput.readNextLine("Enter director name: ")
    val movieStars = ScannerInput.readNextLine("Enter actors name : ")
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
            else -> println("Invalid option entered: $option")
        }
    } else {
        println("Option Invalid - No notes stored")
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

            val newValue = ScannerInput.readNextLine(userInput)

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
        val id = readNextInt("Enter the id of the note to delete: ")
        val noteToDelete = movieAPI.delete(id)
        if (noteToDelete) {
            println("Delete Successful!")
        } else {
            println("Delete NOT Successful")
        }
    }
}

fun archiveMovie() {

}

fun listAllMovies() = println(movieAPI.listAllMovies())
