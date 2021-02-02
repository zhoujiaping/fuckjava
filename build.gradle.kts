import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    java
    kotlin("jvm") version "1.3.50"
}
//ext.set("kotlin_version","1.3.41")
group = "org.sirenia"
version = "1.0-SNAPSHOT"

repositories {
    //mavenCentral()
    maven {
        setUrl("https://maven.aliyun.com/repository/gradle-plugin")
    }
    maven { setUrl("https://maven.aliyun.com/repository/google") }
    maven { setUrl("http://maven.aliyun.com/nexus/content/groups/public/") }
    maven { setUrl("https://maven.aliyun.com/repository/jcenter") }
}

dependencies {
    implementation(kotlin("stdlib-jdk8"))
    testCompile("junit", "junit", "4.12")
    // https://mvnrepository.com/artifact/org.jetbrains.kotlinx/kotlinx-coroutines-core
    //compile("org.jetbrains.kotlinx","kotlinx-coroutines-core", "1.4.2")
    compile("org.jetbrains.kotlinx","kotlinx-coroutines-core", "1.3.9")

}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}
tasks.withType<KotlinCompile> {
    kotlinOptions.jvmTarget = "1.8"
}