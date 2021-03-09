package code.object;

import java.awt.Color;
import java.awt.Graphics2D;

import code.Main;

public class GameObject {
	public double x, y;
	int width, height;
	double col;
	//PLAYER,ENEMY,EBUL,PBUL,EFFECT
	public String tag;
	//高いほど処理優先度が高い
	public int priority=100;
	//高いほど描画優先度が高い
	public int draw_priority=100;
	int hp;
	int damage;
	int window_w, window_h;
	boolean invincible = false;
	final int morton_w = 600 / 8, morton_h = 720 / 8;
	final int game_w=Main.GAME_WIDTH,game_h=Main.GAME_HEIGHT;

	public Type type;

	public enum Type {
		PLAYER,BULLET,ENEMY,ENEMYBULLET
	}

	public GameObject() {

	}

	public GameObject(double x, double y, int width, int height, double col, int hp, int damage, int window_w,
			int window_h,Type type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.col = col;
		this.hp = hp;
		this.damage = damage;
		this.window_w = window_w;
		this.window_h = window_h;
		this.type=type;
	}

	public boolean hitCheck(GameObject o) {

		return Math.pow(x * window_w - o.x * o.window_w, 2) + Math.pow(y * window_h - o.y * o.window_h, 2) >= Math
				.pow(col + o.col, 2);
	}
	//死亡時にtrue
	public boolean hit(int damage) {
		hp-=damage;
		return hp<=0;
	}

	public void update() {

	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.GRAY);
		g2.fillOval((int) (x * window_w) - width / 2, (int) (y * window_h) - height / 2, width, height);
	}

	//所属空間(root:0,親:1,子:2,孫:3),所属空間内での番号
	public int[] calcMorton() {
		int mx0=((int)(x*game_w+col))/morton_w;
		int my0=((int)(y*game_h+col))/morton_h;
		int mx1=((int)(x*game_w-col))/morton_w;
		int my1=((int)(y*game_h-col))/morton_h;
		int m0=(mx0&4)*4+(mx0&2)*2+(mx0&1)+(my0&4)*8+(my0&2)*4+(my0&1)*2;
		int m1=(mx1&4)*4+(mx1&2)*2+(mx1&1)+(my1&4)*8+(my1&2)*4+(my1&1)*2;
		int m=m0^m1;
		int spacenum=((m&48)!=0)?0:((m&12)!=0)?1:((m&3)!=0)?2:3;
		return new int[] {spacenum,m0>>((3-spacenum)*2)};
	}

}
