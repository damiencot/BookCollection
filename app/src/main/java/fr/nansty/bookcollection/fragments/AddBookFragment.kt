package fr.nansty.bookcollection.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import fr.nansty.bookcollection.MainActivity
import fr.nansty.bookcollection.R
import fr.nansty.bookcollection.Repository.BookRepository

class AddBookFragment(private val context: MainActivity) : Fragment(){


    private var uploadedImage: ImageView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_add_book, container, false)

        //recuperer uploadedImage pour lui associer son composant
        uploadedImage = view.findViewById(R.id.preview_image)

        //recupere le bouton pour charge l'image
        val pickupImageButton = view.findViewById<Button>(R.id.upload_button)

        //lorsqu'on clique dessus ça ouvre les images du telephone
        pickupImageButton.setOnClickListener{ pickupImage() }

        return view
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
            val selectedImage = data.data

            //mettre à jour l'apercu de l'image
            uploadedImage?.setImageURI(selectedImage)

            //heberger sur le bucket (firebase)
            val repo = BookRepository()
            repo.uploadImage(selectedImage!!)

        }
    }
}