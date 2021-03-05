package code.scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class InfoScene extends Scene{

	GameScene gameScene;
	int width,height;
	BufferedImage image;
	public InfoScene(int width,int height) {
		this.width=width;
		this.height=height;
		image=new BufferedImage(width,height,BufferedImage.TYPE_4BYTE_ABGR);
	}
	public void init() {

	}
	public void update() {

	}
	public void draw(Graphics2D g2) {

	}
	public void setGameScene(GameScene gameScene) {
		this.gameScene=gameScene;
	}
}
