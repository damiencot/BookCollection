package fr.nansty.bookcollection.Repository

import android.app.PendingIntent.getActivity
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import fr.nansty.bookcollection.Repository.BookRepository.Singleton.bookList
import fr.nansty.bookcollection.Repository.BookRepository.Singleton.databaseRef
import fr.nansty.bookcollection.Repository.BookRepository.Singleton.downloadUri
import fr.nansty.bookcollection.Repository.BookRepository.Singleton.storageReference
import fr.nansty.bookcollection.fragments.HomeFragment
import fr.nansty.bookcollection.model.BookModel
import java.net.URI
import java.util.*
import javax.security.auth.callback.Callback

class BookRepository {

    object Singleton{

        //donner le lien du bucket
        private val BUCKET_URL: String = "gs://bookcollection-e23b7.appspot.com"

        //Se connecter a notre espace de stockage
        val storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(BUCKET_URL)

        //Se connecter à la réference "books"
        val databaseRef = FirebaseDatabase.getInstance().getReference("books")

        //créer une liste qui va contenir nos livres
        val bookList = arrayListOf<BookModel>()

        // contenir le lien de l'image courante
        var downloadUri:Uri? = null
    }

    fun updateData(callback: () -> Unit){
        // absorber les données depuis la databaseRef -> liste de plantes
        databaseRef.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                //retirer les anciennes pour vraiment mettre à jour
                bookList.clear()
                //recolter la liste
                for (ds in snapshot.children){
                    //construire un nouvelle objet livre
                    val book = ds.getValue(BookModel::class.java)

                    //verifier que la plante n'est pas null
                    if(book != null){
                        //ajouter le livre à notre liste
                        bookList.add(book)
                    }
                }

                //actionner le callback
                callback()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    //créer une fonction pour envoyer des fichiers sur le storage
    fun uploadImage(file: Uri, callback: () -> Unit){
        //verifier que ce fichier n'est pas null
        if (file != null){
            //identifiant au hasard format texte + format
            val fileName = UUID.randomUUID().toString() + ".jpg"
            //a quel endroit de la bdd
            val ref = storageReference.child(fileName)
            //envoyer le fichier dans une tache d'envoie
            val uploadTask = ref.putFile(file)


            //demarrer la tache d'envoie
            uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->

                //si il y a eu un probleme lors de l'envoie du fichier
                if (!task.isSuccessful){
                    task.exception?.let { throw it }
                }
                return@Continuation ref.downloadUrl
            }).addOnCompleteListener { task ->
                //verifier si tout a bien fonctionné
                if (task.isSuccessful){
                    //recuperer l'image
                    downloadUri = task.result
                    callback()
                }
            }
        }
    }

    //mettre a jour un objet livre en bdd
    fun updateBook(book: BookModel) {
        databaseRef.child(book.id).setValue(book)
    }

    //inserer un objet livre en bdd
    fun insertBook(book: BookModel) {
        databaseRef.child(book.id).setValue(book).addOnCompleteListener {
            if (it.isSuccessful){

            }
        }

    }

    fun deleteBook(book: BookModel){
        databaseRef.child(book.id).removeValue()
    }

}