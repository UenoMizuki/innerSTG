package code;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;


public class KeyManager implements KeyListener {
	private static boolean[] keyPre;
	private static boolean[] keyNow;
	private static boolean[] keyNext;

	private static KeyManager km;

	public static void Init(JFrame fr) {
		keyPre = new boolean[128];
		keyNow = new boolean[128];
		keyNext = new boolean[128];
		km = new KeyManager();
		fr.addKeyListener(km);
		Key.init();
	}

	public static void update() {
		keyPre = keyNow.clone();
		keyNow = keyNext.clone();
	}

	public static boolean isPressed(int key) {
		return keyNow[key];
	}

	public static boolean isPress(int key) {
		return keyNow[key] && !keyPre[key];
	}

	public static boolean isPressed(int keys[]) {
		boolean result = true;
		for (int key : keys) {
			result = result && keyNow[key];
			if (!result)
				break;
		}

		return result;
	}

	public static boolean onPressed(int key) {
		return keyPre[key] && keyNow[key];
	}

	public static boolean onReleased(int key) {
		return keyPre[key] && !keyNow[key];
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() < 128)
			keyNext[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println(e.getKeyCode());
		if (e.getKeyCode() < 128)
			keyNext[e.getKeyCode()] = false;
	}
	public static void clear() {
		for(int i=0;i<128;i++) {
			keyPre[i]=false;
			keyNow[i]=false;
			keyNext[i]=false;
		}
	}
}
