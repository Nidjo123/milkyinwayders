package com.nidjo123.mi.menu;

import com.nidjo123.mi.Art;
import com.nidjo123.mi.MilkyInwayders;
import com.nidjo123.mi.Sound;

public class MainMenu extends GameMenu {
	private int count = 0;
	public boolean up = false, down = false;
	
	String[] strings = { "Play", "Restart", "About", "Exit", "Resume" };
	
	public void tick(MilkyInwayders game, int bit) {
		if (game.playing) {
			if (bit == 1 && count > 0) {
				count--;
				Sound.menu.play();
			}
			
			if (bit == 2 && count < 3) {
				count++;
				Sound.menu.play();
			}
			
			if (count == 2 && bit == 3) {
				Sound.menupick.play();
				MilkyInwayders.menu.setMenu(new AboutMenu());
			} else if (count == 0 && bit == 3) {
				Sound.menupick.play();
				MilkyInwayders.menu = null;
				game.playing = true;
			} else if (count == 3 && bit == 3) {
				MilkyInwayders.game.stop();
			} else if (count == 1 && bit == 3) {
				Sound.menupick.play();
				MilkyInwayders.game.init();
				MilkyInwayders.menu = null;
				game.playing = true;
			}
		} else {
			if (bit == 1 && count > 0) {
				count--;
				Sound.menu.play();
			}
			
			if (bit == 2 && count < 2) {
				count++;
				Sound.menu.play();
			}
			
			if (count == 2 && bit == 3) {
				Sound.menupick.play();
				MilkyInwayders.game.stop();
			} else if (count == 0 && bit == 3) {
				Sound.menupick.play();
				MilkyInwayders.menu = null;
				game.playing = true;
			} else if (count == 1 && bit == 3) {
				Sound.menupick.play();
				MilkyInwayders.menu.setMenu(new AboutMenu());
			}
		}
	}
	
	public void render(MilkyInwayders game) {
		for (int i = 0; i < game.pixels.length; i++) {
			game.pixels[i] = 0;
		}
		
		for (int i = 0; i < 13; i++) {
			Art.sheet[7][i].draw(i * 8 + game.WIDTH / 2 - 13 * 8 / 2, game.HEIGHT / 4, game);
		}
		
		if (!game.playing) {
			for (int i = 0; i < strings.length - 2; i++) {
				String string = strings[i];
				
				if (i > 0) {
					string = strings[i + 1];
				}
				
				if (count == i) {
					Art.draw(">", game.WIDTH / 2 - 5 * 8 / 2 - 8, game.HEIGHT / 2 + i * 9, game);
					Art.draw(string, game.WIDTH / 2 - 5 * 8 / 2, game.HEIGHT / 2 + i * 9, game);
				} else {
					Art.draw(string, game.WIDTH / 2 - 5 * 8 / 2, game.HEIGHT / 2 + i * 9, game);
				}
			}
		} else {
			for (int i = 0; i < strings.length - 1; i++) {
				String string = strings[i];
				
				if (i == 0 && game.playing) {
					string = strings[4];
				}
				
				if (count == i) {
					Art.draw(">", game.WIDTH / 2 - 5 * 8 / 2 - 8, game.HEIGHT / 2 + i * 9, game);
					Art.draw(string, game.WIDTH / 2 - 5 * 8 / 2, game.HEIGHT / 2 + i * 9, game);
				} else {
					Art.draw(string, game.WIDTH / 2 - 5 * 8 / 2, game.HEIGHT / 2 + i * 9, game);
				}
			}
		}
	}
}
