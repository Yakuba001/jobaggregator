plugins {
    application
}

group = "com.jobaggregator"
version = "0.1.0-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(25)
    }
}

application {
    mainClass.set("com.jobaggregator.Main")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("tools.jackson.core:jackson-databind:3.2.4")
    implementation("org.jsoup:jsoup:1.24.1")

    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.assertj:assertj-core:3.27.7")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()
}