package com.example.kotlinrpgmoc4.module.game

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrpgmoc4.R
import com.example.kotlinrpgmoc4.data.model.Message
import com.example.kotlinrpgmoc4.data.model.Player
import com.example.kotlinrpgmoc4.data.model.Room
import com.example.kotlinrpgmoc4.data.model.RoomName
import com.example.kotlinrpgmoc4.misc.hideKeyboard
import com.example.kotlinrpgmoc4.module.common.BaseActivity
import com.example.kotlinrpgmoc4.module.map.MapActivity
import com.example.kotlinrpgmoc4.module.weapons.WeaponsActivity
import kotlinx.android.synthetic.main.activity_game.*
import java.util.*

class GameActivity : BaseActivity(R.layout.activity_game), GameView {

    //region  * * * Presenter declaration * * *
    private val presenter = GamePresenter(this)

    override fun getPresenter() = presenter
    //endregion

    //region  * * * Variable declaration * * *
    private lateinit var validateBtn: Button
    private lateinit var profileEdt: EditText
    private lateinit var recyclerView: RecyclerView

    private lateinit var mapBtn: AppCompatImageButton
    private lateinit var continueBtn: Button
    private lateinit var footerGroup: Group
    private lateinit var headerGroup: Group

    private lateinit var playerNameTxv: TextView
    private lateinit var lifePointTxv: AppCompatTextView
    private lateinit var weaponImv: AppCompatImageView

