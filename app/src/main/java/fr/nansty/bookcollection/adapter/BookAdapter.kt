package fr.nansty.bookcollection.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import fr.nansty.bookcollection.BookPopup
import fr.nansty.bookcollection.MainActivity
import fr.nansty.bookcollection.R
import fr.nansty.bookcollection.Repository.BookRepository
import fr.nansty.bookcollection.model.BookModel

class BookAdapter(val context: MainActivity, private val bookList: List<BookModel>, private val layoutId: Int) : RecyclerView.Adapter<BookAdapter.ViewHolder>(){

    //boite pour ranger tout les composants à controler
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val bookPicture: ImageView = view.findViewById(R.id.image_item)
        val bookName: TextView? = view.findViewById(R.id.name_item)
        val bookAuthor: TextView? = view.findViewById(R.id.author_item)
        val likeIcon: ImageView = view.findViewById(R.id.like_icon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(layoutId, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        // recupere les informations du livre
        val currentBook = bookList[position]

        //recuperer le repository
        val repo = BookRepository()

        //utiliser glide pour recupere l'image a partir de son lien -> composant
        Glide.with(context).load(Uri.parse(currentBook.imageUrl)).into(holder.bookPicture)

        //mettre à jour le nom et la description du livre
        holder.bookName?.text = currentBook.name
        holder.bookAuthor?.text = currentBook.author

        //verifier si le livre a été liké
        if (currentBook.liked){
            holder.likeIcon.setImageResource(R.drawable.ic_like)
        }else{
            holder.likeIcon.setImageResource(R.drawable.ic_unlike)
        }

        //rajouter une interaction sur cette etoile
        holder.likeIcon.setOnClickListener {
            //inverser si le bouton est like ou non
            currentBook.liked = !currentBook.liked
            //mettre a jour l'objet livre
            repo.updateBook(currentBook)

        }

        //interaction lors du clique sur un livre
        holder.itemView.setOnClickListener {
            // afficher la popup
            BookPopup(this, currentBook).show()
        }
    }

    override fun getItemCount(): Int {
        return bookList.size
    }
}