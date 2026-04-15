package dev.staeming.configreader.internal

import org.apache.commons.io.FilenameUtils
import java.io.File
import java.nio.file.Files
import kotlin.io.path.Path

internal class SettingsFile {
    var settingFile : File? = null
    var settingFileEx : String? = null

    val allowExtensions = listOf("yml","yaml","properties")

    fun setFileSrc(src: String){
        settingFile = File(src)

        if(Files.exists(Path(src)) && isRightFileExtension()) {
           settingFileEx = FilenameUtils.getExtension(src.lowercase())
           return
       }else{
           throw IllegalArgumentException("Config file read error: $src")
       }
    }

    fun isRightFileExtension() : Boolean {
        val sf = settingFile?.path ?: throw Exception("file is not exist error")
        val fileEx = FilenameUtils.getExtension(sf).lowercase()

        return allowExtensions.contains(fileEx)
    }
}