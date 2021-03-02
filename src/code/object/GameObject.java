package code.object;

import java.awt.Color;
import java.awt.Graphics2D;

public class GameObject {
	double x, y;
	int width, height;
	double col;
	String tag;
	int hp;
	int damage;
	int window_w, window_h;
	boolean invincible = false;

	public GameObject() {

	}

	public GameObject(double x, double y, int width, int height, double col, int hp, int damage, int window_w,
			int window_h) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.col = col;
		this.hp = hp;
		this.damage = damage;
		this.window_w = window_w;
		this.window_h = window_h;
	}

	public boolean hitCheck(GameObject o) {

		return Math.pow(x * window_w - o.x * o.window_w, 2) + Math.pow(y * window_h - o.y * o.window_h, 2) >= Math
				.pow(col + o.col, 2);
	}

	public void update() {

	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.GRAY);
		g2.fillOval((int) (x * window_w) - width / 2, (int) (y * window_h) - height / 2, width, height);
	}

}
