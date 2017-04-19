package com.mc.manager.listener;

import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.mc.manager.servermanager.Manager;
import com.mc.manager.tools.Common;

public class PlayerJoin implements Listener {
	private Manager plug = null;
	private List<String> worlds = null;
	private List<Integer> ids = null;

	public PlayerJoin(Manager z) {
		super();
		this.plug = z;
		worlds = z.getConfig().getStringList("Armor.world");
		ids = z.getConfig().getIntegerList("Armor.item");
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player p = null;
		if (e.getPlayer() != null) {
			p = e.getPlayer();
			if(plug.getTeleportMap().get(p.getName())!=null && !plug.getTeleportMap().get(p.getName()).equals("")){
				p.teleport((Location) plug.getTeleportMap().get(p.getName()));
				plug.getTeleportMap().remove(p.getName());
				p.sendMessage("已将你传送至主城");
			}
		}else{
			return;
		}
		
		if (p.isOp()) {
			p.sendMessage(worlds.toString());
			FileConfiguration report = Common.loadConfigs(plug.getDataFolder(), "report");
			Set<String> set = report.getKeys(false);
			if (set != null && set.size() > 0) {
				Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plug, new Runnable() {

					@Override
					public void run() {
						e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.LEVEL_UP, 5.0F, 1.0F);
						e.getPlayer().sendMessage(Manager.prefix + "你有新的玩家投诉要处理，请输入/zhetian see查看");
					}
				}, 600L);

			}

		}

	}
}
