package models

class Movie (var movieId: Int =0,
             var movieName: String,
             var movieGenre: String,
             var directorName: String,
             var stars: String,
             var isMovieArchived: Boolean = false,
             var ratings : MutableSet<Review> = mutableSetOf()
) {
}