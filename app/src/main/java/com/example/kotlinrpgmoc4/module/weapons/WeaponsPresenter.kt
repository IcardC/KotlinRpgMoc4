package com.example.kotlinrpgmoc4.module.weapons

import com.example.kotlinrpgmoc4.data.DataProvider
import com.example.kotlinrpgmoc4.data.model.Weapon
import com.example.kotlinrpgmoc4.module.common.BasePresenter


class WeaponsPresenter(private val view: WeaponsView) : BasePresenter() {

    private var choosingWeapon: Weapon? = null

    override fun onCreate() {
        super.onCreate()
        view.fillWeaponsList(DataProvider.prepareListOfWeapons())
    }

    fun onWeaponClick(weapon: Weapon) {
        choosingWeapon = weapon
        view.fillWeaponDetailsView(weapon)
    }

    fun onValidateClick() {
        choosingWeapon?.let {
            DataProvider.currentWeapon = it
            view.handleValidateAction()
        }
    }

}