package com.example.kotlinrpgmoc4.module.map

import com.example.kotlinrpgmoc4.data.model.Item
import com.example.kotlinrpgmoc4.data.model.Monster
import com.example.kotlinrpgmoc4.data.model.Room
import com.example.kotlinrpgmoc4.module.common.BaseView

/**
 * Created on 02/12/2018 by cyrilicard
 *
 */
interface MapView : BaseView {
    fun displayRoomName(name: String)
    fun prepareNorthRoom(northRoom: Room)
    fun prepareEastRoom(eastRoom: Room)
    fun prepareSouthRoom(southRoom: Room)
    fun prepareWestRoom(westRoom: Room)
    fun prepareMonster(monster: Monster)
    fun clearRoom()
    fun prepareItem(item: Item)
}