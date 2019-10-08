package com.mclans.mod.dlamp.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nonnull;

// 帮助等信息
public class DLampCommand extends CommandBase {
    @Nonnull
    @Override
    public String getName() {
        return "dlamp";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender sender) {
        return "次元矿灯命令";
    }

    @Override
    public void execute(@Nonnull MinecraftServer server, @Nonnull ICommandSender sender, String[] args) {
        if(args.length == 0) {
            // TODO: 显示help
            sender.sendMessage(new TextComponentString("§e欢迎使用【次元矿灯】"));
        } else {
            switch (args[0].toLowerCase()) {
                case "wifi":
                    // TODO: 弹出填写ssid和password的UI
                    sender.sendMessage(new TextComponentString("§e请输入SSID和密码进行配网。。。"));
                    break;
                case "help":
                    // TODO: 显示help
                    sender.sendMessage(new TextComponentString("§e欢迎使用【次元矿灯】"));
                    break;
                case "setup":
                    // TODO: 弹出设置矿灯的UI
                    sender.sendMessage(new TextComponentString("§e设置你的【次元矿灯】。。。"));
                    break;
            }

        }

    }
}
