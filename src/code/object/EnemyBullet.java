package code.object;

import java.awt.Color;
import java.awt.Graphics2D;

import scenes.ManageScene;

public class EnemyBullet extends GameObject{
	public EnemyBullet(double x, double y, int width, int height, double col, int hp, int damage, int window_w,
			int window_h,ManageScene ms) {
		super(x,y,width,height,col,hp,damage,window_w,window_h,Type.ENEMYBULLET);
		this.ms=ms;
	}public EnemyBullet(double x, double y, int width, int height, double col, int hp, int damage, int window_w,
			int window_h,ManageScene ms,AreaType areaType) {
		this(x,y,width,height,col,hp,damage,window_w,window_h,ms);
		this.areaType=areaType;
	}
	public void update() {
		y+=0.01;
		isDead=offscreen();
	}
	public void draw(Graphics2D g2) {
		g2.setColor(Color.red);
		drawObject(g2);
	}
}
