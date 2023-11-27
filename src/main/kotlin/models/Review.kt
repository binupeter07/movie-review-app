package models

data class Review(var ratingId: Int =0,
             var name: String,
             var rating: Int,
             var reviewText: String) {
}