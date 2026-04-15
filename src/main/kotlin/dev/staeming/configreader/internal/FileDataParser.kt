package dev.staeming.configreader.internal

import dev.staeming.configreader.ConfigValues
import dev.staeming.configreader.internal.Mapper.propertiesMapper
import dev.staeming.configreader.internal.Mapper.ymlMapper
import java.nio.file.Path
import java.util.Properties

internal class FileDataParser {
    fun<T: ConfigValues> getSettingValues(configFile: Path, clazz: Class<T>, ext: String): ConfigValues {
        return when(ext) {
            "yaml", "yml" -> ymlParser(configFile, clazz)
            "properties" -> propertiesParser(configFile, clazz)
            else -> throw IllegalArgumentException("Unsupported config file format: $ext")
        }
    }

    private fun<T: ConfigValues> ymlParser(configFile: Path, clazz: Class<T>): ConfigValues {
        return ymlMapper.readValue(configFile.toFile(), clazz)
    }

    private fun<T: ConfigValues> propertiesParser(configFile: Path, clazz: Class<T>): ConfigValues {
        val props = Properties()
        configFile.toFile().inputStream().use { props.load(it) }
        val map = props.entries.associate{ (k,v) ->
            k.toString() to v.toString()
        }
        return propertiesMapper.convertValue(map, clazz)
    }
}