    private lateinit var adapter: GameAdapter
    //endregion

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initAdapter()
        initListener()
    }

    //region * * * * * Base Function * * * * *
    private fun initViews() {
        validateBtn = findViewById(R.id.game_validate_btn)
        profileEdt = findViewById(R.id.game_profile_edt)
        recyclerView = findViewById(R.id.game_recycler_view)
        mapBtn = findViewById(R.id.game_map_btn)
        continueBtn = findViewById(R.id.game_continue_btn)
        footerGroup = findViewById(R.id.game_footer_group)
        headerGroup = findViewById(R.id.header_group)
        playerNameTxv = findViewById(R.id.game_player_name_txv)
        lifePointTxv = findViewById(R.id.game_life_point_txv)
        weaponImv = findViewById(R.id.game_weapon_imv)
    }

    private fun initAdapter() {
        adapter = GameAdapter()
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    private fun initListener() {
        //pass text fill by user
        /*validateBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })*/

        validateBtn.setOnClickListener {
            presenter.onValidateClick(profileEdt.text.toString())
        }

        continueBtn.setOnClickListener { presenter.onContinueClick() }

        recyclerView.addOnLayoutChangeListener { _, _, _, _, bottom, _, _, _, oldBottom ->
            if (bottom < oldBottom) {
                game_recycler_view.smoothScrollToPosition(adapter.itemCount)
            }
        }

        mapBtn.setOnClickListener { presenter.onMapClick() }
    }

    private fun displayMessage(message: Message) {
        Handler().postDelayed({
            adapter.addMessage(message)
            recyclerView.smoothScrollToPosition(adapter.itemCount)
        }, 300)
    }

    override fun addPlayerMessage(message: Message) {
        hideKeyboard()
        displayMessage(message)
        profileEdt.text.clear()
        //game_recycler_view.scrollToPosition(adapter.itemCount - 1)
    }
    //endregion

    //region * * * * * Override Function * * * * *
    override fun displayWelcomeMessage() {
        displayMessage(Message(message = getString(R.string.welcome_message)))
    }

    override fun displayPlayerPseudoReaction(pseudo: String) {
        displayMessage(Message(message = getString(R.string.funny_name, pseudo)))
    }

    override fun displayStartQuestMessage() {
        displayMessage(Message(message = getString(R.string.come_for_quest)))
        displayMessage(Message(message = getString(R.string.yes_or_no_choice)))
    }

    override fun displayStartQuestPositiveAnswer() {
        displayMessage(Message(message = getString(R.string.start_quest_yes)))
        footerGroup.visibility = View.INVISIBLE
        continueBtn.visibility = View.VISIBLE
    }

    override fun displayStartQuestNegativeAnswer() {
        displayMessage(Message(message = getString(R.string.you_dont_do_the_job)))
    }

    override fun displayQuestWtfAnswer() {
        displayMessage(Message(message = getString(R.string.start_quest_bad_answer)))
        val otherChoices = resources.getStringArray(R.array.other_choice_responses)
        displayMessage(Message(message = otherChoices.random()))
    }

    override fun displayDungeonInformation(dungeonName: String) {
        displayMessage(Message(message = getString(R.string.first_foot_in_dungeon, dungeonName)))
        showCongratulationDialog(dungeonName)
    }

    private fun showCongratulationDialog(dungeonName: String) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.congratulation))
            .setMessage(getString(R.string.first_foot_in_dungeon, dungeonName))
            .setPositiveButton(getString(R.string.ok_btn)) { dialog, _ ->
                presenter.onPositiveDialogClick()
                dialog.dismiss()
            }
            .show()
            .setCancelable(false)
    }

    override fun choosePlayerWeaponInformation() {
        Intent(this, WeaponsActivity::class.java)
            .run { startActivityForResult(this, PICK_WEAPON_REQUEST) }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_WEAPON_REQUEST && resultCode == Activity.RESULT_OK) {
            presenter.playerHasChosenHisWeapon()
            headerGroup.visibility = View.VISIBLE
        }
    }

    override fun displayWeaponGameMasterMessage(weaponName: String) {
        displayMessage(
            Message(
                message = getString(
                    R.string.game_master_weapon_comment,
                    weaponName
                )
            )
        )
    }

    override fun displayYouAreSoYoung() {
        displayMessage(Message(message = getString(R.string.you_are_so_young)))
    }

    override fun displayThatOkYouAreWiseEnough() {
        displayMessage(Message(message = getString(R.string.that_ok_you_are_wise_enough)))
    }

    override fun displayGodYouAreSoOld() {
        displayMessage(Message(message = getString(R.string.god_you_are_so_old)))
    }

    override fun displayPlayerIsIn(player: Player) {
        playerNameTxv.text = player.pseudo
        lifePointTxv.text = player.healthPoint.toString()
        player.weapon?.let { weaponImv.setImageResource(it.icon) }
        displayMessage(Message(message = getString(R.string.player_is_in, player.pseudo)))
    }
    //endregion

    //region * * * * * Cours 5 * * * * *

    override fun navigateToMap() {
        Intent(this, MapActivity::class.java)
            .also { startActivity(it) }
    }

    override fun displayNoMapMessage() {
        //En construction!
        //Rome ne s'est pas faite en un jour!
        Toast.makeText(this, getString(R.string.no_map_available), Toast.LENGTH_SHORT).show()
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

    override fun askPlayerWantFighting(typeName: String) {}

    override fun displayContinueOrLeaveChoice() {
        println("---___---___---___---___---___---___---___---")
        println()
        println("Que souhaitez vous faire ?")
        println("  'c' --> Continuer d'avancer")
        println("  'q' --> Quitter le donjon")
        println("----------------------------------------------")
        presenter.continueOrLeaveChoice(readLine())
    }

    override fun displayCurrentRoomInformation(currentRoom: Room) {
        println(
            "\n\n" +
                    "* * * * * ${currentRoom.name} * * * * *" +
                    "\n"
        )
    }

    override fun displayNextCourse() {
        Toast.makeText(
            this,
            "La suite au prochain cours soldat ! \nAiguise ton arme en attendant ;)",
            Toast.LENGTH_SHORT
        )
            .show()
    }

    private fun List<String>.random(): String = get(Random().nextInt(size))
    //endregion

    companion object {
        const val PICK_WEAPON_REQUEST = 1
    }
}