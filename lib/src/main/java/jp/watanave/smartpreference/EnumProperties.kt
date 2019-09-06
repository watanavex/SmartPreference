package jp.watanave.smartpreference

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlin.reflect.KProperty

class PreferenceEnumProperty<T: Enum<*>>(val sharedPreferences: SharedPreferences, val key: String?, val default: T) {
    inline operator fun <reified E: T> getValue(thisRef: Any?, property: KProperty<*>): E {
        val key = this.key ?: property.name

        return this.sharedPreferences.getString(key, null)?.let { name ->
            E::class.java.enumConstants?.first { it.name == name }
        } ?: this.default as E
    }

    inline operator fun <reified E: T> setValue(thisRef: Any?, property: KProperty<*>, value: E) {
        val key = this.key ?: property.name
        this.sharedPreferences.edit {
            this.putString(key, value.name)
        }
    }
}

class PreferenceNullableEnumProperty<T: Enum<*>?>(val sharedPreferences: SharedPreferences, val key: String?) {
    inline operator fun <reified E: T> getValue(thisRef: Any?, property: KProperty<*>): E? {
        val key = this.key ?: property.name

        return this.sharedPreferences.getString(key, null)?.let { name ->
            E::class.java.enumConstants?.first { it!!.name == name }
        }
    }

    inline operator fun <reified E: T> setValue(thisRef: Any?, property: KProperty<*>, value: E?) {
        val key = this.key ?: property.name
        this.sharedPreferences.edit {
            if (value == null) {
                this.remove(key)
                return@edit
            }
            this.putString(key, value.name)
        }
    }
}