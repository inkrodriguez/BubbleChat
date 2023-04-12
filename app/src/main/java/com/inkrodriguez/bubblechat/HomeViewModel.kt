package com.inkrodriguez.bubblechat

import android.widget.Button
import androidx.lifecycle.ViewModel
import com.inkrodriguez.bubblechat.databinding.ActivityHomeBinding

class HomeViewModel : ViewModel() {
    var selectButton = 1
    lateinit var vmBinding: ActivityHomeBinding

    fun setImage(){

        when (selectButton) {
            1 -> {
                vmBinding.btnFeed.setBackgroundResource(R.drawable.ic_home_on)
                vmBinding.btnSearch.setBackgroundResource(R.drawable.ic_search_off)
                vmBinding.btnMessages.setBackgroundResource(R.drawable.ic_message_off)
                vmBinding.btnContacts.setBackgroundResource(R.drawable.ic_contatos_off)
                vmBinding.btnSettings.setBackgroundResource(R.drawable.ic_settings_off)
            }

            2 -> {
                vmBinding.btnFeed.setBackgroundResource(R.drawable.ic_home_off)
                vmBinding.btnSearch.setBackgroundResource(R.drawable.ic_search_on)
                vmBinding.btnMessages.setBackgroundResource(R.drawable.ic_message_off)
                vmBinding.btnContacts.setBackgroundResource(R.drawable.ic_contatos_off)
                vmBinding.btnSettings.setBackgroundResource(R.drawable.ic_settings_off)
            }

            3 -> {
                vmBinding.btnFeed.setBackgroundResource(R.drawable.ic_home_off)
                vmBinding.btnSearch.setBackgroundResource(R.drawable.ic_search_off)
                vmBinding.btnMessages.setBackgroundResource(R.drawable.ic_message_on)
                vmBinding.btnContacts.setBackgroundResource(R.drawable.ic_contatos_off)
                vmBinding.btnSettings.setBackgroundResource(R.drawable.ic_settings_off)
            }
            4 -> {
                vmBinding.btnFeed.setBackgroundResource(R.drawable.ic_home_off)
                vmBinding.btnSearch.setBackgroundResource(R.drawable.ic_search_off)
                vmBinding.btnMessages.setBackgroundResource(R.drawable.ic_message_off)
                vmBinding.btnContacts.setBackgroundResource(R.drawable.ic_contatos_on)
                vmBinding.btnSettings.setBackgroundResource(R.drawable.ic_settings_off)
            }

            5 -> {
                vmBinding.btnFeed.setBackgroundResource(R.drawable.ic_home_off)
                vmBinding.btnSearch.setBackgroundResource(R.drawable.ic_search_off)
                vmBinding.btnMessages.setBackgroundResource(R.drawable.ic_message_off)
                vmBinding.btnContacts.setBackgroundResource(R.drawable.ic_contatos_off)
                vmBinding.btnSettings.setBackgroundResource(R.drawable.ic_settings_on)
            }
        }
    }
}