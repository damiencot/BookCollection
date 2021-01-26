package fr.nansty.bookcollection.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.nansty.bookcollection.R
import fr.nansty.bookcollection.adapter.BookAdapter
import fr.nansty.bookcollection.adapter.BookItemDecoration

class HomeFragment : Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //recuperer le recyclerview
        val horizontalRecyclerView = view.findViewById<RecyclerView>(R.id.horizontal_recyclerView)
        horizontalRecyclerView.adapter = BookAdapter(R.layout.item_horizontal_book)

        //recupere le second recyclerView
        val verticalRecyclerView = view.findViewById<RecyclerView>(R.id.vertical_recyclerView)
        verticalRecyclerView.adapter = BookAdapter(R.layout.item_vertical_book)
        verticalRecyclerView.addItemDecoration(BookItemDecoration( ))

        return view
    }
}