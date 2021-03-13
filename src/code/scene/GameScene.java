package code.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import code.Main;
import code.object.EnemyBullet;
import code.object.GameObject;
import code.object.GameObject.Type;
import code.object.Player;
import scenes.ManageScene;

public class GameScene extends Scene {

	public List<GameObject> objects;
	BufferedImage image;
	int width, height;
	int padding_x, padding_y;
	public boolean isUpdate = true;

	GameObject player;
	List<List<List<GameObject>>> bullets;
	List<List<List<GameObject>>> enemies;
	List<List<List<GameObject>>> enemybullets;
	ManageScene ms;

	public GameScene(int width, int height, int padx, int pady, ManageScene ms) {
		this.width = width;
		this.height = height;
		this.padding_x = padx;
		this.padding_y = pady;
		image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		this.ms = ms;
	}

	//10microsecくらいだった
	public List<List<List<GameObject>>> mortonInit() {
		List<List<List<GameObject>>> o = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			List<List<GameObject>> space = new ArrayList<>();
			for (int j = 0; j < Math.pow(4, i); j++) {
				List<GameObject> num = new ArrayList<>();
				space.add(num);
			}
			o.add(space);
		}
		return o;
	}

	public void init() {
		objects = new ArrayList<>();
		player = new Player(width, height, ms);
		objects.add(player);
	}

	public void update() {
		bullets = mortonInit();
		enemies = mortonInit();
		enemybullets = mortonInit();
		Random r = new Random();
		if (r.nextInt() % 3 == 0) {
			objects.add(new EnemyBullet((r.nextDouble()), (r.nextDouble()) / 4, 10, 10, 5, 10, 1, width, height, ms));
		}
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).update();
			if (objects.get(i).isDead) {
				if(objects.get(i).type==Type.ENEMYBULLET) {
					addScore(100);
				}
				objects.remove(i--);
			}
		}
		for (int i = 0; i < objects.size(); i++) {
			GameObject o = objects.get(i);
			int[] m = o.calcMorton();
			switch (o.type) {
			case PLAYER:
				break;
			case ENEMY:
				enemies.get(m[0]).get(m[1]).add(o);
				break;
			case BULLET:
				bullets.get(m[0]).get(m[1]).add(o);
				break;
			case ENEMYBULLET:
				enemybullets.get(m[0]).get(m[1]).add(o);
				break;
			}
		} //ヒットチェック
		if (Main.debug) {
			System.out.println(objects.size());
		}

	}

	public void draw(Graphics2D g2) {
		Graphics2D g2_temp = g2;
		g2 = (Graphics2D) image.getGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0, 0, width, height);
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).draw(g2);
		}
		g2_temp.drawImage(image, padding_x, padding_y, width, height, null);
	}

	void addScore(int score) {
		ms.addScore(score);
	}
}
