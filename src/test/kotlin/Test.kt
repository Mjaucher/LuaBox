import co.winnerpov.luabox.LuaBox
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.luaj.vm2.LuaValue
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScriptCore: LuaBox(
    path = "${System.getProperty("user.dir")}${File.separator}luabox${File.separator}"
) {

    private val mainScript = newScript("Main.lua")

    init {

        println(path)

        // adding new indexes
        addIndex("kotlinObject", Companion)
        addIndex("greeting", greeting)
    }

    @Test
    fun `call script`() = mainScript.call()

    @Test
    fun `call function from script`() = mainScript.callFunction("helloWorld")

    @Test
    fun `call function with variable args`() =
        mainScript.callFunction(
            "sum",
            LuaValue.valueOf(2),
            LuaValue.valueOf(2)
        )

    companion object {

        const val greeting = "Hello!"

        fun printHi() = println("Hi!")
    }
}