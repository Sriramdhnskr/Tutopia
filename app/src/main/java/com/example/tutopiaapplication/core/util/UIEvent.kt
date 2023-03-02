package com.rating.tmdbclient.core.util

sealed class UIEvent(){
    data class ShowSnackbar(val message: String,var success  :Boolean? = null) : com.rating.tmdbclient.core.util.UIEvent()
    data class Navigation(val message: String) : com.rating.tmdbclient.core.util.UIEvent()
    data class ShowToast(val message: String,var success  :Boolean? = null) : com.rating.tmdbclient.core.util.UIEvent()
}
