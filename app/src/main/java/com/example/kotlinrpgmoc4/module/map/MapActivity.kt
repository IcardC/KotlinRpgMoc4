package com.example.kotlinrpgmoc4.module.map

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import com.example.kotlinrpgmoc4.R
import com.example.kotlinrpgmoc4.data.model.*
import com.example.kotlinrpgmoc4.module.common.BaseActivity
import kotlinx.android.synthetic.main.activity_map.*


class MapActivity : BaseActivity(layoutRes = R.layout.activity_map), MapView {

    private val presenter = MapPresenter(this)
    override fun getPresenter() = presenter

    private lateinit var itemImv: AppCompatImageView
    private lateinit var itemTxv: TextView
    private lateinit var monsterImv: AppCompatImageView
    private lateinit var monsterTxv: TextView
    private lateinit var northDoorImv: AppCompatImageView
    private lateinit var eastDoorImv: AppCompatImageView
    private lateinit var southDoorImv: AppCompatImageView
    private lateinit var westDoorImv: AppCompatImageView
    private lateinit var northRoomNameTxv: TextView
    private lateinit var eastRoomNameTxv: TextView
    private lateinit var southRoomNameTxv: TextView
    private lateinit var westRoomNameTxv: TextView
    private lateinit var lavaFloorImv: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        setSupportActionBar(map_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initListener()
        prepareLavaAnimation()
    }

    private fun initViews() {
        itemImv = findViewById(R.id.map_item_imv)
        itemTxv = findViewById(R.id.map_item_txv)
        monsterImv = findViewById(R.id.map_monster_imv)
        monsterTxv = findViewById(R.id.map_monster_txv)
        northDoorImv = findViewById(R.id.map_north_door_imv)
        eastDoorImv = findViewById(R.id.map_east_door_imv)
        southDoorImv = findViewById(R.id.map_south_door_imv)
        westDoorImv = findViewById(R.id.map_west_door_imv)
        northRoomNameTxv = findViewById(R.id.map_north_room_name_txv)
        eastRoomNameTxv = findViewById(R.id.map_east_room_name_txv)
        southRoomNameTxv = findViewById(R.id.map_south_room_name_txv)
        westRoomNameTxv = findViewById(R.id.map_west_room_name_txv)
        lavaFloorImv = findViewById(R.id.map_lava_floor_imv)
    }

    private fun prepareLavaAnimation() {
        lavaFloorImv.animation = AnimationUtils.loadAnimation(this, R.anim.lava_translation)
    }

    private fun initListener() {
        northDoorImv.setOnClickListener { presenter.onNorthDoorClick() }
        eastDoorImv.setOnClickListener { presenter.onEastDoorClick() }
        southDoorImv.setOnClickListener { presenter.onSouthDoorClick() }
        westDoorImv.setOnClickListener { presenter.onWestDoorClick() }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
            else -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun displayRoomName(name: String) {
        supportActionBar?.title = name
    }

    override fun prepareNorthRoom(northRoom: Room) {
        northRoomNameTxv.text = northRoom.name.roomName
        northDoorImv.setImageResource(getProperDoorDrawable(northRoom.hasLockDoor))

        northRoomNameTxv.visibility = View.VISIBLE
        northDoorImv.visibility = View.VISIBLE
    }

    override fun prepareEastRoom(eastRoom: Room) {
        eastRoomNameTxv.text = eastRoom.name.roomName
        eastDoorImv.setImageResource(getProperDoorDrawable(eastRoom.hasLockDoor))

        eastRoomNameTxv.visibility = View.VISIBLE
        eastDoorImv.visibility = View.VISIBLE
    }

    override fun prepareSouthRoom(southRoom: Room) {
        southRoomNameTxv.text = southRoom.name.roomName
        southDoorImv.setImageResource(getProperDoorDrawable(southRoom.hasLockDoor))

        southRoomNameTxv.visibility = View.VISIBLE
        southDoorImv.visibility = View.VISIBLE
    }

    override fun prepareWestRoom(westRoom: Room) {
        westRoomNameTxv.text = westRoom.name.roomName
        westDoorImv.setImageResource(getProperDoorDrawable(westRoom.hasLockDoor))

        westRoomNameTxv.visibility = View.VISIBLE
        westDoorImv.visibility = View.VISIBLE
    }

    override fun prepareMonster(monster: Monster) {
        monsterImv.setImageResource(
            when (monster.type) {
                MonsterType.ORC -> R.drawable.ic_monster
                MonsterType.TROLL -> R.drawable.ic_troll
                MonsterType.GOBLIN -> R.drawable.ic_goblin
                MonsterType.DRAGON -> R.drawable.ic_dragon_small
            }
        )
        monsterImv.visibility = View.VISIBLE
        monsterTxv.text =
            getString(R.string.monster_information, monster.type.typeName, monster.healthPoint)
        monsterTxv.visibility = View.VISIBLE
    }

    private fun getProperDoorDrawable(hasLockDoor: Boolean): Int {
        return if (hasLockDoor) R.drawable.ic_locked else R.drawable.ic_door
    }

    override fun prepareItem(item: Item) {
        itemImv.setImageResource(
            when (item.type) {
                ItemType.HEALTH_POTION -> R.drawable.ic_potion
                ItemType.GRENADE -> R.drawable.ic_hand_grenade
                ItemType.KEY -> R.drawable.ic_key
            }
        )

        itemTxv.text = getString(
            when (item.type) {
                ItemType.HEALTH_POTION -> R.string.heath_potion_information
                ItemType.GRENADE -> R.string.grenade_information
                ItemType.KEY -> R.string.key_information
            }
        )

        itemImv.visibility = View.VISIBLE
        itemTxv.visibility = View.VISIBLE
    }

    override fun clearRoom() {
        itemImv.visibility = View.GONE
        itemTxv.visibility = View.GONE
        monsterImv.visibility = View.GONE
        monsterTxv.visibility = View.GONE
        northDoorImv.visibility = View.GONE
        eastDoorImv.visibility = View.GONE
        southDoorImv.visibility = View.GONE
        westDoorImv.visibility = View.GONE
        northRoomNameTxv.visibility = View.GONE
        eastRoomNameTxv.visibility = View.GONE
        southRoomNameTxv.visibility = View.GONE
        westRoomNameTxv.visibility = View.GONE
    }
}