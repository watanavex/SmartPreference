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

    protected fun <T: Enum<*>> enum(default: T, key: String? = null) = PreferenceEnumProperty(this.sharedPreferences, key, default)
    protected fun <T: Enum<*>?> nullableEnum(key: String? = null) = PreferenceNullableEnumProperty<T>(this.sharedPreferences, key)

}