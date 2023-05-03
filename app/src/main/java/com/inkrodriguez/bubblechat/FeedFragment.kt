package com.inkrodriguez.bubblechat

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.Adapters.AdapterFeed
import com.inkrodriguez.bubblechat.data.Publication

class FeedFragment : Fragment() {

    private var connect = Firebase.firestore
    private var db = connect

    companion object {
        fun newInstance() = FeedFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_feed, container, false)

        val btnMessages = view.findViewById<ImageView>(R.id.btnMessagesFeed)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewFeedPublication)
        val fragmentManager = requireFragmentManager()

        readDataPublications(recyclerView, fragmentManager)

        btnMessages.setOnClickListener {
            val intent = Intent(this.context, ChatActivity::class.java)
            startActivity(intent)
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun readDataPublications(recyclerView: RecyclerView, fragmentManager: FragmentManager){

        db.collection("publications").addSnapshotListener { value, error ->

            val listPublications: MutableList<Publication>  = mutableListOf()

            if (value != null) {
                value.documents.forEach {
                    listPublications.add(
                        Publication(
                            it.get("username").toString(),
                            it.get("url").toString(),
                            it.get("location").toString(),
                            it.get("title").toString()
                        ))
                }
            }

            val adapter = AdapterFeed(listPublications, fragmentManager, this.requireContext())
            recyclerView.adapter = adapter

        }
    }

}