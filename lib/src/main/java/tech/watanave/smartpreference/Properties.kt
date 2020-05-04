package tech.watanave.smartpreference

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

class PreferenceProperty<T>(val sharedPreferences: SharedPreferences, val key: String?, val default: T) {
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

class PreferenceNullableProperty<T: Any?>(val sharedPreferences: SharedPreferences, val key: String?) {
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