package dev.staeming.configreader

import dev.staeming.configreader.internal.ConfigLoader

/**
 * Provides access to ConfigReader Instance
 * This class handles the initialization and retrieval of configuration files
 */
object ConfigManager {
    /**
     * Retrieves an existing configuration or creates a new config file if it does not exist at the specified path.
     * * This method initializes the configuration file with default values defined in the
     * [values] class if the file is missing.
     *
     * @param T The type of the configuration values, must extend [ConfigValues].
     * @param path The config file path where the configuration file is located.
     * @param values The class reference of the configuration configValues model. values class must extend [ConfigValues]
     * @return A [ConfigReader] instance initialized for the given path.
     */
    fun <T : ConfigValues>getOrCreateConfig(path: String, values: Class<T>) : ConfigReader {
        return ConfigLoader(path,values)
    }

    /**
     * Retrieves an existing configuration at the specified path.
     * * This method initializes the configuration file with default values defined in the
     * [values] class if the file is missing.
     *
     * @param T The type of the configuration values, must extend [ConfigValues].
     * @param path The config file path where the configuration file is located.
     * @param values The class reference of the configuration configValues model. values class must extend [ConfigValues]
     * @return A [ConfigReader] instance initialized for the given path.
     */
    fun <T : ConfigValues>getConfig(path: String, values: Class<T>) : ConfigReader {
        return ConfigLoader(path,values,1)
    }
}