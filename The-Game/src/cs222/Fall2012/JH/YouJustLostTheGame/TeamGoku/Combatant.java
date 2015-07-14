package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

public class Combatant extends Character {
	
	protected String eventTextPlayerDeath = "";
	protected String eventTextTalk = "";
	protected String eventTextDeath = "";
	protected String longDescription = "";
	protected String shortDescription = "";
	
	protected int healAmount = 4;
	protected boolean canHeal = false;
	private boolean statusEnemy = false;

	public Combatant() {
		super();
	}

	public Combatant(int health, int attack) {
		super(health, attack);
		
	}
	
	public void useItem(Item item) {
		
	}
	
	public String getTalkText() {
		return eventTextTalk;
	}
	public String getDeathText() {
		return eventTextDeath;
	}
	public String getPlayerDeathText() {
		return eventTextPlayerDeath;
	}
	public String getOverallDescription() {
		return name + " (" + health + "/" + maxHealth + ")\nStrength: " + getAttack() + "\n" + longDescription;
	}
	
	public void heal() {
		heal(healAmount);
	}
	
	public boolean isEnemy() {
		return statusEnemy;
	}
	public void becomeAggressive() {
		statusEnemy = true;
	}
	public void becomeDocile() {
		statusEnemy = false;
	}
	
	@Override
	public void takesDamage(int damage) {
		super.takesDamage(damage);
		becomeAggressive();
	}
}
