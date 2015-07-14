package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;

public class ConsumableHealth implements Item {

	
	private String name;
	private String longDescription;
	private String shortDescription;
	private int healthModifier;
	
	public static boolean isThisType(String name) {
		if(ItemLibrary.get(name) instanceof ConsumableHealth)
			return true;
		return false;
	}
	
	public ConsumableHealth(String name, String shortDesciription, String longDescription, int value){
		this.name = name;
		this.shortDescription = shortDesciription;
		this.longDescription = longDescription;
		this.healthModifier = value;
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public int getValue() {
		return healthModifier;
	}

	@Override
	public String shortDescription() {
		return shortDescription;
	}

	@Override
	public String longDescription() {
		return longDescription;
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
