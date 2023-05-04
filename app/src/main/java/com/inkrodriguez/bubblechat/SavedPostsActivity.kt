package com.inkrodriguez.bubblechat

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.Adapters.AdapterFeed
import com.inkrodriguez.bubblechat.Adapters.AdapterSavedPosts
import com.inkrodriguez.bubblechat.data.Publication
import com.inkrodriguez.bubblechat.databinding.ActivitySavedPostsBinding

class SavedPostsActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySavedPostsBinding
    private var db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySavedPostsBinding.inflate(layoutInflater)
        setContentView(binding.root)

            var recyclerview = binding.recyclerViewSavedPosts
            var fragmentManager = supportFragmentManager

            readSavedPosts(recyclerview, fragmentManager)

    }

    private fun readSavedPosts(recyclerView: RecyclerView, fragmentManager: FragmentManager){

        db.collection("savedposts").addSnapshotListener { value, error ->

            val listPublications: MutableList<Publication>  = mutableListOf()

            if (value != null) {
                value.documents.forEach {
                    listPublications.add(
                        Publication(
                            it.get("username").toString(),
                            it.get("url").toString(),
                            it.get("location").toString(),
                            it.get("title").toString()
                        )
                    )
                }
            }

            val adapter = AdapterSavedPosts(listPublications, fragmentManager, this)
            recyclerView.adapter = adapter

        }
    }

}