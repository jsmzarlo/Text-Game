package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;

/**
 * Weapon items for modifying player attack value
 * 
 * @author Joseph Leffler, Mike Henderson, David Alexander, Jeffrey McDaniel
 * 
 */

public class Weapon implements Item{

	private String name;
	private String longDescription;
	private String shortDescription;
	private int attackModifier;
	
	public static boolean isThisType(String name) {
		if(ItemLibrary.get(name) instanceof Weapon)
			return true;
		return false;
	}
	
	public Weapon(String name, String shortDescription, String longDescription, int value){
		this.name = name;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.attackModifier = value;
	}
	
	@Override
	public String getName(){
		return name;
	}

	@Override
	public int getValue() {
		return attackModifier;
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