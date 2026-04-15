package dev.staeming.configreader.internal

import dev.staeming.configreader.ConfigReader
import dev.staeming.configreader.ConfigValues
import kotlin.io.path.Path
import kotlin.io.path.exists

internal class ConfigLoader<T: ConfigValues>(path: String, values: Class<T>, mods: Int = 0): ConfigReader {
    private var configFile: SettingsFile = SettingsFile()
    private var targetClass: Class<T> = values

    private var configCache: ConfigValues? = null

    //TODO swap int to enum
    init {
        when(mods){
            0 -> createOrReadConfig(path, values)
            1 -> readConfig(path)
        }
    }

    override val getConfigFilePath = configFile.settingFile?.path

    override val configExtension = configFile.settingFileEx

    override fun load(): ConfigValues {
        if(getConfigFilePath == null || configExtension == null){
            throw IllegalStateException("Config file could not be loaded")
        }

       return configCache ?:FileDataParser().getSettingValues(Path(getConfigFilePath), targetClass, configExtension).also {
           configCache = it
       }
    }

    override fun <T : ConfigValues> load(configClass: Class<T>): ConfigValues {
        if(getConfigFilePath == null || configExtension == null){
            throw IllegalStateException("Config file could not be loaded")
        }

        return FileDataParser().getSettingValues(Path(getConfigFilePath), configClass, configExtension)
    }

    override fun write(newConfigValues: ConfigValues) {
        val convertedPath = configFile.settingFile?.path ?: throw IllegalArgumentException("Config file could not be initialized")
        val writer = FileManager(convertedPath)
        writer.writeFile(newConfigValues)
        configCache = newConfigValues
    }

    override fun reset() {
        configCache = targetClass.getDeclaredConstructor().newInstance()
        val convertedPath = configFile.settingFile?.path ?: throw IllegalArgumentException("Config file could not be initialized")
        val writer = FileManager(convertedPath)
        writer.writeFile(targetClass)
    }

    override fun reload() {
        if(getConfigFilePath == null){
            throw Exception("Config file could not be loaded")
        }

        readConfig(getConfigFilePath)
    }

    private fun createOrReadConfig(path: String, values: Class<T>){
        //phase 0: check file exist
        val convertedPath = Path(path)

        //phase 0.5: create new config file
        if(!convertedPath.exists()){
            val gen = FileManager(convertedPath)
            gen.writeFile(values)
        }
        //phase 1: init logic for class
        configCache = null
        configFile.setFileSrc(path)
    }

    private fun readConfig(path: String) {
        configCache = null
        configFile.setFileSrc(path)
    }
}