package com.inkrodriguez.bubblechat

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.getField
import com.google.firebase.ktx.Firebase


class SettingsFragment : Fragment() {

    private var connect = Firebase.firestore
    private var db = connect

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_settings, container, false)

        val sharedPref: SharedPreferences? = context?.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
        val valorSalvo = sharedPref?.getString("USERNAME", "NADA ENCONTRADO")

        var editFullname = view.findViewById<TextView>(R.id.editSettingsFullname)
        var editCellphone = view.findViewById<TextView>(R.id.editSettingsCellphone)
        var editUsername = view.findViewById<TextView>(R.id.editSettingsUsername)
        var editPassword = view.findViewById<TextView>(R.id.editSettingsPassword)
        var tvFriendsSize = view.findViewById<TextView>(R.id.tvFriendsSize)

        db.collection("users").document("$valorSalvo").addSnapshotListener { value, error ->
            if(error != null){
                Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
            } else {

                editFullname.text = value?.get("fullname").toString()
                editCellphone.text = value?.get("cellphone").toString()
                editUsername.text = value?.get("username").toString()
                editPassword.text = value?.get("password").toString()

                val list: MutableList<Any?> = mutableListOf(value?.get("friends"))
                val friendsList = list.firstOrNull() as? List<*>
                val friendsListSize = friendsList?.size ?: 0
                tvFriendsSize.text = "$friendsListSize"

            }
        }

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }
}