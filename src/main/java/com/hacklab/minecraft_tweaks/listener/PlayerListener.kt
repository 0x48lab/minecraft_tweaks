package com.hacklab.minecraft_tweaks.listener

import codes.quine.labo.lite.romaji.Romaji
import com.hacklab.paper_tweaks.usecase.BedVote
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerChatEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerItemBreakEvent
import org.koin.java.KoinJavaComponent

class PlayerListener : Listener {
    private val bedVote: BedVote by KoinJavaComponent.inject(BedVote::class.java)

    @EventHandler
    fun onPlayerItemBreakEvent(event: PlayerItemBreakEvent) {
        //Bukkit.getLogger().info("onPlayerItemBreakEvent called event.hasBlock()=${event.brokenItem.type}")
        val player = event.player
        val inventory = player.inventory
        val brokenItem = event.brokenItem
        val itemSlot = inventory.contents.indexOf(brokenItem)
        if (inventory.contains(brokenItem.type)) {
            val list = inventory.contents
            for (i in list.indices) {
                val itemStack = list[i]
                if (itemStack != null && itemStack.type == brokenItem.type && itemStack !== brokenItem && i != itemSlot) {
                    inventory.setItem(itemSlot, itemStack)
                    inventory.setItem(i, null)
                    break
                }
            }
        }
    }

    @EventHandler
    fun onPlayerInteract(event: PlayerInteractEvent) {
        // Check if the event has a block (i.e., the player is placing a block)
        //Bukkit.getLogger().info("onPlayerInteract called event.hasBlock=${event.hasBlock()} hasItem=${event.hasItem()}")
        if (event.action == Action.RIGHT_CLICK_AIR) {
            val player = event.player
            val itemInHand = player.inventory.itemInMainHand
            if (itemInHand.type.isBlock && itemInHand.amount == 1 && itemInHand.maxStackSize > 1) {
                Bukkit.getLogger().info("onPlayerInteract itemInHand= ${itemInHand.type}")
                val inventory = player.inventory
                val itemSlot = inventory.heldItemSlot
                if (inventory.contains(itemInHand.type)) {
                    val list = inventory.contents
                    for (i in list.indices) {
                        val itemStack = list[i]
                        if (itemStack != null && itemStack.type == itemInHand.type && itemStack !== itemInHand && i != itemSlot) {
                            Bukkit.getLogger().info("$i -> $itemSlot")
                            inventory.setItem(itemSlot, itemStack)
                            inventory.setItem(i, null)
                            break
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    fun onPlayerBedEnter(event: PlayerBedEnterEvent) {
        val player = event.player
        if (Bukkit.getOnlinePlayers().size > 1) {
            bedVote.bedIn(player)
        }
    }

    @EventHandler
    fun onPlayerChat(event: PlayerChatEvent) {
        val message = event.message

        // ローマ字を日本語に変換する処理を実装
        val translatedMessage: String = convertRomajiToJapanese(message)

        // 変換後のメッセージをプレイヤーに送信
        event.player.sendMessage("<${event.player.name}> $message ($translatedMessage)")

        // チャットメッセージをキャンセル
        event.isCancelled = true
    }

    fun convertRomajiToJapanese(message: String): String {
        return Romaji.toKana(message)
    }
}