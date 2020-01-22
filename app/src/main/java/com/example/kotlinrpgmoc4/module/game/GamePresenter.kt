package com.example.kotlinrpgmoc4.module.game

import com.example.kotlinrpgmoc4.data.DataProvider
import com.example.kotlinrpgmoc4.data.model.*
import com.example.kotlinrpgmoc4.module.common.BasePresenter
import java.util.*

class GamePresenter(private val view: GameView) : BasePresenter() {

    private val dungeon = DataProvider.dungeon

    private var hasChooseWeapon = false
    private lateinit var currentWeapon: Weapon
    private lateinit var player: Player

    private var currentRoom: Room? = null

    private var leaveTheGame = false
    private var winTheGame = false
    private var previousRoom: Room? = null

    private var currentStoryLine: StoryLine = StoryLine.WELCOME_TO_THE_GAME

    override fun onCreate() {
        super.onCreate()
        manageStoryLine()
    }

    @Deprecated(
        "Use manageStoryLine() instead",
        ReplaceWith("manageStoryLine()"),
        DeprecationLevel.ERROR
    )
    fun initGame() {
        // view.displayWelcomeMessage()
        //view.displayPlayerPseudoReaction()
    }

    //Game master message
    private fun manageStoryLine() {
        when (currentStoryLine) {
            StoryLine.WELCOME_TO_THE_GAME -> view.displayWelcomeMessage()
            StoryLine.PLAYER_PSEUDO -> {
                view.displayPlayerPseudoReaction(player.pseudo)
                triggerStoryLineAction()
            }
            StoryLine.START_QUEST -> view.displayStartQuestMessage()
            StoryLine.ANSWER_QUEST -> view.displayStartQuestPositiveAnswer()
            StoryLine.DUNGEON_INFORMATION -> view.displayDungeonInformation(dungeon.name)
            StoryLine.CHOOSE_WEAPON -> view.choosePlayerWeaponInformation()
            StoryLine.CONFIGURE_PLAYER -> view.displayPlayerIsIn(player)
            StoryLine.START_EXPLORATION -> view.displayNextCourse()
        }
    }

    private fun triggerStoryLineAction(answer: String = "") {
        when (currentStoryLine) {
            StoryLine.WELCOME_TO_THE_GAME -> {
                //change to a simple method
                player = Player(pseudo = answer)
                updateStoryLine()
            }
            StoryLine.START_QUEST -> prepareStarQuest(answer)
            StoryLine.CHOOSE_WEAPON -> if (hasChooseWeapon) initPlayer()
            StoryLine.START_EXPLORATION -> {
            }
            else -> updateStoryLine()
        }

        //When actions are done we continue the story
        manageStoryLine()
    }

    private fun updateStoryLine() {
        currentStoryLine = StoryLine.getNextStoryLinePoint(currentStoryLine)
    }


    private fun prepareStarQuest(answer: String) {
        val lAnswer = answer.toLowerCase(Locale.getDefault())
        when {
            YES.contains(lAnswer) -> prepareDungeon()
            NO.contains(lAnswer) -> view.displayStartQuestNegativeAnswer()
            else -> view.displayQuestWtfAnswer()
        }
    }

    private fun prepareDungeon() {
        dungeon.rooms[RoomName.STARTING_ROOM]?.let { currentRoom = it }
        updateStoryLine()
    }

    /*private fun questStarting() {
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
    }*/

    private fun initPlayer() {
        val randomAge = (18..99).random()
        player.apply {
            age = randomAge
            healthPoint = 100
            weapon = currentWeapon
        }

        when {
            randomAge < 21 -> view.displayYouAreSoYoung()
            randomAge in 21..40 -> view.displayThatOkYouAreWiseEnough()
            else -> view.displayGodYouAreSoOld()
        }
        updateStoryLine()
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

    fun onValidateClick(answer: String) {
        if (answer.isNotBlank()) {
            val playerMessage = Message(UserType.PLAYER, answer)
            view.addPlayerMessage(playerMessage)
            triggerStoryLineAction(answer)
        }
    }

    fun onMapClick() {
        currentRoom?.let {
            DataProvider.currentRoom = it
            view.navigateToMap()
        } ?: view.displayNoMapMessage()
    }

    fun onContinueClick() {
        triggerStoryLineAction()
    }

    fun onPositiveDialogClick() {
        triggerStoryLineAction()
    }

    fun playerHasChosenHisWeapon() {
        hasChooseWeapon = true
        currentWeapon = DataProvider.currentWeapon ?: Dagger()
        view.displayWeaponGameMasterMessage(currentWeapon.weaponName)
        triggerStoryLineAction()
    }
}

private val YES = listOf("y", "o", "1", "a", "oui", "yes", "yep", "yop", "ok", "d'accord")
private val NO = listOf("n", "2", "b", "non", "no", "nope", "nan", "c mort", "c'est mort")
private const val QUIT = "q"