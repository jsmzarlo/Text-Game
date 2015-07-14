package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;

import junit.framework.Assert;

import org.junit.Test;

public class GameMapTester {
	
		private final static String FILEPATH = "story1.xml";
		private final Weapon anItem = new Weapon("anItem","v","n",9);
		
		@Test
		public void testItemTrackingofGameMap(){
			CartesianPoint.setCoordinates(0, 0);
			GameMap.start(FILEPATH);
			Room aRoom = GameMap.getCurrentRoom();
			aRoom.putItemInRoom(anItem);
			GameMap.movePlayerNorth();
			GameMap.movePlayerSouth();
			Room bRoom = GameMap.getCurrentRoom();
			try {
				Item here = bRoom.takeItemFromRoom("anItem");
				Assert.assertTrue(here instanceof Weapon);
			} catch (Exception e) {
				
			}
		}
		
		public static void main(String[] args){
			CartesianPoint.setCoordinates(0, 0);
			GameMap.start(FILEPATH);
			
		}
}
