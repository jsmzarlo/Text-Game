package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

import org.junit.*;



public class RoomTester {
	
	private final String GENERAL_ROOM_DESCRIPT= "Room Description";
	private final Room fooRoom = new Room();
	private final Weapon fooItem = new Weapon("an Item","","", 8);
	
	@Test
	public void testCurrentRoomHasRoomDiscription(){
		fooRoom.setShortDescription(GENERAL_ROOM_DESCRIPT);
		Assert.assertEquals(GENERAL_ROOM_DESCRIPT, fooRoom.getQuickRoomDescription());
	}
	
	@Test
	public void testRemoveRoomItems(){
		fooRoom.putItemInRoom(fooItem);
		Assert.assertEquals(fooItem, fooRoom.takeItemFromRoom("an Item"));
	}
	
	@Test(expected = ItemNotFoundInRoomException.class)
	public void testItemNotFoundInRoom(){
		fooRoom.takeItemFromRoom("fooName");
	}
}