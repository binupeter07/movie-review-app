package utils

import models.Movie
import models.Review

object Utilities {

    // NOTE: JvmStatic annotation means that the methods are static i.e. we can call them over the class
    //      name; we don't have to create an object of Utilities to use them.

    @JvmStatic
    fun formatListString(moviesToFormat: List<Movie>): String =
        moviesToFormat
            .joinToString(separator = "\n") { movie ->  "$movie" }


    @JvmStatic
    fun formatSetString(ratingsToFormat: Set<Review>): String =
        ratingsToFormat
            .joinToString(separator = "\n") { review ->  "\t$review" }

}
