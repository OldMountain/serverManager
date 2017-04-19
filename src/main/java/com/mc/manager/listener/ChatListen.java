package com.mc.manager.listener;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatTabCompleteEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.mc.manager.servermanager.Manager;
import com.mc.manager.tools.Common;

public class ChatListen implements Listener {
	private Manager plug = null;
	private Server server = null;
	private Player p = null;
	private String chatMsg = "";
	private String prefix = "";

	public ChatListen(Manager z) {
		super();
		this.plug = z;
		this.server = z.getServer();
		this.prefix = Manager.prefix;
	}

	@EventHandler
	public void chat(AsyncPlayerChatEvent e) {
		if (e.getPlayer() != null && plug.getCustomMap().size() > 0) {
			p = e.getPlayer();
			if (plug.getCustomMap().get(p.getName()) != null) {
				chatMsg = e.getMessage().trim();
				// 如果聊天框内容以“#”开头则修改
				if (chatMsg.startsWith("#")) {
					String value = plug.getCustomMap().get(p.getName());
					if (value.equals("prefix")) {// 前缀
						Common.prefix(p, plug.getPexPlug(),chatMsg.substring(1).trim());
					} else if (value.equals("report")) {// 举报

					}
				} else {
					Common.sendMessage(p, "你已经放弃修改前缀");
				}
				e.setCancelled(true);
			}
			plug.getCustomMap().remove(e.getPlayer().getName());
		}else{//监听脏话
			
		}
		
		
	}
	
	@EventHandler
	public void onTabComplete(PlayerChatTabCompleteEvent e){
		e.getTabCompletions().clear();
		e.getTabCompletions().add("自动补全测试");
	}
}
