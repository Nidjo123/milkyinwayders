package com.nidjo123.mi.entities;

import com.nidjo123.mi.Art;
import com.nidjo123.mi.MilkyInwayders;
import com.nidjo123.mi.Sound;

public class OrangeEnemy extends Entity {
	private int x, y;
	private int dir = 0, dirCount = -1, xx = 4;
	private boolean right = false, left = true, kamikaze = false;
	
	public OrangeEnemy(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void tick(MilkyInwayders game) {
		if (y > 180 || y < 0) {
			die(game);
			game.enemyCount--;
		}
		
		if (kamikaze) {
			game.entities.add(new Particle(x, y, 0xFFC870, 0, 0));
		}
		
		if (MilkyInwayders.game.random.nextInt(3000) == 0 && game.enemyCount <= 10 && !kamikaze) {
			Sound.kamikaze.play();
			kamikaze = true;
		}
		
		if (MilkyInwayders.tickCount % 45 == 0 && !kamikaze) {
			dirCount++;
			if (dirCount % 4 == 0) {
				right = !right;
				left = !left;
				if (right)
					dir = 0;
				else
					dir = 2;
			}
			
			//if (dirCount == 24) {
			//	dirCount = 0;
			//}
			
			move(xx, dir);
		}
		
		if (kamikaze) {
			move(0, dir);
			
			if (y > game.HEIGHT / 2) {
				for (int i = 0; i < game.entities.size(); i++) {
					Entity e = game.entities.get(i);
					if ((e instanceof Player) && e.collidedWith(this, x, y)) {
						e.die(game);
						die(game);
						MilkyInwayders.game.enemyCount--;
					}
				}
			}
		}
		
		if (game.random.nextInt(700) == 0 && !kamikaze) {
			game.entities.add(new Bullet(x, y + 8, 3));
			Sound.eshoot.play();
		}
	}
	
	public void die(MilkyInwayders game) {
		game.entities.remove(this);
		
		for (int i = 0; i < 20; i++) {
			game.entities.add(new Particle(x + game.random.nextInt(6), y + game.random.nextInt(5), 0xFF5735, 0, 1));
		}
	}
	
	public void render(MilkyInwayders game) {
		if (dirCount % 2 == 0) {
			Art.sheet[2][28].draw(x, y, game);
		} else {
			Art.sheet[2][29].draw(x, y, game);
		}
	}
	
	private void move(int xx, int dir) {
		if (dir == 0 && x <= MilkyInwayders.game.WIDTH - 8) {
			x += xx;
		} else if (dir == 2 && x > 2) {
			x -= xx;
		}
		
		if (kamikaze) {
			y += 3;
		}
	}
	
	public boolean collidedWith(Entity e, int xa, int ya) {
		if (xa >= x - 5 && xa <= x + 5 && ya >= y && ya <= y + 8)
			return true;
		return false;
	}
}
