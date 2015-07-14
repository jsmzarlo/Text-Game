package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

import java.util.LinkedList;

import cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku.GUI.GameGUI;

public class Character {
	protected int health = 20;
	protected int maxHealth = 20;
	protected int attack = 5;
	protected String name = "target";
	protected LinkedList<Item> inventory = new LinkedList<Item>();
	
	public Character() {}
	
	public Character(int health, int attack) {
		this.health = health;
		this.attack = attack;
	}
	
	public int getHealth() {
		return health;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public int getAttack() {
		return attack;
	}
	
	public void takesDamage(int damage) {
		if(health < damage) {
			GameGUI.addToConsole(name + " takes " + health + " damage and dies.");
			health = 0;
			return;
		} else {
			health -= damage;
			GameGUI.addToConsole(name + " takes " + damage + " damage.");
		}
	}
	
	public void heal(int healthIncrease) {
		try {
			GameGUI.addToConsole("Attempting to heal " + name + ".");
			if(healthIncrease < 0) throw new Exception();
			int delta;
			if(health + healthIncrease > maxHealth) {
				delta = maxHealth - health;
				health = maxHealth;
			}
			else {
				delta = healthIncrease;
				health += healthIncrease;
			}
			GameGUI.addToConsole(name + " healed by " + delta + ". (" + health + "/" + maxHealth + ")");
		} catch(Exception e) {
			GameLog.warning(name + " attempted to heal by a negative value. Using damage handler instead.");
			takesDamage(healthIncrease * -1);
		}
	}
	public boolean isDead() {
		return (health < 1);
	}
	
}