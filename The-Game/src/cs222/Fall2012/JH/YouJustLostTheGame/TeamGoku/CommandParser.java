package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

import java.util.LinkedList;
import java.util.regex.Pattern;

import cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku.GUI.GameGUI;

/**
 * A parser to interpret user input from the application command line
 * 
 * @author David Alexander <dpalexander@bsu.edu>
 */
public class CommandParser {
	private static boolean debug = false;
	
	public static String input;
	protected static String confirmInput;
	
	protected static boolean confirmResponseLoop = false;
	protected static boolean confirmSyntaxLoop = false;
	protected static boolean avoidParsing = false;
	
	protected static String restart = "(?i)^(restart)|(seppuku)|(kill self)|(suicide)|(commit suicide)|(konami code)|(fuck( [^\\s]*)*)|(shit)|(damn)|(chicken scissors)|(lick)";
	protected static String exit = "(?i)^(exit|quit)$";
	protected static String use = "(?i)^(use|unlock)\\s([^\\s]+).*";
	protected static String useOn = "(?i)^(use|unlock)\\s([^\\s]+)\\s(on|with)\\s([^\\s]+).*";
	protected static String move = "(?i)^(go|move|travel|limp|flee|run)?\\s?(n(orth)?|s(outh)?|e(ast)?|w(est)?).*";
	protected static String take = "(?i)^(take|collect|grab)\\s([^\\s]+).*";
	protected static String attack = "(?i)^(mame|cleave|spit at|attack|harm|hurt|kill|injure)\\s([^\\s]+).*";
	protected static String help = "(?i)^help(\\s([^\\s]+))?.*";
	protected static String talk = "(?i)^talk( to)?\\s(\\w+).*";
	protected static String lookDirection = "(?i)^(look at|look|inspect|search)\\s(n(orth)?|s(outh)?|e(ast)?|w(est)?).*";
	protected static String lookGeneral = "(?i)^(look at|look|inspect|search)(\\saround)?";
	protected static String inspectItem = "(?i)^(look at|look|inspect|search)\\s([^\\s]+).*";
	protected static String equip = "(?i)^(equip|wear|put on|weild)\\s([^\\s]+).*";
	protected static String unequip = "(?i)^(unequip|remove|shed|put away)\\s([^\\s]+).*";
	protected static String discard = "(?i)^(discard|throw away)\\s([^\\s]+).*";
	protected static String confirmYes = "(?i)^y(es)?$";
	protected static String confirmNo = "(?i)^n(o)?$";
	
	// Testing purposes
	public static void main(String [] args){
		//debug = true;
		//print("Testing mode activated.");
		//parse("");
		//print("Testing mode complete.");
	}
	
	protected static void print(String contents) {
		if(debug)
			System.out.println(contents);
		else
			GameGUI.addToConsole(contents);
	}
	
	public static void parse(String command) {
		input = command.trim();
		if(command.length() == 0) return;
		if(!avoidParsing) {
			GameGUI.addToConsole("> " + input);
			GameLog.info("Parsing '" + input + "'.");
		}
		
		if(confirmSyntaxLoop) {
			if(Pattern.matches(confirmYes, input))
				confirm(true);
			else if(Pattern.matches(confirmNo, input))
				confirm(false);
			else {
				confirmSyntaxLoop = false;
				invalidCommand();
				confirmResponseLoop = false;
			}
		} else if(Pattern.matches(exit, input)) {
			parseExit();
		} else if(Pattern.matches(restart, input)) {
			parseRestart();
		} else if(Pattern.matches(useOn, input)) {
			parseUseOn();
		} else if(Pattern.matches(use, input)) {
			parseUse();
		} else if(Pattern.matches(take, input)) {
			parseTake();
		} else if(Pattern.matches(attack, input)) {
			parseAttack();
		} else if(Pattern.matches(help, input)) {
			parseHelp();
		} else if(Pattern.matches(talk, input)) {
			parseTalk();
		} else if(Pattern.matches(lookDirection, input)) {
			parseLookDirection();
		} else if(Pattern.matches(lookGeneral, input)) {
			parseLookGeneral();
		} else if(Pattern.matches(inspectItem, input)) {
			parseInspectItem();
		} else if(Pattern.matches(equip, input)) {
			parseEquip();
		} else if(Pattern.matches(unequip, input)) {
			parseUnequip();
		} else if(Pattern.matches(discard, input)) {
			parseDiscard();
		} else if(Pattern.matches(move, input)) {
			parseMove();
		} else {
			invalidCommand();
		}
	}
	
