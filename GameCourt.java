/**
 * CIS 120 HW10
 * (c) University of Pennsylvania
 * @version 2.0, Mar 2013
 */

import java.awt.*;
import java.awt.event.*;
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
	
	public boolean playing = false;  // whether the game is running
	private JLabel status;       // Current status text (i.e. Running...)
	
	public boolean keyPressed = false; //check if the user is pressing an arrow key

	// Game constants
	public static final int COURT_WIDTH = 600;
	public static final int COURT_HEIGHT = 600;
	public static final double BOAT_VELOCITY = 2;
	// Update interval for timer in milliseconds 
	public static final int INTERVAL = 35; 

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
					boat.v_x = -BOAT_VELOCITY;
//					boat.v_y = 0;
					boat.width = 20;
					boat.height = 10;
					keyPressed = true;
				}
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					boat.v_x = BOAT_VELOCITY;
//					boat.v_y = 0;
					boat.width = 20;
					boat.height = 10;
					keyPressed = true;
				}
				else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
					boat.v_y = BOAT_VELOCITY;
//					boat.v_x = 0;
					boat.width = 10;
					boat.height = 20;
					keyPressed = true;
				}
				else if (e.getKeyCode() == KeyEvent.VK_UP) {
					boat.v_y = -BOAT_VELOCITY;
//					boat.v_x = 0;
					boat.width = 10;
					boat.height = 20;
					keyPressed = true;
				}
			}
			public void keyReleased(KeyEvent e){
				boat.v_x = 0;
				boat.v_y = 0;
				keyPressed = false;
			}
		});
		
		

		this.status = status;
	}

	/** (Re-)set the state of the game to its initial state.
	 */
	public void reset() {

		boat = new Boat(COURT_WIDTH, COURT_HEIGHT);
		pirate = new Pirate(100, 300, 2, -1, COURT_WIDTH, COURT_HEIGHT);
		levelgate = new LevelGate(COURT_WIDTH, COURT_HEIGHT);
		wind = new Wind(200, 300, 20, 20, COURT_WIDTH, COURT_HEIGHT);
		land = new Land(COURT_WIDTH, COURT_HEIGHT);

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
			// current direction. Slows gradually to 1
//			if (Math.abs(boat.v_x) > 1) {
//				boat.v_x = .95 * boat.v_x;
//			}
//			if (Math.abs(boat.v_y) > 1) {
//				boat.v_y = .95 * boat.v_y;
//			}
			if (boat.intersects(wind)) {
				boat.v_x = boat.v_x + wind.v_x;
				boat.v_y = boat.v_y + wind.v_y;
			}
			
			boat.move();
			pirate.move();

			// make the pirate bounce off walls...
			pirate.bounce(pirate.hitWall());
			// ...and the levelgate
			pirate.bounce(pirate.hitObj(levelgate));
		
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
			
			// update the display
			repaint();
		} 
	}

	@Override 
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		pirate.draw(g);
		levelgate.draw(g);
		wind.draw(g);
		land.draw(g);
		boat.draw(g);
	}

	@Override
	public Dimension getPreferredSize(){
		return new Dimension(COURT_WIDTH,COURT_HEIGHT);
	}
}
