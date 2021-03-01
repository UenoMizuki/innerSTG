package code;

import javax.swing.JFrame;

public class Frame extends JFrame {
	public Frame() {
		super();
	}public Frame(int x,int y,String title) {
		super();
		setBounds(0, 0, x, y);
		setTitle(title);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}
}
