import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Pirate extends GameObj {

	public static final String img_file = "";
	public static final int INIT_VEL_X = 2;
	public static final int INIT_VEL_Y = 2;
	public static final int INIT_POS_X = 20;
	public static final int INIT_POS_Y = 20;
	public static final int WIDTH = 15;
	public static final int HEIGHT = 30;
	
	public static BufferedImage img;
	
	public Pirate (int pos_x, int pos_y, int vel_x, int vel_y, int court_width, int court_height) {
		super(vel_x, vel_y, pos_x, pos_y, 
				WIDTH, HEIGHT, court_width, court_height);
	
		//add a picture later
		/* try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			}
		} catch (IOException e) {
			System.out.println("Internal Error:" + e.getMessage());
	
		} */
	}
	
	@Override
	public void draw(Graphics g){
		g.setColor(Color.RED);
		g.fillOval(pos_x, pos_y, width, height);
		
		//add picture later
		//g.drawImage(img, pos_x, pos_y, width, height, null); 
	}
	
}
