package com.nidjo123.mi;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {
	public static Sound shoot = loadSound("snd/shoot.wav");
	public static Sound eshoot = loadSound("snd/eshoot.wav");
	public static Sound bossdie = loadSound("snd/bossdie.wav");
	public static Sound enemydie = loadSound("snd/enemydie.wav");
	public static Sound menupick = loadSound("snd/menupick.wav");
	public static Sound menu = loadSound("snd/menu.wav");
	public static Sound levelup = loadSound("snd/levelup.wav");
	public static Sound playerdie = loadSound("snd/playerdie.wav");
	public static Sound bossshoot = loadSound("snd/playerdie.wav");
	public static Sound kamikaze = loadSound("snd/kamikaze.wav");
	public static Sound bosshurt = loadSound("snd/bosshurt.wav");
	
	public static Sound loadSound(String string) {
		Sound sound = new Sound();
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource(string));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			sound.clip = clip;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sound;
	}
	
	private Clip clip;
	
	public void play() {
		try {
			if (clip != null) {
				new Thread() {
					public void run() {
						synchronized (clip) {
							clip.stop();
							clip.setFramePosition(0);
							clip.start();
						}
					}
				}.start();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
