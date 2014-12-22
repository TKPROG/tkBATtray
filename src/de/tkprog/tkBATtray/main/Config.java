package de.tkprog.tkBATtray.main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.Properties;

public class Config {
	private static Config INSTANCE = new Config();
	private Properties p = new Properties();
	private HashMap<String, String> defaults = new HashMap<String, String>();
	private String ___save;
	private String defaultConfigComm;
	
	private Config(){
		Config_defaults.set(this);
	}
	
	public static Config getInstance(){
		return INSTANCE;
	}
	
	public String getProperty(String key){
		if(p.containsKey(key)){
			return p.getProperty(key);
		}
		p.setProperty(key, getDefaultProperty(key));
		return getProperty(key);
	}

	public String getDefaultProperty(String key) {
		if(defaults.containsKey(key)){
			return defaults.get(key);
		}
		System.err.println("Config.getDefaultProperty(...): No Default Value for '"+key+"' found...");
		return null;
	}
	
	public void setDefaultValue(String key, String defaultValue){
		defaults.put(key, defaultValue);
	}
	
	public void removeDefaultValue(String key){
		defaults.remove(key);
	}
	
	public void fillWithDefaults(){
		p.clear();
		p.putAll(defaults);
	}
	
	public void load(){
		load(___save);
	}
	
	public void load(String saveee){
		p = new Properties();
		try {
			p.load(new FileInputStream(new File(saveee)));
			___save = saveee;
		} catch (Exception e) {
			e.printStackTrace();
			fillWithDefaults();
			store(saveee);
		}
	}
	
	public void store(){
		store(___save);
	}
	
	public void store(String saveee){
		storeC(saveee, defaultConfigComm);
	}
	
	public void storeC(String defaultConfigComm){
		storeC(___save, defaultConfigComm);
	}
	
	public void storeC(String saveee, String defaultConfigComm){
		try {
			p.store(new FileOutputStream(new File(___save)), defaultConfigComm);
			this.defaultConfigComm = defaultConfigComm;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
