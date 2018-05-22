package com.nidjo123.mi;

public class Bitmap {
	public int[] pixels;
	public int w, h;
	
	public Bitmap(int width, int height) {
		pixels = new int[width * height];
		this.w = width;
		this.h = height;
	}
	
	public void draw(int x, int y, MilkyInwayders game) {
		int k = 0;
		for (int i = y; i < y + h; i++) {
			for (int j = x; j < x + w; j++) {
				int src = pixels[k++];
				if (i > MilkyInwayders.game.HEIGHT || j > MilkyInwayders.game.WIDTH || i < 0 || j < 0 || i * game.WIDTH + j > 270 * 180)
					continue;
				if (src != -1)
					game.pixels[i * game.WIDTH + j] = src;
			}
		}
	}
}
