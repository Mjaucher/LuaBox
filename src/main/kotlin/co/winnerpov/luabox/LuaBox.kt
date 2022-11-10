package co.winnerpov.luabox

import org.apache.logging.log4j.LogManager
import org.luaj.vm2.LuaValue
import org.luaj.vm2.lib.jse.CoerceJavaToLua

open class LuaBox(val path: String) {

    private val logger = LogManager.getLogger("LuaBox")
    val indexes = hashMapOf<String, LuaValue>()

    init {

        logger.info("You Got LUABOXED!")
    }

    fun newScript(fileName: String) = LuaScript(fileName, this)

    fun addIndex(key: String, value: Any) {
        indexes[key] = CoerceJavaToLua.coerce(value)
    }
}