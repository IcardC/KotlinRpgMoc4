package com.example.kotlinrpgmoc4.module.game

import com.example.kotlinrpgmoc4.data.model.Message
import com.example.kotlinrpgmoc4.data.model.Player
import com.example.kotlinrpgmoc4.data.model.Room
import com.example.kotlinrpgmoc4.data.model.RoomName
import com.example.kotlinrpgmoc4.module.common.BaseView


interface GameView : BaseView {

    //region * * * * * Cours 1 * * * * *
    fun displayWelcomeMessage()

    fun displayPlayerPseudoReaction(pseudo: String)

    fun displayStartQuestMessage()

    fun displayDungeonInformation(dungeonName: String)

    fun choosePlayerWeaponInformation()

    fun displayStartQuestNegativeAnswer()

    fun displayQuestWtfAnswer()
    //endregion

    //region * * * * * Cours 2 * * * * *
    fun displayWeaponGameMasterMessage(weaponName: String)

    fun displayPlayerIsIn(player: Player)

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

    fun displayYouAreSoYoung()

    fun displayThatOkYouAreWiseEnough()

    fun displayGodYouAreSoOld()

    fun displayCurrentRoomInformation(currentRoom: Room)
    //endregion

    //region * * * * * Cours 3&4 * * * * *
    fun addPlayerMessage(message: Message)

    fun displayStartQuestPositiveAnswer()

    fun displayNextCourse()
    //endregion

    //region * * * * * Cours 5 * * * * *
    fun navigateToMap()

    fun displayNoMapMessage()
    //endregion
}