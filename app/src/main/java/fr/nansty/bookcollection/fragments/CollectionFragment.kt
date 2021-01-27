package fr.nansty.bookcollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.nansty.bookcollection.MainActivity
import fr.nansty.bookcollection.R
import fr.nansty.bookcollection.Repository.BookRepository.Singleton.bookList
import fr.nansty.bookcollection.adapter.BookAdapter
import fr.nansty.bookcollection.adapter.BookItemDecoration

class CollectionFragment(private val context: MainActivity) : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_collection, container, false)

        //recuperer ma recyclerView
        val collectionRecyclerView = view.findViewById<RecyclerView>(R.id.collection_recycler_list)
        //affiche les livres avec un filtre
        collectionRecyclerView.adapter = BookAdapter(context, bookList.filter { it.liked }, R.layout.item_vertical_book)
        collectionRecyclerView.layoutManager = LinearLayoutManager(context)
        collectionRecyclerView.addItemDecoration(BookItemDecoration())

        return view
    }

}