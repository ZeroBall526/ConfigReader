import dev.staeming.configreader.ConfigManager
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals

class ConfigResetTest {
    @Test
    fun resetTest(){
        val i = ConfigManager.getOrCreateConfig("test.yaml", TestConfig::class.java)

        val new = TestConfig(333, 321.4f, false, 1.9, "banana")
        i.write(new)

        i.reset()
        assertEquals(TestConfig(), i.load())
    }

    @AfterTest
    fun delete(){
        File("test.yaml").delete()
    }
}