package com.nidjo123.mi.entities;

import com.nidjo123.mi.Art;
import com.nidjo123.mi.MilkyInwayders;
import com.nidjo123.mi.Sound;

public class Bullet extends Entity {
	private int x, y, ya = 2;
	public int dir;
	
	int scale = MilkyInwayders.game.SCALE;
	
	public Bullet(int x, int y, int dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public void tick(MilkyInwayders game) {
		if (y < 0 || y > MilkyInwayders.game.HEIGHT) {
			die(game);
		}
		
		for (int i = 0; i < game.entities.size(); i++) {
			Entity e = game.entities.get(i);
			if ((e instanceof YellowEnemy || e instanceof GreenEnemy || e instanceof OrangeEnemy || e instanceof BlueEnemy)
					&& e.collidedWith(this, x, y) && dir == 1) {
				e.die(game);
				die(game);
				MilkyInwayders.game.enemyCount--;
				Sound.enemydie.play();
				Player.score += 10;
			} else if (e instanceof Player && e.collidedWith(this, x, y) && dir == 3) {
				e.die(game);
				die(game);
			} else if (e instanceof SpaceBoss && e.collidedWith(this, x, y) && dir == 1) {
				((SpaceBoss) e).hurt(game);
				die(game);
			}
		}
		
		move(ya, dir);
	}
	
	public void die(MilkyInwayders game) {
		game.entities.remove(this);
	}
	
	public void render(MilkyInwayders game) {
		Art.sheet[0][6].draw(x, y, game);
	}
	
	private void move(int ya, int dir) {
		if (dir == 1) {
			y -= ya;
		} else if (dir == 3) {
			y += ya;
		}
	}
}
