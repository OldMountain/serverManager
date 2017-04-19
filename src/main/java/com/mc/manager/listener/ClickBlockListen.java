package com.mc.manager.listener;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

import com.mc.manager.servermanager.Manager;
import com.mc.manager.tools.Common;

public class ClickBlockListen implements Listener{
	private Manager plug = null;
	

	public ClickBlockListen(Manager plug) {
		super();
		this.plug = plug;
	}

	@EventHandler
	public void ClickBolck(PlayerInteractEvent e){
		Player p = e.getPlayer();
		Block block = e.getClickedBlock();
		Common.sendMessage(p, block.getType()+"=>"+block.getLocation());
	}
}
