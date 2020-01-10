package com.example.kotlinrpgmoc4.data.model

import java.util.*


data class Message(
    var user: UserType = UserType.GAME_MASTER,
    var message: String,
    var time: Calendar = Calendar.getInstance()
)

enum class UserType {
    GAME_MASTER,
    PLAYER
}
