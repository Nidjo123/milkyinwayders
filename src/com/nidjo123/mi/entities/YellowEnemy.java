package com.nidjo123.mi.entities;

import com.nidjo123.mi.Art;
import com.nidjo123.mi.MilkyInwayders;
import com.nidjo123.mi.Sound;

public class YellowEnemy extends Entity {
	private int x, y;
	private int dir = 0, dirCount = -1, xx = 4;
	private boolean right = false, left = true;
	
	public YellowEnemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(MilkyInwayders game) {
		if (MilkyInwayders.tickCount % 60 == 0) {
			dirCount++;
			if (dirCount % 4 == 0) {
				right = !right;
				left = !left;
				if (right)
					dir = 0;
				else
					dir = 2;
			}
			
			// if (dirCount == 24) {
			// dirCount = 0;
			// }
			
			move(xx, dir);
		}
		
		if (game.random.nextInt(1100) == 0) {
			game.entities.add(new Bullet(x, y + 8, 3));
			Sound.eshoot.play();
		}
	}
	
	public void die(MilkyInwayders game) {
		game.entities.remove(this);
		
		for (int i = 0; i < 20; i++) {
			game.entities.add(new Particle(x + game.random.nextInt(6), y + game.random.nextInt(5), 0xFFDB59, 0, 1));
		}
	}
	
	public void render(MilkyInwayders game) {
		if (dirCount % 2 == 0) {
			Art.sheet[0][28].draw(x, y, game);
		} else {
			Art.sheet[0][29].draw(x, y, game);
		}
	}
	
	private void move(int xx, int dir) {
		if (dir == 0 && x <= MilkyInwayders.game.WIDTH - 8) {
			x += xx;
		} else if (dir == 2 && x > 2) {
			x -= xx;
		}
	}
	
	public boolean collidedWith(Entity e, int xa, int ya) {
		if (xa >= x - 5 && xa <= x + 5 && ya >= y && ya <= y + 6)
			return true;
		return false;
	}
}
