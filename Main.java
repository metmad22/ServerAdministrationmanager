package de.GamerWorld.SM.Main;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import de.GamerWorld.SM.Commands.GodCommand;
import de.GamerWorld.SM.Commands.backCommand;
import de.GamerWorld.SM.Commands.banCommand;
import de.GamerWorld.SM.Commands.bigtreeCommand;
import de.GamerWorld.SM.Commands.ccCommand;
import de.GamerWorld.SM.Commands.clearinvCommand;
import de.GamerWorld.SM.Commands.fireworkCommand;
import de.GamerWorld.SM.Commands.flyCommand;
import de.GamerWorld.SM.Commands.gamemodeCommand;
import de.GamerWorld.SM.Commands.gmCommand;
import de.GamerWorld.SM.Commands.headCommand;
import de.GamerWorld.SM.Commands.homeCommand;
import de.GamerWorld.SM.Commands.hubCommand;
import de.GamerWorld.SM.Commands.sethubCommand;
import de.GamerWorld.SM.Commands.smCommand;
import de.GamerWorld.SM.Commands.timeCommand;
import de.GamerWorld.SM.Commands.warpCommand;
import de.GamerWorld.SM.Events.ChatEvents;
import de.GamerWorld.SM.Events.InventoryEvents;
import de.GamerWorld.SM.Events.PlayerEvents;


public class Main extends JavaPlugin implements Listener
{
	public static Main plugin;
	protected FileConfiguration config;
	public static HashMap<String, Location> backdb = new HashMap<String, Location>();
	public static String KeineRechte;
	public static String prefix = "§4[§1SM§4]: ";
	public static String prefixb = "§l§7[§4!§7]:§r ";
	public static String noPlayer = "§4You have to be an Player to use this Command!";
	public static String Playerdnx = "§4The Player is not online!";
	public Logger log;
	
	public void onEnable() {
		System.out.println("[ServerManager]: Starting Plugin...");
		System.out.println("[ServerManager]: Loading Events...");
	    getServer().getPluginManager().registerEvents(this, this);
	    getServer().getPluginManager().registerEvents(new PlayerEvents(this), this);
	    getServer().getPluginManager().registerEvents(new InventoryEvents(), this);
	    getServer().getPluginManager().registerEvents(new ChatEvents(this), this);
	    
	    System.out.println("[ServerManager]: Events loaded!");
	    System.out.println("[ServerManager]: Loading Commands...");
		
	    
	    this.getCommand("back").setExecutor(new backCommand());
	    this.getCommand("cc").setExecutor(new ccCommand());
	    this.getCommand("clearinv").setExecutor(new clearinvCommand());
	    this.getCommand("hub").setExecutor(new hubCommand());
	    this.getCommand("sethub").setExecutor(new sethubCommand());
	    this.getCommand("time").setExecutor(new timeCommand());
	    this.getCommand("sm").setExecutor(new smCommand(this));
	    this.getCommand("head").setExecutor(new headCommand());
	    this.getCommand("warp").setExecutor(new warpCommand());
	    this.getCommand("com").setExecutor(new Compass(this));
	    this.getCommand("home").setExecutor(new homeCommand());
	    this.getCommand("ban").setExecutor(new banCommand(this));
	    this.getCommand("unban").setExecutor(new banCommand(this));
	    this.getCommand("fly").setExecutor(new flyCommand(this));
	    this.getCommand("gamemode").setExecutor(new gamemodeCommand(this));
	    this.getCommand("gms").setExecutor(new gmCommand(this));
	    this.getCommand("gmc").setExecutor(new gmCommand(this));
	    this.getCommand("gma").setExecutor(new gmCommand(this));
	    this.getCommand("firework").setExecutor(new fireworkCommand(this));
	    this.getCommand("god").setExecutor(new GodCommand(this));
	    this.getCommand("bigtree").setExecutor(new bigtreeCommand(this));
	    
	    System.out.println("[ServerManager]: Commands loaded!");
	    System.out.println("[ServerManager]: Loading Configs...");
	    loadConfig();
	    loadMessages();
	    loadCompass();
	    loadBook();
	    System.out.println("[ServerManager]: Configs loaded!");
	    System.out.println("[ServerManager]: Plugin started! Have fun with my Plugin! :D");
	}
	
	public void onDisable() {
	    System.out.println("[MinigamesManager]: Stopping Plugin...");
	    System.out.println("[MinigamesManager]: Plugin stopped! Thank you for using my Plugin!");
	}
		
