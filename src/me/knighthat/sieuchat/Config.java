package me.knighthat.sieuchat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config {

	SieuChat plugin;

	public Config(SieuChat plugin) {
		this.plugin = plugin;
		khoiDong();
	}

	private File tep = null;
	private FileConfiguration config = null;

	public void khoiDong() {

		if (tep == null)
			tep = new File(plugin.getDataFolder(), "config.yml");

		if (!tep.exists())
			plugin.saveResource("config.yml", false);
	}

	public void taiLai() {
		if (tep == null)
			tep = new File(plugin.getDataFolder(), "config.yml");

		config = YamlConfiguration.loadConfiguration(tep);
		InputStream duLieu = plugin.getResource("config.yml");
		if (duLieu != null) {
			YamlConfiguration macDinh = YamlConfiguration
					.loadConfiguration(new InputStreamReader(duLieu, Charset.forName("UTF8")));
			config.setDefaults(macDinh);
		}
	}

	public FileConfiguration lay() {
		if (config == null)
			taiLai();
		FileInputStream file = null;
		try {
			file = new FileInputStream(tep);
		} catch (FileNotFoundException e) {
			plugin.getServer().getConsoleSender().sendMessage(plugin.mau("&cKhông tìm thấy " + tep));
		}
		return config = YamlConfiguration.loadConfiguration(new InputStreamReader(file, Charset.forName("UTF8")));
	}

	public void luu() {
		if (tep == null || config == null)
			return;

		try {
			config.save(tep);
		} catch (IOException e) {
			plugin.getServer().getConsoleSender().sendMessage(plugin.mau("&cKhông thể lưu " + tep));
		}
	}
}
