package me.knighthat.sieuchat;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class SieuChat extends JavaPlugin implements CommandExecutor, Listener {

	@Override
	public void onEnable() {
		getCommand("sieuchat").setExecutor(this);
		getServer().getPluginManager().registerEvents(this, this);
	}

	Config cfg = new Config(this);

	public String mau(String a) {
		return ChatColor.translateAlternateColorCodes('&', a);
	}

	private String chat = "";

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onChat(AsyncPlayerChatEvent e) {

		chat = e.getMessage();
		cfg.lay().getConfigurationSection("SieuChat").getKeys(false).forEach(muc -> {
			chat = chat.replace(cfg.lay().getString("SieuChat." + muc + ".Nhap-Vao"),
					cfg.lay().getString("SieuChat." + muc + ".Thay-The"));
		});
		e.setMessage(chat);
		chat = "";
	}

	private String troGiup = "";
	private int macDinh = 0;
	private int themVao = 0;

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (args.length == 0) {
			sender.sendMessage("&6&l/sieuchat bieutuong: &f&lHiển thị danh sách biểu tượng có sẵn");
			if (sender.hasPermission("sieuchat.admin"))
				sender.sendMessage("&6&l/sieuchat tailai: &f&lTải lại plugin");
		} else if (args.length == 1 && args[0].equalsIgnoreCase("tailai") && sender.hasPermission("sieuchat.admin")) {
			cfg.taiLai();
			sender.sendMessage(mau("&aPlugin đã được tải lại!"));
		} else if (args.length == 1 && args[0].equalsIgnoreCase("bieutuong")) {
			cfg.lay().getConfigurationSection("SieuChat").getKeys(false).forEach(muc -> {
				macDinh++;
			});
			cfg.lay().getConfigurationSection("SieuChat").getKeys(false).forEach(muc -> {
				themVao++;
				troGiup = troGiup + cfg.lay().getString("SieuChat." + muc + ".Nhap-Vao") + " -> "
						+ cfg.lay().getString("SieuChat." + muc + ".Thay-The");
				if (macDinh == themVao) {
					troGiup = troGiup + ".";
				} else {
					troGiup = troGiup + ", ";
				}
			});
			sender.sendMessage(troGiup);
			troGiup = "";
			macDinh = 0;
			themVao = 0;
		}
		return true;
	}
}
