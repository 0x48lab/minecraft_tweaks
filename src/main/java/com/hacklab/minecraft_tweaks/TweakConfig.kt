package com.hacklab.minecraft_tweaks

import org.bukkit.plugin.java.JavaPlugin

data class TweakConfig(val plugin: JavaPlugin) {
    private val configFile = plugin.config

    val isNightAutoSkip: Boolean
        get() = configFile.getBoolean("night_auto_skip") ?: false

}