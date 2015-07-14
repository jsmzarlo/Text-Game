package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

import java.util.ArrayList;

import cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku.GUI.GameGUI;

/**
 * General player character information
 * 
 *  @author Joseph Leffler, Mike Henderson, David Alexander, Jeffrey McDaniel
 *  
 */

public final class Player extends Character {
	protected static int defense;
	private static ArrayList<Item> inventory = new ArrayList<Item>();
	private Equipment equipment = new Equipment();
	
	public Player() {
		statsAtStart();
	}

	private void statsAtStart() {
		name = "YOU";
		maxHealth = 100;
		health = 100;
		attack = 3;
		defense = 0;
	}

	@Override
	public int getAttack() {
		try {
			return equipment.weapon.getValue() + attack;
		} catch (Exception e) {
			return attack;
		}
	}
	
	public String getOverallDescription() {
		return getStatsDescription() + "\n==== Your Items ====\n" + getInventoryDescription();
	}
	public String getStatsDescription() {
		return "==== Your Status ====\nHealth: (" + health + "/" + maxHealth + ")\nStrength: " + getAttack() + "\nDefense: " + getDefense();
	}
	public String getInventoryDescription() {
		String output = "";
		boolean weaponEquipped = false;
		boolean armorEquipped = false;
		int i = 0;
		for(Item item : inventory) {
			output += String.format("Slot%02d - ", ++i) + item.getName();
			if(item instanceof ConsumableHealth) {
				output += " (Food)";
			} else if(item instanceof KeyItem) {
				output += " (Key)";
			} else if(item instanceof Weapon) {
				output += " (Weapon)";
				if(equipment.weapon.equals(item) && !weaponEquipped) {
					output += " (X)";
					weaponEquipped = true;
				}
			} else if(item instanceof Armor) {
				output += " (Armor)";
				if(equipment.armor.equals(item) && !armorEquipped) {
					output += " (X)";
					armorEquipped = true;
				}
			}
			output += "\n";
		}
		
		return output;
	}
	
	public int getDefense() {
		try {
			return equipment.armor.getValue() + defense;
		} catch (Exception e) {
			return defense;
		}
	}
	
	public boolean isInInventory(String itemName) {
		return isInInventory(ItemLibrary.get(itemName));
	}
	public boolean isInInventory(Item item) {
		return inventory.contains(item);
	}
	
	public void useItem(String itemName) {
		boolean itemInInventory = isInInventory(itemName);
		boolean itemInRoom = GameMap.getCurrentRoom().itemExistsInRoom(itemName);
		if(!itemInInventory && itemInRoom) {
			Item takenFromRoom = GameMap.getCurrentRoom().takeItemFromRoom(itemName);
			GameGUI.player.addToInventory(takenFromRoom);
			itemInInventory = true;
		}
		if(itemInInventory) {
			Item item = ItemLibrary.get(itemName);
			if(item instanceof Weapon) {
				equipItem(item);
			} else if(item instanceof ConsumableHealth) {
				GameGUI.addToConsole("Consuming " + item.getName() + "...");
				GameMap.getCurrentRoom().combat.healPlayerWith(item);
				GameGUI.updateGUI();
			} else if(item instanceof KeyItem) {
				GameLog.warning("THIS IS A KEY ITEM!");
			} else {
				GameLog.severe("MAJOR ISSUES!");
			}
		} else {
			GameGUI.addToConsole("I'm not sure how I would use that...");
		}
		
	}
	
	
	public void discardItem(String itemName) {
		discardItem(ItemLibrary.get(itemName));
	}
	public void discardItem(Item item){
		if(inventory.contains(item)){
			if(item instanceof KeyItem) {
				GameGUI.addToConsole("You can not remove Key Items");
				return;
			}
			
			GameMap.getCurrentRoom().getAllItems().add(item);
			inventory.remove(item);
			if(item instanceof Armor){
				if(!isInInventory(equipment.armor)){
					equipment.armor = new Armor("","","",0); 
				}
			} else if(item instanceof Weapon){
				if(!isInInventory(equipment.weapon)){
					equipment.weapon = new Weapon("","","",0);
				}
			}
			GameGUI.addToConsole("You toss away " + item + " in disgust");
		} else {
			GameGUI.addToConsole("You do not have " + item);
		}
		GameGUI.updateGUI();
	}
	
	public void addToInventory(Item item) {
		if(isInventoryFull()){
			GameGUI.addToConsole("Your inventory is full. You cannot put " + item + " into your inventory without becuming encumbered");
		}
		else{
			inventory.add(item);
			GameGUI.addToConsole("You put " + item + " in inventory ");
			GameGUI.updateGUI();
		}
	}
	
	public void inspectInventoryFor(Item item){
		if(inventory.contains(item)){
			GameGUI.addToConsole("You have " + item);
		}
		GameGUI.addToConsole("You dont have " + item);
	}
	
	public void equipItem(Item item){
		if(inventory.contains(item)) {
			if(item instanceof Armor) {
				equipment.armor = (Armor)item;
				GameGUI.addToConsole("You slip on your " + item);
		    } else if(item instanceof Weapon) {
		    	equipment.weapon = (Weapon)item;
		   		GameGUI.addToConsole("You bring your "+ item + " to the ready");
		   	 } else {
		   		GameGUI.addToConsole(item + " is not equipable");
		   	} 
		} else {
			GameGUI.addToConsole("You don't have that item");
		}
		GameGUI.updateGUI();
	}
	
	public void unEquipItem(Item item) {
		if(inventory.contains(item)) {
			if(item.equals(equipment.armor)) {
				equipment.armor = new Armor("","","",0);
				GameGUI.addToConsole("You slip off your " + item);
			} else if(item.equals(equipment.weapon)) {
				equipment.weapon = new Weapon("","","",0);
				GameGUI.addToConsole("You put your " + item + " away");
			} else {
				GameGUI.addToConsole(item + " is not equipped");
			}
		} else {
			GameGUI.addToConsole("You don't have that item");
		}
		GameGUI.updateGUI();
	}
	
	@Override
	public void takesDamage(int damage){
		int actualDamage = damage - getDefense();
		
		super.takesDamage(actualDamage);
		
		if(isDead())
			CommandParser.playerDeath();
	}
	
	public boolean isInventoryFull() {
		if(inventory.size()>14)
			{return true;}
		else
			{return false;}
	}
	
	public ArrayList<Item> getInventory(){
		return inventory;
	}
	
	public void hurtSelf() {
		takesDamage(getAttack());
	}
}