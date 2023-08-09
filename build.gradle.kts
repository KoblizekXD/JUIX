plugins {
    id("java")
    `java-library`
}

group = "lol.koblizek"
version = "0.1"

repositories {
    maven {
        name = "jopgamerRepoReleases"
        url = uri("https://repo.jopga.me/releases")
    }
    mavenCentral()
}

dependencies {
    implementation("lol.koblizek:JUI-Win32:1.0")
    implementation("org.jetbrains:annotations:24.0.0")

    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor("org.projectlombok:lombok:1.18.28")
    implementation("org.apache.logging.log4j:log4j-core:2.20.0")
    implementation("org.apache.logging.log4j:log4j-api:2.20.0")
    implementation("org.apache.logging.log4j:log4j-slf4j2-impl:2.20.0")
    implementation("org.reflections:reflections:0.10.2")

    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(20)
    }
}

tasks.test {
    useJUnitPlatform()
}