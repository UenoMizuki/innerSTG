package code.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import scenes.ManageScene;

public class InfoScene extends Scene{

	GameScene gameScene;
	int width,height;
	BufferedImage image;
	ManageScene ms;
	int score;
	public InfoScene(int width,int height,ManageScene ms) {
		this.width=width;
		this.height=height;
		image=new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
		this.ms=ms;
	}
	public void init() {

	}
	public void update() {
		score=ms.getScore();
	}
	public void draw(Graphics2D g2) {
		g2.setColor(Color.black);
		g2.drawString(score+"", 100, 100);
	}
	public void setGameScene(GameScene gameScene) {
		this.gameScene=gameScene;
	}
}
