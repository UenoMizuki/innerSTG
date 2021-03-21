package code.object;

import java.awt.Color;
import java.awt.Graphics2D;

import code.Main;
import scenes.ManageScene;

public class GameObject {
	public double x, y;
	int width, height;
	double col_x, col_y;
	//PLAYER,ENEMY,EBUL,PBUL,EFFECT
	public String tag;
	//高いほど処理優先度が高い
	public int priority = 100;
	//高いほど描画優先度が高い
	public int draw_priority = 100;
	int hp;
	public int damage;
	public double rad = 0;
	int window_w, window_h;
	//画面外で消えるか？
	boolean invincible = false;
	final int morton_w = 600 / 8, morton_h = 720 / 8;
	final int game_w = Main.GAME_WIDTH, game_h = Main.GAME_HEIGHT;
	//対角線の長さ
	double r;
	public Type type;
	ManageScene ms;
	public AreaType areaType = AreaType.CIRCLE;

	public boolean isDead = false;

	public enum AreaType {
		CIRCLE, SQUARE
	}

	public enum Type {
		PLAYER, BULLET, ENEMY, ENEMYBULLET
	}

	public GameObject() {

	}

	int count = 0;

	public GameObject(double x, double y, int width, int height, double col_x, double col_y, int hp, int damage,
			int window_w, int window_h, Type type) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.col_x = col_x;
		this.col_y = col_y;
		this.hp = hp;
		this.damage = damage;
		this.window_w = window_w;
		this.window_h = window_h;
		this.type = type;
		r = Math.sqrt(Math.pow(col_x, 2) + Math.pow(col_y, 2));
	}

	public GameObject(double x, double y, int width, int height, double col_x, double col_y, int hp, int damage,
			int window_w,
			int window_h, Type type, AreaType areaType) {
		this(x, y, width, height, col_x, col_y, hp, damage, window_w, window_h, type);
		this.areaType = areaType;
	}

	public boolean hitCheck(GameObject o) {
		boolean b = false;
		if (this.areaType == AreaType.CIRCLE && o.areaType == AreaType.CIRCLE)
			b = Math.pow(x * window_w - o.x * o.window_w, 2) + Math.pow(y * window_h - o.y * o.window_h, 2) <= Math
					.pow(col_x + o.col_x, 2);
		else if (this.areaType == AreaType.CIRCLE && o.areaType == AreaType.SQUARE) {
			//右上の点の初期角度
			double prad = Math.atan2(o.col_y, o.col_x);

			double p0x = o.x * o.window_w + Math.cos(o.rad - prad) * o.r;
			double p0y = o.y * o.window_h + Math.sin(o.rad - prad) * o.r;
			double p1x = o.x * o.window_w + Math.cos(o.rad + prad) * o.r;
			double p1y = o.y * o.window_h + Math.sin(o.rad + prad) * o.r;
			double p2x = o.x * o.window_w + Math.cos(Math.PI + o.rad - prad) * o.r;
			double p2y = o.y * o.window_h + Math.sin(Math.PI + o.rad - prad) * o.r;
			double p3x = o.x * o.window_w + Math.cos(Math.PI + o.rad + prad) * o.r;
			double p3y = o.y * o.window_h + Math.sin(Math.PI + o.rad + prad) * o.r;

			b |= calc_dist(p0x, p0y, p1x, p1y, x * window_w, y * window_h) < col_x;
			b |= calc_dist(p1x, p1y, p2x, p2y, x * window_w, y * window_h) < col_x;
			b |= calc_dist(p2x, p2y, p3x, p3y, x * window_w, y * window_h) < col_x;
			b |= calc_dist(p3x, p3y, p0x, p0y, x * window_w, y * window_h) < col_x;

			if (!b) {
				b = Math.pow(x * window_w - o.x * o.window_w, 2) + Math.pow(y * window_h - o.y * o.window_h, 2) <= Math
						.pow(col_x, 2);
			}
			if (!b) {
				//b =IsPointInPolygon(x * window_w, y * window_h, new double[] { p0x, p1x, p2x, p3x },new double[] { p0y, p1y, p2y, p3y });
			}

		} else if (this.areaType == AreaType.SQUARE && o.areaType == AreaType.CIRCLE) {
			//右上の点の初期角度
			double prad = Math.atan2(col_y, col_x);

			double p0x = x * window_w + Math.cos(rad - prad) * r;
			double p0y = y * window_h + Math.sin(rad - prad) * r;
			double p1x = x * window_w + Math.cos(rad + prad) * r;
			double p1y = y * window_h + Math.sin(rad + prad) * r;
			double p2x = x * window_w + Math.cos(Math.PI + rad - prad) * r;
			double p2y = y * window_h + Math.sin(Math.PI + rad - prad) * r;
			double p3x = x * window_w + Math.cos(Math.PI + rad + prad) * r;
			double p3y = y * window_h + Math.sin(Math.PI + rad + prad) * r;
			b |= calc_dist(p0x, p0y, p1x, p1y, o.x * o.window_w, o.y * o.window_h) < o.col_x;
			b |= calc_dist(p1x, p1y, p2x, p2y, o.x * o.window_w, o.y * o.window_h) < o.col_x;
			b |= calc_dist(p2x, p2y, p3x, p3y, o.x * o.window_w, o.y * o.window_h) < o.col_x;
			b |= calc_dist(p3x, p3y, p0x, p0y, o.x * o.window_w, o.y * o.window_h) < o.col_x;
			if (!b) {
				b = Math.pow(x * window_w - o.x * o.window_w, 2) + Math.pow(y * window_h - o.y * o.window_h, 2) <= Math
						.pow(o.col_x, 2);
			}
			if (!b) {
				//b = IsPointInPolygon(o.x * o.window_w, o.y * o.window_h, new double[] { p0x, p1x, p2x, p3x },new double[] { p0y, p1y, p2y, p3y });
			}
		} else {
			double prad1 = Math.atan2(col_y, col_x);
			double prad2 = Math.atan2(o.col_y, o.col_x);

			double px1[] = new double[4];
			double py1[] = new double[4];
			double px2[] = new double[4];
			double py2[] = new double[4];
			px1[0] = x * window_w + Math.cos(rad - prad1) * r;
			py1[0] = y * window_h + Math.sin(rad - prad1) * r;
			px1[1] = x * window_w + Math.cos(rad + prad1) * r;
			py1[1] = y * window_h + Math.sin(rad + prad1) * r;
			px1[2] = x * window_w + Math.cos(Math.PI + rad - prad1) * r;
			py1[2] = y * window_h + Math.sin(Math.PI + rad - prad1) * r;
			px1[3] = x * window_w + Math.cos(Math.PI + rad + prad1) * r;
			py1[3] = y * window_h + Math.sin(Math.PI + rad + prad1) * r;

			px2[0] = o.x * o.window_w + Math.cos(o.rad - prad2) * o.r;
			py2[0] = o.y * o.window_h + Math.sin(o.rad - prad2) * o.r;
			px2[1] = o.x * o.window_w + Math.cos(o.rad + prad2) * o.r;
			py2[1] = o.y * o.window_h + Math.sin(o.rad + prad2) * o.r;
			px2[2] = o.x * o.window_w + Math.cos(Math.PI + o.rad - prad2) * o.r;
			py2[2] = o.y * o.window_h + Math.sin(Math.PI + o.rad - prad2) * o.r;
			px2[3] = o.x * o.window_w + Math.cos(Math.PI + o.rad + prad2) * o.r;
			py2[3] = o.y * o.window_h + Math.sin(Math.PI + o.rad + prad2) * o.r;

			for (int i = 0; i < 4; i++)
				for (int j = 0; j < 4; j++)
					if (b = lineIntersection(px1[i % 4], py1[i % 4], px1[(i + 1) % 4], py1[(i + 1) % 4], px2[j % 4],
							py2[j % 4], px2[(j + 1) % 4], py2[(j + 1) % 4])) {
						return b;
					}
			if (!b) {
				//b = IsPointInPolygon(o.x * o.window_w, o.y * o.window_h, px1, py1);
			}
			if (!b) {
				//b = IsPointInPolygon(x * window_w, y * window_h, px2, py2);
				//System.out.println("b");
			}
		}
		return b;
	}

	boolean IsPointInPolygon(double pointTargetx,double pointTargety, double[] aPointx,double[] aPointy) {
		int iCountCrossing = 0;

		double point0x = aPointx[0];
		double point0y = aPointy[0];
		boolean bFlag0x = (pointTargetx <= point0x);
		boolean bFlag0y = (pointTargety <= point0y);

		// レイの方向は、Ｘプラス方向
		for (int ui = 1; ui <= aPointx.length + 1; ui++) {
			double point1x = aPointx[ui % aPointx.length]; // 最後は始点が入る（多角形データの始点と終点が一致していないデータ対応）
			double point1y = aPointy[ui % aPointy.length]; // 最後は始点が入る（多角形データの始点と終点が一致していないデータ対応）
			boolean bFlag1x = (pointTargetx <= point1x);
			boolean bFlag1y = (pointTargety <= point1y);
			if (bFlag0y != bFlag1y) { // 線分はレイを横切る可能性あり。
				if (bFlag0x == bFlag1x) { // 線分の２端点は対象点に対して両方右か両方左にある
					if (bFlag0x) { // 完全に右。⇒線分はレイを横切る
						iCountCrossing += (bFlag0y ? -1 : 1); // 上から下にレイを横切るときには、交差回数を１引く、下から上は１足す。
					}
				} else { // レイと交差するかどうか、対象点と同じ高さで、対象点の右で交差するか、左で交差するかを求める。
					if (pointTargetx <= (point0x+ (point1x - point0x) * (pointTargety - point0y) / (point1y - point0y))) { // 線分は、対象点と同じ高さで、対象点の右で交差する。⇒線分はレイを横切る
						iCountCrossing += (bFlag0y ? -1 : 1); // 上から下にレイを横切るときには、交差回数を１引く、下から上は１足す。
					}
				}
			}
			// 次の判定のために、
			point0x = point1x;
			point0y = point1y;
			bFlag0x = bFlag1x;
			bFlag0y = bFlag1y;
		}

		// クロスカウントがゼロのとき外、ゼロ以外のとき内。
		return (0 != iCountCrossing);
	}

	double calc_dist(double xs, double ys, double xe, double ye, double x, double y) {
		double x0 = x, y0 = y;
		double x1 = xs, y1 = ys;
		double x2 = xe, y2 = ye;

		double a = x2 - x1;
		double b = y2 - y1;
		double a2 = a * a;
		double b2 = b * b;
		double r2 = a2 + b2;
		double tt = -(a * (x1 - x0) + b * (y1 - y0));

		if (tt < 0)
			return Math.sqrt((x1 - x0) * (x1 - x0) + (y1 - y0) * (y1 - y0));
		else if (tt > r2)
			return Math.sqrt((x2 - x0) * (x2 - x0) + (y2 - y0) * (y2 - y0));

		double f1 = a * (y1 - y0) - b * (x1 - x0);
		return Math.sqrt((f1 * f1) / r2);
	}

	boolean lineIntersection(double r1x, double r1y, double r2x, double r2y, double p1x, double p1y, double p2x,
			double p2y) {
		double t1, t2;

		//衝突判定計算
		t1 = (r1x - r2x) * (p1y - r1y) + (r1y - r2y) * (r1x - p1x);
		t2 = (r1x - r2x) * (p2y - r1y) + (r1y - r2y) * (r1x - p2x);

		//それぞれの正負が異なる（積が負になる）か、0（点が直線上にある）ならクロスしている
		if (t1 * t2 > 0) {
			return false;
		}
		t1 = (p1x - p2x) * (r1y - p1y) + (p1y - p2y) * (p1x - r1x);
		t2 = (p1x - p2x) * (r2y - p1y) + (p1y - p2y) * (p1x - r2x);
		if (t1 * t2 > 0) {
			return false;
		}
		return true;
	}

	//死亡時にtrue
	public boolean hit(int damage) {
		hp -= damage;
		if (Main.debug) {
			System.out.println("hit:" + type);
		}
		return hp <= 0;
	}

	public void update() {

	}

	public void draw(Graphics2D g2) {
		g2.setColor(Color.blue);
		drawObject(g2);
	}

	public void drawObject(Graphics2D g2) {
		if (areaType == AreaType.CIRCLE) {
			g2.fillOval((int) (x * window_w) - width / 2, (int) (y * window_h) - height / 2, width, height);
			if (Main.debug) {
				g2.setColor(Color.GRAY);
				g2.fillOval((int) ((x * window_w) - col_x), (int) ((y * window_h) - col_y),(int)col_x*2, (int)col_y*2);
			}
		} else {

			double prad = Math.atan2(col_y, col_x);

			double p0x = x * window_w + Math.cos(rad - prad) * r;
			double p0y = y * window_h + Math.sin(rad - prad) * r;
			double p1x = x * window_w + Math.cos(rad + prad) * r;
			double p1y = y * window_h + Math.sin(rad + prad) * r;
			double p2x = x * window_w + Math.cos(Math.PI + rad - prad) * r;
			double p2y = y * window_h + Math.sin(Math.PI + rad - prad) * r;
			double p3x = x * window_w + Math.cos(Math.PI + rad + prad) * r;
			double p3y = y * window_h + Math.sin(Math.PI + rad + prad) * r;

			//g2.fillOval((int) (x * window_w) - width / 2, (int) (y * window_h) - height / 2, width, height);
			g2.fillOval((int) ((x * window_w) - col_x), (int) ((y * window_h) - col_y), (int) col_x * 2,
					(int) col_y * 2);
			if (Main.debug) {
				g2.setColor(Color.GRAY);
				g2.drawLine((int) p0x, (int) p0y, (int) p1x, (int) p1y);
				g2.drawLine((int) p1x, (int) p1y, (int) p2x, (int) p2y);
				g2.drawLine((int) p2x, (int) p2y, (int) p3x, (int) p3y);
				g2.drawLine((int) p3x, (int) p3y, (int) p0x, (int) p0y);
			}
		}
	}

	public boolean offscreen() {
		return !invincible && ((int) (x * window_w) + width / 2 < 0 || (int) (y * window_h) + height / 2 < 0
				|| (int) (x * window_w) - width / 2 > window_w || (int) (y * window_h) - height / 2 > window_h);
	}

	//所属空間(root:0,親:1,子:2,孫:3),所属空間内での番号
	public int[] calcMorton() {
		int mx0 = 0;
		int my0 = 0;
		int mx1 = 0;
		int my1 = 0;
		if (areaType == AreaType.CIRCLE) {
			mx0 = ((int) (x * game_w + col_x)) / morton_w;
			my0 = ((int) (y * game_h + col_y)) / morton_h;
			mx1 = ((int) (x * game_w - col_x)) / morton_w;
			my1 = ((int) (y * game_h - col_y)) / morton_h;
		} else if (areaType == AreaType.SQUARE) {
			///////回転角で計算するのめんどくさいので多少大きくなるけど対角線の長さを使用
			mx0 = ((int) (x * game_w + r)) / morton_w;
			my0 = ((int) (y * game_h + r)) / morton_h;
			mx1 = ((int) (x * game_w - r)) / morton_w;
			my1 = ((int) (y * game_h - r)) / morton_h;
		}
		int m0 = (mx0 & 4) * 4 + (mx0 & 2) * 2 + (mx0 & 1) + (my0 & 4) * 8 + (my0 & 2) * 4 + (my0 & 1) * 2;
		int m1 = (mx1 & 4) * 4 + (mx1 & 2) * 2 + (mx1 & 1) + (my1 & 4) * 8 + (my1 & 2) * 4 + (my1 & 1) * 2;
		int m = m0 ^ m1;
		int spacenum = ((m & 48) != 0) ? 0 : ((m & 12) != 0) ? 1 : ((m & 3) != 0) ? 2 : 3;

		return new int[] { spacenum, m0 >> ((3 - spacenum) * 2) };
	}
}
