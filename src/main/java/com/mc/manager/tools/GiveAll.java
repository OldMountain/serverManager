package com.mc.manager.tools;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveAll {

	public static void sendToEvery(Player p,int count, String msg) {
		ItemStack item = p.getItemInHand();
		String name = item.getType().toString();
		if(item != null && item.getType() != Material.AIR){
			ItemStack clone = item.clone();
			clone.setAmount(count);
			Player[] players = Bukkit.getServer().getOnlinePlayers();
			if(clone.getItemMeta().getDisplayName() != null){
				name = clone.getItemMeta().getDisplayName();
			}
			for (Player player : players) {
				player.getInventory().addItem(clone);
				player.playSound(player.getLocation(), Sound.LEVEL_UP, 5.0F, 1.0F);
				Common.sendMessage(player, "§e======================");
				Common.sendMessage(player, "你收到了别人赠与的礼物：§2§l" + name);
				Common.sendMessage(player, "数  量：  §4§l" + count);
				Common.sendMessage(player, "发送者：  §2§l" + p.getDisplayName());
				Common.sendMessage(player, "备  注：  §9§l" + msg);
				Common.sendMessage(player, "§e=====================");
			}
			Common.sendMessage(p,"全服共有"+players.length+"人收到你的物品");
		}else{
			Common.sendMessage(p,"你的手里没有发现任何物品");
		}
	}
}
