package dev.staeming.configreader.internal

import tools.jackson.databind.DeserializationFeature
import tools.jackson.databind.SerializationFeature
import tools.jackson.dataformat.javaprop.JavaPropsMapper
import tools.jackson.dataformat.yaml.YAMLMapper
import tools.jackson.dataformat.yaml.YAMLWriteFeature
import tools.jackson.module.kotlin.KotlinModule

internal object Mapper {
    private val kotlinModule = KotlinModule.Builder().build()

    val ymlMapper : YAMLMapper = YAMLMapper
        .builder()
        .addModule(kotlinModule)
        .configure(YAMLWriteFeature.WRITE_DOC_START_MARKER, false)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .enable(SerializationFeature.INDENT_OUTPUT)
        .build()
    val propertiesMapper: JavaPropsMapper = JavaPropsMapper
        .builder()
        .addModule(kotlinModule)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        .build()

}