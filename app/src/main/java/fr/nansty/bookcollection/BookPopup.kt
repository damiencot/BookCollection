package fr.nansty.bookcollection

import android.app.Dialog
import android.media.Image
import android.net.Uri
import android.os.Bundle
import android.view.Window
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import fr.nansty.bookcollection.Repository.BookRepository
import fr.nansty.bookcollection.adapter.BookAdapter
import fr.nansty.bookcollection.model.BookModel

class BookPopup(private val adapter: BookAdapter, private val currentBook: BookModel)
    : Dialog(adapter.context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //pas de titre sur la fenetre popup
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_book_details)
        setupComponents()
        setupCloseButton()
        setupDeleteButton()
        setupLikeButton()
    }

    private fun updateLike(button: ImageView){
        if (currentBook.liked){
            button.setImageResource(R.drawable.ic_like)
        }else{
            button.setImageResource(R.drawable.ic_unlike)
        }
    }

    private fun setupLikeButton() {
        val likeButton = findViewById<ImageView>(R.id.like_button)

        updateLike(likeButton)

        //interaction
        likeButton.setOnClickListener {
            currentBook.liked = !currentBook.liked
            //mettre a jour le livre avec les nouvelles données
            val repo = BookRepository()
            repo.updateBook(currentBook)
            updateLike(likeButton)

        }
    }

    private fun setupDeleteButton() {
        findViewById<ImageView>(R.id.delete_button).setOnClickListener {
            //supprimer le livre de la bdd
            val repo = BookRepository()
            repo.deleteBook(currentBook)
            dismiss()
        }
    }

    private fun setupCloseButton() {
        findViewById<ImageView>(R.id.close_button).setOnClickListener {
            //fermer la fenetre popup
            dismiss()
        }
    }

    private fun setupComponents() {
        //actualiser l'image du livre
        val bookImage = findViewById<ImageView>(R.id.image_item)
        Glide.with(adapter.context).load(Uri.parse(currentBook.imageUrl)).into(bookImage)

        //actualiser le nom du livre
        findViewById<TextView>(R.id.popup_book_name).text = currentBook.name

        //description
        findViewById<TextView>(R.id.popup_book_author_subtitle).text = currentBook.author

        //actualiser le genres
        findViewById<TextView>(R.id.popup_book_genres_subtitle).text = currentBook.genres

        //actualiser la difficulter
        findViewById<TextView>(R.id.popup_book_difficulty_subtitle).text = currentBook.difficulty

    }
}