package com.nidjo123.mi;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.nidjo123.mi.entities.BlueEnemy;
import com.nidjo123.mi.entities.Entity;
import com.nidjo123.mi.entities.GreenEnemy;
import com.nidjo123.mi.entities.OrangeEnemy;
import com.nidjo123.mi.entities.Particle;
import com.nidjo123.mi.entities.Player;
import com.nidjo123.mi.entities.SpaceBoss;
import com.nidjo123.mi.entities.YellowEnemy;
import com.nidjo123.mi.menu.GameMenu;
import com.nidjo123.mi.menu.MainMenu;

public class MilkyInwayders extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	
	public final int WIDTH = 270;
	public final int HEIGHT = 180;
	public final int SCALE = 3;
	
	private Thread thread;
	private boolean running;
	
	public static final MilkyInwayders game = new MilkyInwayders();
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public Random random = new Random();
	private Bitmap bg = new Bitmap(WIDTH, HEIGHT);
	private boolean focused = true, paused = false;
	public static GameMenu menu = new GameMenu();
	public boolean playing = false, won = false, lost = false;
	private boolean bossFight = false;
	
	private BufferedImage img;
	public int[] pixels;
	
	public static int ticks = 0, tickCount = 0;
	
	public MilkyInwayders() {
		img = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
		pixels = new int[WIDTH * HEIGHT];
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
	}
	
	public synchronized void start() {
		if (running)
			return;
		running = true;
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		if (!running)
			return;
		running = false;
		try {
			thread.join();
			System.exit(0);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public synchronized void run() {
		createBufferStrategy(3);
		setBounds(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		addKeyListener(new InputHandler());
		
		requestFocus();
		
		double tickTime = 1000 / 60.;
		long timeThen = System.currentTimeMillis(), fpsTime = System.currentTimeMillis();
		double timer = 0;
		int fps = 0;
		ticks = 0;
		boolean shouldRender = true;
		
		menu.setMenu(new MainMenu());
		
		init();
		
		while (running) {
			long passed = System.currentTimeMillis() - timeThen;
			timeThen = System.currentTimeMillis();
			timer += passed;
			
			while (timer >= tickTime) {
				timer -= tickTime;
				tick();
				ticks++;
				tickCount++;
				shouldRender = true;
			}
			
			if (shouldRender) {
				render();
				fps++;
				shouldRender = false;
			}
			
			if (System.currentTimeMillis() - fpsTime >= 1000) {
				fpsTime = System.currentTimeMillis();
				System.out.println(fps + " fps, " + ticks + " ticks");
				fps = 0;
				ticks = 0;
			}
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}
	
	private void render() {
		BufferStrategy bs = getBufferStrategy();
		
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}
		
		Graphics2D g = (Graphics2D) bs.getDrawGraphics();
		
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
		
		if (menu == null) {
			bg.draw(0, 0, game);
			
			if (!won && !lost || Player.afterTime > 0)
				for (int i = 0; i < entities.size(); i++) {
					entities.get(i).render(game);
				}
			
			if (levelUpTicks > 0 && !won && !lost) {
				Art.draw("Level up!", 0, 1, game);
			}
			
			for (int i = WIDTH * (HEIGHT - 11); i < WIDTH * HEIGHT; i++) {
				pixels[i] &= 0x884444;
				pixels[i] ^= 0x2202aa;
			}
			
			for (int i = 0; i < 13; i++) {
				Art.sheet[7][i].draw(i * 8 + (WIDTH - (13 * 8)), HEIGHT - 9, game);
			}
			
			Art.draw("Score: " + Player.score, 0, HEIGHT - 9, game);
			
			if (won && Player.afterTime <= 0) {
				Art.sheet[4][28].draw(WIDTH / 2 - 8, HEIGHT / 5, game);
				Art.sheet[4][29].draw(WIDTH / 2, HEIGHT / 5, game);
				Art.sheet[5][28].draw(WIDTH / 2 - 8, HEIGHT / 5 + 8, game);
				Art.sheet[5][29].draw(WIDTH / 2, HEIGHT / 5 + 8, game);
				Art.draw("Apple a day keeps the Space Boss", WIDTH / 2 - 32 * 8 / 2, HEIGHT / 4 + 10, game);
				Art.draw("away!", WIDTH / 2 - 5 * 8 / 2, HEIGHT / 4 + 23, game);
				Art.draw("You are golden!", WIDTH / 2 - 15 * 8 / 2, HEIGHT / 2 - 4, game);
				Art.draw("Press enter to restart", WIDTH / 2 - 22 * 8 / 2, HEIGHT / 2 + 9, game);
			} else if (lost && Player.afterTime <= 0) {
				Art.sheet[4][24].draw(WIDTH / 2 - 8, HEIGHT / 5, game);
				Art.sheet[4][25].draw(WIDTH / 2, HEIGHT / 5, game);
				Art.sheet[5][24].draw(WIDTH / 2 - 8, HEIGHT / 5 + 8, game);
				Art.sheet[5][25].draw(WIDTH / 2, HEIGHT / 5 + 8, game);
				Art.draw("Here, have this banana.", WIDTH / 2 - 23 * 8 / 2, HEIGHT / 4 + 10, game);
				Art.draw("At least you've been in space!", WIDTH / 2 - 30 * 8 / 2, HEIGHT / 2 - 4, game);
				Art.draw("Press enter to restart", WIDTH / 2 - 22 * 8 / 2, HEIGHT / 2 + 9, game);
			}
			
			if ((!focused || paused) && (!won || !lost)) {
				for (int i = 0; i < pixels.length; i++) {
					pixels[i] &= 0x4444d4;
				}
				if (ticks >= 31)
					Art.draw("paused!", WIDTH / 2 - (7 * 8) / 2, HEIGHT / 2 - 3, game);
				else
					Art.draw("paused!", WIDTH / 2 - (7 * 8) / 2, HEIGHT / 2, game);
			}
		} else {
			menu.render(game);
		}
		g.drawImage(img, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);
		
		g.dispose();
		bs.show();
	}
	
	int level = 0;
	public int enemyCount = 25;
	private int levelUpTicks = 0;
	
	private void tick() {
		focused = isFocusOwner();
		
		if (Player.afterTime > 0 && (won || lost)) {
			Player.afterTime--;
		}
		
		if (focused && !paused && menu == null && (!won || !lost) && Player.afterTime > 0) {
			for (int i = 0; i < entities.size(); i++) {
				entities.get(i).tick(game);
			}
			
			if (levelUpTicks > 0) {
				levelUpTicks--;
			}
			
			if (enemyCount <= 0 && !bossFight) {
				level++;
				
				if (level > 3) {
					bossFight = true;
					entities.add(new SpaceBoss(WIDTH / 2 - 16, HEIGHT / 3));
				}
				
				if ((!won || !lost) && !bossFight) {
					Sound.levelup.play();
					levelUp();
					levelUpTicks = 180;
				}
			}
		} else if (menu != null) {
			menu.tick(game, 0);
		}
	}
	
	public void init() {
		entities.clear();
		
		for (int i = 0; i < bg.pixels.length; i++) {
			if (random.nextInt(1000) == 0) {
				bg.pixels[i] = 0xD6C6FF;
			} else {
				bg.pixels[i] = 0;
			}
		}
		
		Player.score = 0;
		Player.afterTime = 120;
		
		level = tickCount = 0;
		
		won = lost = false;
		
		placeEnemies(5, 5, 0);
		
		entities.add(new Player((int) (WIDTH / 2) - 8, (int) ((HEIGHT / 5) * 4)));
	}
	
	private void levelUp() {
		for (int i = 0; i < entities.size(); i++) {
			Entity e = entities.get(i);
			if (!(e instanceof Player) && !(e instanceof Particle)) {
				entities.remove(i);
			}
		}
		
		placeEnemies(5, 5, level);
	}
	
	private void placeEnemies(int rows, int cols, int e) {
		enemyCount = rows * cols;
		
		int xx = WIDTH / cols;
		int yy = HEIGHT / 5 * 3 / rows;
		int xOffs = WIDTH / 2 / cols - 12;
		int yOffs = HEIGHT / 5 * 3 / rows - 8;
		
		for (int i = 0; i < cols; i++) {
			for (int j = 0; j < rows; j++) {
				switch (e) {
				case 0:
					entities.add(new YellowEnemy(i * xx + xOffs, j * yy + yOffs));
					break;
				case 1:
					entities.add(new GreenEnemy(i * xx + xOffs, j * yy + yOffs));
					break;
				case 2:
					entities.add(new OrangeEnemy(i * xx + xOffs, j * yy + yOffs));
					break;
				case 3:
					entities.add(new BlueEnemy(i * xx + xOffs, j * yy + yOffs));
					break;
				default:
					entities.add(new YellowEnemy(i * xx + xOffs, j * yy + yOffs));
				}
			}
		}
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Milky Inwayders");
		JPanel panel = new JPanel();
		Dimension size = new Dimension(game.WIDTH * game.SCALE, game.HEIGHT * game.SCALE);
		
		panel.setPreferredSize(size);
		panel.setMinimumSize(size);
		panel.setMaximumSize(size);
		
		panel.add(game);
		frame.setContentPane(panel);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		game.start();
	}
	
	private class InputHandler implements KeyListener {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				Player.left = true;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				Player.right = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				Player.shoot = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_P) {
				paused = !paused;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				menu.tick(game, 1);
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.tick(game, 2);
			} else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (menu != null) {
					menu.tick(game, 3);
				}
				
				if (won || lost) {
					Sound.menupick.play();
					init();
				}
			} else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				menu = new GameMenu();
				menu.setMenu(new MainMenu());
			}
		}
		
		public void keyReleased(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				Player.left = false;
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				Player.right = false;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				Player.shoot = false;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				menu.up = false;
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				menu.down = false;
			}
		}
		
		public void keyTyped(KeyEvent e) {
		}
	}
	
}
