package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

import junit.framework.Assert;
import org.junit.Test;

public class ArmorTester {

	private static final int ARMOR_MODIFYER = 3;
	private static final Armor armor = new Armor("A NAME","","", ARMOR_MODIFYER);
	
	@Test
	public void testGetArmorNameOnArmor() {
		Assert.assertEquals("A NAME", armor.getName());
	}
	
	@Test
	public void testGetArmorModifyer(){
		Assert.assertEquals(ARMOR_MODIFYER, armor.getValue());
	}
}
