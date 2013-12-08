import java.awt.Color;
import java.awt.Graphics;


public class CannonBall extends GameObj {
	
	private static final double WIDTH = 10;
	private static final double HEIGHT = 10;

	public CannonBall(double pos_x, double pos_y, double vel_x, 
			double vel_y, double court_width, double court_height) {
		super(vel_x, vel_y, pos_x, pos_y, WIDTH, HEIGHT, court_width, 
				court_height);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval((int)pos_x, (int)pos_y, (int)width, (int)height);
	}

	// the cannonball should be able to go outside of the map
	@Override
	public void move() {
		pos_x += v_x;
		pos_y += v_y;
	}

}
