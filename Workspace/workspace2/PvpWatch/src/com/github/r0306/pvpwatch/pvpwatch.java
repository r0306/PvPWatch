package com.github.r0306.pvpwatch;

import java.util.ArrayList;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.plugin.java.JavaPlugin;

public class pvpwatch extends JavaPlugin {
	
	ArrayList<String> cons = new ArrayList<String>();
	ArrayList<String> joinCons = new ArrayList<String>();
	
	public void onEnable() {
		loadConfig();
		getServer().getPluginManager().registerEvents(new DeathListener(this), this);
		getServer().getPluginManager().registerEvents(new JoinListener(this), this);
		System.out.println("PvPWatch version [" + getDescription().getVersion() + "] loaded!");
		
	}
	
	public void onDisable() {
		
		System.out.println("PvPWatch version [" + getDescription().getVersion() + "] unloaded");
		
	}
	
	public void loadConfig() {
		
		FileConfiguration cfg = this.getConfig();
		FileConfigurationOptions cfgOptions = cfg.options();
		cfg.addDefault("Kills", 5);
		cfg.addDefault("Minutes", 10);
		cons.add("kick <player>");
		cons.add("say <player> exceeded the PVP limit!");
		cfg.addDefault("Consequences", cons);
		joinCons.add("tell <player> Stop killing people so fast!");
		cfg.addDefault("JoinConsequences", joinCons);
		cfgOptions.copyDefaults(true);
		cfgOptions.header("This is the PvPWatch configuration file." + System.getProperty( "line.separator" ) + "It is strongly recommended that you use NotePad++ to edit this file." + System.getProperty( "line.separator" ) + " " + System.getProperty( "line.separator" ) + "Below are the four basic config options and their functions:" + System.getProperty( "line.separator" ) + "Kills: Sets how many kills are allowed within the time limit." + System.getProperty( "line.separator" ) + "Minutes: The time in minutes that the certain number of kills are allowed." + System.getProperty( "line.separator" ) + "Consequence: Sets the command executed if the player kills more players than allowed within the time limit." + System.getProperty( "line.separator" ) + "Multiple consequences can be set. Just follow the format as shown by the default settings. One line per command." + System.getProperty( "line.separator" ) + "DO NOT USE '/' in front of the command! <player> is used to signify the player." + System.getProperty( "line.separator" ) + "JoinConsequence: Sometimes, you want the consequence to be activated when a player rejoins the server (such as after being kicked)." + System.getProperty( "line.separator" ) + "The usage for this is the same as regular Consequences. Multiple consequences can also be set for this." + System.getProperty( "line.separator" ) + " " + System.getProperty( "line.separator" ) + "Permission Nodes:" + System.getProperty( "line.separator" ) + "pvpwatch.exepmt: Player who has this permission is affected by this plugin or its consequences (defaults to OP)." + System.getProperty( "line.separator" ) + " " + System.getProperty( "line.separator" ) + "Credits:"  + System.getProperty( "line.separator" ) + "This plugin was brought to you by the Dev Nation team [r0306, garrett2smart87, fred302]." + System.getProperty( "line.separator" ) + " ");
		cfgOptions.copyHeader(true);
		saveConfig();
		
	}

}
