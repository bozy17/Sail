import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;


public class Boat extends GameObj {
	
	//public static final String img_file = "sailboat.png";
	public static final int WIDTH = 10;
	public static final int HEIGHT = 20;
	public static final double INIT_VEL_X = 0;
	public static final double INIT_VEL_Y = 0;
	
	// the initial position should be different depending on the level
	public static final int INIT_POS_X = 200;
	public static final int INIT_POS_Y = 200;
	
	public static BufferedImage img;

	public Boat (int court_width, int court_height) {
		super(INIT_VEL_X, INIT_VEL_Y, INIT_POS_X, INIT_POS_Y, 
				WIDTH, HEIGHT, court_width, court_height);
		
		//use picture later
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
		g.setColor(Color.WHITE);
		g.fillOval(pos_x, pos_y, width, height);
		
		//use picture later
		//g.drawImage(img, pos_x, pos_y, width, height, null); 
	}
	
	
}


