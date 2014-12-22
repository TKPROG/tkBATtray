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
import java.io.FileOutputStream;
import java.util.Properties;

import javax.imageio.ImageIO;

public class BatTrayMain {

	private static final String configfile = "battray.conf";
	private static final String configfilecomment = "This is the configuration file of the tkBATtray";
	
	public static void main(String[] args) {
		new BatTrayMain();
	}

	private Config config;
	private PopupMenu popup;
	private TrayIcon trayIcon;
	private SystemTray tray;
	private boolean run = true;
	private BatteryInformation_ArchLinux BIAL;
	private IconImageGen iig;
//	public static AsyncLogger logger;
	
	public BatTrayMain(){
		Config.getInstance().load(configfile);
//		logger = new AsyncLogger(
//				new LoggerContext("LoggerContextFun"), 
//				"LoggerFun", 
//				new MessageFormatMessageFactory());
//		logger.debug("DEBUGGGG");
		BIAL = new BatteryInformation_ArchLinux();
		iig = new IconImageGen();
		iig.setWidth(20);
		iig.setHeight(20);
		startTrayIcon();
		startUpdateThread();
	}

	private void update() {
		gatherInfos();
		calculateInfos();
		updateIcon();
	}
	
	private void updateIcon() {
		BufferedImage d = iig.getImage(BIAL);
		if(d != null){
			trayIcon.setImage(d);
		}
	}

	private void calculateInfos() {
	}

	private void gatherInfos() {
		BIAL.update(Config.getInstance().getProperty(Config_defaults.batDirFolderLocation));
	}

	private void startUpdateThread() {
		Thread t = new Thread(new Runnable(){
			long sleepTime = 0;
			@Override
			public void run() {
				try{
					sleepTime = Long.parseLong(Config.getInstance().getProperty(Config_defaults.updateThreadSleepTime));
				} catch(Exception e){
					e.printStackTrace();
					sleepTime=5000;
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
		BIAL.update(Config.getInstance().getProperty(Config_defaults.batDirFolderLocation));
		BufferedImage d = iig.getImage(BIAL);
		
		System.out.println("\tGenerating Instances...");
		popup = new PopupMenu();
		trayIcon = new TrayIcon(d);
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

}
