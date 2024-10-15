import xyz.jpenilla.runpaper.task.RunServer

plugins {
    id("java")
    id("net.minecrell.plugin-yml.paper") version "0.6.0"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

group = "top.faved.ultracord"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
    maven("https://jitpack.io")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
    paperLibrary("com.github.Carleslc.Simple-YAML:Simple-Yaml:1.8.4")
    paperLibrary("net.dv8tion:JDA:5.1.2")
    paperLibrary("io.github.revxrsal:lamp.common:4.0.0-beta.17")
    paperLibrary("io.github.revxrsal:lamp.bukkit:4.0.0-beta.17")
    paperLibrary("io.github.revxrsal:lamp.jda:4.0.0-beta.17")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.withType<RunServer> {
    minecraftVersion("1.21.1")
}

tasks.withType(xyz.jpenilla.runtask.task.AbstractRun::class) {
    javaLauncher = javaToolchains.launcherFor {
        @Suppress("UnstableApiUsage")
        vendor = JvmVendorSpec.JETBRAINS
        languageVersion = JavaLanguageVersion.of(21)
    }
    jvmArgs("-Xms768M", "-Xmx1536M", "-XX:+AllowEnhancedClassRedefinition", "-XX:+UseG1GC",
        "-XX:+ParallelRefProcEnabled", "-XX:MaxGCPauseMillis=200", "-XX:+UnlockExperimentalVMOptions",
        "-XX:+DisableExplicitGC", "-XX:+AlwaysPreTouch", "-XX:G1HeapWastePercent=5", "-XX:G1MixedGCCountTarget=4",
        "-XX:InitiatingHeapOccupancyPercent=15", "-XX:G1MixedGCLiveThresholdPercent=90",
        "-XX:G1RSetUpdatingPauseTimePercent=5", "-XX:SurvivorRatio=32", "-XX:+PerfDisableSharedMem",
        "-XX:MaxTenuringThreshold=1", "-Dusing.aikars.flags=https://mcflags.emc.gs", "-Daikars.new.flags=true",
        "-XX:G1NewSizePercent=30", "-XX:G1MaxNewSizePercent=40", "-XX:G1HeapRegionSize=8M", "-XX:G1ReservePercent=20")
}

paper {
    apiVersion = "1.21"
    main = "top.faved.ultracord.UltraCord"
    loader = "top.faved.ultracord.UltraCordLoader"
    description = "The ultimate Discord to Minecraft plugin, including Discord <-> Minecraft chatting, linking," +
            " and more!"
    website = "https://github.com/CerialPvP/UltraCord"
    authors = listOf("CerialPvP", "EmirhanTr3")
    generateLibrariesJson = true
}