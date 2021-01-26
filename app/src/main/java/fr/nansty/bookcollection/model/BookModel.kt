package fr.nansty.bookcollection.model

class BookModel(
        val id: String = "book0",
        val name: String = "Coding",
        val author: String = "Jean Paul",
        val imageUrl: String = "",
        val difficulty: String = "low",
        val genres: String = "Romance",
        var liked: Boolean = false
)