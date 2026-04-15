group = "dev.staeming"
version = "1.0.0b"

plugins{
    kotlin("jvm") version "2.2.0"
    id("com.gradleup.shadow") version "9.4.1"
    id("maven-publish")
}

java{
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
}

dependencies{
    implementation("tools.jackson.module:jackson-module-kotlin:3.1.2")
    implementation("tools.jackson.dataformat:jackson-dataformat-yaml:3.1.2")
    implementation("tools.jackson.dataformat:jackson-dataformat-properties:3.1.2")
    implementation("commons-io:commons-io:2.21.0")
    implementation(kotlin("stdlib"))
    testImplementation(kotlin("test"))
}

tasks.named<Jar>("jar"){
    enabled = false
}

tasks.shadowJar{
    archiveClassifier.set("")
    relocate("tools.jackson", "dev.staeming.configreader.internal.jackson")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            from(components["java"])
            artifact(tasks.shadowJar)

            groupId = "dev.staeming"
            artifactId = "ConfigReader"
            version = version
        }
    }
}