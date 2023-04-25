package com.inkrodriguez.bubblechat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.inkrodriguez.bubblechat.Adapters.AdapterUsers
import com.inkrodriguez.bubblechat.data.User

class SearchFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_search, container, false)

        val db = FirebaseFirestore.getInstance().collection("users")

        val recyclerViewSearchUsers = view.findViewById<RecyclerView>(R.id.recyclerViewSearchUsers)
        val editSearch = view.findViewById<EditText>(R.id.editSearch)
        var listUsers: MutableList<User> = mutableListOf()

        editSearch.addTextChangedListener(object: TextWatcher {

            override fun afterTextChanged(text: Editable?) {

                val query = db.whereGreaterThanOrEqualTo("nome", text.toString())
                    .whereLessThanOrEqualTo("nome", text.toString() + "\uf8ff")

                // Realiza a consulta no Firestore
                query.get().addOnSuccessListener { querySnapshot ->
                    //var resultText = ""
                    var resultText: MutableList<User> = mutableListOf()
                    // Processa os resultados da consulta
                    querySnapshot.documents.forEach {
                        listUsers.add(
                            User(
                                nome = it.getString("nome").toString(),
                                username = it.getString("username").toString()
                            )
                                    )
                        resultText += User(it.getString("nome").toString())
                    }

                    var adapter = AdapterUsers(resultText)
                    recyclerViewSearchUsers.adapter = adapter

                    if(editSearch.text.isEmpty()){
                        resultText.clear()
                        adapter.notifyDataSetChanged()
                    }

                }

            }

            override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })


        return view
    }

}