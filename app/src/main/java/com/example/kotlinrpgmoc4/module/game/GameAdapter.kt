package com.example.kotlinrpgmoc4.module.game

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrpgmoc4.R
import com.example.kotlinrpgmoc4.data.model.Message
import com.example.kotlinrpgmoc4.data.model.UserType
import com.example.kotlinrpgmoc4.misc.inflate
import com.example.kotlinrpgmoc4.misc.DateUtils


class GameAdapter : RecyclerView.Adapter<MessageViewHolder>() {

    private var messages: MutableList<Message> = mutableListOf()

    fun addMessage(message: Message) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        return when (viewType) {
            QUESTION_ITEM -> GameMasterMessageViewHolder(parent.inflate(R.layout.item_game_master_message))
            else -> PlayerMessageViewHolder(parent.inflate(R.layout.item_player_message))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getUserType(position)) {
            UserType.GAME_MASTER -> QUESTION_ITEM
            UserType.PLAYER -> ANSWER_ITEM
        }
    }

    private fun getUserType(position: Int) = messages[position].user

    override fun getItemCount(): Int = messages.size

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        holder.bind(messages[position])
    }

    companion object {
        private const val QUESTION_ITEM = 0
        private const val ANSWER_ITEM = 1
    }

    inner class GameMasterMessageViewHolder(view: View) : MessageViewHolder(view) {
        private val messageTxv: TextView = view.findViewById(R.id.game_master_message_txv)
        private val timeTxv: TextView = view.findViewById(R.id.game_master_message_date_txv)

        override fun bind(message: Message) {
            messageTxv.text = message.message
            timeTxv.text = DateUtils.calendarToStringTime(message.time)
        }
    }

    inner class PlayerMessageViewHolder(view: View) : MessageViewHolder(view) {
        private val messageTxv: TextView = view.findViewById(R.id.player_message_txv)
        private val timeTxv: TextView = view.findViewById(R.id.player_message_time_txv)

        override fun bind(message: Message) {
            messageTxv.text = message.message
            timeTxv.text = DateUtils.calendarToStringTime(message.time)
        }
    }

}

open class MessageViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    open fun bind(message: Message) {}
}