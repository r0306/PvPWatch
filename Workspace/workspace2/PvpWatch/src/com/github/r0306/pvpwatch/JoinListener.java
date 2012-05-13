package com.github.r0306.pvpwatch;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {
	
	private pvpwatch plugin;
	public JoinListener(pvpwatch plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		if (DeathListener.joinCon.contains(player)) {
			String[] consequence = new String[plugin.getConfig().getStringList("JoinConsequences").size()];
			plugin.getConfig().getStringList("JoinConsequences").toArray(consequence);
			for (int i = 0; i < consequence.length; i ++) {
				consequence[i] = consequence[i].replaceAll("<player>", player.getName());
				plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), consequence[i]);
			}
			DeathListener.joinCon.remove(player);	
		}		
	}
}