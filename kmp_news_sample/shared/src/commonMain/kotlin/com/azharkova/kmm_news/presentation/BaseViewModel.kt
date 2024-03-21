package com.azharkova.kmm_news.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

open class BaseViewModel : ViewModel(){
    val scope = this.viewModelScope
}