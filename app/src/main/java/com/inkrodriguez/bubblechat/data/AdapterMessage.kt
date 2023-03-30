package com.inkrodriguez.bubblechat.data

import android.app.ActionBar.LayoutParams
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.inkrodriguez.bubblechat.R


class AdapterMessage(private val messages: List<Chat>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        const val VIEW_TYPE_RODRIGO = 1
        const val VIEW_TYPE_SARA = 2
    }

    inner class RodrigoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.tvChatRemetente)
        private val timestampTextView: TextView = itemView.findViewById(R.id.tvHour)
        private val remetente: TextView = itemView.findViewById(R.id.tvRemetente)

        fun bind(message: Chat) {

            var splitHour = message.date.split(" ")
            var hourFormat = splitHour[6]

            timestampTextView.text = hourFormat.dropLast(3)
            messageTextView.text = message.message
            remetente.text = message.remetente
        }
    }

    inner class SaraViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val messageTextView: TextView = itemView.findViewById(R.id.tvChatRemetente)
        private val timestampTextView: TextView = itemView.findViewById(R.id.tvHour)
        private val remetente: TextView = itemView.findViewById(R.id.tvRemetente)

        fun bind(message: Chat) {
            var splitHour = message.date.split(" ")
            var hourFormat = splitHour[6]

            timestampTextView.text = hourFormat.dropLast(3)
            messageTextView.text = message.message
            remetente.text = message.remetente
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (messages[position].remetente == "Rodrigo") {
            VIEW_TYPE_RODRIGO
        } else {
            VIEW_TYPE_SARA
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_RODRIGO) {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
            RodrigoViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_right, parent, false)
            SaraViewHolder(view)
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = messages[position]
        if (holder.itemViewType == VIEW_TYPE_RODRIGO) {
            (holder as RodrigoViewHolder).bind(message)
        } else {
            (holder as SaraViewHolder).bind(message)
        }
    }

    override fun getItemCount(): Int {
        return messages.size
    }
}
