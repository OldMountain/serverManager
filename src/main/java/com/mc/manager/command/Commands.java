package com.mc.manager.command;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.Permission;

import com.mc.manager.listener.ChatListen;
import com.mc.manager.servermanager.Manager;
import com.mc.manager.tools.CheckInventory;
import com.mc.manager.tools.Common;
import com.mc.manager.tools.GiveAll;
import com.mc.manager.tools.LoadBan;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

public class Commands implements CommandExecutor {
	private Manager plug;

	public Commands(Manager plug) {
		this.plug = plug;
	}

	public boolean onCommand(CommandSender sender, Command cmd, String lable, String[] args) {
		boolean flag = true;
		if (lable.equalsIgnoreCase("zt")) {
			Player p = null;
			if (sender instanceof Player) {
				p = (Player) sender;
			}
			if (args.length == 1) {// 二级指令
				switch (args[0]) {
				case "prefix":// 修改前缀
					if (p.hasPermission("manager." + args[0])) {
						Common.sendMessage(p, "请输入自定义前缀并以§r#§a开头：");
						plug.getCustomMap().put(p.getName(), "prefix");
						Common.getInfo(plug.getCustomMap().get(p.getName()));
						plug.registerListener(new ChatListen(plug));
					} else {
						Common.sendMessage(p, "你没有权限这么做");
					}
					break;
				case "loadban":
					if (p.hasPermission("manager." + args[0])) {
						LoadBan.loadToBoss(p, "BanItem", "Blacklist", "所有世界", "违禁");
					} else {
						Common.sendMessage(p, "你没有权限这么做");
					}
					break;
				case "loadw":
					if (p.hasPermission("manager." + args[0])) {
						Set<String> set = plug.getConfig().getConfigurationSection("world").getKeys(false);
						for (String s : set) {
							LoadBan.loadToBoss(p, "Manager", "world." + s + ".item",
									plug.getConfig().getString("world." + s + ".name"), "本世界禁止使用");
						}
					} else {
						Common.sendMessage(p, "你没有权限这么做");
					}
					break;
				case "clearban":
					if (p.hasPermission("manager." + args[0])) {
						LoadBan.clearBan(p);
					} else {
						Common.sendMessage(p, "你没有权限这么做");
					}
					break;
				case "clearw":
					if (p.hasPermission("manager." + args[0])) {
						CheckInventory.clearBan(p, plug);
					} else {
						Common.sendMessage(p, "你没有权限这么做");
					}
					break;
				case "help":
					Common.getHelp(p);
					break;
				case "reg":
					File f = new File("D:\\遮天mc");
					File[] fs = f.listFiles();
					int ii = 0;
					for (File file : fs) {
						try {
							BufferedReader read = new BufferedReader(new FileReader(file));
							String name = read.readLine().split("=")[1];
							String pwd = read.readLine().split("=")[1];
							ii++;
							System.out.println("注册:"+ii);
							Common.excuteConsole(p, "authme register "+name.trim()+" "+pwd.trim());
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
					break;
				default:
					flag = false;
				}
			} else if (args.length != 0) {// 多级指令（至少2个）
				switch (args[0]) {
				case "giveall":
					if (p.hasPermission("manager." + args[0])) {
						String msg = "福利";
						if (args.length == 2 && args[1].matches("[0-9]+")) {
							GiveAll.sendToEvery(p, Integer.parseInt(args[1]), msg);
						} else if (args.length == 3 && args[1].matches("[0-9]+")) {
							GiveAll.sendToEvery(p, Integer.parseInt(args[1]), args[2]);
						} else {
							flag = false;
						}
					} else {
						Common.sendMessage(p, "你没有权限这么做");
					}
					break;
				case "addw":
					if (p.hasPermission("manager." + args[0])) {
						String alia = args[1];
						if (args.length == 3) {
							alia = args[2];
						}
						CheckInventory.addItemToWorld(p, plug, args[1], alia);
					}
					break;
				case "addtp":
					Location l = new Location(plug.getServer().getWorld("world"), -1.5, 66, 5.5);
					plug.getTeleportMap().put(args[1], l);
					sender.sendMessage(Manager.prefix + "添加成功");
					break;
				default:
					Common.getHelp(p);
				}
			} else {
				flag = false;
			}
		}
		return flag;
	}

}
