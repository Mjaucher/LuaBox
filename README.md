# LuaBox
Helper for call lua scripts on Kotlin!

Big thanks to [Luaj](https://github.com/luaj/luaj) library!

### 1. Create a class for working with LuaBox:

```kotlin
class ScriptCore: LuaBox(
    path = "${System.getProperty("user.home")}${File.separator}LuaBoxFolder${File.separator}"
) {
    
}
```

### 2. Add new indexes:

If you want to add some Kotlin or Java object to your lua script use the `addIndex(key: String, value: Any)` function:

*Kotlin:*

```kotlin
// ScriptCore.kt

companion object {
    const val greeting = "Hello!"
    fun printHi() = println("Hi!")
}

init {
    addIndex("kotlinObject", Companion)
    addIndex("greeting", greeting)
}
```

*Lua:*

```lua
kotlinObject:printHi()
print(greeting) --Hello!
```

### 3. Call script:

```kotlin
val script = ScriptCore().newScript("Script.lua")
script.call()
```

In this case, the `Script.lua` should be located in `C:/Users/User/LuaBoxFolder/Script.lua`

### 4. Call function:

*Kotlin:*

```kotlin
val script = ScriptCore().newScript("Script.lua")

script.callFunction("function_name")

script.callFunction(
    "function_with_args",
    CoerceJavaToLua.coerce("You Got LuaBoxed!!!"),
    CoerceJavaToLua.coerce(711)
)
```

*Lua:*

```lua
function function_name()
    --some code
end

function function_with_args(arg1, arg2)
    print(arg1 == "You Got LuaBoxed!!!") --true
    print(arg2 == 711) --true
end
```
