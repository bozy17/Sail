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

/** 
 * Game
 * Main class that specifies the frame and widgets of the GUI
 */
public class Game implements Runnable {
	
    public void run(){
        // NOTE : recall that the 'final' keyword notes inmutability
		  // even for local variables. 

        // Top-level frame in which game components live
		  // Be sure to change "TOP LEVEL FRAME" to the name of your game
        final JFrame frame = new JFrame("Sail");
        frame.setLocation(300,100);

		// Status panel
        JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        JLabel status = new JLabel("Sailing...                      ");
        status_panel.add(status);
        // treasure collected
        JLabel treasureCollected = new JLabel("Treasure Collected: 0                      ");
        status_panel.add(treasureCollected);
        
        // Score panel 
        JPanel score_panel = new JPanel();
        frame.add(score_panel, BorderLayout.NORTH);
        // countdown
        JLabel countdown = new JLabel("30");
        score_panel.add(countdown);

        // Main playing area
        final GameCourt court = new GameCourt(status, treasureCollected,
        		countdown);
        frame.add(court, BorderLayout.CENTER);
        
       //reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		court.reset();
        	}
        });
        status_panel.add(reset);


        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start game
        court.reset();
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
