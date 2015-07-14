package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

/**
 * Corrdinates on which the player resides
 * 
 * @author Joseph Leffler, Mike Henderson, David Alexander, Jeffrey McDaniel
 * 
 */

public class CartesianPoint {
	public static int x;
	public static int y;
	
	public static void setCoordinates(int xCoord, int yCoord) {
		CartesianPoint.x = xCoord;
		CartesianPoint.y = yCoord;
	}
	
	public static String keyCreator(){
		return "XCordinate:"+CartesianPoint.x+"YCordinate:"+CartesianPoint.y;
	}
}