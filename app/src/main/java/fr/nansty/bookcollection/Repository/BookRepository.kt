package fr.nansty.bookcollection.Repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import fr.nansty.bookcollection.Repository.BookRepository.Singleton.bookList
import fr.nansty.bookcollection.Repository.BookRepository.Singleton.databaseRef
import fr.nansty.bookcollection.model.BookModel
import javax.security.auth.callback.Callback

class BookRepository {

    object Singleton{
        //Se connecter à la réference "books"
        val databaseRef = FirebaseDatabase.getInstance().getReference("books")

        //créer une liste qui va contenir nos livres
        val bookList = arrayListOf<BookModel>()
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

    //mettre a jour un objet livre en bdd
    fun updateBook(book: BookModel) {
        databaseRef.child(book.id).setValue(book)
    }

    fun deleteBook(book: BookModel){
        databaseRef.child(book.id).removeValue()
    }

}