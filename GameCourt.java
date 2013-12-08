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

	// the state of the game logic
	private Boat boat;               // the user controlled boat
//	private CannonBall boatCannon;   //the players cannon
	private Pirate pirate1;          // the Pirate (enemy), bounces...for now
	private Pirate pirate2;
//	private boolean destroyed1 = false;  //for the possibility of a player cannon
//	private boolean destroyed2 = false;
	private CannonBall cannonball1;  //the pirate's weapon
	private CannonBall cannonball2;
	private LevelGate levelgate;     // The Gate to the next level, doesn't move
	private Wind wind;               // the wind that affects the sailboat
	private Land land;			     // the land on the map
	
	private Treasure treasure;       // the treasure that must be collected in order to pass
								     // through the gate
	private boolean tIsCol = false;  // check if the treasure has been collected
	private JLabel treasureCollected;
	
	private JLabel countdown;        //timer to keep the players score
	private float ellapsedTime = 30;
	
	private int tickcounter = 0;     //count the number of ticks that have happened

	
	public boolean playing = false;  // whether the game is running
	private JLabel status;           // Current status text (i.e. Running...)
	
	// Game constants
	public static final int COURT_WIDTH = 800;
	public static final int COURT_HEIGHT = 800;
	public static final double BOAT_VELOCITY = 3;
	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 35; 
	
	//the background picture
	public static final String img_file = "seawater.jpg";
	public static BufferedImage img = null;

	public GameCourt(JLabel status, JLabel treasureCollected, final JLabel countdown){
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
				// for the possibility of a player cannon
//				else if (e.getKeyCode() == KeyEvent.VK_SPACE){
//					boatCannon = new CannonBall(boat.pos_x, boat.pos_y, boat.v_x,
//							boat.v_y, COURT_WIDTH, COURT_HEIGHT);
//				}
			}
			public void keyReleased(KeyEvent e){

			}
		});
		
		

		this.status = status;
		this.treasureCollected = treasureCollected;
		this.countdown = countdown;
	}

	/** (Re-)set the state of the game to its initial state.
	 */
	public void reset() {

		boat = new Boat(700, 100, COURT_WIDTH, COURT_HEIGHT);
		pirate1 = new Pirate(100, 300, 2, -1, COURT_WIDTH, COURT_HEIGHT);
		pirate2 = new Pirate(700, 700, 0, -2, COURT_WIDTH, COURT_HEIGHT);
		levelgate = new LevelGate(10, 10, COURT_WIDTH, COURT_HEIGHT);
		wind = new Wind(300, 501, 200, 100, 2, 0, COURT_WIDTH, COURT_HEIGHT);
		land = new Land(300, 1, 200, 500, COURT_WIDTH, COURT_HEIGHT);
		treasure = new Treasure(600, 700, COURT_WIDTH, COURT_HEIGHT);
		cannonball1 = null;
		cannonball2 = null;

		playing = true;
		status.setText("Sailing...                      ");
		treasureCollected.setText("Treasure Collected: 0                      ");
		countdown.setText("30 sec");
		ellapsedTime = 30;
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
			pirate1.move();
			pirate2.move();
			
			//make the pirate shoot every 5 seconds (142 ticks)
			tickcounter++;
			if (tickcounter == 142) {
				cannonball1 = new CannonBall(pirate1.pos_x, pirate1.pos_y,
						(boat.pos_x - pirate1.pos_x)/100, 
						(boat.pos_y - pirate1.pos_y)/100, 
						COURT_WIDTH, COURT_HEIGHT);
				cannonball2 = new CannonBall(pirate2.pos_x, pirate2.pos_y,
						(boat.pos_x - pirate2.pos_x)/100, 
						(boat.pos_y - pirate2.pos_y)/100, 
						COURT_WIDTH, COURT_HEIGHT);
				tickcounter = 0;
			}
			//this is necessary because the cannonball is not always present
			if (cannonball1 != null) {
				cannonball1.move();
				if (boat.intersects(cannonball1)) {
					playing = false;
					status.setText("You Were Destroyed!                      ");
				}
			}
			if (cannonball2 != null) {
				cannonball2.move();
				if (boat.intersects(cannonball2)) {
					playing = false;
					status.setText("You Were Destroyed!                      ");
				}
			}
			// for the possibility of a player cannon
//			if (boatCannon != null) {
//				boatCannon.move();
//				if (boatCannon.intersects(pirate1)) {
//					destroyed1 = true;
//					boatCannon = null;
//				}
//				if (boatCannon.intersects(pirate2)) {
//					destroyed2 = true;
//					boatCannon = null;
//				}
//			}

			// make the pirate bounce off walls...
			pirate1.bounce(pirate1.hitWall());
			// ...and the levelgate
			pirate1.bounce(pirate1.hitObj(levelgate));
			// ...and land
			pirate1.bounce(pirate1.hitObj(land));
			
			//make one of the pirates loop
			if (pirate2.pos_y == 100) {
				pirate2.v_y = 2;
			} else if (pirate2.pos_y == 700) {
				pirate2.v_y = -2;
			}
			
			// check for the game end conditions
			if (boat.intersects(levelgate) && tIsCol) { 
				playing = false;
				status.setText("Next Level!                      ");
			} else if (boat.intersects(levelgate)) {
				status.setText("You must collect the treasure to pass" +
						"through the level gate!                      ");
			} else if (boat.intersects(pirate1) || boat.intersects(pirate2)) {
				playing = false;
				status.setText("You Collided with a Pirate!                      ");
			} else if (boat.intersects(land)) {
				playing = false;
				status.setText("You crashed                      ");
			} 
			
			//check if the boat has collected the treasure
			if (boat.intersects(treasure)) {
				tIsCol = true;
				treasureCollected.setText("Treasure Collected: 1                      ");
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
		levelgate.draw(g);
		pirate1.draw(g);
		pirate2.draw(g);
		wind.draw(g);
		land.draw(g);
		boat.draw(g);
		if (!tIsCol) {
			treasure.draw(g);
		}
		if (cannonball1 != null) {
			cannonball1.draw(g);
		}
		if (cannonball2 != null) {
			cannonball2.draw(g);
		}
		//draw the boat cannonball if not null
//		if (boatCannon != null) {
//			boatCannon.draw(g);
//		}
		//draw the pirates if they haven't been destroyed
//		if (!destroyed1) {
//			pirate1.draw(g);
//		} else {cannonball1 = null;}
//		if (!destroyed2) {
//			pirate2.draw(g);
//		} else {cannonball2 = null;}
		
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}
}
