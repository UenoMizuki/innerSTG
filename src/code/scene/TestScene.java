package code.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import code.KeyManager;
import code.Main;

public class TestScene extends Scene {
	public void init() {

	}

	public void update() {
		if(KeyManager.isPress(KeyEvent.VK_ESCAPE))Main.removeScene();
	}

	public void draw(Graphics2D g2) {

		g2.setColor(Color.black);
		g2.fillRect(0, 0, 600, 720);

		g2.setColor(Color.white);
		g2.fillRect(25, 30, 550, 660);

		g2.setColor(Color.green);
		g2.drawRect(50, 60, 500, 600);

		g2.setColor(Color.blue);
		g2.drawRect(75, 90, 450, 540);

		g2.setColor(Color.red);
		g2.drawRect(100, 120, 400, 480);
	}
}
