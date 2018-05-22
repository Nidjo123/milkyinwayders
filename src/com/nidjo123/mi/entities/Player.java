package com.nidjo123.mi.entities;

import java.util.Random;

import com.nidjo123.mi.Art;
import com.nidjo123.mi.MilkyInwayders;
import com.nidjo123.mi.Sound;

public class Player extends Entity {
	private int x, y, ticksPassed = 0;
	int dir = 1, lastDir;
	int xx = 2;
	public static boolean left, right, shoot;
	public static int score = 0, afterTime = 120;
	Random random = new Random();
	
	public Player(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(MilkyInwayders game) {
		if (left) {
			dir = 2;
		} else if (right) {
			dir = 0;
		} else {
			dir = -1;
		}
		
		ticksPassed++;
		
		if (shoot && ticksPassed > 40) {
			game.entities.add(new Bullet(x + 4, y - 1, 1));
			ticksPassed = 0;
			Sound.shoot.play();
		}
		
		move(xx, dir);
	}
	
	public void die(MilkyInwayders game) {
		Sound.playerdie.play();
		
		game.entities.remove(this);
		
		for (int i = 0; i < 100; i++) {
			game.entities.add(new Particle(x, y, 0x0008FF, 1, 1));
		}
		
		game.lost = true;
	}
	
	private void move(int xx, int dir) {
		if (dir == 0 && x <= MilkyInwayders.game.WIDTH - 17) {
			x += xx;
		} else if (dir == 2 && x > 1) {
			x -= xx;
		}
	}
	
	public void render(MilkyInwayders game) {
		if (dir == 0) {
			Art.sheet[0][2].draw(x, y, game);
			Art.sheet[0][3].draw(x + 8, y, game);
			Art.sheet[1][2].draw(x, y + 8, game);
			Art.sheet[1][3].draw(x + 8, y + 8, game);
		} else if (dir == 2) {
			Art.sheet[0][4].draw(x, y, game);
			Art.sheet[0][5].draw(x + 8, y, game);
			Art.sheet[1][4].draw(x, y + 8, game);
			Art.sheet[1][5].draw(x + 8, y + 8, game);
		} else {
			Art.sheet[0][0].draw(x, y, game);
			Art.sheet[0][1].draw(x + 8, y, game);
			Art.sheet[1][0].draw(x, y + 8, game);
			Art.sheet[1][1].draw(x + 8, y + 8, game);
		}
	}
	
	public boolean collidedWith(Entity e, int xa, int ya) {
		if (xa >= x - 2 && xa <= x + 12 && ya >= y && ya <= y + 16)
			return true;
		return false;
	}
}
