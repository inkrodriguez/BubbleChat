package com.inkrodriguez.bubblechat.data

import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.inkrodriguez.bubblechat.R
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*


class AdapterMessage(private val messages: List<Chat>, private val extras: Bundle?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_ME = 1
        const val VIEW_TYPE_OTHER = 2
    }

    inner class MeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.tvChatRemetente)
        private val timestampTextView: TextView = itemView.findViewById(R.id.tvHour)
        private val remetente: TextView = itemView.findViewById(R.id.tvRemetente)

        fun bind(message: Chat) {
            var date = message.date
            var hour = date.split(" ")

            timestampTextView.text = hour[3]
            messageTextView.text = message.message
            remetente.text = message.remetente
        }
    }

    inner class OtherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.tvChatRemetente)
        private val timestampTextView: TextView = itemView.findViewById(R.id.tvHour)
        private val remetente: TextView = itemView.findViewById(R.id.tvRemetente)

        fun bind(message: Chat) {
            var date = message.date
            var hour = date.split(" ")

            timestampTextView.text = hour[3]
            messageTextView.text = message.message
            remetente.text = message.remetente
        }
    }

    override fun getItemViewType(position: Int): Int {
        val valor = extras?.getString("valorSalvo")

        return if (messages[position].remetente != "$valor") {
            VIEW_TYPE_OTHER
        } else {
            VIEW_TYPE_ME
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_ME) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_right, parent, false)
            MeViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
            OtherViewHolder(view)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder.itemViewType == VIEW_TYPE_ME) {
            (holder as MeViewHolder).bind(message)
        } else {
            (holder as OtherViewHolder).bind(message)
        }

    }

    override fun getItemCount(): Int {
        return messages.size
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun receiveDateAndFormat(): String {
    val zoneId = ZoneId.of("UTC-3")
    val dateTime = LocalDateTime.now(zoneId)
    val formatter = DateTimeFormatter.ofPattern("dd/MMMM/yyyy - HH:mm:ss +")
    val formattedDateTime = dateTime.format(formatter)
    return formattedDateTime.replace("/", " de ").replace("-", "às").replace("+", "UTC-3")
}
