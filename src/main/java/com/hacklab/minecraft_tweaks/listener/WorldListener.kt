package com.hacklab.minecraft_tweaks.listener

import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.world.WorldLoadEvent
import org.bukkit.event.world.WorldUnloadEvent

class WorldListener : Listener {

    @EventHandler
    fun onWorldLoad(event: WorldLoadEvent) {
        Bukkit.getLogger().info("onWorldLoad called ${event.world.name}")
    }

    @EventHandler
    fun onWorldUnload(event: WorldUnloadEvent) {
        Bukkit.getLogger().info("onWorldUnload called ${event.world.name}")
    }

}