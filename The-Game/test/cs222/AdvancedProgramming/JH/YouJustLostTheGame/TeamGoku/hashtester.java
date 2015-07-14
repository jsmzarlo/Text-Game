package cs222.AdvancedProgramming.JH.YouJustLostTheGame.TeamGoku;

import java.util.Hashtable;

public class hashtester {
	
	
	public static void main(String[] args) {
		CartesianPoint.x=-1;
		CartesianPoint.y=-1;
		CartesianPoint.setCoordinates(CartesianPoint.x, CartesianPoint.y);
		System.out.println(CartesianPoint.keyCreator());
		Hashtable<String, Room> hash = new Hashtable<String, Room>();
		Room room = new Room();
		room.setLongDescription("hi");
		hash.put(CartesianPoint.keyCreator(), room);
		CartesianPoint.setCoordinates(-1, -1);
		Room hi = hash.get(CartesianPoint.keyCreator());
		if(hi instanceof Room){
			System.out.println("true");
			System.out.println(hi.getFullRoomDescription());
		}
		else
			System.out.println("false");
	}

}
