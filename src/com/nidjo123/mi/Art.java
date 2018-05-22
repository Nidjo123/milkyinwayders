package com.nidjo123.mi;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	private static String chars = "" + //
			"ABCDEFGHIJKLMNOPQRSTUVWXYZ    " + //
			"1234567890:_=!?+-\"()/,.>'    " + //
			"";
	public static Bitmap[][] sheet = loadNCut("gfx/sheet.png", 8, 8);
	
	private static Bitmap[][] loadNCut(String string, int w, int h) {
		Bitmap[][] result;
		try {
			BufferedImage img = ImageIO.read(Art.class.getResource(string));
			int[] pixels = new int[img.getWidth() * img.getHeight()];
			img.getRGB(0, 0, img.getWidth(), img.getHeight(), pixels, 0, img.getWidth());
			result = new Bitmap[img.getHeight() / w][img.getWidth() / w];
			
			for (int i = 0; i < img.getHeight() / h; i++) {
				for (int j = 0; j < img.getWidth() / w; j++) {
					Bitmap bm = new Bitmap(w, w);
					int z = 0;
					int y = i * h;
					int x = j * w;
					
					for (int k = y; k < y + h; k++) {
						for (int l = x; l < x + w; l++) {
							bm.pixels[z++] = pixels[k * img.getWidth() + l];
							
						}
					}
					
					for (int k = 0; k < bm.pixels.length; k++) {
						if (bm.pixels[k] == 0xffff00ff) {
							bm.pixels[k] = -1;
						}
					}
					
					result[i][j] = bm;
				}
			}
			
			return result;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}
	
	public static void draw(String string, int x, int y, MilkyInwayders game) {
		string = string.toUpperCase();
		for (int i = 0; i < string.length(); i++) {
			int pos = chars.indexOf(string.charAt(i));
			if (pos > 29) {
				pos %= 30;
				if (pos <= 25)
					Art.sheet[29][pos].draw(x + i * 8, y, game);
			} else if (pos <= 25) {
				Art.sheet[28][pos].draw(x + i * 8, y, game);
			}
		}
	}
	
}