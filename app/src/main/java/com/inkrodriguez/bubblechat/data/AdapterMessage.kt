package com.inkrodriguez.bubblechat.data

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inkrodriguez.bubblechat.R


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
