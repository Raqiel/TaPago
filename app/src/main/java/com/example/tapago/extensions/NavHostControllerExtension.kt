package com.example.tapago.extensions

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.google.gson.Gson

fun <T> NavHostController.getArgObject(key: String, backStackEntry: NavBackStackEntry? = this.previousBackStackEntry, classOf: Class<T>): T? {
    return this.getObject(key = key, classOf = classOf, backStackEntry = backStackEntry)
}

fun NavHostController.getArgStringValue(key: String, backStackEntry: NavBackStackEntry? = this.previousBackStackEntry): String? {
    return this.getStringValue(key = key, backStackEntry = backStackEntry)
}

fun NavHostController.setArgObject(key: String, backStackEntry: NavBackStackEntry? = this.currentBackStackEntry, T: Any?) {
    this.setObject(key = key, T, backStackEntry)
}
private fun NavHostController.getStringValue(key: String, backStackEntry: NavBackStackEntry?): String? {
    return backStackEntry?.savedStateHandle?.getLiveData<String>(key = key)?.value
}

private fun NavHostController.setStringValue(key: String, value: String?, backStackEntry: NavBackStackEntry?) {
    backStackEntry?.savedStateHandle?.set(key = key, value = value)
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

private fun NavHostController.setObject(key: String, T: Any?, backStackEntry: NavBackStackEntry?) {
    if (T == null) {
        this.setStringValue(key = key, value = null, backStackEntry = backStackEntry)
    } else {
        try {
            val json = Gson().toJson(T)
            this.setStringValue(key = key, value = json, backStackEntry = backStackEntry)
        } catch (e: Exception) {

        }
    }
}