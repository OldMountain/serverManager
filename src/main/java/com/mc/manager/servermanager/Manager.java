package com.mc.manager.servermanager;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Server;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.mc.manager.command.Commands;
import com.mc.manager.listener.ClickBlockListen;
import com.mc.manager.listener.ClickListen;
import com.mc.manager.listener.PlayerJoin;
import com.mc.manager.tools.Common;

public class Manager extends JavaPlugin {
	public static String prefix = "§a[综合管理]";
	public static String url = "";
	public static String dbName = "";
	public static String userName = "";
	public static String password = "";
	private String pexPlug = "PermissionsEx";//默认pex权限插件
	private Server server = null;
	private HashMap<String, String> customMap = new HashMap<String, String>();
	private HashMap<String, String> cusReport = new HashMap<String, String>();
	private HashMap<String, String> dealReport = new HashMap<String, String>();
	private HashMap<String, Object> teleportMap = new HashMap<String, Object>();

	@Override
	public void onEnable() {
		super.onEnable();
		Common.getInfo("服务器管理插件启动...");
		server = getServer();
		// 初始化参数
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
		File file = new File(getDataFolder(), "config.yml");
		if (!(file.exists())) {
			saveDefaultConfig();
		}
		reloadConfig();
		// 通知前缀
		prefix = "§a" + getConfig().getString("message.prefix");
		pexPlug = getConfig().getString("permission");
		// mysql
		url = "jdbc:mysql://" + getConfig().getString("mysql.url") + "/" + getConfig().getString("mysql.dbName");
		userName = getConfig().getString("mysql.username");
		password = getConfig().getString("mysql.password");
		// 注册独立名利执行器
		getCommand("zt").setExecutor(new Commands(this));
		// 注册聊天监听
		// registerListener(new ChatListen(this));
		registerListener(new ClickListen(this));
		registerListener(new PlayerJoin(this));
		//registerListener(new ClickBlockListen(this));
	}

	// 注册监听
	public void registerListener(Listener listener) {
		server.getPluginManager().registerEvents(listener, this);
	}
	//生成配置文件
	public void newConfig(String path,String fileName){
		
	}

	public HashMap<String, String> getCustomMap() {
		return customMap;
	}

	public void setCustomMap(HashMap<String, String> customHash) {
		this.customMap = customHash;
	}

	public HashMap<String, String> getDealReport() {
		return dealReport;
	}

	public void setDealReport(HashMap<String, String> dealReport) {
		this.dealReport = dealReport;
	}

	public String getPexPlug() {
		return pexPlug;
	}

	public void setPexPlug(String pexPlug) {
		this.pexPlug = pexPlug;
	}

	public HashMap<String, Object> getTeleportMap() {
		return teleportMap;
	}

	public void setTeleportMap(HashMap<String, Object> teleportMap) {
		this.teleportMap = teleportMap;
	}

}
