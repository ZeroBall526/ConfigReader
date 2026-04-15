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
            artifact(tasks.shadowJar)

            artifact(tasks.named("sourcesJar"))
            artifact(tasks.named("javadocJar"))

            groupId = "dev.staeming"
            artifactId = "ConfigReader"
            version = version

            pom {
                withXml {
                    asNode().appendNode("dependencies")
                }
            }
        }
    }

    val repoUsername = (project.findProperty("JFROG_USER") as String?)
        ?: System.getenv("JFROG_USER")

    val repoPassword = (project.findProperty("JFROG_PASSWORD") as String?)
        ?: System.getenv("JFROG_PASSWORD")

    if(repoUsername != null && repoPassword != null){
        repositories {
            maven {
                val releasesRepoUrl = uri(System.getenv("release_local_url") ?: error("JFROG_RELEASE_URL not set"))
                val snapshotsRepoUrl = uri(System.getenv("dev_local_url")  ?: error("JFROG_DEV_URL not set"))

                url = if (version.toString().endsWith("SNAPSHOT")) {
                    snapshotsRepoUrl
                } else {
                    releasesRepoUrl
                }

                isAllowInsecureProtocol = true

                credentials {
                    username = repoUsername
                    password = repoPassword
                }
            }
        }
    }
}