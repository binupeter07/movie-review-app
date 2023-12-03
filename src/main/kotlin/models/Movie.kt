package models

import utils.Utilities

/**
 * Movie Model class represent the information about a movie with the primary constructors of various properties including Movie id, Movie name, Movie genre, Director name , Stars.
 * It also has a mutable set of Review model
 * The functions related to reviews such as addRating, Update rating, Delete rating are written in this model.
 */
data class Movie(
    var movieId: Int = 0,
    var movieName: String,
    var movieGenre: String,
    var directorName: String,
    var stars: String,
    var isMovieArchived: Boolean = false,
    var reviews: MutableSet<Review> = mutableSetOf(),
    var averageRating: Double = 0.0
) {

    private var lastRatingId = 0
    private fun getRatingId() = lastRatingId++

    fun addRating(review: Review): Boolean {
        review.ratingId = getRatingId()
        return reviews.add(review)
    }

    fun update(id: Int, option: Int, updateField: Any): Boolean {
        val foundReview = findOne(id)

        if (foundReview != null) {
            when (option) {
                1 -> foundReview.name = updateField.toString()
                2 -> foundReview.rating = updateField.toString().toInt()
                3 -> foundReview.reviewText = updateField.toString()
            }

            return true
        }
        return false
    }

    fun deleteReview(id: Int): Boolean {
        return reviews.removeIf { review -> review.ratingId == id }
    }

    fun listRatings() =
        if (reviews.isEmpty()) {
            "\tNO RATINGS ADDED"
        } else {
            Utilities.formatSetString(reviews)
        }

    fun updateAverageRating(): Double {
        val average = reviews.map { review -> review.rating.toDouble() }.average()
        return average
    }

    fun searchMoviesByUserName(name: String): List<Review> {
        return reviews.filter { review -> review.name.equals(name, ignoreCase = true) }
    }

    fun findOne(id: Int): Review? {
        return reviews.find { review -> review.ratingId == id }
    }

    fun numberOfRatings() = reviews.size

    fun numberOfFavorites() = reviews.count { review -> review.isFavorite }

    override fun toString(): String {
        val archived = if (isMovieArchived) 'Y' else 'N'
        return "$movieId: Movie Name($movieName), Movie Genre($movieGenre), Movie Director($directorName), Movie Actors($stars),Average Rating($averageRating) Archived($archived) \n${listRatings()}"
    }
}
