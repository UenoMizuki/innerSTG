package code.object;

import java.awt.Color;
import java.awt.Graphics2D;

import scenes.ManageScene;

public class Bullet extends GameObject{
	public Bullet(double x, double y, int width, int height, double col_x, double col_y, int hp, int damage, int window_w,
			int window_h,ManageScene ms) {
		super(x,y,width,height,col_x,col_y,hp,damage,window_w,window_h,Type.BULLET);
		this.ms=ms;
	}public Bullet(double x, double y, int width, int height, double col_x, double col_y, int hp, int damage, int window_w,
			int window_h,ManageScene ms,AreaType areaType) {
		this(x,y,width,height,col_x,col_y,hp,damage,window_w,window_h,ms);
		this.areaType=areaType;
	}
	public void update() {
		y-=0.50;
		isDead=offscreen();
	}
	public void draw(Graphics2D g2) {
		g2.setColor(Color.yellow);
		drawObject(g2);
	}
}