	/**
	 * Exits a dialog 
	 * 
	 * @param decision the user's response to a dialog question
	 */
	protected static void confirm(boolean decision) {
		confirmSyntaxLoop = false;
		if(decision) {
			avoidParsing = true;
			parse(confirmInput);
			avoidParsing = false;
		}
		else GameGUI.addToConsole("Well alrighty then!");
		confirmResponseLoop = false;
		confirmInput = "";
	}
	
	/**
	 * Used to print proper syntax for various commands
	 */
	protected static void displayHelp(String command) {
		print("Welcome to the help dialog!");
		switch(command.toLowerCase()){
			case "help":
				print("Hooray! You know how to use the help dialog!");
				print("HELP => 'help help' (this dialog)");
				print("That was a usage example for the action 'help'. For other available actions, simply type 'help [action]' where '[action]' is what you would like to know more about.");
				break;
			case "use":
				print("USE => 'use potion'");
				print("       'use apple'");
				print("USE ON => 'use sword on troll'");
				print("          'use key on door'");
				print("          'use potion on self'");
				break;
			case "move":
				print("MOVE => 'move north'");
				print("        'move n'");
				print("GO => 'go south'");
				print("      'go s'");
				print("TRAVEL => 'travel east'");
				print("          'travel e'");
				break;
			case "take":
				print("TAKE => 'take cake'");
				print("        'take leaves'");
				print("        'take lawn_gnome'");
				print("COLLECT => 'collect mothers_milk'");
				print("           'collect sword'");
				print("           'collect cannon_of_doom'");
				print("GRAB => 'grab leaf'");
				print("        'grab brick'");
				print("        'grab dildo'");
				break;
			case "attack":
				print("ATTACK => 'attack grue'");
				print("          'attack villager'");
				print("          'attack rat'");
				print("HURT => 'hurt guard'");
				print("        'hurt demon'");
				print("        'hurt angel'");
				print("HARM => 'harm jerk'");
				print("        'harm GLaDOS'");
				print("        'harm Bilbo_Baggins'");
				break;
			case "talk":
				print("TALK => 'talk priest'");
				print("        'talk Space_core'");
				print("        'talk cat'");
				print("TALK TO => 'talk to madman'");
				print("           'talk to prisoner'");
				print("           'talk to voice'");
				break;
			case "look":
				print("LOOK => 'look spear'");
				print("        'look North'");
				print("        'look man'");
				print("INSPECT => 'inspect gadget'");
				print("           'inspect cannon'");
				print("           'inspect chef'");
				print("SEARCH => 'search skull'");
				print("          'search throne'");
				print("          'search David'");
				break;
			case "exit":
				print("EXIT => 'exit'");
				print("QUIT => 'quit'");
				print("RESTART => 'restart'");
				break;
			default:
				print("Possible commands to research are 'use', 'move', 'take', 'attack', 'help', 'talk', 'look', and 'exit'. You may find each of these commands has an alias, but please only use those listed here when searching the help archives.");
		}
	}
	
	protected static void parseRestart() {
		if(confirmResponseLoop) { // if in confirmation loop (confirmed)
			GameMap.start(GameGUI.filePath);
		} else { // if character isn't of type enemy
			GameGUI.addToConsole("Are you sure you want to restart? [Yes/No]");
			confirmInput = input;
			confirmResponseLoop = true;
			confirmSyntaxLoop = true;
		}
	}

