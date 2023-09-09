package com.hacklab.minecraft_tweaks

import com.hacklab.minecraft_tweaks.listener.EntityListener
import com.hacklab.minecraft_tweaks.listener.PlayerListener
import com.hacklab.minecraft_tweaks.listener.WorldListener
import com.hacklab.minecraft_tweaks.usecase.WorldTimer
import com.hacklab.paper_tweaks.command.HomeCommand
import com.hacklab.paper_tweaks.command.PositionCommand
import com.hacklab.paper_tweaks.command.SleepCommand
import com.hacklab.paper_tweaks.usecase.BedVote
import org.bukkit.plugin.java.JavaPlugin
import org.koin.core.context.GlobalContext
import org.koin.core.context.stopKoin
import org.koin.dsl.module

class Main : JavaPlugin() {

    override fun onEnable() {
        // Plugin startup logic
        GlobalContext.startKoin {
            modules(listOf(module))
        }
        server.pluginManager.registerEvents(PlayerListener(), this)
        server.pluginManager.registerEvents(EntityListener(), this)
        server.pluginManager.registerEvents(WorldListener(), this)

        getCommand("position")!!.setExecutor(PositionCommand(this))
        getCommand("home")!!.setExecutor(HomeCommand(this))
        getCommand("sleep")!!.setExecutor(SleepCommand(this))
    }

    override fun onDisable() {
        // Plugin shutdown logic
        stopKoin()
    }

    private val module = module {
        single { this@Main }
        single { TweakConfig(this@Main) }
        single { WorldTimer() }
        single { BedVote() }
    }

}