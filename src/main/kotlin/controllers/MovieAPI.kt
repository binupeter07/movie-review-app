package controllers

import models.Movie
import persistence.Serializer
import utils.Utilities.formatListString
import java.util.ArrayList

/**
 * The MovieAPI controller class in the provided code serves as a central component for managing a collection of movies.
 * It interfaces with a persistence layer through a specified serializer.
 * CRUD (Create, Read, Update, Delete) operations for movies are managed in this controller.
 */

class MovieAPI(serializerType: Serializer) {

    private var serializer: Serializer = serializerType
    private var movies = ArrayList<Movie>()
    private var lastId = 0
    private fun getId() = lastId++

    //  CRUD METHODS FOR MOVIE ArrayList
    fun add(movie: Movie): Boolean {
        movie.movieId = getId()
        return movies.add(movie)
    }

    fun update(id: Int, option: Int, updateField: String): Boolean {
        val foundMovie = findMovie(id)
        if ((foundMovie != null)) {
            when (option) {
                1 -> foundMovie.movieName = updateField
                2 -> foundMovie.movieGenre = updateField
                3 -> foundMovie.directorName = updateField
                4 -> foundMovie.stars = updateField
            }

            return true
        }
        return false
    }

    fun delete(indexToDelete: Int): Movie? {
        return if (isValidListIndex(indexToDelete, movies)) {
            movies.removeAt(indexToDelete)
        } else {
            null
        }
    }

    fun archiveMovie(id: Int): Boolean {
        val foundMovie = findMovie(id)
        if ((foundMovie != null) && (!foundMovie.isMovieArchived)) {
            foundMovie.isMovieArchived = true
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR MOVIE ArrayList
    // ----------------------------------------------
    fun listAllMovies() =
        if (movies.isEmpty()) {
            "No movies stored"
        } else {
            formatListString(movies)
        }

    fun listActiveMovies() =
        if (numberOfActiveMovies() == 0) {
            "No active movies stored"
        } else {
            formatListString(movies.filter { movie -> !movie.isMovieArchived })
        }

    fun listArchivedMovies() =
        if (numberOfArchivedMovies() == 0) {
            "No archived movies stored"
        } else {
            formatListString(movies.filter { movie -> movie.isMovieArchived })
        }

    fun listTopFiveRatedMovies() =
        if (numberOfActiveMovies() == 0) {
            "No active movies stored"
        } else {
            formatListString(movies.sortedByDescending { movie -> movie.averageRating }.take(5))
        }

    fun listTopFiveMoviesByFavorites(): String {
        return if (numberOfActiveMovies() == 0) {
            "No active movies stored"
        } else {
            formatListString(movies.sortedByDescending { movie -> movie.numberOfFavorites() }.take(5))
        }
    }

    // ----------------------------------------------
    //  COUNTING METHODS FOR MOVIE ArrayList
    // ----------------------------------------------
    fun numberOfMovies() = movies.size
    fun numberOfActiveMovies(): Int = movies.count { movie: Movie -> !movie.isMovieArchived }

    fun numberOfArchivedMovies(): Int = movies.count { movie: Movie -> movie.isMovieArchived }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findMovie(movieId: Int) = movies.find { movie -> movie.movieId == movieId }

    fun searchByGenre(genre: String) =
        formatListString(
            movies.filter { movie -> movie.movieGenre.contains(genre, ignoreCase = true) }
        )

    fun searchByActor(actor: String) =
        formatListString(
            movies.filter { movie -> movie.stars.contains(actor, ignoreCase = true) }
        )

    fun searchMoviesByRating(rating: Double): String {
        val searchResults = movies.filter { movie -> movie.averageRating == rating }
        return formatListString(searchResults)
    }

    fun searchReviewByUser(username: String): List<Movie> {
        return movies.filter { movie ->
            movie.searchMoviesByUserName(username).isNotEmpty()
        }
    }

    fun isValidListIndex(index: Int, list: List<Any>): Boolean {
        return (index >= 0 && index < list.size)
    }

    @Throws(Exception::class)
    fun load() {
        movies = serializer.read() as ArrayList<Movie>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(movies)
    }
}
