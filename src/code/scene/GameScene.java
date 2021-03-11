package code.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import code.Key;
import code.KeyManager;
import code.object.GameObject;
import scenes.ManageScene;

public class GameScene extends Scene {

	List<GameObject> objects;
	BufferedImage image;
	int width, height;
	int padding_x, padding_y;
	public boolean isUpdate = true;

	GameObject player;
	List<List<List<GameObject>>> bullets;
	List<List<List<GameObject>>> enemies;
	List<List<List<GameObject>>> enemybullets;
	ManageScene ms;
	public GameScene(int width, int height, int padx, int pady,ManageScene ms) {
		this.width = width;
		this.height = height;
		this.padding_x = padx;
		this.padding_y = pady;
		image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
		this.ms=ms;
	}

	//10microsecくらいだった
	public void mortonInit(List<List<List<GameObject>>> o) {
		o = new ArrayList<>();
		for (int i = 0; i < 4; i++) {
			List<List<GameObject>> space = new ArrayList<>();
			for (int j = 0; j < Math.pow(4, i); j++) {
				List<GameObject> num = new ArrayList<>();
				space.add(num);
			}
			o.add(space);
		}
	}

	public void init() {
		objects = new ArrayList<>();
		player=new GameObject(0.5, 0.8, 30, 30, 15, 10, 1, width, height, GameObject.Type.PLAYER) {
			public void update() {
				double speed = 0.01;
				boolean isOddPress = KeyManager.isPressed(Key.get("DOWN")) ^ KeyManager.isPressed(Key.get("UP"))
						^ KeyManager.isPressed(Key.get("RIGHT")) ^ KeyManager.isPressed(Key.get("LEFT"));
				if (KeyManager.isPressed(Key.get("DOWN"))) {
					this.y += speed * (isOddPress ? 1 : 1.0 / Math.sqrt(2));
				}
				if (KeyManager.isPressed(Key.get("UP"))) {
					this.y -= speed * (isOddPress ? 1 : 1.0 / Math.sqrt(2));
				}
				if (KeyManager.isPressed(Key.get("RIGHT"))) {
					this.x += speed * (isOddPress ? 1 : 1.0 / Math.sqrt(2));
				}
				if (KeyManager.isPressed(Key.get("LEFT"))) {
					this.x -= speed * (isOddPress ? 1 : 1.0 / Math.sqrt(2));
				}
				this.x = this.x < 0 ? 0 : this.x > 1 ? 1 : this.x;
				this.y = this.y < 0 ? 0 : this.y > 1 ? 1 : this.y;
			}
		};
		objects.add(player);
	}

	public void update() {
		mortonInit(bullets);
		mortonInit(enemies);
		mortonInit(enemybullets);
		for (int i = 0; i < objects.size(); i++) {
			objects.get(i).update();
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
		}//ヒットチェック

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
	}void addScore(int score){
		ms.addScore(score);
	}
}