	protected static void parseExit() {
		if(confirmResponseLoop) { // if in confirmation loop (confirmed)
			System.exit(0);
		} else { // if character isn't of type enemy
			GameGUI.addToConsole("Are you sure you want to exit? [Yes/No]");
			confirmInput = input;
			confirmResponseLoop = true;
			confirmSyntaxLoop = true;
		}
	}
	
	protected static void parseUse() {
		GameLog.info("Parsing using USE pattern.");
		String[] args = input.split("\\s");
		try {
			
			if(KeyItem.isThisType(args[1]))
				GameMap.getCurrentRoom().unlockDoor(args[1]);
			else
				GameGUI.player.useItem(args[1]);
		} catch(Exception e) {
			invalidCommand();
		}
	}
	
	protected static void parseUseOn() {
		GameLog.info("Parsing using USE ON pattern.");
		String[] args = input.split("\\s");
		try {
			if(GameMap.isPlayer(args[3])) {
				GameGUI.player.useItem(args[1]);
			} else if(GameMap.isEnemy(args[3])) {
				Combatant enemy = GameMap.getCurrentRoom().getEnemyByName(args[3]);
				if(GameMap.isItem(args[1])) {
					GameLog.info("Using item on enemy!");
					if(GameMap.getCurrentRoom().itemExistsInRoom(args[1])) {
						Item item = GameMap.getCurrentRoom().takeItemFromRoom(args[1]);
						GameGUI.player.addToInventory(item);
					}
					if(GameGUI.player.isInInventory(args[1])) {
						if(Weapon.isThisType(args[1])) {
							GameGUI.player.equipItem(ItemLibrary.get(args[1]));
							GameMap.getCurrentRoom().combat.attack(enemy);
						} else if(KeyItem.isThisType(args[1])){
							GameGUI.addToConsole("Nah, they might steal it.");
						} else if(ConsumableHealth.isThisType(args[1])) {
							GameGUI.addToConsole("Why would you use that on anyone but yourself?");
						} else {
							GameGUI.addToConsole("I'm not even sure how to use that in such a way... Can I have some of whatever you're on?");
						}
					} else {
						GameGUI.addToConsole("You don't have one of those to use");
					}
				} else {
					GameGUI.addToConsole("Why would you use " + args[1] + " like that?");
				}
			} else if(KeyItem.isThisType(args[1])) {
				if(!GameGUI.player.isInInventory(args[1]) && GameMap.getCurrentRoom().itemExistsInRoom(args[1])) {
					GameGUI.player.addToInventory(GameMap.getCurrentRoom().takeItemFromRoom(args[1]));
				}
				if(GameGUI.player.isInInventory(args[1])) {
					GameMap.getCurrentRoom().unlockDoor(args[1]);
					// subject (args[3]) is the door
				} else {
					GameGUI.addToConsole("You don't have that key");
				}
			} else {
				invalidCommand();
			}
		} catch(ObjectNotFoundInRoomException e) {
			GameLog.severe("No Door found", e);
		} catch (Exception e) {
			GameLog.info(null, e);
			invalidCommand();
		}
	}
	
