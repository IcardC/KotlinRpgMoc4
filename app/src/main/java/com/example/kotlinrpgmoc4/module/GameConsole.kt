package com.example.kotlinrpgmoc4.module

@Deprecated("not used anymore")
class GameConsole /*: GameView {

  //  private var presenter: GamePresenter = GamePresenter(this)
    init {
        presenter.initGame()
    }

    override fun displayWelcomeMessage() {
        println("Salut à toi!")
        println("Bienvenue à kotlinCity! \nQuel est ton nom voyageur?\n")
    }

    override fun displayPlayerPseudoReaction() {
        print("Pseudo : ")
        val myPseudo: String = readLine() ?: "toto"
        print("En voilà un drôle de nom $myPseudo")
        presenter.savePlayerPseudo(myPseudo)
    }

    override fun displayStartQuestMessage() {
        print(", tu viens ici pour la quête ?\n")

        printYesOrNoChoice()
        presenter.tryToLaunchDungeon(readLine())
    }

    override fun questStarting() {
        println("Je savais que tu avais l'ame d'un guerrier, je l'ai vu dès que tu t'es présenté à moi !")
    }

    override fun displayStartQuestNegativeAnswer() {
        println(
            "Tu ne faisais pas l'affaire de toute manière! " +
                    "fishtrrr..."
        )
    }

    override fun displayQuestWtfAnswer() {
        println("Hum, Hum, ce n'est pas une bonne réponse")
        println(OTHER_CHOICE_RESPONSES.random())
        presenter.tryToLaunchDungeon(readLine())
    }

    override fun displayDungeonInformation(dungeonName: String) {
        Utils.waitingThread()
        println(HUGE_TITLE)
        println("Vous venez de pénétrer dans le donjon de $dungeonName")
    }

    override fun choosePlayerWeaponInformation(weapons: Array<Weapon>) {
        println("Avant toute chose tu vas devoir choisir une arme !")

        for (weapon in weapons) {
            println("- ${weapon.id} : ${weapon.weaponName.capitalize()}")
        }

        // other way :  weapons.forEach { println("- ${weapon.id} : ${weapon.weaponName.capitalize()}") }

        println("Quel est ton choix ?")
        playerWeaponChoice()
    }

    private fun playerWeaponChoice() {
        val reader = Scanner(System.`in`)
        var correctWeaponChoice = false

        do try {
            presenter.playerChooseWeapon(reader.nextInt())
            correctWeaponChoice = true
        } catch (ex: InputMismatchException) {
            println("Attention à saisir un chiffre valide !")
            reader.next()
        } catch (ex: WeaponException) {
            println(ex.message)
            print("Ressaie :")
            reader.reset()
        }
        while (!correctWeaponChoice)
    }

    override fun displayWeaponGameMasterMessage(weaponName: String) {
        println("Hum, je n'aurais jamais choisi une $weaponName\n")
    }

    override fun displayPlayerIsIn(pseudo: String) {
        println("Te voilà rentré dans le donjon ${pseudo.capitalize()} le...le... brave")
    }

    override fun displayPossibleDirection(room: Room) {
        println("Tu peux choisir parmi ces options : ")
        with(room) {
            northRoom?.run { println("  > North ?") }
            eastRoom?.run { println("  > East ?") }
            southRoom?.run { println("  > South ?") }
            westRoom?.run { println("  > West ?") }
        }
        println("\nVers ou vas t'on ? ")
        presenter.directionChoice(readLine())
    }

    override fun displayHasLockDoorYouNeedKey() {
        println("Mince cette porte est fermé il faut une clef pour l'ouvrir !")
    }

    override fun displayPlayerHasAKey() {
        println("Par chance je vois que vous en avait une dans votre inventaire")
    }

    override fun displayPlayerHasNoKey() {
        println("Vous ne passerez pas !!! (Dumbledor)")
        println("Vous allez devoir trouver une clef pour continuer par ici !")
    }

    override fun displayKeyNotFound() {
        println("Vous n'avez aucune clé dans votre sac à dos !!")
    }

    override fun displayGoToRoom(roomName: RoomName) {

    }

    override fun displayByeByeMessage() {
        println(" _o/ au revoir !")
    }

    override fun displayCongratulationMessage() {
        println("C'est incroyable voici Excalibur !")
        println("\n\n         oxxxxx{=================>\n\n")
        println("Bravo! la partie est finie!")
    }

    override fun displayEmptyRoomMessage() {
        println("Malheuresement cette pièce est vide ! \n")
    }

    override fun playerFoundSomething() {
        println("Vous avez trouvé quelque chose !")
    }

    override fun playerFoundPotion() {
        println("> Un potion de vie !")
    }

    override fun playerFoundGrenade() {
        println("> Une grenade prenez garde !")
    }

    override fun playerFoundKey() {
        println("> Une clef !")
    }

    override fun playerAddItemToInventory() {
        println("Hop dans l'inventaire")
    }

    override fun askPlayerWantFighting(typeName: String) {
        //cours 3
    }

    override fun displayContinueOrLeaveChoice() {
        println("---___---___---___---___---___---___---___---")
        println()
        println("Que souhaitez vous faire ?")
        println("  'c' --> Continuer d'avancer")
        println("  'q' --> Quitter le donjon")
        println("----------------------------------------------")
        presenter.continueOrLeaveChoice(readLine())
    }

    override fun displayYouAreSoYoung() {
        println("Damn! You are soooo young, I don't think you can survive enough time ! Bonne chance mon enfant j'en perds mon français !")
    }

    override fun displayThatOkYouAreWiseEnough() {
        println("Hum, un grand Jedi tu seras !")
    }

    override fun displayGodYouAreSoOld() {
        println("Outch, je te laisserai bien ma place assis dans le métro, si seulement il y en avait un !")
    }

    override fun displayCurrentRoomInformation(currentRoom: Room) {
        println(
            "\n\n" +
                    "* * * * * ${currentRoom.name} * * * * *" +
                    "\n"
        )
    }

    private fun printYesOrNoChoice() {
        println("  'o' --> Oui")
        println("  'n' --> Non")
    }

    private fun List<String>.random(): String = get(Random().nextInt(size))
}*/

