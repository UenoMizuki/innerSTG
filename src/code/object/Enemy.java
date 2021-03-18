package code.object;

import java.awt.Color;
import java.awt.Graphics2D;

import scenes.ManageScene;

public class Enemy extends GameObject{
	int count=0;
	public Enemy(double x, double y, int width, int height, double col, int hp, int damage, int window_w,
			int window_h,ManageScene ms) {
		super(x,y,width,height,col,hp,damage,window_w,window_h,Type.ENEMY);
		this.ms=ms;
	}public Enemy(double x, double y, int width, int height, double col, int hp, int damage, int window_w,
			int window_h,ManageScene ms,AreaType areaType) {
		this(x,y,width,height,col,hp,damage,window_w,window_h,ms);
		this.areaType=areaType;
	}
	public void update() {
		if(++count%10==0) {
			ms.getGameScene().objects.add(new EnemyBullet(x,y,10,10,5,1,1,window_w,window_h,ms));
		}
		isDead=offscreen();
	}
	public void draw(Graphics2D g2) {
		g2.setColor(Color.black);
		drawObject(g2);
	}
}
