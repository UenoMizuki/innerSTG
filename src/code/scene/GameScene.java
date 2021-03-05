package code.scene;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import code.Key;
import code.KeyManager;
import code.object.GameObject;

public class GameScene extends Scene {

	List<GameObject> objects;
	BufferedImage image;
	int width, height;
	int padding_x,padding_y;
	public boolean isUpdate=true;

	public GameScene(int width, int height,int padx,int pady) {
		this.width = width;
		this.height = height;
		this.padding_x=padx;
		this.padding_y=pady;
		image = new BufferedImage(width, height, BufferedImage.TYPE_4BYTE_ABGR);
	}

	public void init() {
		objects=new ArrayList<>();
		objects.add(new GameObject(0.5,0.8,30,30,15,10,1,width,height) {public void update() {
			double speed=0.01;
			boolean isOddPress=KeyManager.isPressed(Key.get("DOWN"))^KeyManager.isPressed(Key.get("UP"))^KeyManager.isPressed(Key.get("RIGHT"))^KeyManager.isPressed(Key.get("LEFT"));
			if(KeyManager.isPressed(Key.get("DOWN"))) {
				this.y+=speed*(isOddPress?1:1.0/Math.sqrt(2));
			}if(KeyManager.isPressed(Key.get("UP"))) {
				this.y-=speed*(isOddPress?1:1.0/Math.sqrt(2));
			}if(KeyManager.isPressed(Key.get("RIGHT"))) {
				this.x+=speed*(isOddPress?1:1.0/Math.sqrt(2));
			}if(KeyManager.isPressed(Key.get("LEFT"))) {
				this.x-=speed*(isOddPress?1:1.0/Math.sqrt(2));
			}
		}});
	}

	public void update() {
		for(int i=0;i<objects.size();i++) {
			objects.get(i).update();
		}

	}

	public void draw(Graphics2D g2) {
		Graphics2D g2_temp=g2;
		g2=(Graphics2D)image.getGraphics();
		g2.setColor(Color.white);
		g2.fillRect(0,0,width, height);
		for(int i=0;i<objects.size();i++) {
			objects.get(i).draw(g2);
		}
		g2_temp.drawImage(image,padding_x,padding_y,width,height,null);
	}
}
