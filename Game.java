/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

// imports necessary libraries for Java swing
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/** 
 * Game
 * Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	
	//keeps track of the level to play next
	public static int levelToPlay = 1;
	
	//background of the main home screen
	public static final String img_file = "sailboat-lake-sunset.jpg";
	public static BufferedImage img = null;
	
    public void run(){
        // NOTE : recall that the 'final' keyword notes inmutability
		  // even for local variables. 
    	
    	//the home screen
    	final JFrame home = new JFrame("Sail");
    	home.setLocation(300, 100);
    	home.setMinimumSize(new Dimension(1100, 800));
    	
    	
    	JPanel menu = new JPanel();
    	home.add(menu);
    	//play
    	JButton play = new JButton("Play");
    	play.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			home.setVisible(false);
    			play(1);
    		}
    	});
    	menu.add(play);
    	JButton quit = new JButton("Quit");
    	quit.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.exit(0);
    		}
    	});
    	menu.add(quit);
    	
    	home.pack();
    	home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	home.setVisible(true);

        
    }
    
    //play the game
    public static void play(int num) {
    	// Top-level frame in which game components live
		  // Be sure to change "TOP LEVEL FRAME" to the name of your game
      final JFrame frame = new JFrame("Sail");
      frame.setLocation(300,100);

		// Status panel
      JPanel status_panel = new JPanel(new BorderLayout());
      frame.add(status_panel, BorderLayout.NORTH);
      // treasure collected
      JLabel treasureCollected = new JLabel("Treasure Collected: 0");
      status_panel.add(treasureCollected, BorderLayout.WEST);
      JLabel countdown = new JLabel("30");
      status_panel.add(countdown, BorderLayout.EAST);

      // Main playing area 
      final int COURT_WIDTH = 800;
      final int COURT_HEIGHT = 800;
      
      // the differenet levels
      Level level1 = new Level(new Boat(100, 100, COURT_WIDTH, COURT_HEIGHT),
      		null,
      		new Pirate(700, 700, 0, -2, COURT_WIDTH, COURT_HEIGHT),
      		null,
      		new LevelGate(790, 10, COURT_WIDTH, COURT_HEIGHT),
      		new Wind(0, 150, 200, 400, 0, 6, COURT_WIDTH, COURT_HEIGHT),
      		new Land(300, 300, 200, 200, COURT_WIDTH, COURT_HEIGHT),
      		new Treasure(100, 700, COURT_WIDTH, COURT_HEIGHT),
      		60, COURT_WIDTH, COURT_HEIGHT);
      Level level2 = new Level(new Boat(700, 100, COURT_WIDTH, COURT_HEIGHT),
      		new Pirate(100, 300, 2, -1, COURT_WIDTH, COURT_HEIGHT),
      		new Pirate(700, 700, 0, -2, COURT_WIDTH, COURT_HEIGHT),
      		null,
      		new LevelGate(10, 10, COURT_WIDTH, COURT_HEIGHT),
      		new Wind(300, 501, 200, 100, 2, 0, COURT_WIDTH, COURT_HEIGHT),
      		new Land(300, 1, 200, 500, COURT_WIDTH, COURT_HEIGHT),
      		new Treasure(600, 700, COURT_WIDTH, COURT_HEIGHT),
      		30, COURT_WIDTH, COURT_HEIGHT);
      
      if (num == 1) {
	      final GameCourt level = new GameCourt(treasureCollected,
	      		countdown, level1);
	      frame.add(level, BorderLayout.CENTER);
      
	      // Put the frame on the screen
	      frame.pack();
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible(true);
	
	      // Start game
	      level.reset();
      } else if (num == 2) {
    	  final GameCourt level = new GameCourt(treasureCollected,
  	      		countdown, level2);
  	      frame.add(level, BorderLayout.CENTER);
        
  	      // Put the frame on the screen
  	      frame.pack();
  	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	      frame.setVisible(true);
  	
  	      // Start game
  	      level.reset();
      }
    }
    
    public void paintComponent(Graphics g) {
    	try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			} 
		} catch (IOException e) {
				System.out.println("Internal Error: " + e.getMessage());
		}
		g.drawImage(img, 0, 0, null);
	}

    /*
     * Main method run to start and run the game
     * Initializes the GUI elements specified in Game and runs it
     * NOTE: Do NOT delete! You MUST include this in the final submission of your game.
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Game());
    }
}
