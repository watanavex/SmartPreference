package jp.watanave.smartpreference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import kotlin.reflect.KProperty

open class Preference(val sharedPreferences: SharedPreferences) {

    constructor(context: Context) : this(PreferenceManager.getDefaultSharedPreferences(context))

    protected fun <T> notnull(default: T, key: String? = null) = PreferenceProperty(this.sharedPreferences, key, default)
    protected fun <T: Any?> nullable(key: String? = null) = PreferenceNullableProperty<T>(this.sharedPreferences, key)

    protected class PreferenceProperty<T>(val sharedPreferences: SharedPreferences, val key: String?, val default: T) {
        inline operator fun <reified E: T> getValue(thisRef: Any?, property: KProperty<*>): E {
            val key = this.key ?: property.name
            val value = this.sharedPreferences.all[key] as? E
            if (value != null) {
                return value
            }
            return this.default as E
        }

        inline operator fun <reified E: T> setValue(thisRef: Any?, property: KProperty<*>, value: E) {
            val key = this.key ?: property.name
            this.sharedPreferences.edit {
                when (value) {
                    is Int -> this.putInt(key, value)
                    is String -> this.putString(key, value)
                    is Boolean -> this.putBoolean(key, value)
                    is Float -> this.putFloat(key, value)
                    is Long -> this.putLong(key, value)
                    else -> throw ClassCastException()
                }
            }
        }
    }

    protected class PreferenceNullableProperty<T: Any?>(val sharedPreferences: SharedPreferences, val key: String?) {
        inline operator fun <reified E: T> getValue(thisRef: Any?, property: KProperty<*>): E? {
            val key = this.key ?: property.name
            return this.sharedPreferences.all[key] as? E
        }

        inline operator fun <reified E: T> setValue(thisRef: Any?, property: KProperty<*>, value: E?) {
            val key = this.key ?: property.name
            this.sharedPreferences.edit {
                if (value == null) {
                    this.remove(key)
                    return@edit
                }
                when (value) {
                    is Int -> this.putInt(key, value)
                    is String -> this.putString(key, value)
                    is Boolean -> this.putBoolean(key, value)
                    is Float -> this.putFloat(key, value)
                    is Long -> this.putLong(key, value)
                    else -> throw ClassCastException()
                }
            }
        }
    }
}