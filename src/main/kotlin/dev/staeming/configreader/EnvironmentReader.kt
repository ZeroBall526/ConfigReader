package dev.staeming.configreader

interface EnvironmentReader {
    fun getValue(key: String): String?

    fun getAllKeys(): List<String>

    fun getAllValues(): List<String>
}