import utils.ScannerInput.readNextInt

fun main(args: Array<String>) {
    println("Hello World!")

    // Try adding program arguments via Run/Debug configuration.
    // Learn more about running applications: https://www.jetbrains.com/help/idea/running-applications.html.
    println("Program arguments: ${args.joinToString()}")
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

}

fun listMovies() {

}

fun updateMovie() {

}

fun deleteMovie() {

}

fun archiveMovie() {

}