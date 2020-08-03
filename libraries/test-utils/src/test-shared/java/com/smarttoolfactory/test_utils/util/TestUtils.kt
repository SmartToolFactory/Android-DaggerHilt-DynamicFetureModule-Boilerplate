package com.smarttoolfactory.test_utils.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Use this method to get json files as string from resources folder to use in tests.
 */
fun getResourceAsText(path: String): String {
    return object {}.javaClass.classLoader!!.getResource(path)!!.readText()
}

inline fun <reified T> convertToObjectFromString(input: String): T? {
    return Gson().fromJsonWithType<T>(input)
}

inline fun <reified T> Gson.fromJsonWithType(json: String): T? =
    fromJson<T>(json, object : TypeToken<T>() {}.type)
