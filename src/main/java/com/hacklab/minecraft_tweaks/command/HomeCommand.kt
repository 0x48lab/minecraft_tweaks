package com.hacklab.paper_tweaks.command

import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

class HomeCommand(val plugin: JavaPlugin) : CommandExecutor {

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            if (sender.level < 5) {
                sender.sendMessage("経験値が足りません！")
                return true
            }

            if (sender.bedSpawnLocation == null) {
                sender.sendMessage("ベッドのスポーン位置が設定されていません！");
                return true;
            }

            sender.giveExpLevels(-5)
            sender.teleport(sender.bedSpawnLocation!!)
            sender.sendMessage("ベッドにテレポートしました！");
        }
        return true
    }
}