package co.winnerpov.luabox

import org.luaj.vm2.compiler.LuaC
import org.luaj.vm2.lib.*
import java.io.IOException
import org.luaj.vm2.Globals
import org.luaj.vm2.LoadState
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.*

class LuaScript(
    private val fileName: String,
    private val luaBox: LuaBox
    ) {

    private val globals = Globals()

    init {

        globals.load(JseBaseLib())
        globals.load(PackageLib())
        globals.load(Bit32Lib())
        globals.load(TableLib())
        globals.load(StringLib())
        globals.load(CoroutineLib())
        globals.load(JseMathLib())
        globals.load(JseIoLib())
        globals.load(JseOsLib())
        globals.load(LuajavaLib())

        globals.set("luabox", CoerceJavaToLua.coerce(luaBox))
        globals.set("script", CoerceJavaToLua.coerce(this))

        luaBox.indexes.forEach {
            globals.set(it.key, it.value)
        }

        LoadState.install(globals)
        LuaC.install(globals)
    }

    fun path(): String = luaBox.path

    fun call() {

        val chunk = globals.loadfile(path() + fileName)

        try {
            setIndexes()
            chunk.call()
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

    fun callFunction(functionName: String, vararg luaValues: LuaValue) {

        val chunk = globals.loadfile(path() + fileName)

        try {
            setIndexes()
            chunk.call()
            globals[functionName].invoke(luaValues)
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
    }

    private fun setIndexes() = luaBox.indexes.forEach {
        globals.set(it.key, it.value)
    }
}