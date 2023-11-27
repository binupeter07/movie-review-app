package models

data class Review(var ratingId: Int =0,
             var name: String,
             var rating: Int,
             var reviewText: String) {
    override fun toString(): String {

        return """
            Movie Review:
                           ID:            $ratingId
                           Reviewer Name: $name
                           Rating:        $rating
                           Review Text:   $reviewText
        """.trimIndent()

    }
}