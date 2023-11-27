package models

class Movie (var movieId: Int =0,
             var movieName: String,
             var movieGenre: String,
             var directorName: String,
             var stars: String,
             var isMovieArchived: Boolean = false,
             var ratings : MutableSet<Review> = mutableSetOf()
) {

    private var lastRatingId = 0
    private fun getRatingId() = lastRatingId++

    fun addRating(review: Review): Boolean {
        review.ratingId = getRatingId()
        return ratings.add(review)
    }
}