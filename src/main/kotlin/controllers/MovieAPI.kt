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

    fun listAllMovies() =
        if (movies.isEmpty()) "No movies stored"
        else Utilities.formatListString(movies)


    fun numberOfMovies() = movies.size

}

