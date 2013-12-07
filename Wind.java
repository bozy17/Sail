import java.awt.Color;
import java.awt.Graphics;


public class Wind extends GameObj {

	public Wind(double pos_x, double pos_y, double width, double height, 
			    double vel_x, double vel_y, int court_width, int court_height) {
		super(vel_x, vel_y, pos_x, pos_y, 
				width, height, court_width, court_height);
	}

	@Override
	public void draw(Graphics g) {
		g.setColor(Color.BLACK);
		g.drawRect((int)pos_x, (int)pos_y, (int)width, (int)height);
	}

	
	
}
