package fr.nansty.bookcollection.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import androidx.fragment.app.Fragment
import fr.nansty.bookcollection.MainActivity
import fr.nansty.bookcollection.R
import fr.nansty.bookcollection.Repository.BookRepository
import fr.nansty.bookcollection.Repository.BookRepository.Singleton.downloadUri
import fr.nansty.bookcollection.model.BookModel
import java.util.*

class AddBookFragment(private val context: MainActivity) : Fragment(){


    private var uploadedImage: ImageView? = null
    private var file: Uri? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add_book, container, false)

        //recuperer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        //recupere le bouton pour charge l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        //lorsqu'on clique dessus ça ouvre les images du telephone
        pickupImageButton.setOnClickListener{ pickupImage() }

        //recuperer le bouton confirmer
        val addButton = view.findViewById<Button>(R.id.add_button)
        addButton.setOnClickListener { sendForm(view) }

        return view
    }

    private fun sendForm(view: View) {

        //heberger sur le bucket (firebase)
        val repo = BookRepository()
        repo.uploadImage(file!!){
            val bookName = view.findViewById<EditText>(R.id.name_input).text.toString()
            val bookAuthor = view.findViewById<EditText>(R.id.author_input).text.toString()
            val bookGenres = view.findViewById<Spinner>(R.id.genres_spinner).selectedItem.toString()
            val bookDifficulty = view.findViewById<Spinner>(R.id.difficulty_spinner).selectedItem.toString()
            val downloadImageUrl = downloadUri

            //creer un nouvel objet BookModel
            val book = BookModel(
                UUID.randomUUID().toString(),
                bookName,
                bookAuthor,
                downloadImageUrl.toString(),
                bookGenres,
                bookDifficulty
            )
            //envoye en bdd
            repo.insertBook(book)
        }
    }

    private fun pickupImage() {
        val intent = Intent()
        intent.type = "image/"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture", ), 79)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 79 && resultCode == Activity.RESULT_OK){
            //verifier si les données sont nulles
            if (data == null || data.data == null) return

            //recuperer l'image selectionner
            file = data.data

            //mettre à jour l'apercu de l'image
            uploadedImage?.setImageURI(file)

        }
    }
}