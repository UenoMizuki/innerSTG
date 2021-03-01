package code.scene;

import java.awt.Graphics2D;

public abstract class Scene {

	public void default_init() {
		init();
	}
	public void default_update(Graphics2D g2) {
		update();
		default_draw(g2);
	}
	public void default_draw(Graphics2D g2) {
		draw(g2);
	}
	public void init() {}
	public void update() {}
	public void draw(Graphics2D g2) {}
}
