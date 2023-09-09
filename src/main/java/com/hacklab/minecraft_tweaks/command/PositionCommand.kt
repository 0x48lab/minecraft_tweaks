package com.hacklab.paper_tweaks.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class PositionCommand(val plugin: JavaPlugin) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val message = "${sender.location.block.x}, ${sender.location.block.y}, ${sender.location.block.z}"
            sender.sendMessage(message)
        }
        return true
    }
}