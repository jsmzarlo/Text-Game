package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


/**
 * Takes XML game story files. Builds current room where the player resides from the game story XML file. 
 * 
 * @author Joseph Leffler, Mike Henderson, David Alexander, Jeffrey McDaniel
 * 
 */


public class XMLparser {
	
	private static Document input;
	private static XPath xpath = XPathFactory.newInstance().newXPath();
	private static Room newRoom;
	
	public XMLparser(String filepath) {
		File file = new File(filepath);
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			input = builder.parse(file);
			buildDoorLibrary();
			buildItemLibrary();
		} catch (ParserConfigurationException e) {
			GameLog.severe("ParserConfiguration exception in builder object parsing the file in the string filePath constructor", e);
		} catch (SAXException e) {
			GameLog.severe("SAXException in builder object parsing the file in the string filePath constructor", e);
		} catch (IOException e) {
			GameLog.severe("IOException in builder object parsing the file in the string filePath constructor", e);
		}
	}
	
	public XMLparser(File file){
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			input = builder.parse(file);
			buildDoorLibrary();
			buildItemLibrary();
		} catch (ParserConfigurationException e) {
			GameLog.severe("ParserConfigurationException in builder object parsing the file in the file filePath constructor", e);
		} catch (SAXException e) {
			GameLog.severe("SAXException in builder object parsing the file in the file filePath constructor", e);
		} catch (IOException e) {
			GameLog.severe("IOException in builder object parsing the file in the file filePath constructor", e);
		}
	}
	
	private void buildItemLibrary(){
		String itemExpression = "//defaults/item";
		try {
			NodeList itemNodeList = (NodeList) xpath.evaluate(itemExpression, input, XPathConstants.NODESET);
			for(int i=0; i<itemNodeList.getLength(); i++){
				
				NamedNodeMap attributes = itemNodeList.item(i).getAttributes();
				String itemName = attributes.getNamedItem("name").getNodeValue();
				String itemType = attributes.getNamedItem("type").getNodeValue();
				
				String longDescriptionExpression = "//item[@name='"+itemName+"']/description/long";
				String longDescription = (String) xpath.evaluate(longDescriptionExpression, input, XPathConstants.STRING);
				longDescription = longDescription.trim();
				
				String shortDescriptionExpression = "//item[@name='"+itemName+"']/description/short";
				String shortDescription = (String) xpath.evaluate(shortDescriptionExpression, input, XPathConstants.STRING);
				shortDescription = shortDescription.trim();
				
				String modifyerExpression = "//item[@name='"+itemName+"']/modifier";
				Node itemModifyer = (Node) xpath.evaluate(modifyerExpression, input, XPathConstants.NODE);
				NamedNodeMap modAttributes = itemModifyer.getAttributes();
				Node itemModAttributes = modAttributes.getNamedItem("value");
				int itemModifyerValue = Integer.parseInt(itemModAttributes.getNodeValue());
				
				if(itemType.equals("weapon")){
					Weapon weapon = new Weapon( itemName, shortDescription, longDescription, itemModifyerValue );
					ItemLibrary.put(itemName, weapon);
					GameLog.info("added item: "+itemName+" to Itemlibrary");
				}
				else if(itemType.equals("armor")){
					Armor armor = new Armor( itemName, shortDescription, longDescription, itemModifyerValue );
					ItemLibrary.put(itemName, armor);
					GameLog.info("added item: "+itemName+" to Itemlibrary");
				}
				else if (itemType.equals("key")){
					KeyItem key = new KeyItem( itemName, shortDescription, longDescription, itemModifyerValue );
					ItemLibrary.put(itemName, key);
					GameLog.info("added item: "+itemName+" to Itemlibrary");
				}
				else{
					ConsumableHealth healthItem = new ConsumableHealth(itemName, shortDescription, longDescription,itemModifyerValue);
					ItemLibrary.put(itemName, healthItem);
					GameLog.info("added item: "+itemName+" to Itemlibrary");
				}
			}
		} catch (XPathExpressionException e) {
			GameLog.severe("Problem with xpath object in buildItemLibrary method", e);
		}catch (NullPointerException e){
			GameLog.severe("Problem with xml file can't parse null pointer exception in buildItemLibrary method", e);
		}
	}
	
	private void buildDoorLibrary(){
		String objectExpression = "//defaults/object";
		try {
			NodeList objectNodeList = (NodeList) xpath.evaluate(objectExpression, input, XPathConstants.NODESET);
			for(int i=0; i<objectNodeList.getLength(); i++){
				
				NamedNodeMap attributes = objectNodeList.item(i).getAttributes();
				String doorName = attributes.getNamedItem("name").getNodeValue();
				String direction = attributes.getNamedItem("direction").getNodeValue();
				Boolean doorLocked = Boolean.parseBoolean(attributes.getNamedItem("locked").getNodeValue());
				String doorID = attributes.getNamedItem("id").getNodeValue();
				
				String longDescriptionExpression = "//object[@name='"+doorName+"']/description/long";
				String longDescription = (String) xpath.evaluate(longDescriptionExpression, input, XPathConstants.STRING);
				longDescription = longDescription.trim();
				
				String shortDescriptionExpression = "//object[@name='"+doorName+"']/description/short";
				String shortDescription = (String) xpath.evaluate(shortDescriptionExpression, input, XPathConstants.STRING);
				shortDescription = shortDescription.trim();
				
				String eventDescriptionExpression = "//object[@name='"+doorName+"']/event/use";
				String eventDescription = (String) xpath.evaluate(eventDescriptionExpression, input, XPathConstants.STRING);
				eventDescription = eventDescription.trim();
				
				int idNumber = Integer.parseInt(doorID);
				
				
				Door door = new Door( doorName, shortDescription, longDescription,direction, doorLocked, idNumber);
				DoorLibrary.doorLibrary.put(doorName, door);
				GameLog.info("added object: "+doorName+" to Objectlibrary");
				
			}
		} catch (XPathExpressionException e) {
			GameLog.severe("Problem with xpath object in buildObjectLibrary method", e);
		}catch (NullPointerException e){
			GameLog.severe("Problem with xml file can't parse null pointer exception in buildObjectLibrary method", e);
		}
	}
	
	
	public Room loadRoom(){
		newRoom = Room.instance();
		loadRoomShortDescript();
		loadRoomLongDescript();
		loadRoomItems();
		loadRoomObjects();
		loadRoomCharacters();
		return newRoom;
	}
	
	private static void loadRoomShortDescript() {
		String expression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/description/short";
		try {
			String newshortDescription = (String) xpath.evaluate(expression, input, XPathConstants.STRING);
			newRoom.setShortDescription(newshortDescription.trim());
		} catch (XPathExpressionException e) {
			GameLog.severe("Problem with xpath object in loadRoomShortDescription method", e);
		}catch (NullPointerException e){
			GameLog.severe("Problem with xml file can't parse null pointer exception in loadRoomShort method", e);
		}
	}
	
	private static void loadRoomLongDescript() {
		String expression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/description/long";
		try {
			String newLongDescription = (String) xpath.evaluate(expression, input, XPathConstants.STRING);
			newRoom.setLongDescription(newLongDescription.trim());
		} catch (XPathExpressionException e) {
			GameLog.severe("Problem with xpath object in loadRoomLongDescription method", e);
		}catch (NullPointerException e){
			GameLog.severe("Problem with xml file can't parse null pointer exception in loadRoomLong method", e);
		}
	}
	
	private static void loadRoomItems() {
		String expression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/item";
		try {
			NodeList roomItemsList = (NodeList) xpath.evaluate(expression, input, XPathConstants.NODESET);
			
			for(int i=0; i<roomItemsList.getLength(); i++){
				Node element = roomItemsList.item(i);
				NamedNodeMap attributes =  element.getAttributes();
				String itemName = attributes.getNamedItem("call").getNodeValue();
				Item newItem = ItemLibrary.get(itemName);
				newRoom.putItemInRoom(newItem);
			}
		} catch (XPathExpressionException e) {
			GameLog.severe("Problem with xpath object in loadRoomItems method", e);
		}catch (NullPointerException e){
			GameLog.severe("Problem with xml file can't parse null pointer exception in loadRoomItems method", e);
		}
	}
	
	private static void loadRoomObjects() {
		String expression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/object";
		try {
			NodeList roomObjectsList = (NodeList) xpath.evaluate(expression, input, XPathConstants.NODESET);
			
			for(int i=0; i<roomObjectsList.getLength(); i++){
				Node element = roomObjectsList.item(i);
				NamedNodeMap attributes =  element.getAttributes();
				String objectName = attributes.getNamedItem("call").getNodeValue();
				Door newObject = DoorLibrary.doorLibrary.get(objectName.toLowerCase());
				newRoom.putDoorInRoom(newObject);
			}
		} catch (XPathExpressionException e) {
			GameLog.severe("Problem with xpath object in loadRoomObjects method", e);
		}catch (NullPointerException e){
			GameLog.severe("Problem with xml file can't parse null pointer exception in loadRoomObjects method", e);
		}
	}
	
	public String roomNorthDescript() {
		String expression = "//room[@x='"+CartesianPoint.x+"' and @y='"+(CartesianPoint.y+1)+"']/description/short";
		String northDescription = "";
		try {
			String newNorthDescription = (String) xpath.evaluate(expression, input, XPathConstants.STRING);
			northDescription = newNorthDescription.trim();
			if(northDescription.equals("")){
				northDescription = "an invisible wall";
			}
		} catch (Exception e) {
			GameLog.severe("Problem with xpath object in RoomNorthDescription method", e);
		}
		return northDescription;
	}
	
	public String roomSouthDescript() {
		String expression = "//room[@x='"+CartesianPoint.x+"' and @y='"+(CartesianPoint.y-1)+"']/description/short";
		String southDescription="";
		try {
			String newSouthDescription = (String) xpath.evaluate(expression, input, XPathConstants.STRING);
			southDescription = newSouthDescription.trim();
			if(southDescription.equals("")){
				southDescription = "an invisible wall";
			}
		} catch (XPathExpressionException e) {
			GameLog.severe("Problem with xpath object in RoomSouthDescription method", e);
		}
		return southDescription;
	}
	
	public String roomEastDescript() {
		String expression = "//room[@x='"+(CartesianPoint.x+1)+"' and @y='"+CartesianPoint.y+"']/description/short";
		String eastDescription="";
		try {
			String newEastDescription = (String) xpath.evaluate(expression, input, XPathConstants.STRING);
			eastDescription = newEastDescription.trim();
			if(eastDescription.equals("")){
				eastDescription = "an invisible wall";
			}
		} catch (XPathExpressionException e) {
			GameLog.severe("Problem with xpath object in RoomEastDescription method", e);
		}
		return eastDescription;
	}
	
	public String roomWestDescript() {
		String expression = "//room[@x='"+(CartesianPoint.x-1)+"' and @y='"+CartesianPoint.y+"']/description/short";
		String westDescription="";
		try {
			String newWestDescription = (String) xpath.evaluate(expression, input, XPathConstants.STRING);
			westDescription = newWestDescription.trim();
			if(westDescription.equals("")){
				westDescription = "an invisible wall";
			}
		} catch (XPathExpressionException e) {
			GameLog.severe("Problem with xpath object in RoomWestDescription method", e);
		}
		return westDescription;
	}
	
	private static void loadRoomCharacters(){
		String expression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/person";
		try {
			NodeList characterNodeList = (NodeList) xpath.evaluate(expression, input, XPathConstants.NODESET);
			for(int i=0; i<characterNodeList.getLength(); i++){
				Combatant character = new Combatant();
				NamedNodeMap characterAttributes = characterNodeList.item(i).getAttributes();
				
				character.name = characterAttributes.getNamedItem("name").getNodeValue();
				GameLog.info("Character name: "+character.name);
				character.maxHealth = Integer.parseInt(characterAttributes.getNamedItem("health").getNodeValue());
				GameLog.info("Character Max Health: "+character.maxHealth);
				character.health = character.maxHealth;
				character.attack = Integer.parseInt(characterAttributes.getNamedItem("attack").getNodeValue());
				GameLog.info("Character attack: "+character.attack);
				boolean characterAlignment = Boolean.parseBoolean(characterAttributes.getNamedItem("enemy").getNodeValue());
				if(characterAlignment){
					character.becomeAggressive();
				}
				
				boolean characterCanHeal = Boolean.parseBoolean(characterAttributes.getNamedItem("heal").getNodeValue());
				character.canHeal = characterCanHeal;
				
				String inventoryExpression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/person[@name='"+character.name+"']/item";
				NodeList characterItemsList = (NodeList) xpath.evaluate(inventoryExpression, input, XPathConstants.NODESET);
				for(int j=0; j<characterItemsList.getLength(); j++){
					Node element = characterItemsList.item(j);
					NamedNodeMap attributes =  element.getAttributes();
					String itemName = attributes.getNamedItem("call").getNodeValue();
					character.inventory.add(ItemLibrary.get(itemName));
				}
				
				String eventTalkExpression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/person/event/talk";
				String eventTalk = (String) xpath.evaluate(eventTalkExpression, input, XPathConstants.STRING);
				character.eventTextTalk = eventTalk.trim();
				
				String eventkillExpression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/person/event/kill";
				String eventkill = (String) xpath.evaluate(eventkillExpression, input, XPathConstants.STRING);
				character.eventTextDeath = eventkill.trim();
				
				String eventPlayerDeathExpression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/person/event/death";
				String eventPlayerDeath = (String) xpath.evaluate(eventPlayerDeathExpression, input, XPathConstants.STRING);
				character.eventTextPlayerDeath = eventPlayerDeath.trim();
				
				String descriptionLongExpression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/person/description/long";
				String descriptionLong = (String) xpath.evaluate(descriptionLongExpression, input, XPathConstants.STRING);
				character.longDescription = descriptionLong.trim();
				
				String descriptionShortExpression = "//room[@x='"+CartesianPoint.x+"' and @y='"+CartesianPoint.y+"']/person/description/short";
				String descriptionShort = (String) xpath.evaluate(descriptionShortExpression, input, XPathConstants.STRING);
				character.shortDescription = descriptionShort.trim();
				
				newRoom.addCharacters(character);
			}
			
		} catch (XPathExpressionException e) {
			GameLog.severe("Problem with xpath object in buildCharacter method", e);
		} catch (NullPointerException e){
			GameLog.severe("Problem with xml file can't parse null pointer exception in buildCharacter method", e);
		}
	}
	
}