package com.example.kotlinrpgmoc4.module.weapons

import com.example.kotlinrpgmoc4.data.model.Weapon
import com.example.kotlinrpgmoc4.module.common.BaseView

/**
 * Created on 11/11/2018 by cyrilicard
 *
 */
interface WeaponsView : BaseView {

    fun fillWeaponsList(weapons: List<Weapon>)

    fun fillWeaponDetailsView(weapon: Weapon)

    fun handleValidateAction()
}