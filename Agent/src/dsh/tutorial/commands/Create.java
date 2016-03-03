package dsh.tutorial.commands;

import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;


public class Create implements CommandExecutor {

	public static final Logger logger = Logger.getLogger("CreateCommand");
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
	
		// checks that only current user uses the command
		if (!(sender instanceof Player)) {
			sender.sendMessage("You must be a player to use this command!");
			return false;
		} 
		
		final Player player = (Player)sender;
		final World world = player.getWorld();
		
		// create the new location for the object - in front of the player
		Location l = new Location(player.getWorld(), player.getLocation().getX() + 2, player.getLocation().getY(), player.getLocation().getZ());
		
		final Entity entity = world.spawnEntity(l, EntityType.CHICKEN);
		player.sendMessage(ChatColor.BLUE + "You have created an entity!");
		
		logger.info("Starting loop");
				
		
		Thread t = new Thread(() -> {
			
			logger.info("starting thread");
			
			while (!player.isDead() && !entity.isDead()) {				
				logger.info("loop cycle");
	            if ( ! player.getLocation().equals(entity.getLocation()) ) {
					logger.info("change entity position: " + Thread.currentThread().getName());				
					entity.teleport(player);			
					
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) { e.printStackTrace(); }
				}				      
			}			
		});
		t.setDaemon(true);
		t.start();
		
		return true;
	}
}
