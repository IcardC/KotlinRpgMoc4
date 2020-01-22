package com.example.kotlinrpgmoc4.module.map

import com.example.kotlinrpgmoc4.data.DataProvider
import com.example.kotlinrpgmoc4.data.model.RoomName
import com.example.kotlinrpgmoc4.module.common.BasePresenter

/**
 * Created on 02/12/2018 by cyrilicard
 *
 */
class MapPresenter(private val view: MapView) : BasePresenter() {

    private val dataProvider = DataProvider
    var currentRoom = dataProvider.currentRoom

    override fun onCreate() {
        super.onCreate()
        prepareCurrentRoom()
    }

    private fun prepareCurrentRoom() {
        with(currentRoom) {
            view.displayRoomName(name.roomName)
            view.clearRoom()

            item?.let { view.prepareItem(it) }
            monster?.let { view.prepareMonster(it) }

            northRoom?.let { view.prepareNorthRoom(it) }
            eastRoom?.let { view.prepareEastRoom(it) }
            southRoom?.let { view.prepareSouthRoom(it) }
            westRoom?.let { view.prepareWestRoom(it) }
        }
    }

    fun onNorthDoorClick() {
        currentRoom.northRoom?.name?.let { onDoorClick(it) }
    }

    fun onEastDoorClick() {
        currentRoom.eastRoom?.name?.let { onDoorClick(it) }
    }

    fun onSouthDoorClick() {
        currentRoom.southRoom?.name?.let { onDoorClick(it) }
    }

    fun onWestDoorClick() {
        currentRoom.westRoom?.name?.let { onDoorClick(it) }
    }

    private fun onDoorClick(roomName: RoomName) {
        currentRoom = dataProvider.dungeon.rooms[roomName] ?: currentRoom
        view.clearRoom()
        prepareCurrentRoom()
    }
}