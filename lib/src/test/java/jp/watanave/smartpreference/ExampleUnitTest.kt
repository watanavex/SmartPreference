package jp.watanave.smartpreference

import android.app.Application
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.runner.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleUnitTest {

    class TestPreference(context: Context): Preference(context) {
        var intValue: Int by notnull(4)
        var intNullableValue: Int? by nullable<Int?>()

        var stringValue: String by notnull("hoge")
        var stringNullableValue: String? by nullable<String?>()

        var keyCheck1: String by notnull("hoge")
        var keyCheck2: String by notnull("hoge", "keyCheck1")
    }

    @Test
    fun intValue() {
        val context = getApplicationContext<Application>()
        val preference = TestPreference(context)

        Assert.assertEquals(4, preference.intValue)
        preference.intValue = 8
        Assert.assertEquals(8, preference.intValue)

        Assert.assertNull(preference.intNullableValue)
        preference.intNullableValue = 12
        Assert.assertEquals(12, preference.intNullableValue)
        preference.intNullableValue = null
        Assert.assertNull(preference.intNullableValue)
    }

    @Test
    fun stringValue() {
        val context = getApplicationContext<Application>()
        val preference = TestPreference(context)

        Assert.assertEquals("hoge", preference.stringValue)
        preference.stringValue = "fuga"
        Assert.assertEquals("fuga", preference.stringValue)

        Assert.assertNull(preference.stringNullableValue)
        preference.stringNullableValue = "poyo"
        Assert.assertEquals("poyo", preference.stringNullableValue)
        preference.stringNullableValue = null
        Assert.assertNull(preference.stringNullableValue)
    }

    @Test
    fun checkKey() {
        val context = getApplicationContext<Application>()
        val preference = TestPreference(context)

        Assert.assertEquals("hoge", preference.keyCheck2)
        preference.keyCheck1 = "fuga"
        Assert.assertEquals("fuga", preference.keyCheck2)
        preference.keyCheck2 = "poyo"
        Assert.assertEquals("poyo", preference.keyCheck1)
    }
}
