import java.awt.Color;
import java.awt.Graphics;


public class Wind extends GameObj {
	
	public static final int INIT_VEL_X = 2;
	public static final int INIT_VEL_Y = 0;

	public Wind(int pos_x, int pos_y, int width, int height, 
						   int court_width, int court_height) {
		super(INIT_VEL_X, INIT_VEL_Y, pos_x, pos_y, 
				width, height, court_width, court_height);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect(pos_x, pos_y, width, height);
	}

	
	
}
