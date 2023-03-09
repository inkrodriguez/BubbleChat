package com.inkrodriguez.bubblechat

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.data.Connect
import com.inkrodriguez.bubblechat.data.MyAdapter
import com.inkrodriguez.bubblechat.data.User

class Conversas : Fragment() {

    companion object {
        fun newInstance() = Conversas()
    }

    private lateinit var viewModel: ConversasViewModel
    private var connect = Firebase.firestore
    private var db = connect


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var view = inflater.inflate(R.layout.fragment_conversas, container, false)

        var lista3: MutableList<User> = mutableListOf()
        db = FirebaseFirestore.getInstance()
        db.collection("users").addSnapshotListener { value, error ->
                value?.documents?.forEach {

                    lista3.add(User(it.get("nome").toString()))

                    try {
                        var recyclerView = view?.findViewById<RecyclerView>(R.id.recyclerView)
                        var adapter = MyAdapter(lista3)
                        recyclerView?.adapter = adapter
                    }
                    catch (e: java.lang.Exception){

                    }
                }
            }

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConversasViewModel::class.java)
        // TODO: Use the ViewModel
    }

}