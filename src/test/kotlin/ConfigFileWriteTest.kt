import dev.staeming.configreader.ConfigManager
import java.io.File
import kotlin.test.AfterTest
import kotlin.test.Test
import kotlin.test.assertNotEquals

class ConfigFileWriteTest {
    @Test
    fun ymlFileWrite() {
        val i = ConfigManager.getOrCreateConfig("test.yaml", TestConfig::class.java)

        val res = (i.load() as TestConfig).copy()
        val newObj = i.load() as TestConfig
        newObj.a += 1
        newObj.b = 9.1f
        newObj.c = false
        newObj.d = 3.9
        newObj.e = "chicken"

        i.write(newObj)
        val aft = i.load() as TestConfig
        assertNotEquals(aft, res)
    }

    @Test
    fun propertiesGenerator(){
        val i = ConfigManager.getOrCreateConfig("test.properties", TestConfig::class.java)

        val res = (i.load() as TestConfig).copy()
        val newObj = i.load() as TestConfig
        newObj.a += 1
        newObj.b = 9.1f
        newObj.c = false
        newObj.d = 3.9
        newObj.e = "chicken"

        i.write(newObj)
        val aft = i.load() as TestConfig
        assertNotEquals(aft, res)
    }

    @AfterTest
    fun delete(){
        File("test.yaml").delete()
        File("test.properties").delete()
    }
}