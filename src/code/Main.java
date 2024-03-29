package code;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;

import code.scene.Scene;
import scenes.ManageScene;

public class Main {

	public static final String user_dir = System.getProperty("user.dir");

	public static final int FPS = 30;

	public static final int WIDTH = 960, HEIGHT = 720;
	public static final int GAME_WIDTH = 600, GAME_HEIGHT = 720;
	public static final int FRAME_MARGIN = 7;
	public static final int FRAME_MARGIN_Y = 30;
	public static final int FRAME_MARGIN_X = 4;
	public static final int WINDOW_WIDTH = WIDTH + FRAME_MARGIN + FRAME_MARGIN_X,
			WINDOW_HEIGHT = HEIGHT + FRAME_MARGIN + FRAME_MARGIN_Y;
	static JFrame fr;
	public static Graphics2D g2;

	static List<Scene> scenes = new ArrayList<>();
	static int sceneNum = -1;

	public static boolean debug=false;

	static BufferedImage backgroundImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_4BYTE_ABGR);

	public static void main(String[] args) {
		fr = new Frame(WINDOW_WIDTH, WINDOW_HEIGHT, "STG");
		KeyManager.Init(fr);
		addScene(new ManageScene());

		Key.replace("UP", "W");
		Key.replace("DOWN", "S");
		Key.replace("RIGHT", "D");
		Key.replace("LEFT", "A");
		Key.replace("Z", "M");

		long error = 0;
		long idealSleep = (1000 << 16) / FPS;
		long oldTime;
		long newTime = System.currentTimeMillis() << 16;
		while (true) {
			oldTime = newTime;
			update(); // メイン処理
			newTime = System.currentTimeMillis() << 16;
			long sleepTime = idealSleep - (newTime - oldTime) - error; // 休止できる時間
			if (sleepTime < 0)
				sleepTime = 0;
			oldTime = newTime;
			try {
				Thread.sleep(sleepTime >> 16);
			} catch (InterruptedException e) {
				// TODO 自動生成された catch ブロック
				e.printStackTrace();
			} // 休止
			newTime = System.currentTimeMillis() << 16;
			error = newTime - oldTime - sleepTime; // 休止時間の誤差
		}
	}

	public static void addScene(Scene scene) {
		scenes.add(scene);
		sceneNum++;
		scene.default_init();
	}

	public static void removeScene() {
		if (scenes.size() > 1) {
			scenes.remove(sceneNum--);
		} else
			System.exit(0);
	}

	public static void update() {
		KeyManager.update();
		if(KeyManager.isPress(KeyEvent.VK_P)&&KeyManager.onPressed(KeyEvent.VK_SHIFT)) {
			debug^=true;
		}
		g2 = (Graphics2D) backgroundImage.getGraphics();
		g2.setColor(new Color(0x330055));
		g2.fillRect(0, 0, WIDTH, HEIGHT);
		scenes.get(sceneNum).default_update(g2);

		g2 = (Graphics2D) fr.getGraphics();
		g2.drawImage(backgroundImage, FRAME_MARGIN_X, FRAME_MARGIN_Y, null);
	}
}
