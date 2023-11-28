package models

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