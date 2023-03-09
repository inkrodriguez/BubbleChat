package com.inkrodriguez.bubblechat

import android.widget.Button
import androidx.lifecycle.ViewModel
import com.inkrodriguez.bubblechat.databinding.ActivityHomeBinding

class HomeViewModel : ViewModel() {
    var selectButton = 2
    lateinit var vmBinding: ActivityHomeBinding

    fun setImage(){

        when (selectButton) {
            1 -> {
                vmBinding.btnMessages.setBackgroundResource(R.drawable.ic_message_off)
                vmBinding.btnContatos.setBackgroundResource(R.drawable.ic_contatos_on)
                vmBinding.btnStatus.setBackgroundResource(R.drawable.ic_status_off)
            }
            2 -> {
                vmBinding.btnMessages.setBackgroundResource(R.drawable.ic_message_on)
                vmBinding.btnContatos.setBackgroundResource(R.drawable.ic_contatos_off)
                vmBinding.btnStatus.setBackgroundResource(R.drawable.ic_status_off)
            }
            3 -> {
                vmBinding.btnMessages.setBackgroundResource(R.drawable.ic_message_off)
                vmBinding.btnContatos.setBackgroundResource(R.drawable.ic_contatos_off)
                vmBinding.btnStatus.setBackgroundResource(R.drawable.ic_status_on)
            }
        }
    }
}