package dev.staeming.configreader

/**
 * Interface for managing configuration files such as YAML and Properties.
 * * This interface provides a standardized way to handle the lifecycle of configuration data,
 * including loading from disk, persisting data, and refreshing internal states.
 * Implementations should handle the underlying file I/O and format-specific parsing.
 */
interface ConfigReader {
    /**
     * Loads the configuration from the physical file and maps it to a [ConfigValues] object.
     * @return An instance of [ConfigValues] populated with data from the file.
     * @throws IllegalStateException If the file path is uninitialized or the file is inaccessible.
     * @throws RuntimeException If a parsing error occurs during the load process.
     */
    fun load(): ConfigValues

    /**
     * Loads the configuration from the physical file and maps it to a [configClass] object.
     * @param configClass The [Class] object representing the specific subtype of [ConfigValues] to be instantiated.
     * @return An instance of [T] populated with data from the file.
     * @throws IllegalStateException If the file path is uninitialized or the file is inaccessible.
     * @throws RuntimeException If a parsing error occurs during the load process.
     */
    fun <T: ConfigValues>load(configClass: Class<T>): ConfigValues

    /**
     * Persists the structure or data of the specified [ConfigValues] class to the file system.
     * * WARNING:
     * Calling this method will **overwrite any existing content** in the configuration file.
     * Any manually added comments or unmapped properties may be lost during the process.
     * @param newConfigValues The class type of the configuration to be written.
     * Typically used to generate a template or a default file.
     * @throws IllegalArgumentException If the destination file path has not been properly initialized.
     */
    fun write(newConfigValues: ConfigValues)

    /**
     * Resets the current configuration file to its default state.
     * * WARNING:
     * Calling this method will **overwrite any existing content** in the configuration file.
     */
    fun reset()

    /**
     * Reloads the configuration by re-reading the file from the disk.
     * * This method synchronizes the internal state of the reader with the current
     * content of the configuration file on the physical drive.
     * @throws Exception If the file cannot be found or an error occurs during the refresh.
     */
    fun reload()

    /**
     * Returns the file path of the current configuration.
     * @return The string representation of the path, or null if no file is associated.
     */
    val getConfigFilePath: String?

    /**
     * Returns the file extension of the current configuration file.
     * * Common values include "yml", "yaml", or "properties".
     * @return The extension string, or null if the file information is missing.
     */
    val configExtension: String?
}