	protected static void parseMove() {
		String[] args = input.split("\\s");
		String argument = "";
		if(args.length > 1)
			argument = args[1];
		else
			argument = args[0];
		GameLog.info("Parsing using MOVE pattern. Argument: '" + argument + "'.");
		commandMove(argument);
	}
	protected static void parseTake() {
		GameLog.info("Parsing using TAKE pattern.");
		String[] args = input.split("\\s");
		
		putItemInInventory(args[1]);
	}
	protected static void parseAttack() {
		GameLog.info("Parsing using ATTACK pattern.");
		// parse information
		String[] args = input.split("\\s");
		String target = "";
		if(args[0].equalsIgnoreCase("spit") && args[1].equalsIgnoreCase("at")) {
			target = args[2];
		} else {
			target = args[1];
		}
		
		GameLog.info("Parsed attack parameters, attacking target.");
		attackEnemy(target);
	}
	protected static void parseHelp() {
		String[] args = input.split("\\s");
		String argument = "";
		if(args.length > 1)
			argument = args[1];
		displayHelp(argument);
		GameLog.info("Parsing using HELP pattern. Argument: '" + argument + "'.");
	}
	protected static void parseTalk() {
		String args[] = input.split("\\s");
		try {
			String subject = "";
			if(args[1].equals("to"))
				subject = args[2];
			else
				subject = args[1];
			
			// if character exists in the room...
			if(GameMap.isEnemy(subject)) {
				// On talking, make character docile
				Combatant person = GameMap.getCurrentRoom().getEnemyByName(subject);
				GameGUI.addToConsole("\n\"\n" + person.getTalkText() + "\n\"");
				person.becomeDocile();
			} else {
				GameGUI.addToConsole("You can't talk to that. Either that or it's ignoring you.");
			}
		} catch(Exception e) {
			GameLog.info(null, e);
			invalidCommand();
		}
	}
	protected static void parseLookDirection() {
		String[] args = input.split("\\s");
		if(args.length > 2 && (args[0] + " " + args[1]).equals("look at"))
			commandLook(args[2]);
		else if(args.length > 1)
			commandLook(args[1]);
		else 
			invalidCommand();
	}

	protected static void parseLookGeneral() {
		print("Looking around...");
		print(GameMap.getCurrentRoom().getFullRoomDescription());
		
		print("\n<NAVIGATION>");
		commandLook("north");
		commandLook("south");
		commandLook("east");
		commandLook("west");
		
		print("\n<ITEMS IN ROOM>\n" + listRoomItems());
		print("\n<CHARACTERS IN ROOM>\n" + listRoomEnemies());
		print("\n<OBJECTS IN ROOM>\n" + listRoomDoors());
		print("");
	}
	protected static void parseInspectItem() { //TODO
		// inspect items and environment
		// look, look at, inspect, search
		String[] args = input.split("\\s");
		try{
			String target = "";
			if(args[1].equals("at"))
				target = args[2];
			else
				target = args[1];
			
			if(GameMap.isPlayer(target)){
				
			} else if(GameMap.isItem(target)) {
				
			} else if(GameMap.isEnemy(target)) {
				
			} else {
				invalidCommand();
			}
			
		} catch (Exception e) {
			invalidCommand();
		}
		
	}
	protected static void parseEquip() {
		String[] args = input.split("\\s");
		String target = "";
		if(args.length > 2 && (args[0] + " " + args[1]).equals("put on"))
			target = args[2];
		else
			target = args[1];
		GameGUI.player.equipItem(ItemLibrary.get(target));
	}
	protected static void parseUnequip() {
		String[] args = input.split("\\s");
		String target = "";
		if(args.length > 2 && (args[0] + " " + args[1]).equals("put away"))
			target = args[2];
		else
			target = args[1];
		
		GameGUI.player.unEquipItem(ItemLibrary.get(target));
	}
	protected static void parseDiscard() {
		String[] args = input.split("\\s");
		try {
			String target = "";
			if(args[1].equals("away"))
				target = args[2];
			else
				target = args[1];
			
			if(GameMap.isItem(target))
				GameGUI.player.discardItem(target);
			else
				GameGUI.addToConsole("You can't throw that away, it isn't even an item!");
			
		} catch(Exception e) {
			GameLog.severe("", e);
		}
	}
	/**
	 * Invalid input handler.
	 */
	protected static void invalidCommand() {
		print("I'm not sure I understand '" + input + "'...");
	}
	
