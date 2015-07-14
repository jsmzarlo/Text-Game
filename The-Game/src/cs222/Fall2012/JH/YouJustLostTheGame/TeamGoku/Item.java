package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

/**
 * Allows player to interact similarly with items of different types i.e. armor and keyItems
 * 
 * @author Joseph Leffler, Mike Henderson, David Alexander, Jeffrey McDaniel
 * 
 */

public interface Item {
	
	public String getName();
	
	public String shortDescription();
	
	public String longDescription();
	
	public int getValue();
}
