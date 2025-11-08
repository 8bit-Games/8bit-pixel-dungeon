plugins {
    kotlin("jvm")
    application
}

dependencies {
    val gdxVersion: String by project

    implementation(project(":core"))
    implementation("com.badlogicgames.gdx:gdx-backend-lwjgl3:$gdxVersion")
    implementation("com.badlogicgames.gdx:gdx-platform:$gdxVersion:natives-desktop")
}

application {
    mainClass.set("com.hardcoredungeon.DesktopLauncherKt")
}

tasks.register<JavaExec>("run") {
    dependsOn(tasks.classes)
    mainClass.set(application.mainClass)
    classpath = sourceSets.main.get().runtimeClasspath
    standardInput = System.`in`
    workingDir = file("../assets")
    isIgnoreExitValue = true
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}
