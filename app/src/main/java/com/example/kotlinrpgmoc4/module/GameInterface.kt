package com.example.kotlinrpgmoc4.module

import com.example.kotlinrpgmoc4.data.model.Room
import com.example.kotlinrpgmoc4.data.model.RoomName
import com.example.kotlinrpgmoc4.data.model.Weapon


interface GameInterface {

    //region * * * * * Cours 1 * * * * *
    fun displayWelcomeMessage()

    fun askPlayerPseudo()

    fun displayStartQuestMessage()

    fun displayDungeonInformation(dungeonName: String)

    fun choosePlayerWeaponInformation(weapons: Array<Weapon>)

    fun questStarting()

    fun questNotStarting()

    fun questWtfAnswer()
    //endregion

    //region * * * * * Cours 2 * * * * *
    fun displayWeaponGameMasterMessage(weaponName: String)

    fun displayPlayerAreIn(pseudo: String)

    fun displayPossibleDirection(room: Room)

    fun displayGoToRoom(roomName: RoomName)

    fun displayHasLockDoorYouNeedKey()

    fun displayPlayerHasAKey()

    fun displayPlayerHasNoKey()

    fun displayKeyNotFound()

    fun displayByeByeMessage()

    fun displayCongratulationMessage()

    fun displayEmptyRoomMessage()

    fun displayContinueOrLeaveChoice()

    fun playerFoundSomething()

    fun playerFoundPotion()

    fun playerFoundGrenade()

    fun playerFoundKey()

    fun playerAddItemToInventory()

    fun askPlayerWantFighting(typeName: String)

    fun youAreSoYoung()

    fun thatOkYouAreWiseEnough()

    fun godYouAreSoOld()

    fun displayCurrentRoomInformation(currentRoom: Room)
    //endregion
}