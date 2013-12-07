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
	private Boat boat;           // the user controlled boat
	private Pirate pirate;       // the Pirate (enemy), bounces...for now
	private LevelGate levelgate; // The Gate to the next level, doesn't move
	private Wind wind;           // the wind that affects the sailboat
	private Land land;			 // the land on the map
	
	private Treasure treasure;       // the treasure that must be collected in order to pass
								     // through the gate
	private boolean tIsCol = false;  // check if the treasure has been collected
	
	public boolean playing = false;  // whether the game is running
	private JLabel status;       // Current status text (i.e. Running...)
	
	// Game constants
	public static final int COURT_WIDTH = 800;
	public static final int COURT_HEIGHT = 800;
	public static final double BOAT_VELOCITY = 2;
	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 35; 
	
	//the background picture
	public static final String img_file = "seawater.jpg";
	public static BufferedImage img = null;

	public GameCourt(JLabel status){
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
		// moves the square.)
		addKeyListener(new KeyAdapter(){
			public void keyPressed(KeyEvent e){
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					if (Math.abs(boat.v_x) > BOAT_VELOCITY) {
						boat.v_x = .95 * boat.v_x;
					} else{
						boat.v_x = -BOAT_VELOCITY;
					}
//					boat.v_y = 0;
					boat.width = 20;
					boat.height = 10;
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					if (Math.abs(boat.v_x) > BOAT_VELOCITY) {
						boat.v_x = .95 * boat.v_x;
					} else{
						boat.v_x = BOAT_VELOCITY;
					}
//					boat.v_y = 0;
					boat.width = 20;
					boat.height = 10;
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					if (Math.abs(boat.v_y) > BOAT_VELOCITY) {
						boat.v_y = .95 * boat.v_y;
					} else{
						boat.v_y = BOAT_VELOCITY;
					}
//					boat.v_x = 0;
					boat.width = 10;
					boat.height = 20;
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP) {
					if (Math.abs(boat.v_y) > BOAT_VELOCITY) {
						boat.v_y = .95 * boat.v_y;
					} else{
						boat.v_y = -BOAT_VELOCITY;
					}
//					boat.v_x = 0;
					boat.width = 10;
					boat.height = 20;
				}
			}
			public void keyReleased(KeyEvent e){
//				boat.v_x = 0;
//				boat.v_y = 0;
			}
		});
		
		

		this.status = status;
	}

	/** (Re-)set the state of the game to its initial state.
	 */
	public void reset() {

		boat = new Boat(500, 100, COURT_WIDTH, COURT_HEIGHT);
		pirate = new Pirate(100, 300, 2, -1, COURT_WIDTH, COURT_HEIGHT);
		levelgate = new LevelGate(10, 10, COURT_WIDTH, COURT_HEIGHT);
		wind = new Wind(200, 400, 100, 100, -2, 0, COURT_WIDTH, COURT_HEIGHT);
		land = new Land(200, 0, 200, 400, COURT_WIDTH, COURT_HEIGHT);
		treasure = new Treasure(500, 500, COURT_WIDTH, COURT_HEIGHT);

		playing = true;
		status.setText("Running...");

		// Make sure that this component has the keyboard focus
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
//			if (boat.intersects(wind)) {
//				boat.v_x = boat.v_x + wind.v_x;
//				boat.v_y = boat.v_y + wind.v_y;
//			}
			
			//change the direction the boat faces
			
			
			
			
			
			boat.move();
			pirate.move();

			// make the pirate bounce off walls...
			pirate.bounce(pirate.hitWall());
			// ...and the levelgate
			pirate.bounce(pirate.hitObj(levelgate));
			// ...and land
			pirate.bounce(pirate.hitObj(land));
		
			// check for the game end conditions
			if (boat.intersects(levelgate)) { 
				playing = false;
				status.setText("Next Level!");

			} else if (boat.intersects(pirate)) {
				playing = false;
				status.setText("You Were Destoyed!");
			} else if (boat.intersects(land)) {
				playing = false;
				status.setText("You crashed");
			}
			
			//check if the boat has collected the treasure
			if (boat.intersects(treasure)) {
				tIsCol = true;
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
		pirate.draw(g);
		levelgate.draw(g);
		wind.draw(g);
		land.draw(g);
		boat.draw(g);
		if (!tIsCol) {
			treasure.draw(g);
		}
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}

	public void setBackground(BufferedImage img) {
		
	}
}
