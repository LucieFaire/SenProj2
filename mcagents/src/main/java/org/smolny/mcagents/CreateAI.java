package org.smolny.mcagents;

import de.inventivegames.npc.NPC;
import de.inventivegames.npc.NPCLib;
import de.inventivegames.npc.ai.NPCAI;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.logging.Logger;

/**
 * Created by dsh org/smolny/mcagents/CreateAI.java:42on 2/19/16.
 */
public class CreateAI implements CommandExecutor {

    public static final Logger logger = Logger.getLogger("CreateCommand");

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        // checks that only current user uses the command
        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command!");
            return false;
        }

        final Player player = (Player)sender;

        // create the new location for the object - in front of the player
        Location l = new Location(player.getWorld(), player.getLocation().getX() + 1, player.getLocation().getY(), player.getLocation().getZ());
        Location l1 = new Location(player.getWorld(), player.getLocation().getX() + 3, player.getLocation().getY(), player.getLocation().getZ());
        World world = player.getWorld();
        world.spawnEntity(l1, EntityType.CHICKEN);

        player.sendMessage(ChatColor.BLUE + "You have created the hero!");

        NPC npc = NPCLib.spawnNPC(NPCLib.NPCType.VILLAGER, l, player.getName());
        LivingEntity hero = npc.getBukkitEntity();

        for (Entity e : hero.getNearbyEntities(10,10,10)) {
            if (e.getType().equals(EntityType.CHICKEN)) {
                ((Damageable) e).damage(100, hero);
                //ItemStack chicken = new ItemStack(Material.RAW_CHICKEN);
                hero.setCanPickupItems(true);
                npc.pathfindTo(e.getLocation());
            }
        }
      return true;
    }
}

