import java.awt.Color;
import java.awt.Graphics;


public class LevelGate extends GameObj {
	
	public static final int SIZE = 15;
	public static final int  INIT_POS_X = 0;
	public static final int INIT_POS_Y = 0;
	public static final int INIT_VEL_X = 0;
	public static final int INIT_VEL_Y = 0;

	public LevelGate(int court_width, int court_height) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, 
				SIZE, SIZE, court_width, court_height);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.ORANGE);
		g.fillOval(pos_x, pos_y, width, height);
	}
	
}
