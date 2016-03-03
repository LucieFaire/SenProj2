package dsh.tutorial;

import java.util.logging.Logger;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import dsh.tutorial.commands.Create;


public class Main extends JavaPlugin {
	public void onEnable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = Logger.getLogger("Minecraft");		
		logger.info(pdfFile.getName() + " has been enabled (V. " + pdfFile.getVersion() + ")");
		
		registerCommands();
	}
	
	public void onDisable() {
		PluginDescriptionFile pdfFile = getDescription();
		Logger logger = Logger.getLogger("Minecraft");
		
		logger.info(pdfFile.getName() + " has been disabled (V. " + pdfFile.getVersion() + ")");
		
	}

	public void registerCommands() {
		getCommand("createmob").setExecutor(new Create());
		
	}
}
