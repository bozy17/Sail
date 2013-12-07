import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Pirate extends GameObj {
	public static final double WIDTH = 15;
	public static final double HEIGHT = 30;
	
	public static BufferedImage img;
	
	public Pirate (double pos_x, double pos_y, double vel_x, double vel_y, 
				   double court_width, double court_height) {
		super(vel_x, vel_y, pos_x, pos_y, 
				WIDTH, HEIGHT, court_width, court_height);
	}
	
	@Override
	public void draw(Graphics g){
		Color dark_red = new Color(139, 0, 0);
		g.setColor(dark_red);
		g.fillOval((int)pos_x, (int)pos_y, (int)width, (int)height);
		}
	
}
