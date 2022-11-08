import co.winnerpov.luabox.LuaBox
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.luaj.vm2.LuaValue
import java.io.File

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class LuaBoxTest {

    private val generalPath = "${System.getProperty("user.dir")}${File.separator}luabox${File.separator}"
    private val luaBox = LuaBox(generalPath)
    private val testScript = luaBox.newScript("Main.lua")

    init {

        println(testScript.path())
    }

    @Test
    fun `call script`() = testScript.call()

    @Test
    fun `call function from script`() = testScript.callFunction("helloWorld")

    @Test
    fun `call function with variable args`() =
        testScript.callFunction(
            "sum",
            LuaValue.valueOf(2),
            LuaValue.valueOf(2)
        )
}