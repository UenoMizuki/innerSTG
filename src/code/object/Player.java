package code.object;

import code.Key;
import code.KeyManager;
import scenes.ManageScene;

public class Player extends GameObject {
	//new GameObject(0.5,0.8,30,30,15,10,1,width,height,GameObject.Type.PLAYER);
	public int spaceNum, num;
	int count = 0;

	public Player(double x, double y, int width, int height, double col_x, double col_y, int hp, int damage,
			int window_w,
			int window_h, ManageScene ms) {
		super(x, y, width, height, col_x, col_y, hp, damage, window_w, window_h, Type.PLAYER);
		this.ms = ms;
	}

	public Player(int width, int height, ManageScene ms) {
		this(0.5, 0.8, 10, 10, 5, 5, 10, 1, width, height, ms);
	}

	public void update() {
		double speed = 0.01;
		boolean isOddPress = KeyManager.isPressed(Key.get("DOWN")) ^ KeyManager.isPressed(Key.get("UP"))
				^ KeyManager.isPressed(Key.get("RIGHT")) ^ KeyManager.isPressed(Key.get("LEFT"));
		if (KeyManager.isPressed(Key.get("DOWN"))) {
			y += speed * (isOddPress ? 1 : 1.0 / Math.sqrt(2));
		}
		if (KeyManager.isPressed(Key.get("UP"))) {
			y -= speed * (isOddPress ? 1 : 1.0 / Math.sqrt(2));
		}
		if (KeyManager.isPressed(Key.get("RIGHT"))) {
			x += speed * (isOddPress ? 1 : 1.0 / Math.sqrt(2));
		}
		if (KeyManager.isPressed(Key.get("LEFT"))) {
			x -= speed * (isOddPress ? 1 : 1.0 / Math.sqrt(2));
		}
		x = x < 0 ? 0 : x > 1 ? 1 : x;
		y = y < 0 ? 0 : y > 1 ? 1 : y;
		if (KeyManager.isPressed(Key.get("Z"))) {
			if (KeyManager.isPressed(Key.get("SHIFT")))
			ms.getGameScene().objects.add(new Bullet(x, y - 0.25, 10, 1000, 5, 500, 100, 1, window_w, window_h, ms, AreaType.SQUARE));
			if(count%3==0) {
				ms.getGameScene().objects.add(new Bullet(x, y, 10, 10, 5, 5, 1, 1, window_w, window_h, ms) {
					public void update() {

						y-=0.025;
						isDead=offscreen();
					}
				});
			}if(count%5==0) {
				ms.getGameScene().objects.add(new Bullet(x, y, 10, 10, 5, 5, 1, 1, window_w, window_h, ms) {
					public void update() {
						x+=0.010;
						y-=0.020;
						isDead=offscreen();
					}
				});ms.getGameScene().objects.add(new Bullet(x, y, 10, 10, 5, 5, 1, 1, window_w, window_h, ms) {
					public void update() {
						x-=0.010;
						y-=0.020;
						isDead=offscreen();
					}
				});
			}if(count%8==0&&!KeyManager.isPressed(Key.get("SHIFT"))) {
				ms.getGameScene().objects.add(new Bullet(x, y, 10, 10, 5, 5, 1, 1, window_w, window_h, ms) {
					public void update() {
						x+=0.020;
						y-=0.020;
						isDead=offscreen();
					}
				});ms.getGameScene().objects.add(new Bullet(x, y, 10, 10, 5, 5, 1, 1, window_w, window_h, ms) {
					public void update() {
						x-=0.020;
						y-=0.020;
						isDead=offscreen();
					}
				});
			}
			count++;
		} else {
			count = 0;
		}
	}
}
