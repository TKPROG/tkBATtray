package de.tkprog.tkBATtray.main;

import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;

public class BatTrayMain {

	private static final String configfile = "battray.conf";
	private static final String configfilecomment = "This is the configuration file of the tkBATtray";
	
	private static final String config_trayiconimageurl = "trayiconimageurl";
	protected static final String config_sleepTime = "updateintervall";

	public static void main(String[] args) {
		new BatTrayMain();
	}

	private Properties config;
	private PopupMenu popup;
	private TrayIcon trayIcon;
	private SystemTray tray;
	private boolean run = true;
	
	public BatTrayMain(){
		try{
			loadConfiguration();
			startTrayIcon();
			startUpdateThread();
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	private void update() {
		gatherInfos();
		calculateInfos();
		updateIcon();
	}
	
	private void updateIcon() {
		// TODO Auto-generated method stub
		
	}

	private void calculateInfos() {
		// TODO Auto-generated method stub
		
	}

	private void gatherInfos() {
	}

	private void startUpdateThread() {
		Thread t = new Thread(new Runnable(){
			long sleepTime = 0;
			@Override
			public void run() {
				try{
					sleepTime = Long.parseLong(getProperty(config_sleepTime));
				} catch(Exception e){
					e.printStackTrace();
					sleepTime=1000;
				}
				while(run){
					update();
					try {
						Thread.sleep(sleepTime);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
	}

	private void startTrayIcon() {
		System.out.println("Starting Tray Icon...");
		System.out.print("Checking if supported...");
		if(!SystemTray.isSupported()){
			System.out.println("\nERR.: SystemTray is not supported!\nQuitting");
			return;
		}
		System.out.println(" SUPPORTED :)");
		System.out.println("Loading TrayIconImage...");
		Image trayiconimage = null;
		if((trayiconimage = loadTrayIconImage(getProperty(config_trayiconimageurl)))==null){
			System.out.println("ERR.: Wasn't able to load the image at '"+getProperty(config_trayiconimageurl)+"'\nQuitting");
			return;
		}
		
		System.out.println("\tGenerating Instances...");
		popup = new PopupMenu();
		trayIcon = new TrayIcon(trayiconimage);
        tray = SystemTray.getSystemTray();
       
        // Create a pop-up menu components
        MenuItem aboutItem = new MenuItem("About");
        CheckboxMenuItem cb1 = new CheckboxMenuItem("Set auto size");
        CheckboxMenuItem cb2 = new CheckboxMenuItem("Set tooltip");
        Menu displayMenu = new Menu("Display");
        MenuItem errorItem = new MenuItem("Error");
        MenuItem warningItem = new MenuItem("Warning");
        MenuItem infoItem = new MenuItem("Info");
        MenuItem noneItem = new MenuItem("None");
        MenuItem exitItem = new MenuItem("Exit");
       
        //Add components to pop-up menu
        popup.add(aboutItem);
        popup.addSeparator();
        popup.add(cb1);
        popup.add(cb2);
        popup.addSeparator();
        popup.add(displayMenu);
        displayMenu.add(errorItem);
        displayMenu.add(warningItem);
        displayMenu.add(infoItem);
        displayMenu.add(noneItem);
        popup.add(exitItem);
       
        trayIcon.setPopupMenu(popup);
        
		System.out.println("\tAdding Tray Icon...");
       
        try {
            tray.add(trayIcon);
        } catch (Exception e) {
			System.out.println("ERR.: TrayIcon could not be added.\nQuitting");
			return;
        }
	}

	private String getProperty(String con) {
		System.out.println("# Getting property...");
		if(config.containsKey(con)){
			System.out.println("# Found some and giving it back!");
			return config.getProperty(con);
		}
		System.out.println("# Didn't found it...");
		config.put(con, getPropertyDefault(con));
		storeConfiguration();

		System.out.println("# Returning:");
		return getProperty(con);
	}

	private Object getPropertyDefault(String con) {
		System.out.println("# Searching for Property Default...");
		if(con.equals(config_trayiconimageurl)){
			System.out.println("# Found config_trayiconimageurl!");
			return "batttray_icon.png";
		}
		else if(con.equals(config_sleepTime)){
			System.out.println("# Found config_sleepTime!");
			return "1000";
		}
		System.out.println("# Found nothing :(");
		return null;
	}

	private Image loadTrayIconImage(String trayiconimageurl2) {
		Image out = null;
		try {
			out = ImageIO.read(new File(trayiconimageurl2));
			System.out.println("* Loaded Image *");
			out = (out.getScaledInstance(20, 20, Image.SCALE_DEFAULT));
			System.out.println("\tScaled to: "+16+"x"+16);
		} catch (Exception e) {
			e.printStackTrace();
			out = null;
		}
		return out;
	}

	private void loadConfiguration() {
		System.out.println("Loading Configuration...");
		config = new Properties();
		try {
			config.load(new FileInputStream(new File(configfile)));
		} catch (Exception e) {
			e.printStackTrace();
			generateDefaultConfiguration();
			storeConfiguration();
		}
	}

	private void storeConfiguration() {
		try {
			System.out.print("Storing configuration... ");
			config.store(new FileOutputStream(new File(configfile)), configfilecomment);
			System.out.println(" DONE");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void generateDefaultConfiguration() {
		config = new Properties();
		config.put(config_trayiconimageurl, getPropertyDefault(config_trayiconimageurl));
		config.put(config_sleepTime, getPropertyDefault(config_sleepTime));
	}

}
