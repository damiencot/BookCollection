package fr.nansty.bookcollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.nansty.bookcollection.MainActivity
import fr.nansty.bookcollection.R
import fr.nansty.bookcollection.Repository.BookRepository.Singleton.bookList
import fr.nansty.bookcollection.adapter.BookAdapter
import fr.nansty.bookcollection.adapter.BookItemDecoration
import fr.nansty.bookcollection.model.BookModel

class HomeFragment(private val context: MainActivity) : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //recuperer le recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recyclerView)
        horizontalRecyclerView.adapter = BookAdapter(context, bookList.filter { !it.liked }, R.layout.item_horizontal_book)

        //recupere le second recyclerView
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recyclerView)
        verticalRecyclerView.adapter = BookAdapter(context, bookList, R.layout.item_vertical_book)
        verticalRecyclerView.addItemDecoration(BookItemDecoration( ))

        return view
    }
}