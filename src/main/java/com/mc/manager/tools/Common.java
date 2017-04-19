package com.mc.manager.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.mc.manager.servermanager.Manager;

public class Common {
	private static String prefix = Manager.prefix;

	// 提示信息
	public static void sendMessage(Player p, String msg) {
		if (p != null) {
			p.sendMessage(prefix + "§5" + msg);
		} else {
			getInfo(prefix + "§5" + msg);
		}

	}

	public static void getInfo(String msg) {
		Bukkit.getLogger().info("[debug]" + msg);
	}

	public static void getWarn(String msg) {
		Bukkit.getLogger().warning("[debug]" + msg);
	}

	// 执行控制台指令
	public static boolean excuteConsole(Player p, String cmd) {
		boolean flag = false;
		flag = p.getServer().dispatchCommand(p.getServer().getConsoleSender(), cmd);
		return flag;
	}
	// 执行玩家指令
	public static boolean excutePlayer(Player p, String cmd) {
		boolean flag = false;
		flag = p.getServer().dispatchCommand(p, cmd);
		return flag;
	}

	// 导入文件
	public static FileConfiguration loadConfigs(File Folder, String fileName) {
		if (!Folder.exists()) {
			Folder.mkdir();
		}
		File file = new File(Folder, fileName + ".yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		FileConfiguration c = YamlConfiguration.loadConfiguration(file);
		return c;
	}

	// 帮助
	public static void getHelp(Player p) {
		String helpMsg = "§a========================\n" + prefix + "§e" + "欢迎使用遮天服务器管理插件\n" 
	            + "§7"	+ "/zt prefix: §9自定义前缀\n" 
	            + "§7"	+ "/zt clearban: §9清除banitem列表中不存在的物品\n" 
				+ "§7" + "/zt loadban: §9将物品封禁列表导入菜单\n" 
				+ "§7" + "/zt loadw: §9将禁止带入某世界的物品封禁列表导入菜单\n" 
	            + "§7"	+ "/zt addw <world> [别名] : §9将手中物品添加到不可带进<world>黑名单\n" 
				+ "§7"	+ "/zt giveall <数量> [备注]: §9给在线玩家每人发一个手中物品\n" 
	            + "§7" + "/zt reload: §9重载插件\n";
		sendMessage(p, helpMsg);
	}

	// 注销监听
	public static void unregisterListener(Listener listener) {
		AsyncPlayerChatEvent.getHandlerList().unregister(listener);
	}

	// 修改前缀
	public static void prefix(Player p, String pex, String prefix) {
		if (pex.equals("PermissionsEx")) {
			excuteConsole(p, "pex user " + p.getName() + " prefix " + prefix);
		} else if (pex.equals("EssentialsGroup")) {

		}
		Common.sendMessage(p, "修改成功");
	}

	// 获取ConfigurationSection
	public static ConfigurationSection getConfigSection(Manager plug, String section) {
		ConfigurationSection configSection = plug.getConfig().getConfigurationSection(section);
		return configSection;
	}

	// 获取ConfigurationSection
	public static List<String> getConfig(Manager plug, String key) {
		List<String> list = (List<String>) plug.getConfig().getStringList(key);
		return list;
	}
}
