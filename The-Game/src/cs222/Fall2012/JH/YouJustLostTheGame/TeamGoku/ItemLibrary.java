package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

import java.util.Hashtable;

public final class ItemLibrary {
	
	private final static Hashtable<String,Item> ITEMLIBRARY = new Hashtable<String,Item>();
	
	public static void put(String itemName, Item item) {
		ITEMLIBRARY.put(itemName.toLowerCase(), item);
	}
	public static Item get(String itemName) {
		return ITEMLIBRARY.get(itemName.toLowerCase());
	}
	
}
