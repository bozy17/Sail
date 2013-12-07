import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Treasure extends GameObj {
	
	public static final String img_file = "treasure.png";
	public static BufferedImage img;
	public static double WIDTH = 25;
	public static double HEIGHT = 20;

	public Treasure(double pos_x, double pos_y, double court_width, 
					double court_height) {
		super(0, 0, pos_x, pos_y, 
				WIDTH, HEIGHT, court_width, court_height);
		

		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			} 
		} catch (IOException e) {
				System.out.println("Internal Error:" + e.getMessage());
		} 
	}

	@Override
	public void draw(Graphics g) {
		g.drawImage(img, (int)pos_x, (int)pos_y, (int)width, 
				    (int)height, null);
	}
	
	

}
