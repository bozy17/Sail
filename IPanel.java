import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class IPanel extends JPanel {

	private final String img_file = "Sail.png";
	private BufferedImage img = null;
	
	public IPanel () {
		super();
		super.setPreferredSize(new Dimension(1000, 800));
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			} 
		} catch (IOException e) {
				System.out.println("Internal Error: " + e.getMessage());
		}
		g.drawImage(img, 0, 0, 1000, 800, null);
		
	}
}
