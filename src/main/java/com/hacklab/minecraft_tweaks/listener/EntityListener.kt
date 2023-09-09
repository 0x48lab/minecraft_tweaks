package com.hacklab.minecraft_tweaks.listener

import org.bukkit.entity.Creeper
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityExplodeEvent

class EntityListener : Listener {
    @EventHandler
    fun onEntityExplode(event: EntityExplodeEvent) {
        val entity = event.entity
        if (entity is Creeper) {
            // クリーパーが爆発したことを検知
            println("A creeper exploded at location: " + event.location)

            // クリーパーの爆発による被害を防ぐため、爆発イベントをキャンセル
            event.setCancelled(true)
        }
    }
}