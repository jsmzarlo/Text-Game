package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;

import java.util.LinkedList;

import cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku.GUI.GameGUI;

/**
 * Data Structure holding current room information where the player resides.
 * 
 * @author Joseph Leffler, Mike Henderson, David Alexander, Jeffrey McDaniel
 * 
 */

public class Room {

	private String shortDescription;
	private String longDescription;
	private LinkedList<Item> items = new LinkedList<Item>();
	private Door door;
	private LinkedList<Combatant> enemies = new LinkedList<Combatant>();
	public CombatSystem combat;
	
	public Room(){
		combat = new CombatSystem(enemies);
	}
	
	public static Room instance(){
		return new Room();
	}

	public String getQuickRoomDescription() {
		return shortDescription;
	}

	public void setLongDescription(String longRoomDescription) {
		longDescription = longRoomDescription;
	}
	
	public void setShortDescription(String shortRoomDescription) {
		shortDescription = shortRoomDescription;
	}
	
	public String getFullRoomDescription(){
		return longDescription;
	}
	
	public LinkedList<Item> getAllItems(){
		return items;
	}
	
	
	public void addCharacters(Combatant newCharacter){
		enemies.add(newCharacter);
	}
	
	public void removeCharacter(Combatant enemy) {
		enemies.remove(enemy);
	}
	
	public LinkedList<Combatant> getRoomEnemies(){
		return enemies;
	}
	
	public Combatant getEnemyByName(String name) throws EnemyNotFoundInRoomException {
		for(Combatant enemy : enemies) {
			if(enemy.name.equalsIgnoreCase(name))
				return enemy;
		}
		throw new EnemyNotFoundInRoomException();
	}

	public boolean itemExistsInRoom(String itemName) {
		return items.contains(ItemLibrary.get(itemName));
	}
	public boolean itemExistsInRoom(Item item) {
		return items.contains(item);
	}
	
	public Item takeItemFromRoom(String itemName) {
		Item item = ItemLibrary.get(itemName);
		if(itemExistsInRoom(itemName)) {
			items.remove(item);
			return item;
		} else {
			GameLog.warning("Item not found in the Room");
			throw new ItemNotFoundInRoomException();
		}
	}

	public void putItemInRoom(Item item) {
		items.add(item);
	}
	
	public void putDoorInRoom(Door object) {
		door = object;
	}
	
	public Door getRoomDoor(){
		return door;
	}

	public boolean containsLockedDoor() {
		if(door == null){
			return false;
		}
		else{
			return door.getLockedValue();
		}
	}
	
	public void dropInventory(Character enemy){
		items.addAll(enemy.inventory);
	}
	
	public boolean canUnlock(String itemName) {
		Key doorKey = (Key) ItemLibrary.get(itemName);
		if(door != null)
			return door.getID() == doorKey.getValue();
		return false;
	}
	
	public void unlockDoor(String itemName){
		if(canUnlock(itemName) && GameGUI.player.isInInventory(itemName)) {
			door.setLockedValueFalse();
			GameGUI.addToConsole("A path opens up to the " + door.getDirection());
		}
	}
}