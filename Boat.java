import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Boat extends GameObj {
	
	//public static final String img_file = "sailboat.png";
	public static final double WIDTH = 10;
	public static final double HEIGHT = 20;
	
	public static BufferedImage img;

	public Boat (double pos_x, double pos_y, double court_width, 
			     double court_height) {
		
		//the initial velocity should always be 0, and the start postion
		//will be different for each level
		super(0, 0, pos_x, pos_y, 
				WIDTH, HEIGHT, court_width, court_height);
	}
	
	@Override
	public void draw(Graphics g){
		g.setColor(Color.WHITE);
		g.fillOval((int)pos_x, (int)pos_y, (int)width, (int)height);
		
		// create a border for the boat
		g.setColor(Color.BLACK);
		g.drawOval((int)pos_x, (int)pos_y, (int)width, (int)height);
	}
	
	
}