	public void loadConfig() {
		saveConfig();
		
		String string1 = "Config.Chat.JoinMessages.Player";
		getConfig().addDefault(string1, "[&2+&r]: &e%p%");
		
		String string2 = "Config.Chat.JoinMessages.OP";
		getConfig().addDefault(string2, "[&2+&r]: &e%p%");
				
		String string3 = "Config.Chat.LeaveMessages.Player";
		getConfig().addDefault(string3, "[&4-&r]: &e%p%");
		
		String string4 = "Config.Chat.LeaveMessages.OP";
		getConfig().addDefault(string4, "[&4-&r]: &e%p%");
		
		this.getConfig().addDefault("Config.Chat.Colors activated?", Boolean.valueOf(true));
		
		this.getConfig().addDefault("Config.Teleport.Compass activated?", Boolean.valueOf(true));		
		
		this.getConfig().addDefault("Config.default.Reload.Message Length before start", Integer.valueOf(1));
		
		this.getConfig().addDefault("Config.default.Reload.Message Length after finish", Integer.valueOf(1));
		
		this.getConfig().addDefault("Config.default.Respawn at home", Boolean.valueOf(true));
		
		this.getConfig().addDefault("Config.default.Broadcast message ban", Boolean.valueOf(true));
		
		getConfig().options().copyDefaults(true);
		getConfig().options().header("This is the default Config of ServerManager! At the point Reload, just fill in the value of 1,2,3,4,5!");
	    saveConfig();
		
	}
	
	public void loadMessages() {
		File file = new File("plugins/ServerManager", "messages.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		try {
			cfg.save(new File("plugins/ServerManager", "messages.yml"));
		} catch (IOException e) {
			System.out.println("[ServerManager]: Saving of Messages faild!!!");
			e.printStackTrace();
		}
		
		String string1 = "messages.default.no permissions";
		cfg.addDefault(string1, "&4You don´t have the &lPermissions&r&4 to do that!");
		
		String string2 = "messages.Chat.ChatCleaner";
		cfg.addDefault(string2, "&2The Chat has been cleared!");
		
		String string3 = "messages.Teleport.Teleport to hub";
		cfg.addDefault(string3, "&2You have been teleported to the hub!");
		
		String string4 = "messages.default.Not enough arguments";
		cfg.addDefault(string4, "&4Not enough arguments!");
		
		String string7 = "messages.default.Too much arguments";
		cfg.addDefault(string7, "&4Too much arguments!");
		
		String string5 = "messages.default.No book in Hand";
		cfg.addDefault(string5, "&cYou have to have an book in your hand!");
		
		String string6 = "messages.default.Book saved";
		cfg.addDefault(string6, "&2The Spawnbook has saved!");
		
		String string8 = "messages.time.Set to Day";
		cfg.addDefault(string8, "&2The time has been set too &lDay&r&2!");
		
		String string9 = "messages.time.Set to Night";
		cfg.addDefault(string9, "&2The time has been set too &lNight&r&2!");
		
		String string10 = "messages.Teleport.Back";
		cfg.addDefault(string10, "&2You have been teleported back to your last location!");
		
		String string11 = "messages.Inventory.Inventory cleared";
		cfg.addDefault(string11, "&2Your inventory has been cleared!");
		
		String string12 = "messages.Inventory.Player not online";
		cfg.addDefault(string12, "&cThe Player is not online!");
		
		String string13 = "messages.Teleport.Kompass deactivated";
		cfg.addDefault(string13, "&4The Teleportcompass is deactivated!");
		
		String string14 = "messages.Teleport.Teleport to Hall of Fame";
		cfg.addDefault(string14, "&2You have been teleported to the Hall of Fame!");
			
		String string15 = "messages.Heads.Get Head";
		cfg.addDefault(string15, "&2You got an head from &l%p%&r&2!");
		
		String string16 = "messages.Teleport.Warp already exists";
		cfg.addDefault(string16, "&cThe Warp '%warpname%' already exists!");
		
		String string17 = "messages.Teleport.Teleported to the Warp";
		cfg.addDefault(string17, "&2You have been teleportet to the warp '%warpname%'!");
		
		String string18 = "messages.Teleport.Warp does not exists";
		cfg.addDefault(string18, "&cThe Warp '%warpname%' does not exist!");
		
		String string19 = "messages.Teleport.Warp removed";
		cfg.addDefault(string19, "&6The Warp '%warpname%' has been removed!");
		
		String string20 = "messages.default.Command does not exists!";
		cfg.addDefault(string20, "&7The Commad does not exists! &a[%command%]");
		
		String string21 = "messages.defalaut.Reload.Before reload.Line 1";
		cfg.addDefault(string21, "&4Reload starting! Lagging is normal!");
		
		String string22 = "messages.defalaut.Reload.Before reload.Line 2";
		cfg.addDefault(string22, "&cPlease fill the line!");
		
		String string23 = "messages.defalaut.Reload.Before reload.Line 3";
		cfg.addDefault(string23, "&cPlease fill the line!");
		
		String string24 = "messages.defalaut.Reload.Before reload.Line 4";
		cfg.addDefault(string24, "&cPlease fill the line!");
		
		String string25 = "messages.defalaut.Reload.Before reload.Line 5";
		cfg.addDefault(string25, "&cPlease fill the line!");
		
		String string26 = "messages.default.Reload.After reload.Line 1";
		cfg.addDefault(string26, "&2Reload finished! You can play normal again!");
		
		String string27 = "messages.default.Reload.After reload.Line 2";
		cfg.addDefault(string27, "&cPlease fill the line!");
		
		String string28 = "messages.default.Reload.After reload.Line 3";
		cfg.addDefault(string28, "&cPlease fill the line!");
		
		String string29 = "messages.default.Reload.After reload.Line 4";
		cfg.addDefault(string29, "&cPlease fill the line!");
		
		String string30 = "messages.default.Reload.After reload.Line 5";
		cfg.addDefault(string30, "&cPlease fill the line!");
		
		String string31 = "messages.Teleport.Teleported to the Home";
		cfg.addDefault(string31, "&2You have benn teleportet to the home '%homename%'");
		
		String string32 = "messages.Teleport.Home does not exists";
		cfg.addDefault(string32, "&cThe home '%homename%' does not exists!");
		
		String string33 = "messages.Teleport.Home removed";
		cfg.addDefault(string33, "&6The Home '%homename% have benn removed!");
		
		String string34 = "messages.Ban.Ban Broadcast";
		cfg.addDefault(string34, "&6The Player &4&l%player%&r&6 was baned by %banner% for &4%reason%&6 from the server!");
		
		String string35 = "messages.Ban.Default Ban Reason";
		cfg.addDefault(string35, "&7The Banhammer has spoken!");
		
		String string36 = "messages.Ban.Unbanmessage";
		cfg.addDefault(string36, "&6The Player %player% has been unbanned!");
		
		String string37 = "messages.Ban.Not Banned";
		cfg.addDefault(string37, "&cThe Player %player% is not banned!");
		
		String string38 = "messages.Gamemode.Change";
		cfg.addDefault(string38, "&7Your Gamemode has been changed to %gamemode%!");
		
		String string39 = "messages.Gamemode.Change other";
		cfg.addDefault(string39, "&7You changed the gamemode of %player% to %gamemode%!");
		
		String string40 = "messages.Gamemode.Is same as now";
		cfg.addDefault(string40, "&7The Gamemode has &nnot&r&7 been set to %gamemode%!");
				
		cfg.options().copyDefaults(true);
	    cfg.options().header("This is the messages file of ServerManager. You can change the messages here. The ColorCodes are with &");	    

	    KeineRechte = ChatColor.translateAlternateColorCodes('&', string1);
	    
	    try {
			cfg.save(new File("plugins/ServerManager", "messages.yml"));
		} catch (IOException e) {
			System.out.println("[ServerManager]: Saving of Messages faild!!!");
			e.printStackTrace();
		}
	}
	
