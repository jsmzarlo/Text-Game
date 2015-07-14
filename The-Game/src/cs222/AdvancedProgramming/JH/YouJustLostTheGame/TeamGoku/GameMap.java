package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;

import java.util.Hashtable;
import java.util.regex.Pattern;

import cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku.GUI.GameGUI;


/**
 * Moves the player and holds the current room 
 * 
 * @author Joseph Leffler, Mike Henderson, David Alexander, Jeffrey McDaniel
 * 
 */


public class GameMap {
	
	private static Hashtable<String, Room> roomsTable;
	private static Room currentRoom;
	private static XMLparser xml;
	
	public static void start(String questXMLFile){
		xml = new XMLparser(questXMLFile);
		GameGUI.player = new Player();
		GameGUI.player.getInventory().clear();
		GameGUI.resetConsole();
		roomsTable = new Hashtable<String, Room>();
		CartesianPoint.setCoordinates(0,0);
		GameGUI.updateGUI();
		GameGUI.addToConsole("Loaded scenario from " + questXMLFile);
		loadRoom();
	}
	public static void restart() {
		GameLog.info("Restarting game");
		start(GameGUI.filePath);
	}
	
	public static void movePlayerNorth(){
		if(!xml.roomNorthDescript().equals("an invisible wall")){
			Door currentRoomDoor = currentRoom.getRoomDoor();
			if(currentRoom.containsLockedDoor() && currentRoomDoor.getDirection().equalsIgnoreCase("north")){
				
				GameGUI.addToConsole("You run head first into a "+ currentRoomDoor.getName());
			}
			else{
			CartesianPoint.y++;
			loadRoom();
			}
		}
		else
			GameGUI.addToConsole("You run head first into an invisible wall. You remain the same room.");
		
	}

	public static void movePlayerSouth(){
		if(!xml.roomSouthDescript().equals("an invisible wall")){
			Door currentRoomDoor = currentRoom.getRoomDoor();
			if(currentRoom.containsLockedDoor() && currentRoomDoor.getDirection().equalsIgnoreCase("south")){
				
				GameGUI.addToConsole("You run head first into a "+ currentRoomDoor.getName());
			}
			else{
			CartesianPoint.y--;
			loadRoom();
			}
		}
		else
			GameGUI.addToConsole("You run head first into an invisible wall. You remain the same room.");
		
	}
	
	public static void movePlayerEast(){
		if(!xml.roomEastDescript().equals("an invisible wall")){
			Door currentRoomDoor = currentRoom.getRoomDoor();
			if(currentRoom.containsLockedDoor() && currentRoomDoor.getDirection().equalsIgnoreCase("east")){
				
				GameGUI.addToConsole("You run head first into a "+ currentRoomDoor.getName());
			}
			else{
			CartesianPoint.x++;
			loadRoom();
			}
		}
		else
			GameGUI.addToConsole("You run head first into an invisible wall. You remain the same room.");
		
	}
	
	public static void movePlayerWest(){
		if(!xml.roomWestDescript().equals("an invisible wall")){
			Door currentRoomDoor = currentRoom.getRoomDoor();
			if(currentRoom.containsLockedDoor() && currentRoomDoor.getDirection().equalsIgnoreCase("west")){
				
				GameGUI.addToConsole("You run head first into a "+ currentRoomDoor.getName());
			}
			else{
			CartesianPoint.x--;
			loadRoom();
			}
		}
		else
			GameGUI.addToConsole("You run head first into an invisible wall. You remain the same room.");
		
	}
	
	private static void loadRoom() {
		currentRoom = roomsTable.get(CartesianPoint.keyCreator());
		if(!(currentRoom instanceof Room)){
			currentRoom = xml.loadRoom();
			roomsTable.put(CartesianPoint.keyCreator(), currentRoom);
		}
		GameGUI.addToConsole(currentRoom.getFullRoomDescription());
	}
	
	public static Room getCurrentRoom(){
		return currentRoom;
	}
	
	public static String getNorthDescript(){
		return xml.roomNorthDescript();
	}
	
	public static String getSouthDescript(){
		return xml.roomSouthDescript();
	}
	
	public static String getWestDescript(){
		return xml.roomWestDescript();
	}
	
	public static String getEastDescript(){
		return xml.roomEastDescript();
	}
	
	public static boolean isItem(String itemName) {
		if(ItemLibrary.get(itemName) instanceof Item)
			return true;
		return false;
	}
	public static boolean isEnemy(String enemyName) {
		try {
			@SuppressWarnings("unused")
			Combatant foo = currentRoom.getEnemyByName(enemyName);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public static boolean isPlayer(String name) {
		return Pattern.matches("(?i)(self|me|myself)", name);
	}
}