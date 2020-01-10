package com.example.kotlinrpgmoc4.module.weapons

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrpgmoc4.R
import com.example.kotlinrpgmoc4.data.model.*
import com.example.kotlinrpgmoc4.module.common.BaseActivity

/**
 * Created on 11/11/2018 by cyrilicard
 *
 */
class WeaponsActivity : BaseActivity(layoutRes = R.layout.activity_weapons), WeaponsView {

    override fun getPresenter() = presenter
    private val presenter = WeaponsPresenter(view = this)

    private lateinit var recyclerView: RecyclerView
    private lateinit var pictoImv: AppCompatImageView
    private lateinit var informationTxv: TextView

    private lateinit var adapter: WeaponAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViews()
        initAdapter()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initViews() {
        recyclerView = findViewById(R.id.weapons_recycler_view)
        pictoImv = findViewById(R.id.weapons_picto_imv)
        informationTxv = findViewById(R.id.weapons_information_txv)
    }

    private fun initAdapter() {
        // can be converted in method reference !
        adapter = WeaponAdapter { weapon -> presenter.onWeaponClick(weapon) }
        val linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.stackFromEnd = true
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
    }

    override fun fillWeaponsList(weapons: List<Weapon>) {
        adapter.weapons = weapons
    }

    override fun fillWeaponDetailsView(weapon: Weapon) {
        pictoImv.setImageResource(weapon.icon)

        val bonus = when (weapon) {
            is Dagger, is Sword, is Axe -> String()
            is Bow -> getString(R.string.nb_of_arrow, weapon.nbOfArrow)
            is MagicWand -> getString(R.string.nb_of_magic_damage, weapon.magicDamage)
        }
        informationTxv.text = getString(
            R.string.weapon_details, weapon.id.toString(), weapon.weaponName, bonus
        ) //complete with more details
    }

    override fun handleValidateAction() {
        val result = Intent()
        //result.putExtra(EXTRA_TASK_DESCRIPTION, taskDescription)
        setResult(Activity.RESULT_OK, result)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_weapons, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        item?.let {
            val id = item.itemId

            when (id) {
                R.id.menu_validate -> presenter.onValidateClick()
                android.R.id.home -> {
                    finish()
                    return true
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
