package scenes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import code.KeyManager;
import code.Main;
import code.scene.GameScene;
import code.scene.Scene;

public class TestScene extends Scene {
	List<GameScene> gameScenes;
	public void init() {
		gameScenes=new ArrayList<>();
		GameScene gs=new GameScene(550, 660,25,30);
		gameScenes.add(gs);
		gs.init();
	}

	public void update() {
		if (KeyManager.isPress(KeyEvent.VK_ESCAPE))
			Main.removeScene();
		for(int i=0;i<gameScenes.size();i++) {
			gameScenes.get(i).update();
		}
	}

	public void draw(Graphics2D g2) {

		g2.setColor(Color.black);
		g2.fillRect(0, 0, 600, 720);
		for(int i=0;i<gameScenes.size();i++) {
			gameScenes.get(i).draw(g2);
		}
		/*
			g2.setColor(Color.white);
			g2.fillRect(25, 30, 550, 660);
			g2.setColor(Color.green);
			g2.drawRect(50, 60, 500, 600);
			g2.setColor(Color.blue);
			g2.drawRect(75, 90, 450, 540);
			g2.setColor(Color.red);
			g2.drawRect(100, 120, 400, 480);*/
	}
}
