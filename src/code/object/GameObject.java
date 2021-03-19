package code.object;

import java.awt.Color;
import java.awt.Graphics2D;

import code.Main;
import scenes.ManageScene;

public class GameObject {
	public double x, y;
	int width, height;
	double col_x, col_y;
	//PLAYER,ENEMY,EBUL,PBUL,EFFECT
	public String tag;
	//高いほど処理優先度が高い
	public int priority = 100;
	//高いほど描画優先度が高い
	public int draw_priority = 100;
	int hp;
	public int damage;
	double rad = 0;
	int window_w, window_h;
	boolean invincible = false;
	final int morton_w = 600 / 8, morton_h = 720 / 8;
	final int game_w = Main.GAME_WIDTH, game_h = Main.GAME_HEIGHT;

	public Type type;
	ManageScene ms;
	public AreaType areaType = AreaType.CIRCLE;

	public boolean isDead = false;

	public enum AreaType {
		CIRCLE, SQUARE
	}

	public enum Type {
		PLAYER, BULLET, ENEMY, ENEMYBULLET
	}

	public GameObject() {

	}

	public GameObject(double x, double y, int width, int height, double col_x, double col_y, int hp, int damage,
			int window_w,
			int window_h, Type type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.col_x = col_x;
		this.col_y = col_y;
		this.hp = hp;
		this.damage = damage;
		this.window_w = window_w;
		this.window_h = window_h;
		this.type = type;
	}

	public GameObject(double x, double y, int width, int height, double col_x, double col_y, int hp, int damage,
			int window_w,
			int window_h, Type type, AreaType areaType) {
		this(x, y, width, height, col_x, col_y, hp, damage, window_w, window_h, type);
		this.areaType = areaType;
	}

	public boolean hitCheck(GameObject o) {
		if (this.areaType == AreaType.CIRCLE && o.areaType == AreaType.CIRCLE)
			return Math.pow(x * window_w - o.x * o.window_w, 2) + Math.pow(y * window_h - o.y * o.window_h, 2) <= Math
					.pow(col_x + o.col_x, 2);
		else if (this.areaType == AreaType.CIRCLE && o.areaType == AreaType.SQUARE) {
			return false;
		} else if (this.areaType == AreaType.SQUARE && o.areaType == AreaType.CIRCLE) {
			return false;
		} else {
			return false;
		}
	}

	double calc_dist(double xs, double ys, double xe, double ye, double x, double y) {
		double x0 = x, y0 = y;
		double x1 = xs, y1 = ys;
		double x2 = xe, y2 = ye;

		double a = x2 - x1;
		double b = y2 - y1;
		double a2 = a * a;
		double b2 = b * b;
		double r2 = a2 + b2;
		double tt = -(a * (x1 - x0) + b * (y1 - y0));

		if (tt < 0)
			return Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
		else if (tt > r2)
			return Math.sqrt((x2 - x0) * (x2 - x0) + (y2 - y0) * (y2 - y0));

		double f1 = a * (y1 - y0) - b * (x1 - x0);
		return Math.sqrt((f1 * f1) / r2);
	}

	//死亡時にtrue
	public boolean hit(int damage) {
		hp -= damage;
		if (Main.debug) {
			System.out.println("hit");
		}
		return hp <= 0;
	}

	public void update() {

	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.GRAY);
		drawObject(g2);
	}

	public void drawObject(Graphics2D g2) {
		g2.fillOval((int) (x * window_w) - width / 2, (int) (y * window_h) - height / 2, width, height);
	}

	public boolean offscreen() {
		return (int) (x * window_w) + width / 2 < 0 || (int) (y * window_h) + height / 2 < 0
				|| (int) (x * window_w) - width / 2 > window_w || (int) (y * window_h) - height / 2 > window_h;
	}

	//所属空間(root:0,親:1,子:2,孫:3),所属空間内での番号
	public int[] calcMorton() {
		int mx0=0;
		int my0=0;
		int mx1=0;
		int my1=0;
		if (areaType == AreaType.CIRCLE) {

			mx0 = ((int) (x * game_w + col_x)) / morton_w;
			my0 = ((int) (y * game_h + col_y)) / morton_h;
			mx1 = ((int) (x * game_w - col_x)) / morton_w;
			my1 = ((int) (y * game_h - col_y)) / morton_h;
		} else if(areaType==AreaType.SQUARE){
			///////ここ描こうね

		}
		int m0 = (mx0 & 4) * 4 + (mx0 & 2) * 2 + (mx0 & 1) + (my0 & 4) * 8 + (my0 & 2) * 4 + (my0 & 1) * 2;
		int m1 = (mx1 & 4) * 4 + (mx1 & 2) * 2 + (mx1 & 1) + (my1 & 4) * 8 + (my1 & 2) * 4 + (my1 & 1) * 2;
		int m = m0 ^ m1;
		int spacenum = ((m & 48) != 0) ? 0 : ((m & 12) != 0) ? 1 : ((m & 3) != 0) ? 2 : 3;
		return new int[] { spacenum, m0 >> ((3 - spacenum) * 2) };
	}
}
