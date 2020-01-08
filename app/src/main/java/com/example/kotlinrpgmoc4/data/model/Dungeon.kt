package com.example.kotlinrpgmoc4.data.model

data class Dungeon(val name : String = "Kotlin", val rooms : Map<RoomName, Room>)
