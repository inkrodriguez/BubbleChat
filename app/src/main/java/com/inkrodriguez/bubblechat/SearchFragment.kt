package com.inkrodriguez.bubblechat

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.google.firebase.firestore.FirebaseFirestore

class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_search, container, false)

        val db = FirebaseFirestore.getInstance().collection("users")

        val editSearch = view.findViewById<EditText>(R.id.editSearch)
        val textView = view.findViewById<TextView>(R.id.textView3)

        editSearch.addTextChangedListener(object: TextWatcher {

            override fun afterTextChanged(text: Editable?) {

                if(editSearch.text.isEmpty()){
                    textView.setText("")
                    return
                }
                val query = db.whereGreaterThanOrEqualTo("nome", text.toString())
                    .whereLessThanOrEqualTo("nome", text.toString() + "\uf8ff")

                // Realiza a consulta no Firestore
                query.get().addOnSuccessListener { querySnapshot ->
                    var resultText = ""
                    // Processa os resultados da consulta
                    querySnapshot.documents.forEach {
                        resultText += it.getString("nome")
                    }

                    textView.text = resultText

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