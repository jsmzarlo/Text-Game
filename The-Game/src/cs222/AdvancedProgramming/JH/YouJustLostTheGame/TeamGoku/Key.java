package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;

public class Key implements Item {
	
	private String name;
	private String longDescription;
	private String shortDescription;
	private int idNumber;

	public Key(String name, String shortDesciription, String longDescription, int idNumber){
		this.name = name;
		this.shortDescription = shortDesciription;
		this.longDescription = longDescription;
		this.idNumber = idNumber;
	}

	@Override
	public String getName() {
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
	
	@Override
	public String toString() {
		return getName();
	}


	@Override
	public int getValue() {
		return idNumber;
	}
	
}
