package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;

/**
 * Special items that can not be discarded and only used on their specific door.
 * 
 * @author Joseph Leffler, Mike Henderson, David Alexander, Jeffrey McDaniel
 * 
 */

public class KeyItem implements Item {

	private String name;
	private String longDescription;
	private String shortDescription;
	private int itemValue;
	
	public static boolean isThisType(String name) {
		if(ItemLibrary.get(name) instanceof KeyItem)
			return true;
		return false;
	}
	
	public KeyItem(String name, String shortDescription, String longDescription, int value){
		this.name = name;
		this.longDescription = longDescription;
		this.shortDescription = shortDescription;
		this.itemValue = value;
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public int getValue(){
		return itemValue;
	}
	
	@Override
	public String toString(){
		return name;
	}

	@Override
	public String shortDescription() {
		return shortDescription;
	}

	@Override
	public String longDescription() {
		return longDescription;
	}
}

