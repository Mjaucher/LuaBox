package co.winnerpov.luabox

import org.apache.logging.log4j.LogManager
import org.luaj.vm2.Globals
import org.luaj.vm2.LoadState
import org.luaj.vm2.LuaValue
import org.luaj.vm2.compiler.LuaC
import org.luaj.vm2.lib.*
import org.luaj.vm2.lib.jse.*
import java.io.IOException


class LuaBox(val scriptFolder: String) {

    private val logger = LogManager.getLogger("LuaBox")

    init {

        logger.info("You Got LUABOXED!")
    }

    fun newScript(fileName: String) = LuaScript(fileName, this)

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

            LoadState.install(globals)
            LuaC.install(globals)
        }

        fun path(): String = luaBox.scriptFolder
        
        fun call() {

            val chunk = globals.loadfile(path() + fileName)

            try {
                chunk.call()
            } catch(exception: IOException) {
                exception.printStackTrace()
            }
        }

        fun callFunction(functionName: String, vararg luaValues: LuaValue) {

            val path = LuaValue.valueOf(path() + fileName)

            try {
                globals["dofile"].call(path)
                globals[functionName].invoke(luaValues)
            } catch(exception: IOException) {
                exception.printStackTrace()
            }
        }
    }
}