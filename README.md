# LuaBox
Helper for call lua scripts on Kotlin, Java and other JVM languages!

### 1. Initialize LuaBox:

```kotlin
val luaBoxFolderPath = "C:/Users/User/LuaBoxFolder/"
val luaBox = LuaBox(luaBoxFolderPath)
```

### 2. Add new indexes:

If you want to add some Kotlin or Java object to your lua script use the `addIndex(key: String, value: Any)` function:

*Kotlin:*

```kotlin
object KotlinObject {
    const val greeting = "Hello!"
    fun printHi() = println("Hi!")
}

luaBox.addIndex("kotlinObj", KotlinObject)
luaBox.addIndex("greeting", KotlinObject.greeting)
```

*Lua:*

```lua
kotlinObj:printHi()
print(greeting) --Hello!
```

### 3. Call script:

```kotlin
val script = luaBox.newScript("Script.lua")
script.call()
```

In this case, the `Script.lua` should be located in `C:/Users/User/LuaBoxFolder/Script.lua`

### 4. Call function:

*Kotlin:*

```kotlin
val script = luaBox.newScript("Script.lua")

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
