package com.kraken.zombifier;

import java.util.ArrayList;
import java.util.WeakHashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class MainListener implements Listener {
	
	Main plugin;
	WeakHashMap<String, Boolean> options = new WeakHashMap<>();
	String language;
	
	ArrayList<Player> zombieCooldown = new ArrayList<>();
	
    public MainListener(Main plugin, String language) {
  	  
  	  plugin.getServer().getPluginManager().registerEvents(this, plugin);
  	  this.plugin = plugin;
  	  this.language = language;
  	  
    }
    
    public void setOption(String option, boolean setting) {
    	options.put(option, setting);
    }
    
    public void setLanguage(String language) {
    	this.language = language;
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
    	
    	//Get the player & death location
    	Player player = (Player) event.getEntity();
    	Location loc = player.getLocation();

    	//Spawn the zombie mob
		if ( options.get("enabled") && !zombieCooldown.contains(player) ) {
			
			World world = loc.getWorld();
			world.spawnEntity(loc, EntityType.ZOMBIE);
			
			zombieCooldown.add(player);
			
			Bukkit.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
	    		public void run() {
	    			zombieCooldown.remove(player);
	    		}
	    	}, 1000);
			
		}
    	
    }

    
}
