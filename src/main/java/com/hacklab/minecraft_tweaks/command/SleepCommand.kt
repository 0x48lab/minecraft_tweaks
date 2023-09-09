package com.hacklab.paper_tweaks.command

import com.hacklab.paper_tweaks.usecase.BedVote
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.koin.java.KoinJavaComponent

class SleepCommand(val plugin: JavaPlugin) : CommandExecutor {
    private val bedVote: BedVote by KoinJavaComponent.inject(BedVote::class.java)

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            if (bedVote.canBedIn(sender)) {
                bedVote.bedIn(sender)
            } else {
                sender.sendMessage("今は誰もベッドに寝ていないので使えません")
            }
        }
        return true
    }
}