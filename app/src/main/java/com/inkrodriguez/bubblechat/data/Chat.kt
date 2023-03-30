package com.inkrodriguez.bubblechat.data

data class Chat(
    var id: Number = 0,
    var remetente: String = "",
    var destinatario: String = "",
    var message: String = "",
    var date: String = "",
)