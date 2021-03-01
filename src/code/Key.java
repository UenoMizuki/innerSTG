package code;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Key {
	public static final int A=KeyEvent.VK_A,B=KeyEvent.VK_B,C=KeyEvent.VK_C,D=KeyEvent.VK_D,E=KeyEvent.VK_E,F=KeyEvent.VK_F,G=KeyEvent.VK_G,H=KeyEvent.VK_H,I=KeyEvent.VK_I,J=KeyEvent.VK_J,K=KeyEvent.VK_K,L=KeyEvent.VK_L,M=KeyEvent.VK_M,N=KeyEvent.VK_N,O=KeyEvent.VK_O,P=KeyEvent.VK_P,Q=KeyEvent.VK_Q,R=KeyEvent.VK_R,S=KeyEvent.VK_S,T=KeyEvent.VK_T,U=KeyEvent.VK_U,V=KeyEvent.VK_V,W=KeyEvent.VK_W,X=KeyEvent.VK_X,Y=KeyEvent.VK_Y,Z=KeyEvent.VK_Z;
	public static final int SHIFT=KeyEvent.VK_SHIFT,CONTROL=KeyEvent.VK_CONTROL,UP=KeyEvent.VK_UP,DOWN=KeyEvent.VK_DOWN,RIGHT=KeyEvent.VK_RIGHT,LEFT=KeyEvent.VK_LEFT;
	static Map<String,Integer>keys;
	static void init() {
		keys=new HashMap<>();
		keys.put("A",A);
		keys.put("B",B);
		keys.put("C",C);
		keys.put("D",D);
		keys.put("E",E);
		keys.put("F",F);
		keys.put("G",G);
		keys.put("H",H);
		keys.put("I",I);
		keys.put("J",J);
		keys.put("K",K);
		keys.put("L",L);
		keys.put("M",M);
		keys.put("N",N);
		keys.put("O",O);
		keys.put("P",P);
		keys.put("Q",Q);
		keys.put("R",R);
		keys.put("S",S);
		keys.put("T",T);
		keys.put("U",U);
		keys.put("V",V);
		keys.put("W",W);
		keys.put("X",X);
		keys.put("Y",Y);
		keys.put("Z",Z);
		keys.put("SHIFT",SHIFT);
		keys.put("CONTROL",CONTROL);
		keys.put("UP",UP);
		keys.put("DOWN",DOWN);
		keys.put("RIGHT",RIGHT);
		keys.put("LEFT",LEFT);
	}
	static void change(String a,String b) {
		if(keys.containsKey(a)&&keys.containsKey(b)) {
			int n=keys.get(a);
			keys.remove(a);
			keys.put(b,keys.get(b));
			keys.remove(b);
			keys.put(a, n);
		}

		System.out.println("the key is None");
	}
	public static int get(String key) {
		return keys.get(key);
	}
}
