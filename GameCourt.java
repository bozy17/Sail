/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.ArrayList;

/**
 * GameCourt
 * 
 * This class holds the primary game logic of how different objects 
 * interact with one another.  Take time to understand how the timer 
 * interacts with the different methods and how it repaints the GUI 
 * on every tick().
 *
 */
@SuppressWarnings("serial")
public class GameCourt extends JPanel {
	
	//the level that is being played
	private Level level;

	// the state of the game logic
	private Boat boat;               // the user controlled boat
	private Pirate pirate1;          // the Pirate (enemy), bounces...for now
	private Pirate pirate2;
	private Pirate pirate3;
	private CannonBall cannonball1;  //the pirates' weapon
	private CannonBall cannonball2;
	private CannonBall cannonball3;
	private LevelGate levelgate;     // The Gate to the next level, doesn't move
	private Wind wind;               // the wind that affects the sailboat
	private Land land;			     // the land on the map
	
	private Treasure treasure;       // the treasure that must be collected in order to pass
								     // through the gate
	private boolean tIsCol = false;  // check if the treasure has been collected
	private JLabel treasureCollected;
	
	private JLabel countdown;        //timer to keep the players score
	private float ellapsedTime;
	
	private int tickcounter = 0;     //count the number of ticks that have happened

	
	public boolean playing = false;  // whether the game is running
	public boolean won = false;
	private JLabel status;           // Current status text (i.e. Running...)
	
	// Game constants
	private int COURT_WIDTH = 800;
	private int COURT_HEIGHT = 800;
	public static final double BOAT_VELOCITY = 3;
	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 35; 
	
	//the background picture
	public static final String img_file = "seawater.jpg";
	public static BufferedImage img = null;

