package models

/**
 * Review Model class represent the information about the reviews related to a particular movie.
 * This model consist of primary constructors of various properties including Rating id, Name, rating, Review text.
 */
data class Review(var ratingId: Int =0,
             var name: String,
             var rating: Int,
             var reviewText: String,
             var isFavorite:Boolean = false
    ) {
    override fun toString(): String {

        return """
            Movie Review:
                           ID:            $ratingId
                           Reviewer Name: $name
                           Rating:        $rating
                           Review Text:   $reviewText
                           Favourite Movie:$isFavorite
        """.trimIndent()

    }
}