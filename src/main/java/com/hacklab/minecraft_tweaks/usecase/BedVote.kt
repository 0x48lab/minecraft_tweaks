package com.hacklab.paper_tweaks.usecase

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.entity.Player
import org.bukkit.event.Listener
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.atomic.AtomicReference

class BedVote {
    private val bedInDay = AtomicLong(0)
    private val world: AtomicReference<World?> = AtomicReference(null)
    private val voters = ConcurrentHashMap.newKeySet<String>()

    private fun checkVotes(): Boolean {
        val onlinePlayerNames = Bukkit.getServer().onlinePlayers.map { it.name }.toSet()
        return voters.containsAll(onlinePlayerNames)
    }

    fun canBedIn(player: Player): Boolean {
        val now = getDay(player.world)
        val bedDay = bedInDay.get()
        if (now == bedDay) {
            return voters.size > 0
        }
        return false
    }

    private fun setWorld(player: Player) {
        val now = getDay(player.world)
        val bedDay = bedInDay.get()
        if (now != bedDay) {
            world.set(player.world)
            voters.clear()
            bedInDay.set(getDay(player.world))
            Bukkit.getServer().onlinePlayers.forEach { onlinePlayer ->
                if (onlinePlayer != player) {
                    onlinePlayer.sendMessage("${player.name} さんがベッドに入りました。チャットで'/sleep'と入力するとベッドに入ったことにできます。")
                }
            }
        }
    }

    fun bedIn(player: Player) {
        setWorld(player)
        voters.add(player.name)
        if (checkVotes()) {
            world.get()?.time = 0
            world.set(null)
        }
    }

    fun getDay(world: World): Long {
        return world.fullTime / 24000
    }

}