	public void loadCompass() {
		File file = new File("plugins/ServerManager/Teleportation", "compass.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		try {
			cfg.save(new File("plugins/ServerManager/Teleportation", "compass.yml"));
		} catch (IOException e) {
			System.out.println("[ServerManager]: Saving of Compass faild!!!");
			e.printStackTrace();
		}
		
		cfg.addDefault("Compass.Teleportpoints.Hub.activated?", Boolean.valueOf(true));	
		
		cfg.addDefault("Compass.default.Fill empty fields?", Boolean.valueOf(true));
		
		String string = "Compass.Teleportpoints.Hub.Name";
		cfg.addDefault(string, "&4&lHub");
		
		String stringhl = "Compass.Teleportpoints.Hub.Lore";
		cfg.addDefault(stringhl, "&5Klick to teleport to the Hub!");
		
		cfg.addDefault("Compass.Teleportpoints.Hall of Fame.activated?", Boolean.valueOf(true));
		
		String string2 = "Compass.Teleportpoints.Hall of Fame.Name";
		cfg.addDefault(string2, "&b&lHall of Fame");
		
		String string3 = "Compass.Teleportpoints.Hall of Fame.Lore";
		cfg.addDefault(string3, "&5Klick to teleport to the Hall of Fame!");
		
		String stringKN = "Compass.default.Name";
		cfg.addDefault(stringKN, "&8Where do you want to go?");
				
		cfg.options().copyDefaults(true);
	    cfg.options().header("This is Config for the Teleportcompass. The ColorCodes are with &");	    

	    try {
			cfg.save(new File("plugins/ServerManager/Teleportation", "compass.yml"));
		} catch (IOException e) {
			System.out.println("[ServerManager]: Saving of Compass faild!!!");
			e.printStackTrace();
		}
		
	}
	
	public void loadBook() {
		File file = new File("plugins/ServerManager", "book.yml");
		FileConfiguration cfg = YamlConfiguration.loadConfiguration(file);

		try {
			cfg.save(new File("plugins/ServerManager", "book.yml"));
		} catch (IOException e) {
			System.out.println("[ServerManager]: Saving of Book faild!!!");
			e.printStackTrace();
		}
		
		String string = "Book.author";
		cfg.addDefault(string, "&aServerOwner");
		
		
		cfg.options().copyDefaults(true);
	    cfg.options().header("This is the bookfile! You can change here you startbook!");	    

	    try {
			cfg.save(new File("plugins/ServerManager", "book.yml"));
		} catch (IOException e) {
			System.out.println("[ServerManager]: Saving of Book faild!!!");
			e.printStackTrace();
		}
		
		
		
		//BuchinHand
	}
}
