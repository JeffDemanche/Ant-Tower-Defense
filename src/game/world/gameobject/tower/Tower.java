package game.world.gameobject.tower;

import engine.world.GameSystem;
import engine.world.gameobject.GameObject;

public abstract class Tower extends GameObject {
	
	public Tower(GameSystem system, String towerType, int towerId) {
		super(system, createName(towerType, towerId));
	}
	
	public abstract int getCost();
	
	private static String createName(String towerType, int towerId) {
		return towerType + towerId;
	}

}