	protected static void commandMove(String direction) {
		direction = direction.toLowerCase();
		if (direction.equals("north") || direction.equals("n")) {
			print("Going north");
			if(!debug) GameMap.movePlayerNorth();
		} else if(direction.equals("south") || direction.equals("s")) {
			print("Going south");
			if(!debug) GameMap.movePlayerSouth();
		} else if(direction.equals("east") || direction.equals("e")) {
			print("Going east");
			if(!debug) GameMap.movePlayerEast();
		} else if(direction.equals("west") || direction.equals("w")) {
			print("Going west");
			if(!debug) GameMap.movePlayerWest();
		} else {
			invalidCommand();
		}
	}
	protected static void commandLook(String direction) {
		direction = direction.toLowerCase();
		
		if (direction.equals("north") || direction.equals("n"))
			print("To the north you see " + GameMap.getNorthDescript());
		else if(direction.equals("south") || direction.equals("s"))
			print("To the south you see " + GameMap.getSouthDescript());
		else if(direction.equals("east") || direction.equals("e"))
			print("To the east you see " + GameMap.getEastDescript());
		else if(direction.equals("west") || direction.equals("w"))
			print("To the west you see " + GameMap.getWestDescript());
		else
			invalidCommand();
	}
	
	protected static void putItemInInventory(String target) {	
		try {
			if(!GameGUI.player.isInventoryFull()) {
				Item item = GameMap.getCurrentRoom().takeItemFromRoom(target);
				GameGUI.player.addToInventory(item);
			} else {
				print("Your inventory is full. You can not put '" + target + "' into your inventory without becuming encumbered");
			}
		} catch(ItemNotFoundInRoomException e) {
			GameLog.warning(null, e);
			print("No item '" + target + "' was found in the room.");
		} catch(Exception e2) {
			GameLog.warning(null, e2);
			print("No item '" + target + "' was found in the room either.");
		}
	}
	
	protected static void attackEnemy(String target) {
		GameLog.info("Attacking enemy (" + target + ").");
		try {
			CombatSystem combat = GameMap.getCurrentRoom().combat;
			Combatant enemy = combat.getEnemyByName(target);
			
			if(confirmResponseLoop) { // if in confirmation loop (confirmed)
				combat.attack(enemy);
			} else if(!enemy.isEnemy()) { // if character isn't of type enemy
				GameGUI.addToConsole("This character is not an enemy. Are you sure you want to attack? [Yes/No]");
				confirmInput = input;
				confirmResponseLoop = true;
				confirmSyntaxLoop = true;
			} else { // if there's no need to confirm anything
				combat.attack(enemy);
			}
		} catch (Exception e) {
			GameLog.info(null, e);
			print(e.getMessage());
		}
		
	}
	
	private static String listRoomItems() {
		LinkedList<Item> roomItemsList = GameMap.getCurrentRoom().getAllItems();
		String roomItemDescriptions = "";
		for(Item element : roomItemsList)
			roomItemDescriptions += element.getName() + "\n";
		if(roomItemDescriptions.equals(""))
			roomItemDescriptions = "[none]";
		return roomItemDescriptions.trim();
	}
	
	private static String listRoomEnemies() {
		LinkedList<Combatant> roomEnemyList = GameMap.getCurrentRoom().getRoomEnemies();
		String roomEnemyDescriptions = "";
		for(Character element : roomEnemyList)
			roomEnemyDescriptions += element.name + "\n";
		if(roomEnemyDescriptions.equals(""))
			roomEnemyDescriptions = "[none]";
		return roomEnemyDescriptions.trim();
	}
	
	private static String listRoomDoors() {
		Boolean roomContainDoor = GameMap.getCurrentRoom().containsLockedDoor();
		String roomDoorDescriptions = "";
		if(roomContainDoor){
			Door element = GameMap.getCurrentRoom().getRoomDoor();
				roomDoorDescriptions +=  element.shortDescription() + "\n";
		}
		return roomDoorDescriptions.trim();
	}
	
	public static void playerDeath() {
		GameMap.restart();
		
		GameGUI.addToConsole("You died a terrible death of epic proportions...");
		GameGUI.addToConsole("A small child urinates on your corpse, an unknown hero's memorial.");
	}
}
