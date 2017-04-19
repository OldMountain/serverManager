package com.mc.manager.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class LoadBan {
	private Player p = null;

	public LoadBan(Player p) {
		super();
		this.p = p;
	}

	public static void loadToBoss(Player p,String floderName,String key,String worldName,String ban_reason) {
		File file1 = new File("plugins/"+floderName+"/config.yml");
		File file2 = new File("plugins/BossShop/shops");
		FileConfiguration fileBan = YamlConfiguration.loadConfiguration(file1);
		List<String> listBan = fileBan.getStringList(key);
		FileConfiguration[] bossConf = new FileConfiguration[listBan.size() / 45 + 1];
		File[] files = new File[listBan.size() / 45 + 1];
		for (int i = 0; i < listBan.size() / 45 + 1; i++) {
			files[i] = new File(file2, floderName + i + ".yml");
			if (!files[i].exists()) {
				try {
					files[i].createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					Common.getInfo(files[i].getName() + "创建失败");
				}
			}
			bossConf[i] = YamlConfiguration.loadConfiguration(files[i]);
			// 其他
			bossConf[i].set("signs.text", "[Menu]");
			bossConf[i].set("signs.NeedPermissionToCreateSign", "true");
			bossConf[i].set("version", "1.2");
			bossConf[i].set("Setting.AllowChangeRightClickBuyCount", "false");
			bossConf[i].set("Setting.HideItemFlag", "63");
			bossConf[i].set("Setting.DisplayName", "&l遮天");
			bossConf[i].set("Setting.EnterMessage", "");
			bossConf[i].set("Setting.LeaveMessage", "");
			bossConf[i].set("Setting.ShopTypeSpecial", "false");
			bossConf[i].set("Setting.RightClickBuyCount", "1");
			bossConf[i].set("Setting.ShowRightClickBuyCount", "true");
			bossConf[i].set("shop", "");
		}
		String msg = "封禁列表读取完成,请输入/bs reload重载";
		for (int i = 0; i < listBan.size(); i++) {
			String[] s = listBan.get(i).split(":");
			String id = s[0];
			String data = "0";
			String world = worldName;
			String reason = ban_reason;
			if(s.length > 1){
				data = s[1];
			}
			if (data.equals("-1") || data.equals("*")) {
				data = "0";
			}
			if (s.length == 2) {
				Common.sendMessage(p, "物品封禁失败：\nid:" + id + "\n原因：未写理由");
			} else if (s.length == 3) {
				reason = s[2];
			} else if(s.length > 3){
				world = s[2];
				reason = s[3];
			}
			List<String> menuItem = new ArrayList<String>();
			menuItem.add("id" + ":" + id + ":" + data);
			menuItem.add("amount" + ":" + "1");
			menuItem.add("lore" + ":" + "§6理由：§4" + reason);
			menuItem.add("lore" + ":" + "§6禁止使用的世界：§5" + world);
			// shop
			bossConf[i / 45].set("shop.ban" + id + "_" + data + ".RewardType", "shop");
			bossConf[i / 45].set("shop.ban" + id + "_" + data + ".PriceType", "free");
			bossConf[i / 45].set("shop.ban" + id + "_" + data + ".Reward", "");
			bossConf[i / 45].set("shop.ban" + id + "_" + data + ".MenuItem", menuItem);
			bossConf[i / 45].set("shop.ban" + id + "_" + data + ".InventoryLocation", i % 45 + 1);
			bossConf[i / 45].set("shop.ban" + id + "_" + data + ".ExtraPermission", "");
		}
		for (int i = 0; i < listBan.size() / 45 + 1; i++) {
			try {
				List<String> menuBack = new ArrayList<String>();
				menuBack.add("id:331");
				menuBack.add("amount:1");
				menuBack.add("name:§4返回主菜单");
				bossConf[i].set("shop.back.RewardType", "shop");
				bossConf[i].set("shop.back.PriceType", "free");
				bossConf[i].set("shop.back.Reward", "Menu");
				bossConf[i].set("shop.back.MenuItem", menuBack);
				bossConf[i].set("shop.back.InventoryLocation", 54);
				bossConf[i].set("shop.back.ExtraPermission", "");
				if (i == 0) {
					if (listBan.size() / 45 > i) {
						List<String> menuNext = new ArrayList<String>();
						menuNext.add("id:262");
						menuNext.add("amount:1");
						menuNext.add("name:§4下一页");
						bossConf[i].set("shop.next.RewardType", "shop");
						bossConf[i].set("shop.next.PriceType", "free");
						bossConf[i].set("shop.next.Reward", floderName + (i + 1));
						bossConf[i].set("shop.next.MenuItem", menuNext);
						bossConf[i].set("shop.next.InventoryLocation", 47);
						bossConf[i].set("shop.next.ExtraPermission", "");
					}
				} else {
					List<String> menuPre = new ArrayList<String>();
					menuPre.add("id:262");
					menuPre.add("amount:1");
					menuPre.add("name:§4上一页");
					bossConf[i].set("shop.pre.RewardType", "shop");
					bossConf[i].set("shop.pre.PriceType", "free");
					bossConf[i].set("shop.pre.Reward", floderName + (i - 1));
					bossConf[i].set("shop.pre.MenuItem", menuPre);
					bossConf[i].set("shop.pre.InventoryLocation", 46);
					bossConf[i].set("shop.pre.ExtraPermission", "");
					if (listBan.size() / 45 > i) {
						List<String> menuNext = new ArrayList<String>();
						menuNext.add("id:262");
						menuNext.add("amount:1");
						menuNext.add("name:§4下一页");
						bossConf[i].set("shop.next.RewardType", "shop");
						bossConf[i].set("shop.next.PriceType", "free");
						bossConf[i].set("shop.next.Reward", floderName + (i + 1));
						bossConf[i].set("shop.next.MenuItem", menuNext);
						bossConf[i].set("shop.next.InventoryLocation", 47);
						bossConf[i].set("shop.next.ExtraPermission", "");

					}
				}
				bossConf[i].save(files[i]);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Common.sendMessage(p, msg);
	}
	//清除banitem插件黑名单列表
	public static void clearBan(Player p) {
		File file1 = new File("plugins/BanItem/config.yml");
		FileConfiguration fileBan = YamlConfiguration.loadConfiguration(file1);
		List<String> listBan = fileBan.getStringList("Blacklist");
		List<String> newList = new ArrayList<String>();
		ItemStack item = new ItemStack(Material.AIR);
		int count = 0;
		for (int i = 0; i < listBan.size(); i++) {
			String s = listBan.get(i).split(":")[0];
			int id = Integer.parseInt(s);
			item.setTypeId(id);
			if(item == null || item.getType() == Material.AIR){
				count++;
			}else{
				newList.add(listBan.get(i));
			}
		}
		try {
			fileBan.set("Blacklist", newList);
			fileBan.save(file1);
			Common.sendMessage(p, "共清除"+count+"个ban列表中不存在的物品，请输入/banitem reload重载列表");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Common.sendMessage(p, "清除异常");
		}
	}
}
