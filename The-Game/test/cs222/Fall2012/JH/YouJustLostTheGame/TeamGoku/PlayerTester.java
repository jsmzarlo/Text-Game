package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

import junit.framework.Assert;
import org.junit.Test;

public class PlayerTester {

	private static final int STARTHEALTH = 100;
	private static final Player person = new Player();
	private static final int STARTATTACK = 0;
	private final Weapon weapon = new Weapon("an Item","","", 8);

	@Test
	public void testGetCurrentHealthOnPerson(){
		Assert.assertEquals(STARTHEALTH, person.getHealth());
	}
	
	
	@Test
	public void testGetAttackValueOfPerson(){
		Assert.assertEquals(STARTATTACK, person.getAttack());
	}
	
	@Test
	public void testItemsInInventory(){
		person.addToInventory(weapon);
		//System.out.println(person.inspectInventoryFor(weapon));
		//Assert.assertEquals(("You have " + weapon),person.inspectInventoryFor(weapon));
	}
	
	@Test
	public void testPlayerTakesDamage(){
		int expected = 70;
		person.takesDamage(30);
		Assert.assertEquals(expected, person.getHealth());
	}
}
