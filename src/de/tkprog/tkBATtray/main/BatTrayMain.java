package de.tkprog.tkBATtray.main;

import java.awt.CheckboxMenuItem;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

import javax.imageio.ImageIO;

public class BatTrayMain {

	private static final String configfile = "battray.conf";
	private static final String configfilecomment = "This is the configuration file of the tkBATtray";
	private static BufferedReader br;
	
	public static void main(String[] args) {
		logger = new Logger("log/log_"+System.currentTimeMillis()+".log");
		logger.logln("*---------------------------------------------*");
		logger.logln("*          tkBATtray - Battery widget         *");
		logger.logln("*                 by TKPRO(G)                 *");
		logger.logln("*---------------------------------------------*\r\n");
		
		checkSystem();
		
		new BatTrayMain();
	}

	private static boolean checkSystem() {
		String sys = getSystem();
		logger.logDebugln("Running \""+sys+"\"");
		if(sys.contains("Lin") || sys.contains("lin")){
			logger.logDebugln("\tACCEPTED");
		}
		else{
			logger.logln("You're not running a Linux distribution. Do you really want to continue? (y/N): ");
			br = new BufferedReader(new InputStreamReader(System.in));
			try {
				String l = br.readLine();
				logger.logDebugln("User answered \""+l+"\".");
				if(l.equalsIgnoreCase("y")){
					logger.logDebugln("\tcontinuing");
					return true;
				}
				else{
					logger.logDebugln("\tstoping");
				}
			} catch (Exception e) {
				logger.logError(e);
			}
			logger.logErrorln("Exiting program");
			System.exit(0);
		}
		return false;
	}

	private static String getSystem() {
		return System.getProperty("os.name");
	}

	private PopupMenu popup;
	private TrayIcon trayIcon;
	private SystemTray tray;
	private boolean run = true;
	private BatteryInformation_ArchLinux BIAL;
	private IconImageGen iig;
	public static Logger logger;
	
	public BatTrayMain(){
		Config.getInstance().load(configfile);
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
		logger.logInfoln("Starting Tray Icon...");
		logger.logInfoln("Checking if supported...");
		if(!SystemTray.isSupported()){
			logger.logErrorln("ERR.: SystemTray is not supported!");
			logger.logErrorln("Quitting");
			return;
		}
		logger.log(" SUPPORTED :)");
		logger.logInfoln("Loading TrayIconImage...");
		BIAL.update(Config.getInstance().getProperty(Config_defaults.batDirFolderLocation));
		BufferedImage d = iig.getImage(BIAL);
		
		logger.logInfoln("\tGenerating Instances...");
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
        
		logger.logInfoln("\tAdding Tray Icon...");
       
        try {
            tray.add(trayIcon);
        } catch (Exception e) {
			logger.logErrorln("ERR.: TrayIcon could not be added.\nQuitting");
			return;
        }
	}

}
