package cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku;

import java.util.LinkedList;
import java.util.Random;
import cs222.Fall2012.JH.YouJustLostTheGame.TeamGoku.GUI.GameGUI;

public class CombatSystem {
	protected boolean inCombat = true;
	protected boolean isEnemyTurn = true;
	protected Random random = new Random();
	protected LinkedList<Combatant> enemies;
	protected Player YOU = GameGUI.player;
	
	public CombatSystem(LinkedList<Combatant> enemies) {
		this.enemies = enemies;
		isEnemyTurn = random.nextBoolean();
		nextTurn();
	}
	
	public boolean activeCombat() {
		return inCombat;
	}
	
	public void healPlayerWith(Item potion) {
		if(potion instanceof ConsumableHealth) {
			GameGUI.addToConsole("You use '" + potion.getName() + "'.");
			YOU.heal(potion.getValue());
			YOU.getInventory().remove(potion);
			GameGUI.updateGUI();
			nextTurn();
		} else {
			GameGUI.addToConsole(potion.getName() + " is not something you can use to heal yourself...");
		}
	}
	public void attack(Character enemy) throws EnemyNotFoundInRoomException {
		if(enemy instanceof Player)
			YOU.hurtSelf();
		else {
			GameGUI.addToConsole("You attack '" + enemy.name + "'.");
			enemy.takesDamage(YOU.getAttack());
		}
		if(enemy.isDead()){
			GameMap.getCurrentRoom().dropInventory(enemy);
			enemies.remove(enemy);
		}
		nextTurn();
		
	}
	
	protected boolean allEnemiesAreDead() {
		for(Combatant combatant: enemies)
			if(combatant.isEnemy())
				return false;
		return true;
	}
	
	public Combatant getEnemyByName(String name) throws Exception {
		for(Combatant enemy : enemies) {
			if(enemy.name.compareToIgnoreCase(name) == 0)
				return enemy;
		}
		throw new Exception("No target '" + name + "' found in the room");
	}

	protected void betweenTurnsTasks() {
		inCombat = !allEnemiesAreDead();
		isEnemyTurn = !isEnemyTurn;
	}
	
	public void nextTurn() {
		betweenTurnsTasks();
		
		if(isEnemyTurn && inCombat)
			enemyTurn();
		else if(inCombat)
			playerTurn();
	}
	
	protected void playerTurn() {
		GameGUI.addToConsole("Your turn. What do you do?");
	}
	
	protected void enemyTurn() {
		GameGUI.addToConsole("Enemies attack!");
		for(Combatant enemy : enemies) {
			if(random.nextBoolean()
					&& enemy.canHeal
					&& enemy.health < (enemy.maxHealth/2 + 1) )
				enemy.heal();
			else
				YOU.takesDamage(enemy.getAttack());
		}
		GameGUI.updateGUI();
		nextTurn();
	}
	
	public static void main(String[] args) {
		
	}
}
