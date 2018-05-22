package com.nidjo123.mi;

import java.applet.Applet;
import java.awt.BorderLayout;

public class MIApplet extends Applet {
	private static final long serialVersionUID = 1L;
	
	public void init() {
		setLayout(new BorderLayout());
		add(MilkyInwayders.game, BorderLayout.CENTER);
	}
	
	public void start() {
		MilkyInwayders.game.start();
	}
	
	public void stop() {
		MilkyInwayders.game.stop();
	}
}
