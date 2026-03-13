package com.jonathan.xgithubapi.ui.viewmodel.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import io.mockk.mockk

inline fun <reified T> mockkObserver(liveData: LiveData<T>? = null) =
    mockk<Observer<T>>(relaxUnitFun = true).apply { liveData?.observeForever(this) }
