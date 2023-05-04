package com.inkrodriguez.bubblechat.Adapters

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.CommentsActivity
import com.inkrodriguez.bubblechat.R
import com.inkrodriguez.bubblechat.data.Like
import com.inkrodriguez.bubblechat.data.Publication

class AdapterFeed(
    private val publication: MutableList<Publication>,
    private val fragmentManager: FragmentManager,
    private val context: Context
): RecyclerView.Adapter<AdapterFeed.PublicationViewHolder>() {

    private var db = Firebase.firestore

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PublicationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_feed_publication, parent, false)
        return PublicationViewHolder(view)
    }

    override fun onBindViewHolder(holder: PublicationViewHolder, position: Int) {
        val item = holder.bind(publication[position])
        val username = publication[position].username
        val url = publication[position].url

//        holder.itemView.setOnClickListener {
//            val intent = Intent()
//            intent.putExtra("url", url)
//
//            val bottomSheetDialog = ExemploBottomSheetDialog()
//            bottomSheetDialog.setIntent(intent)
//            bottomSheetDialog.show(fragmentManager, "ExemploBottomSheetDialog")
//        }
    }

    override fun getItemCount(): Int {
        return publication.size
    }

    //SERÁ O LAYOUT DO ITEM, RECUPERA OS DADOS DO ITEMVIEW.
    class PublicationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Publication) {
            with(itemView) {

                val sharedPref: SharedPreferences? =
                    context?.getSharedPreferences("USERNAME", Context.MODE_PRIVATE)
                val sharedPreferencesValue =
                    sharedPref?.getString("USERNAME", "NADA ENCONTRADO").toString()

                val db = Firebase.firestore

                val image = findViewById<ImageView>(R.id.imageFeedPublication)
                val imagePerfil = findViewById<ImageView>(R.id.imagePerfilPublicationFeed)
                val tvUsername = findViewById<TextView>(R.id.tvUsernameFeedPublication)
                val btnSave = findViewById<ImageView>(R.id.imageViewSaveFeed)
                val btnLike = findViewById<ImageView>(R.id.imageViewLikeFeed)
                val tvLocation = findViewById<TextView>(R.id.tvLocationFeedPublication)
                val tvLike = findViewById<TextView>(R.id.tvLikesPerfilSheetDialog)
                val tvTitle = findViewById<TextView>(R.id.tvTitlePerfilSheetDialog)
                val tvSizeComments = findViewById<TextView>(R.id.tvSizeComments)
                val imageViewComments = findViewById<ImageView>(R.id.imageViewCommentsFeed)
                val link = data.url

                tvUsername.text = data.username
                tvLocation.text = data.location
                tvTitle.text = data.title
                Glide.with(context).load(link).into(image)


                imageViewComments.setOnClickListener {
                    val intent = Intent(context, CommentsActivity::class.java)
                        .putExtra("username", data.username)
                        .putExtra("url", data.url)
                    startActivity(context, intent, null)
                }

                val collectionSavedPosts = db.collection("savedposts")
                val querySavedPosts = collectionSavedPosts.whereEqualTo("url", data.url).whereEqualTo("username", sharedPreferencesValue)

                querySavedPosts.addSnapshotListener { value, error ->

                    //verifica no banco se está com existe ou não para deixar a imagem do botão pressionada.
                        if(value?.documents?.size == 1){
                            btnSave.setImageResource(R.drawable.ic_save_up)
                        }   else {
                            btnSave.setImageResource(R.drawable.ic_fav_bubble)
                        }


                    val docSize: Int = value?.documents?.size ?: 0

                    btnSave.setOnClickListener {

                        if(docSize == 1){
                            value?.documents?.forEach { document ->
                                var docId = document.id
                                collectionSavedPosts.document(docId).delete().addOnSuccessListener {
                                    Log.d(TAG, "Document deleted successfully!")
                                    btnSave.setImageResource(R.drawable.ic_fav_bubble)
                                }.addOnFailureListener { Log.d(TAG, "Error deleting document!") }
                            }
                        } else {
                            val savedPost =
                                Publication(username = sharedPreferencesValue, url = data.url)
                            collectionSavedPosts.add(savedPost).addOnSuccessListener {
                                Log.d(TAG, "Document successfully added!")
                                btnSave.setImageResource(R.drawable.ic_save_up)
                            }.addOnFailureListener {
                                Log.d(TAG, "Error adding document")
                            }

                        }
                    }


                }



                val collectionLikes = db.collection("likes")
                val queryLikes = collectionLikes.whereEqualTo("url", data.url).whereEqualTo("username", sharedPreferencesValue)

                    queryLikes.addSnapshotListener { it, error ->

                        //verifica no banco se está com true ou false para deixar a imagem do botão pressionada.
                        it?.documents?.forEach {
                            var result = it.get("like")
                            if(result == true){
                                btnLike.setImageResource(R.drawable.ic_like_up)
                            }else{
                                btnLike.setImageResource(R.drawable.ic_like_bubble)
                            }
                        }

                        //verifica no banco se está com true ou false e atualiza ou adiciona.
                        btnLike.setOnClickListener { _ ->
                            if(it?.documents?.size == 1) {
                                it?.documents?.forEach {
                                    var result = it.get("like")
                                    if(result == false){
                                        collectionLikes.document(it.id).update("like", true).addOnSuccessListener {
                                            btnLike.setImageResource(R.drawable.ic_like_up)
                                        }.addOnFailureListener { Toast.makeText(context, "Erro1", Toast.LENGTH_SHORT).show() }
                                    } else {
                                        collectionLikes.document(it.id).update("like", false).addOnSuccessListener {
                                            btnLike.setImageResource(R.drawable.ic_like_bubble)
                                        }.addOnFailureListener { Toast.makeText(context, "Erro2", Toast.LENGTH_SHORT).show() }
                                    }
                                }
                            } else {
                                var like = Like(username = sharedPreferencesValue, url = data.url, like = true)
                                collectionLikes.add(like).addOnSuccessListener {
                                    btnLike.setImageResource(R.drawable.ic_like_up)
                                }.addOnFailureListener { Toast.makeText(context, "Erro2", Toast.LENGTH_SHORT).show() }
                            }
                        }

                        //contagem de curtida nas fotos logo após o clique.
                        db.collection("likes").whereEqualTo("url", data.url).whereEqualTo("like", true)
                            .addSnapshotListener { value, error ->
                                tvLike.text = "${value?.documents?.size.toString()} likes"
                            }

                    }

                db.collection("users").document(data.username)
                    .addSnapshotListener { value, error ->
                        if (error != null) {
                            Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show()
                        } else {
                            val urlImagePerfil = value?.get("fotoperfil")
                            Glide.with(context).load(urlImagePerfil).into(imagePerfil)
                        }
                    }

                db.collection("comments").whereEqualTo("url", data.url)
                    .addSnapshotListener { value, error ->
                        if (value?.documents?.size != 0) {
                            tvSizeComments.text =
                                "${value?.documents?.size.toString()} comments"
                        } else {
                            tvSizeComments.text = "0 comments"
                        }
                    }

                //Contagem de curtidas nas fotos
                db.collection("likes").whereEqualTo("url", data.url).whereEqualTo("like", true)
                    .addSnapshotListener { value, error ->
                        tvLike.text = "${value?.documents?.size.toString()} likes"
                    }

            }
        }

        }

    }