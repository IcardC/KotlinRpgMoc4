package com.example.kotlinrpgmoc4.module.weapons

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.kotlinrpgmoc4.R
import com.example.kotlinrpgmoc4.data.model.Weapon
import com.example.kotlinrpgmoc4.misc.inflate

class WeaponAdapter(private var weaponListener: (Weapon) -> Unit) :

    RecyclerView.Adapter<WeaponAdapter.WeaponViewHolder>() {

    var weapons: List<Weapon> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    //oldway :
    /*fun fillWeaponsList(weapons: List<Weapon>) {
        this.weapons = weapons
        notifyDataSetChanged()
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeaponViewHolder {
        return WeaponViewHolder(parent.inflate(R.layout.item_weapon))
    }

    override fun getItemCount() = weapons.size

    override fun onBindViewHolder(holder: WeaponViewHolder, position: Int) {
        val weapon = weapons[position]

        with(holder) {
            pictoImv.setImageResource(weapon.icon)
            nameTxv.text = weapon.weaponName

            itemView.setOnClickListener { weaponListener.invoke(weapon) }
        }
    }

    inner class WeaponViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var pictoImv: AppCompatImageView = view.findViewById(R.id.weapon_picto_imv)
        var nameTxv: TextView = view.findViewById(R.id.weapon_name_txv)
    }
}
