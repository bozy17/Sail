import java.awt.Color;
import java.awt.Graphics;


public class Land extends GameObj {

	public static final int POS_X = 500;
	public static final int POS_Y = 200;
	public static final int WIDTH = 100;
	public static final int HEIGHT = 200;
	
	public Land(int court_width, int court_height) {
		super(0, 0, POS_X, POS_Y, WIDTH, HEIGHT, 
				court_width, court_height);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.GREEN);
		g.fillRect(POS_X, POS_Y, WIDTH, HEIGHT);
	}

	
	
}
