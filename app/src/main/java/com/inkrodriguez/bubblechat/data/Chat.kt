package com.inkrodriguez.bubblechat.data

data class Chat(
    var id: String = "",
    var remetente: String = "",
    var destinatario: String = "",
    var message: String = "",
    var data: String = "",
    var hora: String = ""
)