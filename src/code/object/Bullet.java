package code.object;

import java.awt.Color;
import java.awt.Graphics2D;

import scenes.ManageScene;

public class Bullet extends GameObject{
	public Bullet(double x, double y, int width, int height, double col, int hp, int damage, int window_w,
			int window_h,ManageScene ms) {
		super(x,y,width,height,col,hp,damage,window_w,window_h,Type.BULLET);
		this.ms=ms;
	}
	public void update() {
		y-=0.05;
	}
	public void draw(Graphics2D g2) {
		g2.setColor(Color.yellow);
		drawObject(g2);
	}
}
