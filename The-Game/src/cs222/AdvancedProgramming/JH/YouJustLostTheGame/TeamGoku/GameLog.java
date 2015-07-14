package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.XMLFormatter;


/**
 * Logging module able to be called statically.
 * USE: GameLog.log(Level.SEVERE, "There was an error!");
 * USE: GameLog.warning("", Exception);
 * 
 * @author dpalexander
 */
public class GameLog {
	protected static Logger logger = Logger.getLogger("YouJustLostTheGame");;
	protected static Level logLevel = Level.INFO;
	protected static boolean isSetup = false;
	protected static FileHandler txt;
	protected static FileHandler xml;
	
	protected static void setup() {
		logger.setLevel(logLevel);
		logger.setUseParentHandlers(false);
		
		try {
			//setupTextLogger();
			setupXMLLogger();
		} catch(IOException e) {
			logger.log(Level.SEVERE, null, e);
		} finally {
			isSetup = true;
		}
	}
	
	protected static void setupTextLogger() throws IOException {
		txt = new FileHandler(logger.getName() + ".log"); // System.currentTimeMillis()
		txt.setFormatter(new SimpleFormatter());
		logger.addHandler(txt);
	}
	protected static void setupXMLLogger() throws IOException {
		xml = new FileHandler(logger.getName() + ".log.xml"); // System.currentTimeMillis()
		xml.setFormatter(new XMLFormatter());
		logger.addHandler(xml);
	}
	
	public static Logger getLogger() {
		setup();
		return logger;
	}
	
	public static void log(Level level, String msg) {
		if(!isSetup) setup();
		logger.log(level, msg);
	}
	public static void log(Level level, String msg, Throwable thrown) {
		if(!isSetup)
			setup();
		logger.log(level, msg, thrown);
	}
	public static void info(String msg) {
		GameLog.log(Level.INFO, msg);
	}
	public static void info(String msg, Throwable thrown) {
		GameLog.log(Level.INFO, msg, thrown);
	}
	public static void severe(String msg) {
		GameLog.log(Level.SEVERE, msg);
	}
	public static void severe(String msg, Throwable thrown) {
		GameLog.log(Level.SEVERE, msg, thrown);
	}
	public static void warning(String msg) {
		GameLog.log(Level.WARNING, msg);
	}
	public static void warning(String msg, Throwable thrown) {
		GameLog.log(Level.WARNING, msg, thrown);
	}
}