	public GameCourt(JLabel treasureCollected, JLabel countdown,
			Level level){
		this.level = level;
		
		// creates border around the court area, JComponent method
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		
        // The timer is an object which triggers an action periodically
        // with the given INTERVAL. One registers an ActionListener with
        // this timer, whose actionPerformed() method will be called 
        // each time the timer triggers. We define a helper method
        // called tick() that actually does everything that should
        // be done in a single timestep.
		Timer timer = new Timer(INTERVAL, new ActionListener(){
			public void actionPerformed(ActionEvent e){
				tick();
			}
		});
		timer.start(); // MAKE SURE TO START THE TIMER!

		// Enable keyboard focus on the court area
		// When this component has the keyboard focus, key
		// events will be handled by its key listener.
		setFocusable(true);

		// this key listener allows the square to move as long
		// as an arrow key is pressed, by changing the square's
		// velocity accordingly. (The tick method below actually 
		// moves the boat.)
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					boat.v_x = -BOAT_VELOCITY;
					boat.v_y = 0;
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					boat.v_x = BOAT_VELOCITY;
					boat.v_y = 0;
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					boat.v_y = BOAT_VELOCITY;
					boat.v_x = 0;
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP) {
					boat.v_y = -BOAT_VELOCITY;
					boat.v_x = 0;
				} 
			}
			public void keyReleased(KeyEvent e){

			}
		});
		
		

		this.treasureCollected = treasureCollected;
		this.countdown = countdown;
	}

	/** (Re-)set the state of the game to its initial state.
	 */
	public void reset() {

		boat = level.boat;
		pirate1 = level.pirate1;
		pirate2 = level.pirate2;
		pirate3 = level.pirate3;
		levelgate = level.levelgate;
		wind = level.wind;
		land = level.land;
		treasure = level.treasure;
		cannonball1 = null;
		cannonball2 = null;
		cannonball3 = null;

		playing = true;
		treasureCollected.setText("Treasure Collected: 0");
		countdown.setText(Float.toString(ellapsedTime));
		ellapsedTime = level.ellapsedTime;
		tickcounter = 0;

		// Make sure that this component has the .keyboard focus
		requestFocusInWindow();
	}

    /**
     * This method is called every time the timer defined
     * in the constructor triggers.
     */
	void tick(){
		if (playing) {
			// advance the boat and pirate in their
			// current direction.
			if (boat.intersects(wind)) {
				boat.v_x = wind.v_x;
				boat.v_y = wind.v_y;
			}
			
			//change the direction the boat faces
			if (boat.v_x != 0) {
				boat.width = 20;
				boat.height = 10;
			} else {
				boat.width = 10;
				boat.height = 20;
			}
			
			
			//make the countdown start and end
			ellapsedTime -= .035;
			countdown.setText(Float.toString(ellapsedTime) + " sec");
			if (ellapsedTime <= 0) {
				playing = false;
				countdown.setText("0 sec");
			}
			
			boat.move();
			if (pirate1 != null) {
				pirate1.move();
			}
			if (pirate2 != null) {
				pirate2.move();
			}
			if (pirate3 != null) {
				pirate3.move();
			}
			
			//make the pirate shoot every 5 seconds (142 ticks)
			tickcounter++;
			if (tickcounter == 142) {
				if (pirate1 != null) {
					cannonball1 = new CannonBall(pirate1.pos_x, pirate1.pos_y,
							(boat.pos_x - pirate1.pos_x)/100, 
							(boat.pos_y - pirate1.pos_y)/100, 
							COURT_WIDTH, COURT_HEIGHT);
				}
				if (pirate2 != null) {
					cannonball2 = new CannonBall(pirate2.pos_x, pirate2.pos_y,
							(boat.pos_x - pirate2.pos_x)/100, 
							(boat.pos_y - pirate2.pos_y)/100, 
							COURT_WIDTH, COURT_HEIGHT);
				}
				if (pirate3 != null) {
					cannonball3 = new CannonBall(pirate3.pos_x, pirate3.pos_y,
							(boat.pos_x - pirate3.pos_x)/100, 
							(boat.pos_y - pirate3.pos_y)/100, 
							COURT_WIDTH, COURT_HEIGHT);
				}
				tickcounter = 0;
			}
			//this is necessary because the cannonball is not always present
			if (cannonball1 != null) {
				cannonball1.move();
				if (boat.intersects(cannonball1)) {
					playing = false;
				}
			}
			if (cannonball2 != null) {
				cannonball2.move();
				if (boat.intersects(cannonball2)) {
					playing = false;
				}
			}
			if (cannonball3 != null) {
				cannonball3.move();
				if (boat.intersects(cannonball3)) {
					playing = false;
				}
			}

			if (pirate1 != null) {
				// make the pirate bounce off walls...
				pirate1.bounce(pirate1.hitWall());
				// ...and the levelgate
				pirate1.bounce(pirate1.hitObj(levelgate));
				// ...and land
				pirate1.bounce(pirate1.hitObj(land));
			}
			
			if (pirate2 != null) {
				//make one of the pirates loop
				if (pirate2.pos_y == 100) {
					pirate2.v_y = 2;
				} else if (pirate2.pos_y == 700) {
					pirate2.v_y = -2;
				}
			}
			
			// check for the game end conditions
			if (boat.intersects(levelgate) && tIsCol) { 
				playing = false;
				won = true;
				Game.levelToPlay ++;
				
	        	//bring up a panel that allows the user to quit or go to the next level
				JFrame levelChange = new JFrame("Level Select");
				
				//center the screen
				Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
				int w = levelChange.getSize().width;
		  	  	int h = levelChange.getSize().height;
		  	  	int x = (dim.width - w) / 2;
		  	  	int y = (dim.height - h) / 2;
		  	  	levelChange.setLocation(x, y);
				
				JPanel choiceText = new JPanel();
				levelChange.add(choiceText, BorderLayout.NORTH);
				//text to let the player know they won
				JLabel text = new JLabel();
				text.setText("You are Victorious!  Continue by clicking next.");
				choiceText.add(text);
				//continue and quit buttons
				JPanel choiceButtons = new JPanel();
				levelChange.add(choiceButtons, BorderLayout.SOUTH);
				JButton quit = new JButton("Quit");
				quit.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Game.main(null);
					}
				});
				JButton next = new JButton("Next");
				next.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						Game.play(Game.levelToPlay);
					}
				});
				choiceButtons.add(next);
				choiceButtons.add(quit);
				
				//put the levelChange on the screen
				levelChange.pack();
				levelChange.setLocationRelativeTo(null);
				levelChange.setVisible(true);
		        
			} else if (boat.intersects(levelgate)) {
			} else if (pirate1 != null &&boat.intersects(pirate1)) {
				playing = false;
			} else if (pirate2 != null && boat.intersects(pirate2)) {
				playing = false;
			} else if (pirate3 != null && boat.intersects(pirate3)) {
				playing = false;
			} else if (boat.intersects(land)) {
				playing = false;
			} 
			
			//check if the boat has collected the treasure
			if (boat.intersects(treasure)) {
				tIsCol = true;
				treasureCollected.setText("Treasure Collected: 1");
			}
			
			// update the display
			repaint();
		} 
	}

	
	//only repaints the things that are being updated
	@Override 
	public void paintComponent(Graphics g){
		try {
			if (img == null) {
				img = ImageIO.read(new File(img_file));
			} 
		} catch (IOException e) {
				System.out.println("Internal Error: " + e.getMessage());
		}
		g.drawImage(img, 0, 0, null);
		
		//draw the images, checking if they are null
		if (levelgate != null) {
			levelgate.draw(g);
		}
		if (pirate1 != null) {
			pirate1.draw(g);
		}
		if (pirate2 != null) {
			pirate2.draw(g);
		}
		if (wind != null) {
			wind.draw(g);
		}
		if (land != null) {
			land.draw(g);
		}
		if (boat != null) {
			boat.draw(g);
		}
		if (!tIsCol && treasure != null) {
			treasure.draw(g);
		}
		if (cannonball1 != null) {
			cannonball1.draw(g);
		}
		if (cannonball2 != null) {
			cannonball2.draw(g);
		}
		if (cannonball3 != null) {
			cannonball3.draw(g);
		}
		
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}
}
