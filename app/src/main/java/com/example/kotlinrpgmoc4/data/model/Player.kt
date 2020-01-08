package com.example.kotlinrpgmoc4.data.model

/**
 * Created by cyrilicard on 02/09/2018.
 */
data class Player(
    val pseudo: String,
    var age: Int,
    var healthPoint: Int,
    var weapon: Weapon,
    var items : MutableList<Item>,
    var nbPotion : Int = 0,
    var nbGrenade : Int = 0,
    var nbKey : Int = 0
)

/**
 * transform latter in sealed class
 */
enum class Weapon (val id : Int, val weaponName : String){
  DAGGER(1, "dague"),
  SWORD(2, "épée"),
  AXE(3, "hache"),
  BOW(4, "arc"),
  MAGIC_WAND(5, "baguette magique");

  companion object {
    fun getById(id : Int) = values().find { it.id == id }
  }
}