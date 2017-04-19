package com.mc.manager.listener;

import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import com.mc.manager.servermanager.Manager;
import com.mc.manager.tools.CheckInventory;

public class ClickListen implements Listener {
	private Manager plug = null;
	private Set<String> worlds = null;
	private List<Integer> ids = null;

	public ClickListen(Manager z) {
		super();
		this.plug = z;
		worlds = plug.getConfig().getConfigurationSection("world").getKeys(false);
	}

	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent e) {
		Player p = e.getPlayer();
		String curWorld = p.getWorld().getName();
		for (String w : worlds) {
			if (w.equals(curWorld)) {
				ids = plug.getConfig().getIntegerList("world."+w+".item");
				CheckInventory.checkEmpty(p, ids);
			}
		}

	}

}
