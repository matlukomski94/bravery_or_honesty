package com.mb.core.base

import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val classTag: String = this::class.java.simpleName
}