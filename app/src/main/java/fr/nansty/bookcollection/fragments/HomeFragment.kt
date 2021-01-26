package fr.nansty.bookcollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.nansty.bookcollection.MainActivity
import fr.nansty.bookcollection.R
import fr.nansty.bookcollection.adapter.BookAdapter
import fr.nansty.bookcollection.adapter.BookItemDecoration
import fr.nansty.bookcollection.model.BookModel

class HomeFragment(private val context: MainActivity) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //créer une liste qui va stocker ces livres
        val bookList = arrayListOf<BookModel>()

        //enregistrer un premier livre dans notre liste (coding)
        bookList.add(BookModel(
                "Coding",
                "Mike T",
                "https://cdn.pixabay.com/photo/2016/03/31/20/51/book-1296045_960_720.png",
                false
        ))

        //enregistrer un deuxieme livre dans notre liste (stars)
        bookList.add(BookModel(
                "Stars",
                "Joseph P",
                "https://cdn.pixabay.com/photo/2014/09/08/05/06/book-438935_960_720.png",
                false
        ))

        //enregistrer un troisieme livre dans notre liste (stars)
        bookList.add(BookModel(
                "Seigneur",
                "Boby L",
                "https://cdn.pixabay.com/photo/2015/12/04/17/06/notebook-1076812_960_720.jpg",
                true
        ))

        //enregistrer un quatrieme livre dans notre liste (stars)
        bookList.add(BookModel(
                "La Rose",
                "K Garcia",
                "https://cdn.pixabay.com/photo/2014/04/03/11/52/book-312393_960_720.png",
                false
        ))


        //recuperer le recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recyclerView)
        horizontalRecyclerView.adapter = BookAdapter(context, bookList, R.layout.item_horizontal_book)

        //recupere le second recyclerView
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recyclerView)
        verticalRecyclerView.adapter = BookAdapter(context, bookList, R.layout.item_vertical_book)
        verticalRecyclerView.addItemDecoration(BookItemDecoration( ))

        return view
    }
}