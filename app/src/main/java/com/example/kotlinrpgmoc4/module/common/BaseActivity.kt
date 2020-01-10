package com.example.kotlinrpgmoc4.module.common

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity(@LayoutRes val layoutRes: Int) : AppCompatActivity(), BaseView {

    protected abstract fun getPresenter(): BasePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setView()
        getPresenter().baseView = this
        lifecycle.addObserver(getPresenter())
    }

    private fun setView() {
        setContentView(layoutRes)
    }
}
