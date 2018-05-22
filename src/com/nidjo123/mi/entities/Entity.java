package com.nidjo123.mi.entities;

import com.nidjo123.mi.MilkyInwayders;

public abstract class Entity {
	int x, y;
	int xx;
	
	/*
	 * public Entity(int x, int y) { this.x = x; this.y = y; }
	 */
	
	public void tick(MilkyInwayders game) {
	}
	
	protected void die(MilkyInwayders game) {
		game.entities.remove(this);
	}
	
	public void render(MilkyInwayders game) {
	}
	
	private void move(int xx, int dir) {
	}
	
	public boolean collidedWith(Entity e, int xa, int ya) {
		return false;
	}
}
