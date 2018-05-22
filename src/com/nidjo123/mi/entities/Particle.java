package com.nidjo123.mi.entities;

import java.util.Random;

import com.nidjo123.mi.MilkyInwayders;

public class Particle extends Entity {
	private int life, dir, col, gravity, bit;
	private int x, y;
	private double xa, ya;
	Random random = new Random();
	
	public Particle(int x, int y, int col, int gravity, int bit) {
		int xx = x + random.nextInt(8);
		int yy = y + random.nextInt(5);
		this.x = xx;
		this.y = yy;
		this.life = random.nextInt(20) + 10;
		this.col = col;
		this.gravity = gravity;
		this.bit = bit;
		dir = 1;
		xa = (random.nextDouble() - random.nextDouble()) * 1 + 3;
		ya = (random.nextDouble() - random.nextDouble()) * 1 + 3;
		if (random.nextInt(10) == 0) {
			this.col = 0xFFD259;
		}
		if (random.nextInt(3) == 0)
			dir = 0;
		if (random.nextInt(3) == 1)
			dir = 2;
	}
	
	public void tick(MilkyInwayders game) {
		if (life-- <= 0)
			die(game);
		
		move(dir);
	}
	
	public void die(MilkyInwayders game) {
		game.entities.remove(this);
	}
	
	public void render(MilkyInwayders game) {
		if (y > game.HEIGHT || x > game.WIDTH || y < 0 || x < 0 || y * game.WIDTH + x > 270 * 180 - 1 || y * game.WIDTH + x < 0)
			return;
		game.pixels[(int) (y * game.WIDTH + x)] = col;
	}
	
	private void move(int dir) {
		if (y > 2 && y < MilkyInwayders.game.HEIGHT - 2 && gravity == 0) {
			y -= ya;
		} else if (y > 2 && y < MilkyInwayders.game.HEIGHT - 2 && gravity == 1) {
			y += ya;
		}
		
		ya *= 0.94;
		
		if (bit == 1) {
			if (x > 0 && x < MilkyInwayders.game.WIDTH - 1 && dir == 0) {
				x += xa;
			} else if (x > 0 && x < MilkyInwayders.game.WIDTH - 1 && dir == 2) {
				x -= xa;
			}
		}
	}
}
