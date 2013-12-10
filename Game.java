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
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.BasicSplitPaneUI;

/** 
 * Game
 * Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {

	//keeps track of the level to play next
	public static int levelToPlay = 1;
	
	//keeps track of the current game window (for disposing purposes)
	public static JFrame current;
	//keeps track of the current pop up window (for disposing purposes)
	public static JFrame currentPop;
	
	//background of the main home screen
	public static final String img_file = "sailboat-lake-sunset.jpg";
	private BufferedImage img = null;
	
    public void run(){
        // NOTE : recall that the 'final' keyword notes immutability
		  // even for local variables. 
    	
    	//the home screen
    	final JFrame home = new JFrame("Sail");
    	
    	current = home;
    	
    	//got this from http://stackoverflow.com/questions/9543320/
    	//how-to-position-the-form-in-the-center-screen
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	int w = home.getSize().width;
    	int h = home.getSize().height;
    	int x = (dim.width - w) / 2;
    	int y = (dim.height - h) / 2;
    	home.setLocation(x, y);
    	
    	JPanel menu = new JPanel();
    	home.add(menu, BorderLayout.NORTH);
    	IPanel pic = new IPanel();
    	home.add(pic, BorderLayout.CENTER);
    	
    	//the different buttons on the home screen
    	final JButton play = new JButton("Play");
    	play.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			home.setVisible(false);
    			home.dispose();
    			GameCourt.scoreTracker = 0;
    			play(1);
    		}
    	});
    	menu.add(play);
    	JButton direct = new JButton("Directions");
    	direct.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			directions();
    		}
    	});
    	menu.add(direct);
    	JButton coolFeats = new JButton("Cool Features");
    	coolFeats.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			coolFeatures();
    		}
    	});
    	menu.add(coolFeats);
    	JButton quit = new JButton("Quit");
    	quit.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			System.exit(0);
    		}
    	});
    	menu.add(quit);
    	
    	home.pack();
    	home.setLocationRelativeTo(null);
    	home.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	home.setVisible(true);
    }
    
    public static void coolFeatures() {
    	
    	final JFrame frame = new JFrame("Cool Features");
    	
    	//center on screen
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	int w = frame.getSize().width;
    	int h = frame.getSize().height;
    	int x = (dim.width - w) / 2;
    	int y = (dim.height - h) / 2;
    	frame.setLocation(x, y);
    	
    	String coolness = "<html><body>There are a number of cool features in this game:<br><br>" +
    			"1) A level system that easily allows for input of new levels and different difficulty levels.<br>" +
    			"2) A weapon system for the pirates that incrementally shoots at the boat<br>" +
    			"3) Wind is present, helping or hurting the player by pushing the boat in a certain direction<br>" +
    			"4) Pirates are programmed individually with AI that allows them to bounce, loop, or follow the player</body></html>";
    	
    	JLabel cool = new JLabel();
	    cool.setBorder(new EmptyBorder(10, 10, 10, 10));
	    frame.add(cool, BorderLayout.NORTH);
	    cool.setText(coolness);
	    
	    JPanel cool_panel = new JPanel();
	    JButton ok = new JButton("Ok");
	    ok.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			frame.setVisible(false);
    			frame.dispose();
    		}
    	});
	    cool_panel.add(ok, BorderLayout.EAST);
	    frame.add(cool_panel, BorderLayout.SOUTH);
	    
	   // Put the frame on the screen
	      frame.pack();
	      frame.setLocationRelativeTo(null);
	      frame.setVisible(true);
    }
    
    //get the directions for the game
    public static void directions() {
    	
    	final JFrame frame = new JFrame("Directions");
    	
    	//center on screen
    	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
    	int w = frame.getSize().width;
    	int h = frame.getSize().height;
    	int x = (dim.width - w) / 2;
    	int y = (dim.height - h) / 2;
    	frame.setLocation(x, y);
    	
    	String message = "<html><body>You are a sailboat lost at sea after a terrible storm…you must navigate through the" + "<br>" +
	    		"murky and pirate infested waters on your journey back home by USING THE ARROW" + "<br>" +
	    		"KEYS.  You must pilot your sailboat (White Ship), pick up the treasure (Treasure" + "<br>" +
	    		"Chest) and then sail through the level gate (Yellow Circle) to pass each level.  If you " + "<br>" +
	    		"do not pick up the magical treasure, the level gates will not be open for you.  Be on" + "<br>" +
	    		"the lookout for pirates (Red Ships), if you crash into them or they shoot you with" + "<br>" +
	    		"their cannons (Black Cannonballs), you will be destroyed.  Also, make sure you" + "<br>" +
	    		"avoid any land (Green) because you will crash if you run directly into it." + "<br>" +
	    		"Miscellaneous wind gusts (Outlined Black) will either aid you or hinder you on your" + "<br>" +
	    		"journey, so either avoid them or don’t…does the risk outweigh the rewards?   Take" + "<br>" +
	    		"in to account all of your obstacles, and don’t forget to watch the timer, as you only" + "<br>" +
	    		"have a certain amount of time to navigate through each level.  Your objective is to" + "<br>" +
	    		"make it through all of the levels in the fastest amount of time possible.  Your score" + "<br>" +
	    		"at the end will be the total amount of time it took for you to navigate the levels." + "<br>" +
	    		"  Good luck…and smooth sailing, captain!</body></html>";
    	
	    JLabel directions = new JLabel();
	    directions.setBorder(new EmptyBorder(10, 10, 10, 10));
	    frame.add(directions, BorderLayout.NORTH);
	    directions.setText(message);
	    
	    JPanel ok_panel = new JPanel();
	    JButton ok = new JButton("Ok");
	    ok.addActionListener(new ActionListener() {
    		public void actionPerformed(ActionEvent e) {
    			frame.setVisible(false);
    			frame.dispose();
    		}
    	});
	    ok_panel.add(ok, BorderLayout.EAST);
	    frame.add(ok_panel, BorderLayout.SOUTH);
	    
	   // Put the frame on the screen
	      frame.pack();
	      frame.setLocationRelativeTo(null);
	      frame.setVisible(true);
    }
    
    //play the game
    public static void play(int num) {
    	// Top-level frame in which game components live
		  // Be sure to change "TOP LEVEL FRAME" to the name of your game
      final JFrame frame = new JFrame("Sail");
      
      current = frame;
      
      //center the screen
      Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
  	  int w = frame.getSize().width;
  	  int h = frame.getSize().height;
  	  int x = (dim.width - w) / 2;
  	  int y = (dim.height - h) / 2;
  	  frame.setLocation(x, y);

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
      		new LevelGate(775, 10, COURT_WIDTH, COURT_HEIGHT),
      		new Wind(10, 150, 280, 500, 0, 6, COURT_WIDTH, COURT_HEIGHT),
      		new Land(300, 300, 200, 200, COURT_WIDTH, COURT_HEIGHT),
      		new Treasure(100, 700, COURT_WIDTH, COURT_HEIGHT),
      		60, COURT_WIDTH, COURT_HEIGHT);
      if (num == 1) {
	      final GameCourt level = new GameCourt(treasureCollected,
	      		countdown, level1);
	      frame.add(level, BorderLayout.CENTER);
      
	      // Put the frame on the screen
	      frame.pack();
	      frame.setLocationRelativeTo(null);
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible(true);
	
	      // Start game
	      level.reset();
      } 
      
      Level level2 = new Level(new Boat(700, 100, COURT_WIDTH, COURT_HEIGHT),
      		new Pirate(100, 300, 2, -1, COURT_WIDTH, COURT_HEIGHT),
      		new Pirate(700, 700, 0, -2, COURT_WIDTH, COURT_HEIGHT),
      		null,
      		new LevelGate(10, 10, COURT_WIDTH, COURT_HEIGHT),
      		new Wind(300, 501, 200, 100, 2, 0, COURT_WIDTH, COURT_HEIGHT),
      		new Land(300, 1, 200, 500, COURT_WIDTH, COURT_HEIGHT),
      		new Treasure(600, 700, COURT_WIDTH, COURT_HEIGHT),
      		60, COURT_WIDTH, COURT_HEIGHT);
      if (num == 2) {
    	  final GameCourt level = new GameCourt(treasureCollected,
  	      		countdown, level2);
  	      frame.add(level, BorderLayout.CENTER);
        
  	      // Put the frame on the screen
  	      frame.pack();
  	      frame.setLocationRelativeTo(null);
  	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	      frame.setVisible(true);
  	
  	      // Start game
  	      level.reset();
      } 
      
      Level level3 = new Level(new Boat(10, 770, COURT_WIDTH, COURT_HEIGHT),
    		  new Pirate(100, 100, 3, -3, COURT_WIDTH, COURT_HEIGHT),
    		  new Pirate(700, 700, 0, -2, COURT_WIDTH, COURT_HEIGHT),
    		  null,
    		  new LevelGate(780, 10, COURT_WIDTH, COURT_HEIGHT),
    		  new Wind(200, 200, 400, 400, -7, 0, COURT_WIDTH, COURT_HEIGHT),
    		  new Land(1, 600, 600, 140, COURT_WIDTH, COURT_HEIGHT),
    		  new Treasure(10, 570, COURT_WIDTH, COURT_HEIGHT),
    		  60, COURT_WIDTH, COURT_HEIGHT);
      if (num == 3) {
    	  final GameCourt level = new GameCourt(treasureCollected,
    	      		countdown, level3);
    	      frame.add(level, BorderLayout.CENTER);
          
    	      // Put the frame on the screen
    	      frame.pack();
    	      frame.setLocationRelativeTo(null);
    	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	      frame.setVisible(true);
    	
    	      // Start game
    	      level.reset();
      }
      
      Level level4 = new Level(new Boat(775, 775, COURT_WIDTH, COURT_HEIGHT),
    		  new Pirate(100, 500, -2, 1, COURT_WIDTH, COURT_HEIGHT),
    		  new Pirate(600, 700, 0, -2, COURT_WIDTH, COURT_HEIGHT),
    		  null,
    		  new LevelGate(10, 10, COURT_WIDTH, COURT_HEIGHT),
    		  new Wind(441, 30, 350, 400, 0, 7, COURT_WIDTH, COURT_HEIGHT),
    		  new Land(30, 30, 400, 400, COURT_WIDTH, COURT_HEIGHT),
    		  new Treasure(775, 10, COURT_WIDTH, COURT_HEIGHT),
    		  60, COURT_WIDTH, COURT_HEIGHT);
    
    if (num == 4) {
  	  final GameCourt level = new GameCourt(treasureCollected,
  	      		countdown, level4);
  	      frame.add(level, BorderLayout.CENTER);
        
  	      // Put the frame on the screen
  	      frame.pack();
  	      frame.setLocationRelativeTo(null);
  	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	      frame.setVisible(true);
  	
  	      // Start game
  	      level.reset();
    }
    
    Level level5 = new Level(new Boat(10, 10, COURT_WIDTH, COURT_HEIGHT),
  		  null,
  		  null,
  		  null,
  		  new LevelGate(10, 775, COURT_WIDTH, COURT_HEIGHT),
  		  null,
  		  new Land(40, 40, 715, 715, COURT_WIDTH, COURT_HEIGHT),
  		  new Treasure(765, 770, COURT_WIDTH, COURT_HEIGHT),
  		  60, COURT_WIDTH, COURT_HEIGHT);
  
    if (num == 5) {
	  final GameCourt level = new GameCourt(treasureCollected,
	      		countdown, level5);
	      frame.add(level, BorderLayout.CENTER);
      
	      // Put the frame on the screen
	      frame.pack();
	      frame.setLocationRelativeTo(null);
	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	      frame.setVisible(true);
	
	      // Start game
	      level.reset();
    }
    
    Level level6 = new Level(new Boat(10, 10, COURT_WIDTH, COURT_HEIGHT),
    		  new Pirate(400, 400, -4, 0, COURT_WIDTH, COURT_HEIGHT),
    		  new Pirate(600, 400, 0, -2, COURT_WIDTH, COURT_HEIGHT),
    		  new Pirate(200, 400, 0, -1, COURT_WIDTH, COURT_HEIGHT),
    		  new LevelGate(765, 765, COURT_WIDTH, COURT_HEIGHT),
    		  null,
    		  null,
    		  new Treasure(760, 10, COURT_WIDTH, COURT_HEIGHT),
    		  60, COURT_WIDTH, COURT_HEIGHT);
    
      if (num == 6) {
  	  final GameCourt level = new GameCourt(treasureCollected,
  	      		countdown, level6);
  	      frame.add(level, BorderLayout.CENTER);
        
  	      // Put the frame on the screen
  	      frame.pack();
  	      frame.setLocationRelativeTo(null);
  	      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  	      frame.setVisible(true);
  	
  	      // Start game
  	      level.reset();
      }
      
      if (num == 7) {
    	  final JFrame Win = new JFrame();
    	  
    	  currentPop = Win;
    	  
    	  Win.setLocation(x, y);
    	  
    	  JPanel choice = new JPanel();
    	  Win.add(choice, BorderLayout.NORTH);
    	  //text to let the player know they are victorious
    	  JLabel text = new JLabel();
    	  text.setText("<html><body><center>You have beaten the Game!<br>" +
    	  		"Your score is " + Float.toString(GameCourt.scoreTracker) + "<br>" +
    	  		"Would you like to play again?</center></body></html>");
    	  choice.add(text);
    	  //Restart and quit buttons
    	  JPanel buttons = new JPanel();
    	  Win.add(buttons, BorderLayout.SOUTH);
    	  JButton quit = new JButton("Quit");
    	  quit.addActionListener(new ActionListener() {
    		  public void actionPerformed(ActionEvent e) {
    			  currentPop.setVisible(false);
    			  currentPop.dispose();
    			  current.setVisible(false);
    			  current.dispose();
    			  GameCourt.scoreTracker = 0;
    			  Game.main(null);
    		  }
    	  });
    	  JButton replay = new JButton("Replay");
    	  replay.addActionListener(new ActionListener() {
    		  public void actionPerformed(ActionEvent e) {
    			  currentPop.setVisible(false);
    			  currentPop.dispose();
    			  current.setVisible(false);
    			  current.dispose();
    			  GameCourt.scoreTracker = 0;
    			  Game.levelToPlay = 1;
    			  Game.play(Game.levelToPlay);
    		  }
    	  });
    	  buttons.add(replay);
    	  buttons.add(quit);
    	  
    	  //put the frame on the screen
    	  Win.pack();
    	  Win.setLocationRelativeTo(null);
    	  Win.setVisible(true);
      }
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
