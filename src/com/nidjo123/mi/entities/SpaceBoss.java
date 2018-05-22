package com.nidjo123.mi.entities;

import com.nidjo123.mi.Art;
import com.nidjo123.mi.MilkyInwayders;
import com.nidjo123.mi.Sound;

public class SpaceBoss extends Entity {
	private int x, y;
	private int dir = 0, dirCount = -1, xx = 10;
	private boolean right = false, left = true;
	private int health;
	
	public SpaceBoss(int x, int y) {
		this.x = x;
		this.y = y;
		health = 200;
	}
	
	public void tick(MilkyInwayders game) {
		if (health <= 0) {
			die(game);
			game.won = true;
			Player.score += 1000;
		}
		
		/*
		 * for (int i = 0; i < game.entities.size(); i++) { Entity e =
		 * game.entities.get(i); if ((e instanceof Bullet) &&
		 * e.collidedWith(this, x, y) && dir == 1) { e.die(game); health -= 10;
		 * } }
		 */
		
		if (MilkyInwayders.tickCount % 30 == 0) {
			dirCount++;
			if (dirCount % 4 == 0) {
				right = !right;
				left = !left;
				if (right)
					dir = 0;
				else
					dir = 2;
			}
			
			if (dirCount == 24) {
				dirCount = 0;
			}
			
			move(xx, dir);
		}
		
		if (game.random.nextInt(20) == 0) {
			game.entities.add(new Bullet(x + 13, y + 24, 3));
			Sound.bossshoot.play();
		}
	}
	
	public void die(MilkyInwayders game) {
		game.entities.remove(this);
		
		Sound.bossdie.play();
		
		for (int i = 0; i < 200; i++) {
			game.entities.add(new Particle(x + game.random.nextInt(6), y + game.random.nextInt(5), 0x5E76FF, 0, 1));
		}
	}
	
	public void hurt(MilkyInwayders game) {
		for (int i = 0; i < 30; i++) {
			game.entities.add(new Particle(x + 12, y + 14, 0x5E76FF, 0, 1));
		}
		Sound.bosshurt.play();
		health -= 10;
	}
	
	public void render(MilkyInwayders game) {
		if (dirCount % 2 == 0) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					Art.sheet[6 + j][22 + i].draw(x + i * 8, y + j * 8, game);
				}
			}
		} else {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					Art.sheet[6 + j][26 + i].draw(x + i * 8, y + j * 8, game);
				}
			}
		}
		
		renderBossHealth(game);
	}
	
	private void renderBossHealth(MilkyInwayders game) {
		Art.draw("Boss Health: ", 1, 1, game);
		double hh = Math.ceil((double) health / 10. / 2.);
		
		for (int i = 0; i < hh; i++) {
			Art.sheet[0][8].draw(13 * 8 + i * 8, 1, game);
		}
	}
	
	private void move(int xx, int dir) {
		if (dir == 0 && x <= MilkyInwayders.game.WIDTH - 30) {
			x += xx;
		} else if (dir == 2 && x > 30) {
			x -= xx;
		}
	}
	
	public boolean collidedWith(Entity e, int xa, int ya) {
		if (xa >= x + 2 && xa <= x + 30 && ya >= y + 2 && ya <= y + 30)
			return true;
		return false;
	}
}
