package com.inkrodriguez.bubblechat.Adapters

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.ExemploBottomSheetDialog
import com.inkrodriguez.bubblechat.R
import com.inkrodriguez.bubblechat.data.Publication

class AdapterFeed(
    private val publication: MutableList<Publication>,
    private val fragmentManager: FragmentManager,
    private val context: Context
): RecyclerView.Adapter<AdapterFeed.PublicationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_feed_publication, parent, false)
        return PublicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val item = holder.bind(publication[position])
        val username = publication[position].username
        val url = publication[position].url

        holder.itemView.setOnClickListener {
            val intent = Intent()
            intent.putExtra("url", url)

            val bottomSheetDialog = ExemploBottomSheetDialog()
            bottomSheetDialog.setIntent(intent)
            bottomSheetDialog.show(fragmentManager, "ExemploBottomSheetDialog")
        }
    }

    override fun getItemCount(): Int {
        return publication.size
    }

    //SERÁ O LAYOUT DO ITEM, RECUPERA OS DADOS DO ITEMVIEW.
    class PublicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Publication) {
            with(itemView) {
                val image = findViewById<ImageView>(R.id.imageFeedPublication)
                val imagePerfil = findViewById<ImageView>(R.id.imagePerfilPublicationFeed)
                val tvUsername = findViewById<TextView>(R.id.tvUsernameFeedPublication)
                val link = data.url

                tvUsername.text = data.username
                Glide.with(context).load(link).into(image)

                val sharedPref: SharedPreferences? =
                    context?.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
                val sharedPrefencesValue = sharedPref?.getString("USERNAME", "NADA ENCONTRADO").toString()

                val db = Firebase.firestore
                val usersRef = db.collection("publications")
                val query = usersRef.whereEqualTo("username", sharedPrefencesValue)

                db.collection("users").document(data.username).addSnapshotListener { value, error ->
                    if (error != null) {
                        Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
                    } else {

                        val urlImagePerfil = value?.get("fotoperfil")
                        Glide.with(context).load(urlImagePerfil).into(imagePerfil)
                    }
                }
            }
        }
    }
}