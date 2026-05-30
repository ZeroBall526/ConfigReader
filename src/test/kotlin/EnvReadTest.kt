import dev.staeming.configreader.ConfigManager
import org.junit.Test

class EnvReadTest {
    @Test
    fun testEnvRead() {
        val secret = ConfigManager.getEnv(".env")
        /*
        println(secret.getValue("s1"))
        println(secret.getValue("s2"))
        println(secret.getValue("s3"))
        println(secret.getValue("ss"))

         */
    }
}