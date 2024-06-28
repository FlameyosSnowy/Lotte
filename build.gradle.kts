plugins {
    id("java")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version("8.1.1")
}

group = "me.flame.lotte"
version = "1.0.0"

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("org.jetbrains:annotations:24.0.1")
    annotationProcessor("org.jetbrains:annotations:24.0.1")
}

tasks.shadowJar {
    archiveClassifier.set("")
}
java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

publishing {
    repositories {
        maven {
            name = "mainRepository"
            url = uri("https://repo.foxikle.dev/flameyos")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()
            artifact(tasks["shadowJar"])
        }
    }
}

tasks {
    java.toolchain.languageVersion.set(JavaLanguageVersion.of(17))

    javadoc {
        (options as StandardJavadocDocletOptions).tags("apiNote:a:API Note:")
        options.encoding = Charsets.UTF_8.name()
        exclude("**/internal/**", "**/versions/**")
    }

    processResources {
        filteringCharset = Charsets.UTF_8.name()
    }

    jar {
        archiveClassifier = "core"
    }
}