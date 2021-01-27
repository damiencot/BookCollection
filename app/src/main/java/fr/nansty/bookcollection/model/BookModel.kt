package fr.nansty.bookcollection.model

class BookModel(
        val id: String = "book0",
        val name: String = "Coding",
        val author: String = "Jean Paul",
        val imageUrl: String = "https://user-images.githubusercontent.com/8820662/106026307-f3ce2b00-60c9-11eb-9f38-d02073cabb9b.jpg",
        val difficulty: String = "low",
        val genres: String = "Romance",
        var liked: Boolean = false
)