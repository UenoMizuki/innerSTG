package code.scene;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class GameScene extends Scene {
	BufferedImage image;
	int width, height;

	public GameScene(int width, int height) {
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
	}

	public void init() {

	}

	public void update() {

	}

	public void draw(Graphics2D g2) {

	}

}
