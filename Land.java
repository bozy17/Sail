import java.awt.Color;
import java.awt.Graphics;


public class Land extends GameObj {
	
	//land object that can be changed depending on the level 
	public Land(double pos_x, double pos_y, double width, double height, 
			    double court_width, double court_height) {
		super(0, 0, pos_x, pos_y, width, height, 
				court_width, court_height);
	}

	@Override
	public void draw(Graphics g) {
		Color dark_green = new Color(0, 100, 0);
		g.setColor(dark_green);
		g.fillRoundRect((int)pos_x, (int)pos_y, (int)width, (int)height, 20, 20);
		
		//create the look of a beach
		Color sand = new Color(238, 214, 175);
		g.setColor(sand);
		g.drawRoundRect((int)pos_x, (int)pos_y, (int)width, (int)height, 20, 20);
	}

	
	
}
