package controllers

import models.Movie
import utils.Utilities
import java.util.ArrayList

class MovieAPI {

    private var movies = ArrayList< Movie>()
    private var lastId = 0
    private fun getId() = lastId++

    //  CRUD METHODS FOR MOVIE ArrayList
    fun add(movie: Movie): Boolean {
        movie.movieId = getId()
        return movies.add(movie)
    }

    fun update(id: Int, option: Int, updateField: String ):Boolean{
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

    fun delete(id: Int) = movies.removeIf { movie -> movie.movieId == id }

    fun archiveMovie(id: Int): Boolean {
        val foundMovie = findMovie(id)
        if (( foundMovie != null) && (!foundMovie.isMovieArchived)) {
            foundMovie.isMovieArchived = true
            return true
        }
        return false
    }

    // ----------------------------------------------
    //  LISTING METHODS FOR MOVIE ArrayList
    // ----------------------------------------------
    fun listAllMovies() =
        if (movies.isEmpty()) "No movies stored"
        else Utilities.formatListString(movies)

    fun listActiveMovies() =
        if (numberOfActiveMovies() == 0) "No active movies stored"
        else Utilities.formatListString(movies.filter { movie -> !movie.isMovieArchived })

    // ----------------------------------------------
    //  COUNTING METHODS FOR MOVIE ArrayList
    // ----------------------------------------------
    fun numberOfMovies() = movies.size
    fun numberOfActiveMovies(): Int = movies.count { movie: Movie -> !movie.isMovieArchived }

    // ----------------------------------------------
    //  SEARCHING METHODS
    // ---------------------------------------------
    fun findMovie(movieId : Int) =  movies.find{ movie -> movie.movieId == movieId }

}



