package com.mc.manager.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.mc.manager.servermanager.Manager;

public class CheckInventory {

	public static boolean checkEmpty(Player p, List<Integer> ids) {
		p.setFlying(false);
		boolean flag = true;
		PlayerInventory inv = p.getInventory();
		List<ItemStack> l = new ArrayList<ItemStack>();
		ItemStack is = new ItemStack(Material.AIR);
		for (int i = 0; i < 40; i++) {
			ItemStack item = inv.getItem(i);
			if (item != null && item.getType() != Material.AIR) {
				for (int j = 0; j < ids.size(); j++) {
					if (item.getTypeId() == ids.get(j)) {
						l.add(item.clone());
						inv.setItem(i, is);
					}
				}
			}
		}
		for (int i = 0; i < l.size(); i++) {
			p.getEnderChest().addItem(l.get(i));
		}
		p.sendMessage(Manager.prefix + "发现" + l.size() + "个违禁装备，服务器已经帮你卸下，并扔进末影箱");
		return flag;
	}

	public static void addItemToWorld(Player p, Manager plug, String key,String alia) {
		ItemStack item = p.getItemInHand();
		if (item == null || item.getType() == Material.AIR) {
			Common.sendMessage(p, "手中未发现物品");
			return;
		}
		String id = String.valueOf(item.getTypeId());
		String data = String.valueOf(item.getDurability());
		Set<String> name = plug.getConfig().getConfigurationSection("world").getKeys(false);
		List<String> values = new ArrayList<String>();
		for (String n : name) {
			if (n.equals(key)) {
				values = plug.getConfig().getStringList("world." + key + ".item");
				for (int i = 0; i < values.size(); i++) {
					if (values.get(i).equals(id)) {
						Common.sendMessage(p, "该物品已存在");
						return;
					}
				}
			}
			values.add(id);
			plug.getConfig().getConfigurationSection("world").set(key + ".item", values);
			plug.getConfig().getConfigurationSection("world").set(key + ".name", alia);
		}
		plug.saveConfig();
		plug.reloadConfig();
		Common.sendMessage(p, "增加成功");
	}

	// 清除副本世界物品黑名单列表
	public static void clearBan(Player p, Manager plug) {
		File file1 = new File(plug.getDataFolder(),"/config.yml");
		FileConfiguration fileBan = YamlConfiguration.loadConfiguration(file1);
		List<String> listBan = null;
		ItemStack item = new ItemStack(Material.AIR);
		int count = 0;
		Set<String> set = fileBan.getConfigurationSection("world").getKeys(false);
		for (String section : set) {
			List<String> newList = new ArrayList<String>();
			listBan = fileBan.getStringList("world." + section + ".item");
			for (int i = 0; i < listBan.size(); i++) {
				String s = listBan.get(i);
				int id = Integer.parseInt(s);
				item.setTypeId(id);
				if (item == null || item.getType() == Material.AIR) {
					count++;
				} else {
					newList.add(listBan.get(i));
				}
			}
			try {
				fileBan.set("world." + section + ".item", newList);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				Common.sendMessage(p, "清除异常");
			}
		}
		try {
			fileBan.save(file1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Common.sendMessage(p, "共清除" + count + "个");
	}
}
