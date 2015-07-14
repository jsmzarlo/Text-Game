package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

public class Door{
	
	private String name;
	private String longDescription;
	private String shortDescription;
	private String direction;
	private Boolean lockedValue;
	private int idNumber;

	public Door(String name, String shortDesciription, String longDescription,String direction, Boolean lockedValue, int idNumber){
		this.name = name;
		this.shortDescription = shortDesciription;
		this.longDescription = longDescription;
		this.lockedValue = lockedValue;
		this.idNumber = idNumber;
	}

	public String getName() {
		return name;
	}

	public String shortDescription() {
		return shortDescription;
	}

	public String longDescription() {
		return longDescription;
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
	public String getDirection() {
		return direction;
	}

	public int getID() {
		
		return idNumber;
	}

	public Boolean getLockedValue() {
		return lockedValue;
	}
	
	public void setLockedValueFalse() {
		this.lockedValue = false; 
	}
}
