package dev.staeming.configreader.internal

import dev.staeming.configreader.EnvironmentReader
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

internal class EnvironmentLoader: EnvironmentReader {
    private val environment : HashMap<String, String> = HashMap()

    constructor(path: String){
        try {
            BufferedReader(FileReader(path)).use { br ->
                var line: String?
                while ((br.readLine().also { line = it }) != null) {
                    if(line != null && (line.isEmpty() || line.trim().startsWith("#"))) continue

                    val original: List<String> = line?.split("=") ?:continue
                    environment[original[0]] = original[1].trim().removeSurrounding("\"")
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun getValue(key: String): String? = environment[key]

    override fun getAllKeys(): List<String> = environment.keys.toList()

    override fun getAllValues(): List<String> = environment.values.toList()
}