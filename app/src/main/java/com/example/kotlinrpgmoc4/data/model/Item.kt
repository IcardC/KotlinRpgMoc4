package com.example.kotlinrpgmoc4.data.model

data class Item (val type : ItemType)

enum class ItemType {
    HEALTH_POTION,
    GRENADE,
    KEY,
}