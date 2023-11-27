package controllers

import models.Movie
import utils.Utilities
import java.util.ArrayList

class MovieAPI {

    private var movies = ArrayList< Movie>()
    private var lastId = 0
    private fun getId() = lastId++

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

        // if the note was not found, return false, indicating that the update was not successful
        return false
    }
    fun listAllMovies() =
        if (movies.isEmpty()) "No movies stored"
        else Utilities.formatListString(movies)


    fun numberOfMovies() = movies.size
    fun findMovie(movieId : Int) =  movies.find{ movie -> movie.movieId == movieId }

}



