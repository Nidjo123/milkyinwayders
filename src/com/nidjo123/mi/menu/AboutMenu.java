package com.nidjo123.mi.menu;

import com.nidjo123.mi.Art;
import com.nidjo123.mi.MilkyInwayders;

public class AboutMenu extends GameMenu {
	public void tick(MilkyInwayders game, int bit) {
		if (bit == 3) {
			MilkyInwayders.menu.setMenu(new MainMenu());
		}
	}
	
	public void render(MilkyInwayders game) {
		for (int i = 0; i < game.pixels.length; i++) {
			game.pixels[i] = 0;
		}
		
		for (int i = 0; i < 13; i++) {
			Art.sheet[7][i].draw(i * 8 + game.WIDTH / 2 - 13 * 8 / 2, 5, game);
		}
		
		Art.draw("In a galaxy far, far away called", 2, 28, game);
		Art.draw("Milky Way... wait. It's not that", 2, 38, game);
		Art.draw("far. Anyway, Use arrows to move ", 2, 48, game);
		Art.draw("and space to shoot. You can also ", 2, 19 + 40, game);
		Art.draw("pause the game with p. ", 2, 19 + 50, game);
		Art.draw("Level up and beat all the enemies!", 2, 19 + 60, game);
		Art.draw("It was made by Norre and Nidjo123.", 2, 19 + 70, game);
		Art.draw("We hope you'll enjoy playing it!", 2, 19 + 80, game);
		Art.draw("Warning!", 2, 19 + 110, game);
		Art.draw("Every time enemy kills you, one", 2, 19 + 120, game);
		Art.draw("kitten dies. Better be careful!", 2, 19 + 130, game);
	}
}
