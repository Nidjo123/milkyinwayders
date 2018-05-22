package com.nidjo123.mi.menu;

import com.nidjo123.mi.MilkyInwayders;

public class GameMenu {
	GameMenu menu;
	public boolean up = false, down = false;
	
	public void tick(MilkyInwayders game, int bit) {
			menu.tick(game, bit);
	}
	
	public void render(MilkyInwayders game) {
		menu.render(game);
	}
	
	public void setMenu(GameMenu menu) {
		this.menu = menu;
	}
}
