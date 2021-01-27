package fr.nansty.bookcollection

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.nansty.bookcollection.Repository.BookRepository
import fr.nansty.bookcollection.fragments.AddBookFragment
import fr.nansty.bookcollection.fragments.CollectionFragment
import fr.nansty.bookcollection.fragments.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //charger notre repository
        val repo = BookRepository()

        // mettre a jour la liste de livre
        repo.updateData{
            //injecter le fragment dans notre boite (fragment_container)
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, HomeFragment(this))
            transaction.addToBackStack(null)
            transaction.commit()
        }


    }
}