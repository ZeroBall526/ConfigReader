import dev.staeming.configreader.ConfigManager
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ConfigFileGenTest {

    @Test
    fun yamlGenerator(){
        val i = ConfigManager.getOrCreateConfig("test.yaml", TestConfig::class.java)

        assertTrue(File("test.yaml").length() > 0, "config file is not written to test.yaml")

        val res = i.load() as TestConfig
        assertEquals(res, TestConfig())
    }

    @Test
    fun customLoadTest(){
        val i = ConfigManager.getOrCreateConfig("test.yaml", TestConfig::class.java)

        val res = i.load(PartialConfig::class.java) as PartialConfig

        println(res.e)
        println(res.s)

    }

    @Test
    fun propertiesGenerator(){
        val i = ConfigManager.getOrCreateConfig("test.properties", TestConfig::class.java)

        assertTrue(File("test.properties").length() > 0, "config file is not written to test.properties")

        val res = i.load() as TestConfig
        assertEquals(res, TestConfig())
    }

    @AfterTest
    fun delete(){
        File("test.yaml").delete()
        File("test.properties").delete()
    }
}