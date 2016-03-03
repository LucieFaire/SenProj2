package org.smolny.mcagents;

import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

/**
 * Created by veter on 05.02.2016.
 */
public class MCAgentsPlugin extends JavaPlugin {

    public static final Logger logger = Logger.getLogger("Minecraft");

    public void onEnable() {
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " has been enabled (V. " + pdfFile.getVersion() + ")");
        registerCommands();
    }

    public void onDisable() {
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " has been disabled (V. " + pdfFile.getVersion() + ")");
    }


    public void registerCommands() {
        getCommand("createmob").setExecutor(new Create());
        getCommand("createai").setExecutor(new CreateAI());
    }
}
