package com.inkrodriguez.bubblechat

import android.content.ContentValues
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
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
import com.inkrodriguez.bubblechat.data.*

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

        var recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)


        db.collection("users")
            .get()
            .addOnSuccessListener { result ->
                val users = result.documents.map { document ->
                    val nome = document.getString("nome")
                    User(nome = nome.toString())
                }

                val adapter = AdapterContatos(users)

                recyclerView.adapter = adapter


            }
            .addOnFailureListener { exception ->
                Log.d(ContentValues.TAG, "Error getting documents: ", exception)
            }

        return view

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ConversasViewModel::class.java)
        // TODO: Use the ViewModel
    }



}