package models

import utils.Utilities

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

    fun update(id: Int, option: Int, updateField:Any): Boolean {
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

    fun listRatings() =
        if (ratings.isEmpty()) "\tNO RATINGS ADDED"
        else Utilities.formatSetString(ratings)


    fun findOne(id: Int): Review? {
        return ratings.find { review -> review.ratingId == id }
    }
    fun numberOfRatings() = ratings.size
}