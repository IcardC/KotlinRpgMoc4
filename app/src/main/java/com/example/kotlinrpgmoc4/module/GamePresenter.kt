package com.example.kotlinrpgmoc4.module

import com.example.kotlinrpgmoc4.data.DataProvider
import com.example.kotlinrpgmoc4.data.model.*
import com.example.kotlinrpgmoc4.data.model.exception.WeaponException

class GamePresenter(private val view: GameInterface) {

    private lateinit var pseudo: String
    private val dungeon by lazy { DataProvider.initDungeon() }
    private lateinit var player: Player

    private var currentRoom: Room? = null

    private var leaveTheGame = false
    private var winTheGame = false
    private var previousRoom: Room? = null

    fun initGame() {
        view.displayWelcomeMessage()
        view.askPlayerPseudo()
    }

    fun savePlayerPseudo(pseudo: String) {
        this.pseudo = pseudo
        askForStartingQuest()
    }

    private fun askForStartingQuest() {
        view.displayStartQuestMessage()
    }

    fun tryToLaunchDungeon(comeToStartQuest: String?) {
        when {
            YES.contains(comeToStartQuest?.toLowerCase()) -> {
                view.questStarting()
                questStarting()
            }
            NO.contains(comeToStartQuest?.toLowerCase()) -> view.questNotStarting()
            else -> view.questWtfAnswer()
        }
    }

    private fun questStarting() {
        dungeon.rooms[RoomName.STARTING_ROOM]?.let { currentRoom = it }
        view.displayDungeonInformation(dungeon.name)
        choosePlayerWeapon()
    }

    private fun choosePlayerWeapon() {
        view.choosePlayerWeaponInformation(Weapon.values())
    }

    fun playerChooseWeapon(weaponChoice: Int) {
        Weapon.getById(weaponChoice)?.let { weapon ->
            view.displayWeaponGameMasterMessage(weapon.weaponName)
            initPlayer(weapon)
            startDungeon()
        } ?: throw WeaponException()
    }

    private fun initPlayer(weapon: Weapon) {
        val randomAge = (18..99).random()

        when {
            randomAge < 21 -> view.youAreSoYoung()
            (randomAge >= 21) and (randomAge < 40) -> view.thatOkYouAreWiseEnough()
            else -> view.godYouAreSoOld()
        }

        player = Player(
            pseudo = pseudo,
            age = randomAge,
            healthPoint = 100,
            weapon = weapon,
            items = mutableListOf()
        )
        view.displayPlayerAreIn(pseudo)
    }

    private fun startDungeon() {

        while (!leaveTheGame && !winTheGame) {
            checkCurrentRoom()
            if (leaveTheGame || winTheGame) break
            askForThePossiblePath()
        }

        if (leaveTheGame) view.displayByeByeMessage() else view.displayCongratulationMessage()
    }

    private fun askForThePossiblePath() {
        currentRoom?.let { view.displayPossibleDirection(it) }
    }

    private fun checkCurrentRoom() {
        currentRoom?.let { nonNullRoom ->

            view.displayCurrentRoomInformation(nonNullRoom)
            when {
                nonNullRoom.item != null -> playerFoundItem(nonNullRoom.item)
                nonNullRoom.monster != null -> askForAFight(nonNullRoom.monster)
                else -> view.displayEmptyRoomMessage()
            }
        }

        if (!leaveTheGame && !winTheGame) view.displayContinueOrLeaveChoice()
    }

    private fun playerFoundItem(item: Item) {
        view.playerFoundSomething()
        when (item.type) {
            ItemType.HEALTH_POTION -> {
                view.playerFoundPotion()
                player.nbPotion++
            }
            ItemType.GRENADE -> {
                view.playerFoundGrenade()
                player.nbGrenade++
            }
            ItemType.KEY -> {
                view.playerFoundKey()
                player.nbKey++
            }
        }
        player.items.add(item)
        view.playerAddItemToInventory()
    }

    private fun askForAFight(monster: Monster) {
        view.askPlayerWantFighting(monster.type.typeName)
    }

    fun directionChoice(direction: String?) {
        direction?.let { nonNullDirection ->

            previousRoom = currentRoom?.copy()

            currentRoom = when {
                NORTH.contains(nonNullDirection.toLowerCase()) -> currentRoom?.northRoom
                EAST.contains(nonNullDirection.toLowerCase()) -> currentRoom?.eastRoom
                SOUTH.contains(nonNullDirection.toLowerCase()) -> currentRoom?.southRoom
                WEST.contains(nonNullDirection.toLowerCase()) -> currentRoom?.westRoom
                else -> null
            }

            currentRoom?.let { nonNullCurrentRoom ->
                view.displayGoToRoom(nonNullCurrentRoom.name)
                if (nonNullCurrentRoom.hasLockDoor) {
                    view.displayHasLockDoorYouNeedKey()
                    if (player.nbKey > 0) {
                        view.displayPlayerHasAKey()
                        removeKeyToPlayer()
                    } else {
                        view.displayPlayerHasNoKey()
                        playerHasNoKey()
                    }
                }
            }
        }
    }

    private fun removeKeyToPlayer() {
        val key = player.items.find { item -> item.type == ItemType.KEY }
        key?.let { nonNullKey ->
            player.nbKey--
            player.items.remove(nonNullKey)
        } ?: view.displayKeyNotFound()
    }

    private fun playerHasNoKey() {
        currentRoom = previousRoom
    }

    fun continueOrLeaveChoice(userChoice: String?) {
        userChoice?.let { if (it == QUIT) leaveTheGame = true }
    }
}

private val YES = listOf("y", "o", "1", "a", "oui", "yes", "yep", "yop", "ok", "d'accord")
private val NO = listOf("n", "2", "b", "non", "no", "nope", "nan", "c mort", "c'est mort")
private const val QUIT = "q"