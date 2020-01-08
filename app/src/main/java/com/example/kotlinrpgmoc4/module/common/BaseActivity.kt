package com.example.kotlinrpgmoc4.module.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import rpg.kotlin.com.esgikotlinrpgandroid.module.common.BasePresenter

abstract class BaseActivity constructor(@LayoutRes val layoutRes: Int) : AppCompatActivity() {

  protected abstract fun getPresenter(): BasePresenter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setView()
    onCreated()
  }

  private fun setView() {
    setContentView(layoutRes)
  }

  open fun onCreated() {
    getPresenter().onCreated()
  }

  override fun onResume() {
    super.onResume()
    getPresenter().onResume()
  }

  override fun onPause() {
    getPresenter().onPause()
    super.onPause()
  }

  override fun onStop() {
    getPresenter().onStop()
    super.onStop()
  }

  override fun onDestroy() {
    getPresenter().onDestroy()
    super.onDestroy()
  }
}
