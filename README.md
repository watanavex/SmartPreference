# SmartPreference
[![](https://jitpack.io/v/susan335/SmartPreference.svg)](https://jitpack.io/#susan335/SmartPreference)  

This library makes SharedPreferences convenient.  
SmartPreference is Type-safety.

# Installation
Add it in your root build.gradle at the end of repositories:
```
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
  
Add the dependency
```
	dependencies {
	        implementation 'com.github.susan335:SmartPreference:0.0.1'
	}
```

# Usage
Implement a subclass of Preference class

```
class MyPreference(context: Context): Preference(context) {

    // Declares a property

    // If it is a Nonnull type, use a property delegate from the `notnull` function.
    // The argument is the default value.
    var name: String by notnull("no name")

    // If it is a Nullable type, use a property delegate from the `nullable` function.
    var age: Int? by nullable<Int?>()

}
```

For example, if you use it from an activity ...
```
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val pref = MyPreference(context = this)
        pref.name = "susan"
        pref.age = null

        val myName = pref.name
    }
}
```