package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;


/**
 * Armor items for modifying player armor value
 * 
 * @author Joseph Leffler, Mike Henderson, David Alexander, Jeffrey McDaniel
 * 
 */

public final class Armor implements Item {
	
	private String name;
	private String longDescription;
	private String shortDescription;
	private int armorModifier;
	
	public static boolean isThisType(String name) {
		if(ItemLibrary.get(name) instanceof Armor)
			return true;
		return false;
	}
	
	public Armor(String name, String shortDescription, String longDescription, int value){
		this.name = name;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.armorModifier = value;
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public int getValue() {
		return armorModifier;
	}
	
	@Override
	public String toString(){
		return name;
	}

	@Override
	public String longDescription() {
		return longDescription;
	}
	
	@Override
	public String shortDescription() {
		return shortDescription;
	}
}