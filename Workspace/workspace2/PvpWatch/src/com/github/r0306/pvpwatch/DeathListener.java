package com.github.r0306.pvpwatch;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.entity.PlayerDeathEvent;

public class DeathListener implements Listener {
	
	public static HashMap<Player, Integer> kills = new HashMap<Player, Integer>();
	public static HashMap<Player, Long> time = new HashMap<Player, Long>();
	public static ArrayList<Player> joinCon = new ArrayList<Player>();
	private pvpwatch plugin;
	public DeathListener(pvpwatch plugin) {
		this.plugin = plugin;
	}
	Player player = null;
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		int killTime = plugin.getConfig().getInt("Minutes");
		if (event.getEntity().getLastDamageCause().getCause() != DamageCause.ENTITY_ATTACK && event.getEntity().getLastDamageCause().getCause() != DamageCause.PROJECTILE) {
			return;
		}
		if (event.getEntity().getKiller() instanceof Player) {
			player = event.getEntity().getKiller();
		}
		else
		{
			return;
		}

		Calendar c = Calendar.getInstance();
		if (player.hasPermission("pvpwatch.exempt") || player.isOp()) {
			return;
		}
		if (!kills.containsKey(player) || !time.containsKey(player)) {
			kills.put(player, 1);
			time.put(player, c.getTimeInMillis()/60000 + killTime);
			if (kills.get(player) == plugin.getConfig().getInt("Kills")) {
				player.sendMessage(ChatColor.AQUA + "Your PVP limit has been reached. Stop killing players");
				player.sendMessage(ChatColor.AQUA + "or you will be punished.");
				long timeLeft = (time.get(player) - c.getTimeInMillis()/60000) * 1200;
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
					public void run() {
					   if (time.containsKey(player) || kills.containsKey(player)) {
					   player.sendMessage(ChatColor.GREEN + "You may now start killing other players.");
					   }
					}
				}, timeLeft);
			}
		}
		else
		{
			String[] consequence = new String[plugin.getConfig().getStringList("Consequences").size()];
			plugin.getConfig().getStringList("Consequences").toArray(consequence);
			if (time.get(player) > c.getTimeInMillis()/60000 && kills.get(player) >= plugin.getConfig().getInt("Kills")) {	
				for (int i = 0; i < consequence.length; i ++) {
					consequence[i] = consequence[i].replaceAll("<player>", player.getName());
					plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), consequence[i]);
				}
				time.remove(player);
				kills.remove(player);
				joinCon.add(player);
			}
			else if (time.get(player) < c.getTimeInMillis()/60000) {
				time.put(player, c.getTimeInMillis()/60000 + killTime);
				kills.put(player, 1);
				if (kills.get(player) == plugin.getConfig().getInt("Kills")) {
					player.sendMessage(ChatColor.AQUA + "Your PVP limit has been reached. Stop killing players");
					player.sendMessage(ChatColor.AQUA + "or you will be punished.");
					long timeLeft = (time.get(player) - c.getTimeInMillis()/60000) * 1200;
					plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							if (time.containsKey(player) || kills.containsKey(player)) {
								player.sendMessage(ChatColor.GREEN + "You may now start killing other players.");
							}
						}
					}, timeLeft);
				}
				else
				{
				kills.put(player, kills.get(player) + 1);
					if (kills.get(player) == plugin.getConfig().getInt("Kills")) {
						player.sendMessage(ChatColor.AQUA + "Your PVP limit has been reached. Stop killing players");
						player.sendMessage(ChatColor.AQUA + "or you will be punished.");
						long timeLeft = (time.get(player) - c.getTimeInMillis()/60000) * 1200;
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
							public void run() {
								if (time.containsKey(player) || kills.containsKey(player)) {
									player.sendMessage(ChatColor.GREEN + "You may now start killing other players.");
								}
							}
						}, timeLeft);
					}
				}
			}
		}
	}
}
