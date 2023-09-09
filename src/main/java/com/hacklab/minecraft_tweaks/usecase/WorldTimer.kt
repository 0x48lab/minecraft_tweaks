package com.hacklab.minecraft_tweaks.usecase

import com.hacklab.minecraft_tweaks.Main
import com.hacklab.minecraft_tweaks.TweakConfig
import org.bukkit.Bukkit
import org.bukkit.World
import org.koin.java.KoinJavaComponent

class WorldTimer {
    private val javaPlugin: Main by KoinJavaComponent.inject(Main::class.java)
    private val config: TweakConfig by KoinJavaComponent.inject(TweakConfig::class.java)

    init {
        Bukkit.getScheduler().runTaskTimer(javaPlugin, Runnable {
            checkAndSkipNight()
        }, 0, 20)
    }

    private fun checkAndSkipNight() {
        if (config.isNightAutoSkip) {
            // すべてのワールドを対象にタイムスキップを監視
            for (world: World in Bukkit.getWorlds()) {
                val time = world.time
                // モンスターが出現する時間（夜）になったら朝に変更
                if (time in 13000..13100) { // 13000 は夜の始まり、13100 は夜の終わり
                    world.time = 0 // 朝に変更
                }
            }
        }
    }

}