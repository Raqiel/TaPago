package com.example.tapago.extensions

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson

fun <T> NavHostController.getArgObject(key: String, backStackEntry: NavBackStackEntry? = this.previousBackStackEntry, classOf: Class<T>): T? {
    return this.getObject(key = key, classOf = classOf, backStackEntry = backStackEntry)
}

private fun NavHostController.getStringValue(key: String, backStackEntry: NavBackStackEntry?): String? {
    return backStackEntry?.savedStateHandle?.getLiveData<String>(key = key)?.value
}
private fun <T> NavHostController.getObject(key: String, classOf: Class<T>, backStackEntry: NavBackStackEntry?): T? {
    var model: T? = null
    val value = this.getStringValue(key = key, backStackEntry = backStackEntry)
    if (value != null) {
        try {
            model = Gson().fromJson(value, classOf)
        } catch (e: Exception) {
            //Timber.e(e)
        }
    }
    return model
}