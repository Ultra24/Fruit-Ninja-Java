// Import all packages
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;

public class Main extends JPanel implements ActionListener, MouseListener, MouseMotionListener {

	/* Declaring the JFrame object to display the frame */
	public JFrame frame;

	/* Creating the Variables object to initialize all required variables */
	public Variables vars = new Variables();

	/* Creates the constructor to initiliaze variables and frame */
	public Main() {

		/* Creating the JFrame and initializing its properties */
		frame = new JFrame();
		frame.setSize(460, 306);
		frame.setTitle("Fruit Ninja");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setContentPane(this);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);

		/* Initializing the big font (fBig) using try-catch */
		try {

			/* Using the "Gang of Three" font from the folder */
			vars.fBig = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/gang-of-three.ttf")).deriveFont(70f);

			/* Creating the GraphicsEnvironment variable */
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			/* Registering the font for the GraphicsEnvironment variable */
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/gang-of-three.ttf")));

			/* Initializing the FontMetrics object */
			FontMetrics fm = getFontMetrics(vars.fBig);
		} 

		/* Leaving the catch scenario empty */
		catch (Exception e) {}	

		/* Initializing the medium font (fMed) using try-catch */
		try {

			/* Using the "Gang of Three" font from the folder */
			vars.fMed = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/gang-of-three.ttf")).deriveFont(40f);

			/* Creating the GraphicsEnvironment variable */
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			/* Registering the font for the GraphicsEnvironment variable */
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/gang-of-three.ttf")));

			/* Initializing the FontMetrics object */
			FontMetrics fm = getFontMetrics(vars.fMed);
		} 

		/* Leaving the catch scenario empty */
		catch (Exception e) {}	

		/* Initializing the large font (fLarge) using try-catch */
		try {

			/* Using the "Gang of Three" font from the folder */
			vars.fLarge = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/gang-of-three.ttf")).deriveFont(120f);

			/* Creating the GraphicsEnvironment variable */
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			/* Registering the font for the GraphicsEnvironment variable */
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/gang-of-three.ttf")));

			/* Initializing the FontMetrics object */
			FontMetrics fm = getFontMetrics(vars.fLarge);
		} 

		/* Leaving the catch scenario empty */
		catch (Exception e) {}

		/* Initializing the small font (fSmall) using try-catch */
		try {

			/* Using the "Gang of Three" font from the folder */
			vars.fSmall = Font.createFont(Font.TRUETYPE_FONT, new File("fonts/gang-of-three.ttf")).deriveFont(30f);

			/* Creating the GraphicsEnvironment variable */
			GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

			/* Registering the font for the GraphicsEnvironment variable */
			ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("fonts/gang-of-three.ttf")));

			/* Initializing the FontMetrics object */
			FontMetrics fm = getFontMetrics(vars.fSmall);
		} 

		/* Leaving the catch scenario empty */
		catch (Exception e) {}


		/* Initializing the background music for the main menu using try-catch */
		try {

			/* Initializing the file object for the music */
			File menuTheme = new File("sfx/theme.wav");
			File shopTheme = new File("sfx/Music-dojo.wav");
			File gameTheme = new File("sfx/senseiDojo.wav");

			/* Opening an audio input stream for the music */
			AudioInputStream audioIn2 = AudioSystem.getAudioInputStream(menuTheme);
			AudioInputStream audioIn3 = AudioSystem.getAudioInputStream(shopTheme);
			AudioInputStream audioIn4 = AudioSystem.getAudioInputStream(gameTheme);

			/* Retrieving and opening the audio clip from the newly-created stream */
			vars.bg = AudioSystem.getClip();
			vars.bg.open(audioIn2);

			/* Retrieving and opening the audio clip from the newly-created stream */
			vars.dg = AudioSystem.getClip();
			vars.dg.open(audioIn3);

			/* Retrieving and opening the audio clip from the newly-created stream */
			vars.gt = AudioSystem.getClip();
			vars.gt.open(audioIn4);

			/* Starting the background music for the main menu */
			vars.bg.start();
			vars.bg.loop(vars.bg.LOOP_CONTINUOUSLY);
		}

		/* Leaving the catch scenario empty */
		catch (Exception e) { }

		/* Required methods for MouseListener & MouseMotionListener */
		setLayout(null);
		setFocusable(true);
		requestFocus();
		addMouseListener(this);
		addMouseMotionListener(this);

		/* Initializing all of the required Timers */
		vars.startGameTimer = new Timer(700, this);
		vars.fruitMoveTimer = new Timer(25, this);
		vars.titleTimer = new Timer(1000, this);
		vars.xPopupTimer = new Timer(500, this);
		vars.movementTimer = new Timer(25, this);

		/* Starting the title screen timer */
		vars.titleTimer.start();	
	}

	/* The main method where the initial code is executed */
	public static void main(String[] args) {

		/* Call the main constructor */
		new Main();
	}

	/* The actionPerformed method updates the logic for the paint method */
	public void actionPerformed(ActionEvent e) {

		/* Checking if the starting (title) timer's interval has elapsed */
		if (e.getSource() == vars.titleTimer) {

			/* Decrementing the starting (title) timer by 1 second */
			vars.titleCount -= 1;
		}

		/* Checking if the movement timer's interval has elapsed */
		if (e.getSource() == vars.movementTimer) {

			/* Incrementing the movement delay by 1 */
			vars.moveDelay++; 

			// Delay the movement of the ninja until 10 counts of the 25 ms timer (0.25 seconds) so that the user can read the text
			if (vars.moveDelay >= 10) {

				/* Setting the ninja to begin moving right */
				vars.right = true;
			}

			/* Checking if the ninja is set to move right */
			if (vars.right) {	

				// Rotates between two images every count for more accurate walk animation
				if (vars.moveDelay % 2 == 0) {

					/* Displays the first ninja walk image */
					vars.ninjaWalkImg = new ImageIcon("images/ninja-walk.png");
				} 

				else {

					/* Displays the second ninja walk image */
					vars.ninjaWalkImg = new ImageIcon("images/ninja-walk-2.png");
				}

				/* Increasing the X-position of the ninja by 10px */
				vars.ninjaXPos += 10;

				// Updating the hitboxes for both the ninja and blade
				vars.ninjaHitBox = new Rectangle2D.Double(vars.ninjaXPos, 350, 120, 160);
				vars.bladeHitBox = new Rectangle2D.Double(920, 340, 125, 125);

				/* Checking if the two rectangle masks intersect with each other */
				// The !vars.collided condition makes it so that the JOptionPane only shows up once
				if (vars.ninjaHitBox.intersects(vars.bladeHitBox) && !vars.collided) {

					/* Display the message to the user which awards them the blade */
					JOptionPane.showMessageDialog(null, "Nicely done, " + vars.name + "!\nGo forth with this prestigious blade.\nPractice the art of Fruit Ninjutsu by slicing fruit.", vars.name.substring(0,1).toUpperCase() + vars.name.substring(1) + " acquires the Blade!", JOptionPane.INFORMATION_MESSAGE, vars.basicBladeLogo);			  	

					// Set to true so the if statement won't run again (JOptionPane won't show again)
					vars.collided = true;

					/* Incrementing the story level by 1 */
					vars.storyLevel += 1;
				}

				/* Repainting the frame's components */
				repaint();
			}
		}

		/* Checking if the starting (title) timer is complete */
		if (vars.titleCount < 0) {

			/* Stopping the starting (title) timer and repainting the frame */
			vars.titleTimer.stop();
			repaint();

			/* Checking if the name phase is not complete */
			if (!vars.namePhase) {

				/* Create an infinite while-loop for name checking */
				while (true) {

					/* Ask the user for their name */
					vars.name = (String) JOptionPane.showInputDialog(null, "Welcome to my Dojo! \nWhat is your name, young one?", "Fruit Ninja", JOptionPane.PLAIN_MESSAGE, vars.senseiCoconut, null, null);

					/* Checking if the name value is null (cancel is pressed) */
					if (vars.name == null) {

						/* Set the name to a placeholder */
						vars.name = "Ninja Noob";

						/* Resizing and reecentering the frame for the main background */
						frame.setSize(1000, 800);
						frame.setLocationRelativeTo(null);
					}

					/* Check if the length of the name is greater than 10 characters */
					if (vars.name.length() > 10) {

						/* Notify the user that their selected name must be shortened */
						JOptionPane.showMessageDialog(null, "Your name must be less than \n10 characters long, young one!", "Fruit Ninja", JOptionPane.PLAIN_MESSAGE, vars.senseiCoconut);
					}

					/* Check if the length of the name is less than 3 characters */
					else if (vars.name.length() < 3) {

						/* Notify the user that their selected name must be increased in characters */
						JOptionPane.showMessageDialog(null, "Your name must be at least 3 characters long, young one!", "Fruit Ninja", JOptionPane.PLAIN_MESSAGE, vars.senseiCoconut);
					}

					/* Checking the default condition (if each if-statement is passed) */
					else {

						/* Exit the infinite while-loop */
						break;
					}
				}

				/* Initializing the FontMetrics object */
				FontMetrics fm = getFontMetrics(vars.fMed);

				/* Storing the width of the name in nameWidth */
				vars.nameWidth = fm.stringWidth("Welcome, " + vars.name);

				/* Set the name phase to complete */
				vars.namePhase = true;
			}

			/* Resizing and reecentering the frame for the main background */
			frame.setSize(1000, 800);
			frame.setLocationRelativeTo(null);
		}

		/* Checking if the ready phase is not complete */
		if (vars.readyStart && vars.arcadeMode) {

			/* Decrementing the starting game timer by 1 second */
			vars.startGameSec--;

			/* Checking if the starting game timer is complete */
			if (vars.startGameSec < 0) {

				/* Stopping the game timer and starting the projectile (fruit) timer */
				vars.startGameTimer.stop();
				vars.fruitMoveTimer.start();
			} 

			// If in the "ready, go" phase, play a sound effect for the game starting
			else if (vars.startGameSec == 2 && vars.arcadeMode) {

				/* Initializing the "ready, go" music for the arcade mode using try-catch */
				try {

					/* Initializing the file object for the music */
					File startSfx = new File("sfx/Game-start.wav");

					/* Opening an audio input stream for the music */
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(startSfx);

					/* Retrieving and opening the audio clip from the newly-created stream */
					vars.startSfx = AudioSystem.getClip();
					vars.startSfx.open(audioIn);

					vars.startSfx.start();
				} 

				catch (Exception e1) {} // "e" paramater not possible, because ActionPerformer method already uses "e" paramater

			}

			/* Checking if the projectile (fruit) timer's interval has elapsed */
			if (e.getSource() == vars.fruitMoveTimer) {

				/* Checks for the lose condition (if there are no lives remaining) */
				if (vars.livesRemaining <= 0) {

					/* Stopping the background music for the game modes */
					vars.gt.stop();

					// Game over sfx try-catch
					try { // Open an audio input stream.
						File gameOver = new File("sfx/Game-over.wav");
						AudioInputStream audioIn = AudioSystem.getAudioInputStream(gameOver);

						vars.gameOverSfx = AudioSystem.getClip();
						vars.gameOverSfx.open(audioIn);
						vars.gameOverSfx.start();

					} catch (Exception e2) {} // "e" paramater not possible, because ActionPerformer method already uses "e" paramater

					/* Restarts the game mode */
					restartGame(true);

					/* Starting the background music for the main menu */
					vars.bg.start();
					vars.bg.loop(vars.bg.LOOP_CONTINUOUSLY);
				}

				/* Checking if the melon is supposed to move upwards */
				if (vars.melonUp) {

					/* Checking if the Y-position of the melon is greater than 250px */
					if (vars.yPosMelon > (int)(Math.random() * 41 + 230)) {

						/* Decreasing the Y-position of the melon by 10px to move upwards */
						vars.yPosMelon -= 10;
					} 

					/* Checking if the Y-position of the melon reaches 250px */
					if (vars.yPosMelon <= (int)(Math.random() * 41 + 230)) {

						/* Set melonUp boolean to false (begins to move downwards) */
						vars.melonUp = false;
					}

					/* Update each of the melon's Rectangle2D masks */
					vars.melonBox = new Rectangle2D.Double(vars.xPosMelon, vars.yPosMelon, 125, 125);
				}

				/* Checking if the melon is supposed to move downwards */
				else if (!vars.melonUp) {

					/* Checking if the Y-position of the melon is equal to 810px */
					if (vars.yPosMelon == 810) {

						/* Set the melon to begin moving upwards */
						vars.melonUp = true;

						/* Reset the X position of the melon to make it appear randomized */
						vars.xPosMelon = vars.rand.nextInt(700) + 50;
					}

					/* Checking if the Y-position of the melon is greater than or equal to 800px */
					else if (vars.yPosMelon >= 800) {

						// Set the drawing for the fruit splatter to not occur
						vars.drawMelonSplatter = false;

						/* Set the melon to begin moving upwards */
						vars.melonUp = true;

						/* Reset the X and Y-positions of the melon to make it appear at the bottom */
						vars.yPosMelon = 800;
						vars.xPosMelon = vars.rand.nextInt(700) + 50;

						/* Decrement the lives remaining by 1 */
						vars.livesRemaining -= 1;
					}

					/* Increasing the Y-position of the melon by 10px to move downwards */
					vars.yPosMelon += 10;

					/* Update each of the melon's Rectangle2D masks */
					vars.melonBox = new Rectangle2D.Double(vars.xPosMelon, vars.yPosMelon, 125, 125);
				}

				/* Checking if the pineapple is supposed to move upwards */
				if (vars.pineappleUp) {

					/* Checking if the Y-position of the pineapple is greater than 250px */
					if (vars.yPosPineapple > (int)(Math.random() * 41 + 230)) {

						/* Decreasing the Y-position of the pineapple by 10px to move upwards */
						vars.yPosPineapple -= 10;
					} 

					/* Checking if the Y-position of the pineapple reaches 250px */
					if (vars.yPosPineapple <= (int)(Math.random() * 41 + 230)) {

						/* Set pineappleUp boolean to false (begins to move downwards) */
						vars.pineappleUp = false;
					}

					/* Update each of the pineapple's Rectangle2D masks */
					vars.pineappleBox = new Rectangle2D.Double(vars.xPosPineapple, vars.yPosPineapple, 125, 125);
				}

				/* Checking if the pineapple is supposed to move downwards */
				else if (!vars.pineappleUp) {

					/* Checking if the Y-position of the pineapple is equal to 810px */
					if (vars.yPosPineapple == 810) {

						/* Set the pineapple to begin moving upwards */
						vars.pineappleUp = true;

						/* Reset the X position of the pineapple to make it appear randomized */
						vars.xPosPineapple = vars.rand.nextInt(700) + 50;
					}

					/* Checking if the Y-position of the pineapple is greater than or equal to 800px */
					else if (vars.yPosPineapple >= 800) {

						// Set the drawing for the fruit splatter to not occur
						vars.drawPineappleSplatter = false;

						/* Set the pineapple to begin moving upwards */
						vars.pineappleUp = true;

						/* Reset the X and Y-positions of the pineapple to make it appear at the bottom */
						vars.yPosPineapple = 800;
						vars.xPosPineapple = vars.rand.nextInt(700) + 50;

						/* Decrement the lives remaining by 1 */
						vars.livesRemaining -= 1;
					}

					/* Increasing the Y-position of the pineapple by 10px to move downwards */
					vars.yPosPineapple += 10;

					/* Update each of the pineapple's Rectangle2D masks */
					vars.pineappleBox = new Rectangle2D.Double(vars.xPosPineapple, vars.yPosPineapple, 125, 125);
				}

				// Checking if the tri-fruit powerup is not enabled
				if (!vars.onTriFruit) {

					/* Checking if the coconut is supposed to move upwards */
					if (vars.coconutUp) {

						/* Checking if the Y-position of the coconut is greater than 250px */
						if (vars.yPosCoconut > (int)(Math.random() * 41 + 230)) {

							/* Decreasing the Y-position of the coconut by 10px to move upwards */
							vars.yPosCoconut -= 10;
						} 

						/* Checking if the Y-position of the coconut reaches 250px */
						if (vars.yPosCoconut <= (int)(Math.random() * 41 + 230)) {

							/* Set coconutUp boolean to false (begins to move downwards) */
							vars.coconutUp = false;
						}

						/* Update each of the coconut's Rectangle2D masks */
						vars.coconutBox = new Rectangle2D.Double(vars.xPosCoconut, vars.yPosCoconut, 125, 125);
					}

					/* Checking if the coconut is supposed to move downwards */
					else if (!vars.coconutUp) {

						// Set the drawing for the fruit splatter to not occur
						vars.drawCoconutSplatter = false;

						/* Checking if the Y-position of the coconut is equal to 810px */
						if (vars.yPosCoconut == 810) {

							/* Set the coconut to begin moving upwards */
							vars.coconutUp = true;

							/* Reset the X position of the coconut to make it appear randomized */
							vars.xPosCoconut = vars.rand.nextInt(700) + 50;
						}

						/* Checking if the Y-position of the coconut is greater than or equal to 800px */
						else if (vars.yPosCoconut >= 800) {

							// Set the drawing for the fruit splatter to not occur
							vars.drawCoconutSplatter = false;

							/* Set the coconut to begin moving upwards */
							vars.coconutUp = true;

							/* Reset the X and Y-positions of the coconut to make it appear at the bottom */
							vars.yPosCoconut = 800;
							vars.xPosCoconut = vars.rand.nextInt(700) + 50;

							/* Decrement the lives remaining by 1 */
							vars.livesRemaining -= 1;
						}

						/* Increasing the Y-position of the coconut by 10px to move downwards */
						vars.yPosCoconut += 10;

						/* Update each of the coconut's Rectangle2D masks */
						vars.coconutBox = new Rectangle2D.Double(vars.xPosCoconut, vars.yPosCoconut, 125, 125);
					}
				}

				/* Checking if the mango is supposed to move upwards */
				if (vars.mangoUp) {

					/* Checking if the Y-position of the mango is greater than 250px */
					if (vars.yPosMango > (int)(Math.random() * 41 + 230)) {

						/* Decreasing the Y-position of the mango by 10px to move upwards */
						vars.yPosMango -= 10;
					} 

					/* Checking if the Y-position of the mango reaches 250px */
					if (vars.yPosMango <= (int)(Math.random() * 41 + 230)) {

						/* Set mangoUp boolean to false (begins to move downwards) */
						vars.mangoUp = false;
					}

					/* Update each of the mango's Rectangle2D masks */
					vars.mangoBox = new Rectangle2D.Double(vars.xPosMango, vars.yPosMango, 125, 125);
				}

				/* Checking if the mango is supposed to move downwards */
				else if (!vars.mangoUp) {

					/* Checking if the Y-position of the mango is equal to 810px */
					if (vars.yPosMango == 810) {

						/* Set the mango to begin moving upwards */
						vars.mangoUp = true;

						/* Reset the X position of the mango to make it appear randomized */
						vars.xPosMango = vars.rand.nextInt(700) + 50;
					}

					/* Checking if the Y-position of the mango is greater than or equal to 800px */
					else if (vars.yPosMango >= 800) {

						// Set the drawing for the fruit splatter to not occur
						vars.drawMangoSplatter = false;

						/* Set the mango to begin moving upwards */
						vars.mangoUp = true;

						/* Reset the X and Y-positions of the mango to make it appear at the bottom */
						vars.yPosMango = 800;
						vars.xPosMango = vars.rand.nextInt(700) + 50;

						/* Decrement the lives remaining by 1 */
						vars.livesRemaining -= 1;
					}

					/* Increasing the Y-position of the mango by 10px to move downwards */
					vars.yPosMango += 10;

					/* Update each of the mango's Rectangle2D masks */
					vars.mangoBox = new Rectangle2D.Double(vars.xPosMango, vars.yPosMango, 125, 125);
				}

				// Checking if the tri-fruit powerup is not enabled
				if (!vars.onTriFruit) {

					/* Checking if the strawberry is supposed to move upwards */
					if (vars.strawberryUp) {

						/* Checking if the Y-position of the strawberry is greater than 250px */
						if (vars.yPosStrawberry > (int)(Math.random() * 41 + 230)) {

							/* Decreasing the Y-position of the strawberry by 10px to move upwards */
							vars.yPosStrawberry -= 10;
						} 

						/* Checking if the Y-position of the strawberry reaches 250px */
						if (vars.yPosStrawberry <= (int)(Math.random() * 41 + 230)) {

							/* Set strawberryUp boolean to false (begins to move downwards) */
							vars.strawberryUp = false;
						}

						/* Update each of the strawberry's Rectangle2D masks */
						vars.strawberryBox = new Rectangle2D.Double(vars.xPosStrawberry, vars.yPosStrawberry, 125, 125);
					}

					/* Checking if the strawberry is supposed to move downwards */
					else if (!vars.strawberryUp) {

						/* Checking if the Y-position of the strawberry is equal to 810px */
						if (vars.yPosStrawberry == 810) {

							/* Set the strawberry to begin moving upwards */
							vars.strawberryUp = true;

							/* Reset the X position of the strawberry to make it appear randomized */
							vars.xPosStrawberry = vars.rand.nextInt(700) + 50;
						}

						/* Checking if the Y-position of the strawberry is greater than or equal to 800px */
						else if (vars.yPosStrawberry >= 800) {

							// Set the drawing for the fruit splatter to not occur
							vars.drawStrawberrySplatter = false;

							/* Set the strawberry to begin moving upwards */
							vars.strawberryUp = true;

							/* Reset the X and Y-positions of the strawberry to make it appear at the bottom */
							vars.yPosStrawberry = 800;
							vars.xPosStrawberry = vars.rand.nextInt(700) + 50;

							/* Decrement the lives remaining by 1 */
							vars.livesRemaining -= 1;
						}

						/* Increasing the Y-position of the strawberry by 10px to move downwards */
						vars.yPosStrawberry += 10;

						/* Update each of the strawberry's Rectangle2D masks */
						vars.strawberryBox = new Rectangle2D.Double(vars.xPosStrawberry, vars.yPosStrawberry, 125, 125);
					}
				}

				// Checking if the tri-fruit powerup is not enabled
				if (!vars.onTriFruit) {

					/* Checking if the apple is supposed to move upwards */
					if (vars.appleUp) {

						/* Checking if the Y-position of the apple is greater than 250px */
						if (vars.yPosApple > (int)(Math.random() * 41 + 230)) {

							/* Decreasing the Y-position of the apple by 10px to move upwards */
							vars.yPosApple -= 10;
						} 

						/* Checking if the Y-position of the apple reaches 250px */
						if (vars.yPosApple <= (int)(Math.random() * 41 + 230)) {

							/* Set appleUp boolean to false (begins to move downwards) */
							vars.appleUp = false;
						}

						/* Update each of the apple's Rectangle2D masks */
						vars.appleBox = new Rectangle2D.Double(vars.xPosApple, vars.yPosApple, 125, 125);
					}

					/* Checking if the apple is supposed to move downwards */
					else if (!vars.appleUp) {

						/* Checking if the Y-position of the apple is equal to 810px */
						if (vars.yPosApple == 810) {

							/* Set the apple to begin moving upwards */
							vars.appleUp = true;

							/* Reset the X position of the apple to make it appear randomized */
							vars.xPosApple = vars.rand.nextInt(700) + 50;
						}

						/* Checking if the Y-position of the apple is greater than or equal to 800px */
						else if (vars.yPosApple >= 800) {

							// Set the drawing for the fruit splatter to not occur
							vars.drawAppleSplatter = false;

							/* Set the apple to begin moving upwards */
							vars.appleUp = true;

							/* Reset the X and Y-positions of the apple to make it appear at the bottom */
							vars.yPosApple = 800;
							vars.xPosApple = vars.rand.nextInt(700) + 50;

							/* Decrement the lives remaining by 1 */
							vars.livesRemaining -= 1;
						}

						/* Increasing the Y-position of the apple by 10px to move downwards */
						vars.yPosApple += 10;

						/* Update each of the apple's Rectangle2D masks */
						vars.appleBox = new Rectangle2D.Double(vars.xPosApple, vars.yPosApple, 125, 125);
					}
				}

				/* Checking if the bomb is supposed to move upwards */
				if (vars.bombUp) {

					/* Checking if the Y-position of the bomb is greater than 250px */
					if (vars.yPosBomb > (int)(Math.random() * 41 + 230)) {

						/* Decreasing the Y-position of the bomb to move upwards by 10px */
						if (vars.onSlowBomb)
							vars.yPosBomb -= 10;

						/* Decreasing the Y-position of the bomb to move upwards by 15px */
						else if (!vars.onSlowBomb)
							vars.yPosBomb -= 15;
					} 

					/* Checking if the Y-position of the bomb reaches 250px */
					if (vars.yPosBomb <= (int)(Math.random() * 41 + 230)) {

						/* Set bombUp boolean to false (begins to move downwards) */
						vars.bombUp = false;
					}

					/* Update each of the bomb's Rectangle2D masks */
					vars.bombBox = new Rectangle2D.Double(vars.xPosBomb, vars.yPosBomb, 125, 125);
				}

				/* Checking if the boomb is supposed to move downwards */
				else if (!vars.bombUp) {

					/* Checking if the Y-position of the bomb is equal to 810px */
					if (vars.yPosBomb == 810) {

						/* Set the bomb to begin moving upwards */
						vars.bombUp = true;

						/* Reset the X position of the bomb to make it appear randomized */
						vars.xPosBomb = vars.rand.nextInt(700) + 50;
					}

					/* Checking if the Y-position of the bomb is greater than or equal to 800px */
					else if (vars.yPosBomb >= 800) {

						/* Set the apple to begin moving upwards */
						vars.bombUp = true;

						/* Reset the X and Y-positions of the bomb to make it appear at the bottom */
						vars.yPosBomb = 800;
						vars.xPosBomb = vars.rand.nextInt(700) + 50;
					}

					/* Increasing the Y-position of the bomb to move downwards by 10px */
					if (vars.onSlowBomb)
						vars.yPosBomb += 10;

					/* Increasing the Y-position of the bomb to move downwards by 15px */
					else if (!vars.onSlowBomb)
						vars.yPosBomb += 15;

					/* Update each of the bomb's Rectangle2D masks */
					vars.bombBox = new Rectangle2D.Double(vars.xPosBomb, vars.yPosBomb, 125, 125);
				}
			}

			// Checking if there are 2 lives remaining
			if (vars.livesRemaining == 2) {

				// Starts the timer for the xPopup
				vars.xPopupTimer.start();

				/* Checking if the projectile (fruit) timer's interval has elapsed */
				if (e.getSource() == vars.xPopupTimer) {

					// Sets the boolean flag for drawing the xPopup to true
					vars.drawXPopup = true;

					// Decrements the counter for the xPopup
					vars.xPopupCount--;
				}

				/* Stops the timer for the xPopup */
				vars.xPopupTimer.stop();
			}

		}
	}

	/* The paint method is used to draw the graphical user interface */
	public void paint(Graphics g) {

		/* Initialize the Graphics2D object and repaint the frame */
		super.paint(g);
		Graphics2D g2 = (Graphics2D)g;

		/* Checking if the starting phase is not complete */
		if (vars.titleCount >= 0) {

			/* Draws the starting (title) image */
			g2.drawImage(vars.titleBackground.getImage(), 0, 0, this);
		}

		/* Checking if the starting phase is complete and the player has not selected the arcade or story modes */
		else if (vars.titleCount < 0 && !vars.arcadeMode && !vars.storyMode) {

			/* Setting the font of the Graphics2D object to fMed */
			g2.setFont(vars.fMed);

			/* Setting the colour of the Graphics2D object */
			g2.setColor(new Color(186, 76, 71));

			/* Draws the background image, title text, name, and profile */
			g2.drawImage(vars.dojoBackground.getImage(), 0, 0, 1000, 800, this);
			g2.drawImage(vars.titleText.getImage(), 0, 0, this);
			g2.drawString("Welcome, " + vars.name, 960 - vars.nameWidth, 90);
			g2.drawImage(vars.profile.getImage(), 660, 110, this);

			/* Draws each of the buttons for choosing the game modes */
			g2.drawImage(vars.storyBtn.getImage(), vars.x_pos_story, vars.y_pos_story, 265, 245, this);

			// Checking if the story mode is not finished
			if (!vars.storyFinished) {

				/* Draws the locked button for the arcade and shop modes */
				g2.drawImage(vars.lockedBtn.getImage(), vars.x_pos_arcade, vars.y_pos_arcade, 265, 245, this);
				g2.drawImage(vars.lockedBtn.getImage(), vars.x_pos_shop, vars.y_pos_shop, 205, 205, this);
			}

			// Checking if the story mode is finished
			else {

				/* Draws the arcade and shop modes without the locked buttons */
				g2.drawImage(vars.arcadeBtn.getImage(), vars.x_pos_arcade, vars.y_pos_arcade, 265, 245, this);
				g2.drawImage(vars.shopBtn.getImage(), vars.x_pos_shop, vars.y_pos_shop, 205, 205, this);
			}

			/* Draws the button to quit the game */
			g2.drawImage(vars.quitBtn.getImage(), vars.x_pos_quit, vars.y_pos_quit, 120, 120, this);
			g2.drawImage(vars.bombSmall.getImage(), vars.x_pos_quit + 33, vars.y_pos_quit + 33, 55, 55, this);
		}

		/* Checking if the player has chosen the shop mode */
		if (vars.profileMode) {

			/* Stopping the background music from the main menu */
			vars.bg.stop();
			vars.gt.stop();

			/* Starting the background music for the profile mode */
			vars.dg.start();
			vars.dg.loop(vars.dg.LOOP_CONTINUOUSLY);

			/* Drawing the background image */
			g2.drawImage(vars.shopBackground.getImage(), 0, 0, 1000, 800, this);

			/* Draws the back button in the bottom-right corner */
			g2.drawImage(vars.backBtn.getImage(), vars.x_pos_quit - 90, vars.y_pos_quit, 210, 120, this);

			/* Display profile information */
			g2.drawImage(vars.profileHead.getImage(), 50, 50, this);

			/* Setting the colour of the Graphics2D object */
			g2.setColor(new Color(186, 76, 71));

			/* Setting the font to medium size and drawing the title */
			g2.setFont(vars.fMed);
			g2.drawString("PROFILE INFORMATION", 150, 100);

			// Changing the color of the text and displaying general information
			g2.setColor(new Color(237, 183, 180));
			g2.drawString("Student Name: " + vars.name, 120, 200);
			g2.drawString("Highest Score: " + vars.arcadeHighScore, 120, 250);
			g2.drawString("Total Score: " + vars.arcadeTotalScore, 120, 300);
			g2.drawString("Secret Codes Collected: " + Integer.toString(vars.codesCollected) + "/4", 120, 350);

			// Drawing the image for the player's body
			g2.drawImage(vars.playerBody.getImage(), 120, 400, this);
		}

		/* Checking if the player has chosen the shop mode */
		if (vars.shopMode) {

			/* Stopping the background music from the main menu */
			vars.bg.stop();
			vars.gt.stop();

			/* Starting the background music for the shop mode */
			vars.dg.start();
			vars.dg.loop(vars.dg.LOOP_CONTINUOUSLY);

			/* Drawing the background image */
			g2.drawImage(vars.shopBackground.getImage(), 0, 0, 1000, 800, this);

			/* Draws the back button in the bottom-right corner */
			g2.drawImage(vars.backBtn.getImage(), vars.x_pos_quit - 90, vars.y_pos_quit, 210, 120, this);

			// Draw the Blades section of the shop
			g2.setColor(new Color(252, 183, 114));
			g2.fillRoundRect(110, 100, 200, 70, 25, 25);

			// Blades section of the shop text
			g2.setColor(new Color(148, 75, 1));
			g2.drawString("Blades", 142, 150);

			// Draw the Backgrounds section of the shop
			g2.setColor(new Color(252, 183, 114));
			g2.fillRoundRect(330, 100, 300, 70, 25, 25);

			// Backgrounds section of the shop text
			g2.setColor(new Color(148, 75, 1));
			g2.drawString("Backgrounds", 350, 150);

			// Draw the Specials section of the shop
			g2.setColor(new Color(252, 183, 114));
			g2.fillRoundRect(650, 100, 250, 70, 25, 25);

			// Specials section of the shop text
			g2.setColor(new Color(148, 75, 1));
			g2.drawString(" Specials", 680, 150);

			// Draw the secret codes section of the shop
			g2.drawImage(vars.codeLogo.getImage(), 25, 675, this);

			/* Checking if the user is selecting for blades */
			if (vars.bladeSection) {

				// Draw the Blades section of the shop
				g2.setColor(new Color(227, 128, 61));
				g2.fillRoundRect(110, 100, 200, 70, 25, 25);

				// Blades section of the shop text
				g2.setColor(new Color(148, 75, 1));
				g2.drawString("Blades", 142, 150);

				/* Setting the font to bolded Arial and size 30 */
				g2.setFont(new Font("Arial", Font.BOLD, 30));

				// Display the basic blade logo
				g2.drawImage(vars.basicBladeLogo.getImage(), 150, 210, this);
				g2.setColor(Color.WHITE);
				g2.drawString("Classic Steel", 100, 370);

				// Display the available blades for purchase
				g2.setColor(Color.RED);

				/* Displays the 2nd blade - shiny red */
				g2.drawImage(vars.shinyRedLogo.getImage(), 430, 200, 120, 120, this);
				g2.drawString("Shiny Red", 420, 370);
				g2.drawString("1000 Points", 415, 410);

				/* Displays the 3rd blade - icy sharp */
				g2.setColor(new Color(40, 141, 250));
				g2.drawImage(vars.iceBladeLogo.getImage(), 710, 200, 120, 120, this);
				g2.drawString("Icy Sharp", 705, 370);
				g2.drawString("2500 Points", 700, 410);

				// Displays which blade the user currently has equipped
				g2.setColor(Color.WHITE);
				g2.drawString("Equipped Blade: " + vars.currentBlade, 290, 600);
			}

			/* Checking if the user is selecting for blades */
			if (vars.bgSection) {

				// Draw the Backgrounds section of the shop
				g2.setColor(new Color(227, 128, 61));
				g2.fillRoundRect(330, 100, 300, 70, 25, 25);

				// Backgrounds section of the shop text
				g2.setColor(new Color(148, 75, 1));
				g2.drawString("Backgrounds", 350, 150);

				/* Setting the font to bolded Arial and size 30 */
				g2.setFont(new Font("Arial", Font.BOLD, 30));

				// Display the basic dojo logo
				g2.drawImage(vars.basicDojoLogo.getImage(), 150, 210, 120, 120, this);
				g2.setColor(Color.WHITE);
				g2.drawString("Classic Dojo", 110, 370);

				/* Displays the 2nd dojo - cloudy skies */
				g2.setColor(new Color(0, 183, 255));
				g2.drawImage(vars.cloudDojoLogo.getImage(), 415, 190, 160, 160, this);
				g2.drawString("Cloudy Skies", 405, 370);
				g2.drawString("1500 Points", 415, 410);

				/* Displays the 3rd dojo - cherry blossom */
				g2.setColor(new Color(242, 145, 195));
				g2.drawImage(vars.cherryDojoLogo.getImage(), 710, 205, 130, 130, this);
				g2.drawString("Cherry Blossom", 660, 370);
				g2.drawString("3000 Points", 695, 410);

				// Displays which dojo the user currently has equipped
				g2.setColor(Color.WHITE);
				g2.drawString("Equipped Background: " + vars.currentDojo, 210, 600);
			}

			/* Checking if the user is selecting for blades */
			if (vars.specialsSection) {

				// Draw the Backgrounds section of the shop
				g2.setColor(new Color(227, 128, 61));
				g2.fillRoundRect(650, 100, 250, 70, 25, 25);

				// Specials section of the shop text
				g2.setColor(new Color(148, 75, 1));
				g2.drawString(" Specials", 680, 150);

				/* Setting the font to bolded Arial and size 30 */
				g2.setFont(new Font("Arial", Font.BOLD, 30));

				// Display the 2x score logo
				g2.drawImage(vars.twoxscore.getImage(), 140, 210, this);
				g2.setColor(new Color(118, 190, 239));
				g2.drawString("2.0x Score", 135, 370);
				g2.drawString("2250 Points", 130, 410);

				// Display the slow bomb logo
				g2.drawImage(vars.bomb.getImage(), 445, 220, 100, 100, this);
				g2.setColor(new Color(209, 0, 1));
				g2.drawString("Slower Bombs", 405, 370);
				g2.drawString("3500 Points", 415, 410);

				/* Displays the 3rd powerup - tri-fruit */
				g2.setColor(new Color(117, 142, 12));
				g2.drawImage(vars.triFruit.getImage(), 720, 205, this);
				g2.drawString("Tri-Fruit", 710, 370);
				g2.drawString("5000 Points", 695, 410);

				// Sets the font's color to white
				g2.setColor(Color.WHITE);

				/* This block of if-statements checks for every possible powerup loadout that the
				 * user could have selected and displays that with g2.drawString()
				 * 
				 * There are 3 powerups and 8 total scenarios (including using no powerups and all)
				 */

				if (!vars.on2x && !vars.onSlowBomb && !vars.onTriFruit) {
					g2.drawString("Equipped Powerups: N/A (None)", 210, 600);
				}
				if (vars.on2x && !vars.onSlowBomb && !vars.onTriFruit) {
					g2.drawString("Equipped Powerups: 2.0x Score", 210, 600);
				}
				if (vars.on2x && vars.onSlowBomb && !vars.onTriFruit) {
					g2.drawString("Equipped Powerups: 2.0x Score & Slower Bombs", 210, 600);
				}
				if (vars.on2x && !vars.onSlowBomb && vars.onTriFruit) {
					g2.drawString("Equipped Powerups: 2.0x Score & Tri-Fruit", 210, 600);
				}
				if (!vars.on2x && vars.onSlowBomb && !vars.onTriFruit) {
					g2.drawString("Equipped Powerups: Slower Bombs", 210, 600);
				}
				if (!vars.on2x && vars.onSlowBomb && vars.onTriFruit) {
					g2.drawString("Equipped Powerups: Slower Bombs & Tri-Fruit", 210, 600);
				}
				if (!vars.on2x && !vars.onSlowBomb && vars.onTriFruit) {
					g2.drawString("Equipped Powerups: Tri-Fruit", 210, 600);
				}
				if (vars.on2x && vars.onSlowBomb && vars.onTriFruit) {
					g2.drawString("Equipped Powerups: Using All", 210, 600);
				}
			}

			// Display the player's total points
			g2.setColor(new Color(255, 220, 0));
			g2.setFont(new Font("Arial", Font.BOLD, 20));
			g2.drawString("Points: " + vars.arcadeTotalScore, 50, 50);
		}

		/* Checking if the player has chosen the story mode */
		if (vars.storyMode) {

			/* Stopping the background music from the main menu */
			vars.bg.stop(); 
			vars.gt.stop();

			/* Starting the background music for the story mode */
			vars.dg.start();
			vars.dg.loop(vars.dg.LOOP_CONTINUOUSLY);

			/* Drawing the background image */
			if (!vars.collided) {
				g2.drawImage(vars.dojoBackground.getImage(), 0, 0, 1000, 800, this);

				/* Draws the back button in the bottom-right corner */
				g2.drawImage(vars.backBtn.getImage(), vars.x_pos_quit - 90, vars.y_pos_quit, 210, 120, this);

				// Draws the next button in the center of the bottom
				g2.drawImage(vars.nextBtn.getImage(), vars.x_pos_quit - 330, vars.y_pos_quit, 210, 120, this);
			}

			/* Checking if the story has not been finished */
			if (!vars.storyFinished) {

				// Level 0: Conditional statements for which level of the story the user is in
				if (vars.storyLevel == 0) {

					/* Sets the font size to medium and the colour to yellow */
					g2.setFont(vars.fMed);
					g2.setColor(Color.YELLOW);

					/* Displays the players body and brief story intro */
					g2.drawString("Long ago lived a young man,", 100, 350);
					g2.drawString("his name was " + vars.name + "...", 100, 450);
					g2.drawImage(vars.playerBody2.getImage(), 700, 300, this);
				}

				// Level 1: Conditional statements for which level of the story the user is in
				else if (vars.storyLevel == 1) {

					/* Draws the background and text (stored in the image "senseiText1") */
					g2.drawImage(vars.swordBackground.getImage(), 0, 0, 1000, 800, this);
					g2.drawImage(vars.senseiText1.getImage(), 135, 200, this);

					/* Draws both the back and next buttons */
					g2.drawImage(vars.backBtn.getImage(), vars.x_pos_quit - 90, vars.y_pos_quit, 210, 120, this);
					g2.drawImage(vars.nextBtn.getImage(), vars.x_pos_quit - 330, vars.y_pos_quit, 210, 120, this);
				}	

				// Level 2: Conditional statements for which level of the story the user is in
				else if (vars.storyLevel == 2) {

					/* Starts the movement timer for the ninja */
					vars.movementTimer.start();

					/* Sets the font size to medium */
					g2.setFont(vars.fMed);

					/* Checks if the ninja has not yet collided with the blade */
					if (!vars.collided) {

						// Displays text for this movement cutscene
						g2.setColor(Color.BLACK);
						g2.fillRect(0, 0, 1000, 300); 
						g2.setColor(Color.WHITE);
						g2.drawString(vars.name +  " Goes to pick up the blade", 200, 170);

						// Draws the images for the ninja and blade
						g2.setColor(Color.BLACK);
						g2.fillRect(0, 500, 1000, 300);
						g2.drawImage(vars.ninjaWalkImg.getImage(), vars.ninjaXPos, 350, 120, 160, this);
						g2.drawImage(vars.basicBlade.getImage(), 870, 340, 125, 125, this);
					}

				}

				// Level 3: Conditional statements for which level of the story the user is in
				else if (vars.storyLevel == 3) {

					/* Stops the movement timer for the ninja */
					vars.movementTimer.stop();

					// Sets the properties for the Graphics2D object
					g2.setColor(Color.BLACK);
					g2.fillRect(0, 0, getWidth(), getHeight());
					
					// Displays large text saying that the story is complete
					g2.setColor(Color.GREEN);
					g2.setFont(vars.fBig);
					g2.drawString("Story Mode Complete!", 130, 100);
				
					/* Displays brief text for controls of the game */
					g2.setFont(vars.fSmall);
					g2.drawString("To practice the art of Fruit Ninjutsu, slice fruits by", 110, 160);
					g2.drawString("holding and dragging your Mouse or Trackpad!", 110, 190);
					g2.drawString("Avoid hitting bombs and slice all fruits you see!", 110, 220);
					g2.drawImage(vars.mouseDrag.getImage(), 240, 320, 500, 280, this);

					// Sets the shop mode to false and storyFinished to true
					vars.shopMode = false;
					vars.storyFinished = true;

					// Displays the image for the next button
					g2.drawImage(vars.nextBtn.getImage(), vars.x_pos_quit - 120, vars.y_pos_quit, 210, 120, this);
				}
			}

			// All levels complete: Conditional statements for which level of the story the user is in
			else {

				// Sets the properties for the Graphics2D object
				g2.setColor(Color.BLACK);
				g2.fillRect(0, 0, getWidth(), getHeight());
				g2.setColor(Color.GREEN);

				// Displays large text saying that the story is complete
				g2.setColor(Color.GREEN);
				g2.setFont(vars.fBig);
				g2.drawString("Story Mode Complete!", 130, 100);
				
				/* Displays brief text for controls of the game */
				g2.setFont(vars.fSmall);
				g2.drawString("To practice the art of Fruit Ninjutsu, slice fruits by", 110, 160);
				g2.drawString("holding and dragging your Mouse or Trackpad!", 110, 190);
				g2.drawString("Avoid hitting bombs and slice all fruits you see!", 110, 220);
				
				g2.drawImage(vars.mouseDrag.getImage(), 240, 320, 500, 280, this);

				// Sets the shop mode to false
				vars.shopMode = false;

				// Displays the image for the next button
				g2.drawImage(vars.nextBtn.getImage(), vars.x_pos_quit - 120, vars.y_pos_quit, 210, 120, this);
			}

		}

		/* Checking if the player has chosen the arcade game mode */
		if (vars.arcadeMode) {

			/* Stopping the background music from the main menu */
			vars.bg.stop();
			vars.dg.stop();

			/* Starting the background music for the game modes */
			vars.gt.start();
			vars.gt.loop(vars.dg.LOOP_CONTINUOUSLY);

			/* Drawing the background image depending on which is selected */
			if (vars.currentDojo == "Classic Dojo") {
				g2.drawImage(vars.dojoBackground.getImage(), 0, 0, 1000, 800, this);
			} else if (vars.currentDojo == "Cloudy Skies") {
				g2.drawImage(vars.cloudySkiesBackground.getImage(), 0, 0, 1000, 800, this);
			} else if (vars.currentDojo == "Cherry Blossom") {
				g2.drawImage(vars.cherryBlossomBackground.getImage(), 0, 0, 1000, 800, this);
			} else if (vars.currentDojo == "Sunlight Dojo") {
				g2.drawImage(vars.sunlightBackground.getImage(), 0, 0, 1000, 800, this);
			}

			/* Checks if 3 lives are remaning */
			if (vars.livesRemaining == 3) {

				/* Draws the image which displays 3 lives */
				g2.drawImage(vars.threeLives.getImage(), 870, 10, this);
			}

			/* Checks if 2 lives are remaning */
			else if (vars.livesRemaining == 2) {

				/* Draws the image which displays 2 lives */
				g2.drawImage(vars.twoLives.getImage(), 870, 10, this);
			}

			/* Checks if 1 life is remaning */
			else if (vars.livesRemaining == 1) {

				/* Draws the image which displays 1 life */
				g2.drawImage(vars.oneLive.getImage(), 870, 10, this);
			}

			/* Checks if no lives are remaning */
			else if (vars.livesRemaining <= 0) {

				/* Draws the images which display 0 lives and game over */
				g2.drawImage(vars.noLives.getImage(), 870, 10, this);
				g2.drawImage(vars.gameOver.getImage(), 250, 300, 500, 90, this);		
			}

			/* Setting the colour of the Graphics2D object to orange */
			g2.setColor(new Color(255, 208, 0));

			/* Setting the font of the Graphics2D object to fBig */
			g2.setFont(vars.fBig);

			/* Draws the player's score for the arcade mode */
			g2.drawString("" + vars.arcadeScore, 90, 65);

			/* Draws the image for the score icon */
			g2.drawImage(vars.scoreFruit.getImage(), 10, 10, 70, 70, this);

			/* Setting the font of the Graphics2D object to fMed */
			g2.setFont(vars.fMed);

			/* Draws the player's best score for the arcade mode */
			g2.drawString("BEST: " + vars.arcadeHighScore, 20, 120);

			/* Draws the back button in the bottom-right corner */
			g2.drawImage(vars.backBtn.getImage(), vars.x_pos_quit - 70, vars.y_pos_quit, 210, 120, this);

			/* Checking if it is required for the intro text to be displayed */
			if (vars.readyStart) {

				/* Setting the font of the Graphics2D object to fLarge */
				g2.setFont(vars.fLarge);

				/* Checking if it is required for the "ready" text to be displayed */
				if (vars.startGameSec >= 2) {

					/* Drawing the "ready?" text in the center of the screen */
					g2.drawString(vars.startGame, 310, 400);
				}

				/* Checking if it is required for the "go" text to be displayed */
				if (vars.startGameSec <= 1 && vars.startGameSec >= 0) {

					/* Setting the colour of the Graphics2D object to green */
					g2.setColor(Color.green);

					/* Drawing the "go" text in the center of the screen */
					g2.drawString("GO!", 400, 400);
				}

				/* Checking if the intro phase has been completed */
				if (vars.startGameSec < 0) {	

					/* Draws each of the projectiles (fruit) with their updated positions */
					g2.drawImage(vars.fruitImages[0].getImage(), vars.xPosMelon, vars.yPosMelon, 100, 100, this);
					g2.drawImage(vars.fruitImages[1].getImage(), vars.xPosPineapple, vars.yPosPineapple, 100, 100, this);
					g2.drawImage(vars.fruitImages[2].getImage(), vars.xPosCoconut, vars.yPosCoconut, 100, 100, this);
					g2.drawImage(vars.fruitImages[3].getImage(), vars.xPosMango, vars.yPosMango, 100, 100, this);
					g2.drawImage(vars.fruitImages[4].getImage(), vars.xPosStrawberry, vars.yPosStrawberry, 100, 100, this);
					g2.drawImage(vars.fruitImages[5].getImage(), vars.xPosApple, vars.yPosApple, 100, 100, this);
					g2.drawImage(vars.bomb.getImage(), vars.xPosBomb, vars.yPosBomb, 100, 100, this);	

					// Draws the fruit splatters for the pineapple
					if (vars.drawPineappleSplatter) {
						g2.drawImage(vars.yellowSplatterImg.getImage(), vars.xPosPineappleSplatter, vars.yPosPineappleSplatter, this);
					}

					// Draws the fruit splatters for the strawberry
					if (vars.drawStrawberrySplatter) {
						g2.drawImage(vars.redSplatterImg.getImage(), vars.xPosStrawberrySplatter, vars.yPosStrawberrySplatter, this);
					}

					// Draws the fruit splatters for the coconut
					if (vars.drawCoconutSplatter) {
						g2.drawImage(vars.whiteSplatterImg.getImage(), vars.xPosCoconutSplatter, vars.yPosCoconutSplatter, this);
					}

					// Draws the fruit splatters for the apple
					if (vars.drawAppleSplatter) {
						g2.drawImage(vars.redSplatterImg.getImage(), vars.xPosAppleSplatter, vars.yPosAppleSplatter, this);
					}

					// Draws the fruit splatters for the mango
					if (vars.drawMangoSplatter) {
						g2.drawImage(vars.redSplatterImg.getImage(), vars.xPosMangoSplatter, vars.yPosMangoSplatter, this);
					}

					// Draws the fruit splatters for the melon
					if (vars.drawMelonSplatter) {
						g2.drawImage(vars.yellowSplatterImg.getImage(), vars.xPosMelonSplatter, vars.yPosMelonSplatter, this);
					}

					/* Drawing the blade depending on which is selected */
					if (vars.currentBlade == "Classic Steel") {
						g2.drawImage(vars.basicBlade.getImage(), vars.mouseX - 50, vars.mouseY - 30, 100, 100, this);
					} else if (vars.currentBlade == "Shiny Red") {
						g2.drawImage(vars.redBlade.getImage(), vars.mouseX - 50, vars.mouseY - 30, 100, 100, this);
					} else if (vars.currentBlade == "Icy Sharp") {
						g2.drawImage(vars.blueBlade.getImage(), vars.mouseX - 50, vars.mouseY - 30, 100, 100, this);
					} else if (vars.currentBlade == "Rock Blade") {
						g2.drawImage(vars.rockBlade.getImage(), vars.mouseX - 50, vars.mouseY - 30, 130, 100, this);
					}

				}	
			}
		}

		/* Repaints the frame's components */
		repaint();
	}

	/* The mousePressed method checks if the mouse is both clicked and released */
	public void mousePressed(MouseEvent e) {

		/* Checking if the player is in the main menu */
		if (!vars.arcadeMode && !vars.storyMode && !vars.shopMode && !vars.profileMode) {

			/* Checking if the player has pressed in the bounds of the profile mode */
			if (e.getX() >= 660 && e.getX() <= 957 && e.getY() >= 110 && e.getY() <= 197) {

				/* Setting the profileMode boolean to true and arcadeMode, storyMode, & shopMode to false */
				vars.shopMode = false;
				vars.storyMode = false;
				vars.arcadeMode = false;
				vars.profileMode = true;

				/* Renaming the title of the frame to suit the chosen mode (profile) */
				frame.setTitle("Fruit Ninja: Profile");

				/* Repaints the frame's components */
				repaint();
			}

			/* Checking if the player has pressed in the bounds of the shop mode */
			if (vars.storyFinished && e.getX() >= vars.x_pos_shop + 20 && e.getX() <= vars.x_pos_shop + 200 && e.getY() >= vars.y_pos_shop && e.getY() <= vars.y_pos_shop + 200) {

				/* Setting the shopMode boolean to true and arcadeMode, storyMode, & profileMode to false */
				vars.shopMode = true;
				vars.storyMode = false;
				vars.arcadeMode = false;
				vars.profileMode = false;

				/* Renaming the title of the frame to suit the chosen mode (shop) */
				frame.setTitle("Fruit Ninja: Shop");

				/* Repaints the frame's components */
				repaint();

				/* Checking if the user has not seen the instructions for the shop */
				if (!vars.seenShop) {

					// Displaying the instructions for the shop
					JOptionPane.showMessageDialog(null, "Welcome to my Shop! \nHave a look around, here you can buy \nBlades, Dojo Backgrounds, and Specials. \nSlice lots of fruits to buy items!", "Sensei's Shop", JOptionPane.PLAIN_MESSAGE, vars.sensei);

					// Settings the seenShop boolean flag to true
					vars.seenShop = true;
				}
			}

			/* Checking if the user attempted to click the shop mode but has not finished the story */
			else if (!vars.storyFinished && e.getX() >= vars.x_pos_shop + 20 && e.getX() <= vars.x_pos_shop + 200 && e.getY() >= vars.y_pos_shop && e.getY() <= vars.y_pos_shop + 200) {

				// Indicate to the user in a JOptionPane to finis the story
				JOptionPane.showMessageDialog(null, "Try finishing\nthe story mode!", "Locked Mode", JOptionPane.PLAIN_MESSAGE, vars.sensei);
			}

			/* Checking if the player has pressed in the bounds of the arcade mode */
			if (vars.storyFinished && e.getX() >= vars.x_pos_arcade + 20 && e.getX() <= vars.x_pos_arcade + 210 && e.getY() >= vars.y_pos_arcade && e.getY() <= vars.y_pos_arcade + 210) {

				/* Setting the arcadeMode boolean to true and shopMode, storyMode, & profileMode to false */
				vars.arcadeMode = true;
				vars.shopMode = false;
				vars.storyMode = false;
				vars.profileMode = false;

				/* Renaming the title of the frame to suit the chosen mode (arcade) */
				frame.setTitle("Fruit Ninja: Arcade");

				/* Repaints the frame's components */
				repaint();

				/* Starting the beginning game timer and setting the readyStart boolean to true */
				vars.startGameTimer.start();
				vars.readyStart = true;
			}

			/* Checking if the user attempted to click the arcade mode but has not finished the story */
			else if (!vars.storyFinished && e.getX() >= vars.x_pos_arcade + 20 && e.getX() <= vars.x_pos_arcade + 210 && e.getY() >= vars.y_pos_arcade && e.getY() <= vars.y_pos_arcade + 210) {

				// Indicate to the user in a JOptionPane to finis the story
				JOptionPane.showMessageDialog(null, "Try finishing\nthe story mode!", "Locked Mode", JOptionPane.PLAIN_MESSAGE, vars.sensei);
			}

			/* Checking if the player has pressed in the bounds of the story mode */
			if (e.getX() >= vars.x_pos_story + 20 && e.getX() <= vars.x_pos_story + 235 && e.getY() >= vars.y_pos_story && e.getY() <= vars.y_pos_story + 200) {

				/* Setting the storyMode boolean to true and shopMode, arcadeMode, & profileMode to false */
				vars.storyMode = true;
				vars.arcadeMode = false;
				vars.shopMode = false;
				vars.profileMode = false;

				/* Renaming the title of the frame to suit the chosen mode (story) */
				frame.setTitle("Fruit Ninja: Story");

				/* Repaints the frame's components */
				repaint();
			}

			/* Checking if the player has not selected the shop mode and story mode */
			if (!vars.shopMode && !vars.storyMode && !vars.profileMode) {

				/* Checking if the player has pressed in the bounds of the quit option */
				if (e.getX() >= vars.x_pos_quit && e.getX() <= vars.x_pos_quit + vars.quitBtn.getIconWidth() && e.getY() >= vars.y_pos_quit && e.getY() <= vars.y_pos_quit + vars.quitBtn.getIconHeight()) {

					/* Initializing the quit sound effect for the main menu using try-catch */
					try {

						/* Initializing the file object for the music */
						File quitSfx = new File("sfx/menu-bomb.wav");

						/* Opening an audio input stream for the music */
						AudioInputStream audioIn = AudioSystem.getAudioInputStream(quitSfx);

						/* Retrieving and opening the audio clip from the newly-created stream */
						vars.quitGameSfx = AudioSystem.getClip();
						vars.quitGameSfx.open(audioIn);

						vars.quitGameSfx.start();
					} 

					catch (Exception e1) {} // "e" paramater not possible, because mousePressed method already uses "e" paramater

					/* Display the departing message and exit the program */
					JOptionPane.showMessageDialog(null, "Thanks for playing.\nSee you soon!", "Fruit Ninja", JOptionPane.PLAIN_MESSAGE, vars.senseiCoconut);
					System.exit(0);
				}
			}
		}

		/* Checking if the player has selected the story mode */
		if (vars.storyMode) {

			/* Checking if the second story level is not active */
			if (vars.storyLevel != 2) {

				/* Checking if the user clicked in the bounds of the next button */
				if (e.getX() >= 520 && e.getX() <= 730 && e.getY() >= 650 && e.getY() <= 770) {

					// Increments the story level by 1
					vars.storyLevel += 1;
				}

				/* Checking if the user clicked in the bounds of the back button */
				if (e.getX() >= vars.x_pos_quit - 90 && e.getX() <= vars.x_pos_quit + 210 && e.getY() >= vars.y_pos_quit && e.getY() <= vars.y_pos_quit + 210) {

					/* Checking if the story level is greater than 0 (1st level) */
					if (vars.storyLevel > 0) {

						// Decrements the story level by 1
						vars.storyLevel -= 1;
					}

					/* Checking if the story is on the 1st level (quits story mode) */
					else {

						/* Stopping the music for the game modes */
						vars.dg.stop();
						vars.gt.stop();

						/* Starting the background music for the main menu */
						vars.bg.start();
						vars.bg.loop(vars.bg.LOOP_CONTINUOUSLY);

						/* Setting the shopMode, arcadeMode, storyMode, and profileMode booleans to false */
						vars.shopMode = false;
						vars.arcadeMode = false;
						vars.storyMode = false;
						vars.profileMode = false;

						/* Renaming the title of the frame to suit the main menu */
						frame.setTitle("Fruit Ninja");

						/* Repaints the frame's components */
						repaint();
					}
				}
			}

			/* Checking if the ninja collided with the blade in the 2nd level */
			if (vars.collided) {

				/* Checking if the button to exit out of the mode was pressed */
				if (e.getX() >= (vars.x_pos_quit - 120) && e.getX() <= (vars.x_pos_quit + 90) && e.getY() >= vars.y_pos_quit && e.getY() <= (vars.y_pos_quit + 210)) {

					// Sets the storyMode boolean flag to falsee
					vars.storyMode = false;
				}
			}
		}

		/* Checking if only the shop mode is selected */
		if (vars.shopMode) {

			/* Checking if the user clicked in the bounds of the cheat codes button */
			if (e.getX() >= 25 && e.getX() <= 109 && e.getY() >= 675 && e.getY() <= 757) {

				// Displaying the showInputDialog pane prompting the user for the cheat code
				vars.cheatCode = (String) JOptionPane.showInputDialog(null, "I got what you need, but\nwhat's the secret code?", "Secret Menu", JOptionPane.PLAIN_MESSAGE, vars.ninjaImg, null, null);

				/* Checking if the input was empty */
				if (vars.cheatCode == null) {

					// Prompt to the user they were incorrect
					JOptionPane.showMessageDialog(null, "Get out of here!", "Secret Menu", JOptionPane.PLAIN_MESSAGE, vars.ninjaImg);
				}

				/* Checking if the input was not empty */
				else if (vars.cheatCode != null) {
					
					/* Checking if the 1st cheat code was entered */
					if (vars.cheatCode.equals("supersecretcode")) {

						/* Checking if this cheat code was not previously found */
						if (!vars.secretFound) {

							// Increases the codes collected (for profile mode) and sets secretFound to true
							vars.codesCollected += 1;
							vars.secretFound = true;
						}

						// Prompt to the user they were correct
						JOptionPane.showMessageDialog(null, "Keep this classified.", "Secret Menu", JOptionPane.PLAIN_MESSAGE, vars.ninjaImg);

						// Increase the total arcade score by 29570
						vars.arcadeTotalScore += 29570;
					}

					/* Checking if the 2nd cheat code was entered */
					else if (vars.cheatCode.equals("reset")) {

						/* Checking if this cheat code was not previously found */
						if (!vars.resetFound) {

							// Increases the codes collected (for profile mode) and sets resetFound to true
							vars.codesCollected += 1;
							vars.resetFound = true;
						}

						// Prompt to the user they were correct (at their own caution)
						JOptionPane.showMessageDialog(null, "If that's what you want.", "Secret Menu", JOptionPane.PLAIN_MESSAGE, vars.ninjaImg);

						// Set the total arcade score to 0
						vars.arcadeTotalScore = 0;
					}

					/* Checking if the 3rd cheat code was entered */
					else if (vars.cheatCode.equals("rockblade")) {

						/* Checking if this cheat code was not previously found */
						if (!vars.rockFound) {

							// Increases the codes collected (for profile mode) and sets rockFound to true
							vars.codesCollected += 1;
							vars.rockFound = true;
						}

						// Prompt to the user they were correct
						JOptionPane.showMessageDialog(null, "Rock Arcade mode with this Rock Blade!", "Secret Menu: Blade Unlocked", JOptionPane.PLAIN_MESSAGE, vars.rockBladeLogo);

						// Set the user's currrent blade to the rock blade
						vars.currentBlade = "Rock Blade";
					}

					/* Checking if the 4th cheat code was entered */
					else if (vars.cheatCode.equals("sunlight")) {

						/* Checking if this cheat code was not previously found */
						if (!vars.sunlightFound) {

							// Increases the codes collected (for profile mode) and sets sunlightFound to true
							vars.codesCollected += 1;
							vars.sunlightFound = true;
						}

						// Set the user's currrent dojo to the sunlight dojo
						vars.currentDojo = "Sunlight Dojo";

						// Prompt to the user they were correct
						JOptionPane.showMessageDialog(null, "A breath of fresh air, enjoy the Sunlight Dojo!", "Secret Menu: Background Unlocked", JOptionPane.PLAIN_MESSAGE, vars.sunlightDojoLogo);
					}
					
					/* Checking if the input was anything else */
					else {

						// Prompt to the user they were incorrect
						JOptionPane.showMessageDialog(null, "Get out of here!", "Secret Menu", JOptionPane.PLAIN_MESSAGE, vars.ninjaImg);
					}
				}
			}

			// Check if the user clicks on the blades section button
			if (e.getX() >= 110 && e.getX() <= 310 && e.getY() >= 100 && e.getY() <= 170) {

				/* Initializing the click sound effect for the blades section using try-catch */
				try {

					/* Initializing the file object for the music */
					File uiBtn = new File("sfx/ui-button-click.wav");

					/* Opening an audio input stream for the music */
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(uiBtn);

					/* Retrieving and opening the audio clip from the newly-created stream */
					vars.uiBtn = AudioSystem.getClip();
					vars.uiBtn.open(audioIn);

					vars.uiBtn.start();
				} 

				catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

				/* Setting the bladeSection boolean to true and the bgSection & specialsSection booleans to false */
				vars.bladeSection = true;
				vars.bgSection = false;
				vars.specialsSection = false;
			}

			// Check if the user clicks on the blade selection
			if (vars.bladeSection) {

				/* Checking if the user equips the first new blade */
				if (e.getX() >= 150 && e.getX() <= 249 && e.getY() >= 210 && e.getY() <= 311) {

					/* Initializing the new sword sound effect for the blades section using try-catch */
					try {

						/* Initializing the file object for the music */
						File equipBlade = new File("sfx/equip-new-sword.wav");

						/* Opening an audio input stream for the music */
						AudioInputStream audioIn = AudioSystem.getAudioInputStream(equipBlade);

						/* Retrieving and opening the audio clip from the newly-created stream */
						vars.equipBlade = AudioSystem.getClip();
						vars.equipBlade.open(audioIn);

						vars.equipBlade.start();
					} 

					catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

					// Sets the current blade to the classic steel
					vars.currentBlade = "Classic Steel";
				}

				// Check if the user clicks on the shiny red blade
				if (e.getX() >= 430 && e.getX() <= 550 && e.getY() >= 200 && e.getY() <= 320) {

					/* Check if user has enough points to buy the shiny red blade and doesn't already have it */
					if (vars.arcadeTotalScore >= 1000 && vars.hasShinyRed == false) {

						// Confirming the purchase with a showConfirmDialog pane
						vars.confirmShinyRed = JOptionPane.showConfirmDialog(null, "You have enough points to buy the Shiny Red blade! \nAre you sure you want to buy the Shiny Red blade?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);

						// Checking if the user confirms the purchase
						if (vars.confirmShinyRed == 0) {

							/* Initializing the equip sword sound effect for the blades section using try-catch */
							try {

								/* Initializing the file object for the music */
								File equipBlade = new File("sfx/equip-new-sword.wav");

								/* Opening an audio input stream for the music */
								AudioInputStream audioIn = AudioSystem.getAudioInputStream(equipBlade);

								/* Retrieving and opening the audio clip from the newly-created stream */
								vars.equipBlade = AudioSystem.getClip();
								vars.equipBlade.open(audioIn);

								vars.equipBlade.start();
							} 

							catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

							// Unlocks the shiny red blade, sets it to the current, and subtracts 1000 points
							vars.currentBlade = "Shiny Red";
							vars.hasShinyRed = true;
							vars.arcadeTotalScore -= 1000;
						}
					} 

					// Checking if the user does not have enough points for the purchase
					else if (vars.arcadeTotalScore <= 1000 && !vars.hasShinyRed) {

						/* Initializing the equip locked sound effect for the blades section using try-catch */
						try {

							/* Initializing the file object for the music */
							File lockedItem = new File("sfx/equip-locked.wav");

							/* Opening an audio input stream for the music */
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(lockedItem);

							/* Retrieving and opening the audio clip from the newly-created stream */
							vars.lockedItem = AudioSystem.getClip();
							vars.lockedItem.open(audioIn);

							vars.lockedItem.start();
						} 

						catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

						// Inform the user in a showMessageDialog pane that they lack the points for the shiny red blade
						JOptionPane.showMessageDialog(null, "You don't have enough points for the Shiny Red blade. \nYou need " + Integer.toString(1000 - vars.arcadeTotalScore) + " more points in order to buy it.", "Not Enough Points", JOptionPane.ERROR_MESSAGE);
					}

					// Checking if the user has already unlocked the shiny red blade
					if (vars.hasShinyRed) {

						/* Initializing the equip blade sound effect for the blades section using try-catch */
						try {

							/* Initializing the file object for the music */
							File equipBlade = new File("sfx/equip-new-sword.wav");

							/* Opening an audio input stream for the music */
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(equipBlade);

							/* Retrieving and opening the audio clip from the newly-created stream */
							vars.equipBlade = AudioSystem.getClip();
							vars.equipBlade.open(audioIn);

							vars.equipBlade.start();
						} 

						catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

						// Sets the current blade to the shiny red
						vars.currentBlade = "Shiny Red";
					}
				} 

				// Check if the user clicks on the icy sharp blade
				else if (e.getX() >= 710 && e.getX() <= 830 && e.getY() >= 200 && e.getY() <= 320) {

					/* Check if user has enough points to buy the shiny red blade and doesn't already have it */
					if (vars.arcadeTotalScore >= 2500 && vars.hasIcySharp == false) {

						// Confirming the purchase with a showConfirmDialog pane
						vars.confirmIcySharp = JOptionPane.showConfirmDialog(null, "You have enough points to buy the Icy Sharp blade! \nAre you sure you want to buy the Icy Sharp blade?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);

						// Checking if the user confirms the purchase
						if (vars.confirmIcySharp == 0) {

							/* Initializing the equip blade sound effect for the blades section using try-catch */
							try {

								/* Initializing the file object for the music */
								File equipBlade = new File("sfx/equip-new-sword.wav");

								/* Opening an audio input stream for the music */
								AudioInputStream audioIn = AudioSystem.getAudioInputStream(equipBlade);

								/* Retrieving and opening the audio clip from the newly-created stream */
								vars.equipBlade = AudioSystem.getClip();
								vars.equipBlade.open(audioIn);

								vars.equipBlade.start();
							} 

							catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

							// Unlocks the icy sharp blade, sets it to the current, and subtracts 2500 points
							vars.currentBlade = "Icy Sharp";
							vars.hasIcySharp = true;
							vars.arcadeTotalScore -= 2500;
						}
					}

					// Checking if the user does not have enough points for the purchase
					else if (vars.arcadeTotalScore <= 2500 && !vars.hasIcySharp) {

						/* Initializing the locked blade sound effect for the blades section using try-catch */
						try {

							/* Initializing the file object for the music */
							File lockedItem = new File("sfx/equip-locked.wav");

							/* Opening an audio input stream for the music */
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(lockedItem);

							/* Retrieving and opening the audio clip from the newly-created stream */
							vars.lockedItem = AudioSystem.getClip();
							vars.lockedItem.open(audioIn);

							vars.lockedItem.start();
						} 

						catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

						// Inform the user in a showMessageDialog pane that they lack the points for the icy sharp blade
						JOptionPane.showMessageDialog(null, "You don't have enough points for the Icy Sharp blade. \nYou need " + Integer.toString(2500 - vars.arcadeTotalScore) + " more points in order to buy it.", "Not Enough Points", JOptionPane.ERROR_MESSAGE);
					}

					// Checking if the user has already unlocked the shiny red blade
					if (vars.hasIcySharp) {

						/* Initializing the equip blade sound effect for the blades section using try-catch */
						try {

							/* Initializing the file object for the music */
							File equipBlade = new File("sfx/equip-new-sword.wav");

							/* Opening an audio input stream for the music */
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(equipBlade);

							/* Retrieving and opening the audio clip from the newly-created stream */
							vars.equipBlade = AudioSystem.getClip();
							vars.equipBlade.open(audioIn);

							vars.equipBlade.start();
						} 

						catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

						// Sets the current blade to the shiny red
						vars.currentBlade = "Icy Sharp";
					}

				}

			}

			// Check if the user clicks on the backgrounds section button
			if (e.getX() >= 330 && e.getX() <= 630 && e.getY() >= 100 && e.getY() <= 170) {

				/* Initializing the click sound effect for the backgrounds section using try-catch */
				try {

					/* Initializing the file object for the music */
					File uiBtn = new File("sfx/ui-button-click.wav");

					/* Opening an audio input stream for the music */
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(uiBtn);

					/* Retrieving and opening the audio clip from the newly-created stream */
					vars.uiBtn = AudioSystem.getClip();
					vars.uiBtn.open(audioIn);

					vars.uiBtn.start();
				} 

				catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

				/* Setting the bgSection boolean to true and the bladeSelection & specialsSection booleans to false */
				vars.bgSection = true;
				vars.bladeSection = false;
				vars.specialsSection = false;
			}

			// Check if the user clicks on the background selection
			if (vars.bgSection) {

				// Check if the user clicks on the cloudy skies background
				if (e.getX() >= 415 && e.getX() <= 575 && e.getY() >= 190 && e.getY() <= 350) {

					/* Check if user has enough points to buy the cloudy skies background and doesn't already have it */
					if (vars.arcadeTotalScore >= 1500 && vars.hasCloudBg == false) {

						// Confirming the purchase with a showConfirmDialog pane
						vars.confirmCloud = JOptionPane.showConfirmDialog(null, "You have enough points to buy the Cloudy Skies Background! \nAre you sure you want to buy the Cloudy Skies Background?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);

						// Checking if the user confirms the purchase
						if (vars.confirmCloud == 0) {

							/* Initializing the equip new wallpaper sound effect for the backgrounds section using try-catch */
							try {

								/* Initializing the file object for the music */
								File equipBg = new File("sfx/equip-new-wallpaper.wav");

								/* Opening an audio input stream for the music */
								AudioInputStream audioIn = AudioSystem.getAudioInputStream(equipBg);

								/* Retrieving and opening the audio clip from the newly-created stream */
								vars.equipBg= AudioSystem.getClip();
								vars.equipBg.open(audioIn);

								vars.equipBg.start();
							} 

							catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

							// Unlocks the cloudy skies background, sets it to the current, and subtracts 1500 points
							vars.currentDojo = "Cloudy Skies";
							vars.hasCloudBg = true;
							vars.arcadeTotalScore -= 1500;
						}
					}

					// Checking if the user does not have enough points for the purchase
					else if (vars.arcadeTotalScore <= 1500 && !vars.hasCloudBg) {

						/* Initializing the equip locked sound effect for the backgrounds section using try-catch */
						try {

							/* Initializing the file object for the music */
							File lockedItem = new File("sfx/equip-locked.wav");

							/* Opening an audio input stream for the music */
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(lockedItem);

							/* Retrieving and opening the audio clip from the newly-created stream */
							vars.lockedItem = AudioSystem.getClip();
							vars.lockedItem.open(audioIn);

							vars.lockedItem.start();
						} 

						catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

						// Inform the user in a showMessageDialog pane that they lack the points for the cloudy skies background
						JOptionPane.showMessageDialog(null, "You don't have enough points for the Cloudy Skies background. \nYou need " + Integer.toString(1500 - vars.arcadeTotalScore) + " more points in order to buy it.", "Not Enough Points", JOptionPane.ERROR_MESSAGE);
					}

					// Checking if the user has already unlocked the cloudy skies background
					else if (vars.hasCloudBg) {

						/* Initializing the equip new wallpaper sound effect for the backgrounds section using try-catch */
						try {

							/* Initializing the file object for the music */
							File equipBg = new File("sfx/equip-new-wallpaper.wav");

							/* Opening an audio input stream for the music */
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(equipBg);

							/* Retrieving and opening the audio clip from the newly-created stream */
							vars.equipBg= AudioSystem.getClip();
							vars.equipBg.open(audioIn);

							vars.equipBg.start();
						} 

						catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

						// Sets the current dojo to the cloudy skies and sets the boolean flag to true
						vars.currentDojo = "Cloudy Skies";
						vars.hasCloudBg = true;
					}
				}

				// Check if the user clicks on the cherry blossom background
				if (e.getX() >= 710 && e.getX() <= 840 && e.getY() >= 205 && e.getY() <= 335) {

					/* Check if user has enough points to buy the cherry blossom background and doesn't already have it */
					if (vars.arcadeTotalScore >= 3000 && vars.hasCherryBg == false) {

						// Confirming the purchase with a showConfirmDialog pane
						vars.confirmCherry = JOptionPane.showConfirmDialog(null, "You have enough points to buy the Cherry Blossom Background! \nAre you sure you want to buy the Cherry Blossom Background?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);

						// Checking if the user confirms the purchase
						if (vars.confirmCherry == 0) {

							/* Initializing the equip new wallpaper sound effect for the backgrounds section using try-catch */
							try {

								/* Initializing the file object for the music */
								File equipBg = new File("sfx/equip-new-wallpaper.wav");

								/* Opening an audio input stream for the music */
								AudioInputStream audioIn = AudioSystem.getAudioInputStream(equipBg);

								/* Retrieving and opening the audio clip from the newly-created stream */
								vars.equipBg= AudioSystem.getClip();
								vars.equipBg.open(audioIn);

								vars.equipBg.start();
							} 

							catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

							// Unlocks the cherry blossom background, sets it to the current, and subtracts 3000 points
							vars.currentDojo = "Cherry Blossom";
							vars.hasCherryBg = true;
							vars.arcadeTotalScore -= 3000;
						}
					}

					// Checking if the user does not have enough points for the purchase
					else if (vars.arcadeTotalScore <= 3000 && !vars.hasCherryBg) {

						/* Initializing the equip locked sound effect for the blades section using try-catch */
						try {

							/* Initializing the file object for the music */
							File lockedItem = new File("sfx/equip-locked.wav");

							/* Opening an audio input stream for the music */
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(lockedItem);

							/* Retrieving and opening the audio clip from the newly-created stream */
							vars.lockedItem = AudioSystem.getClip();
							vars.lockedItem.open(audioIn);

							vars.lockedItem.start();
						} 

						catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

						// Inform the user in a showMessageDialog pane that they lack the points for the cherry blossom background
						JOptionPane.showMessageDialog(null, "You don't have enough points for the Cherry Blossom background.. \nYou need " + Integer.toString(3000 - vars.arcadeTotalScore) + " more points in order to buy it.", "Not Enough Points", JOptionPane.ERROR_MESSAGE);
					}

					// Checking if the user has already unlocked the cherry blossom background
					else if (vars.hasCherryBg) {

						/* Initializing the equip new wallpaper sound effect for the backgrounds section using try-catch */
						try {

							/* Initializing the file object for the music */
							File equipBg = new File("sfx/equip-new-wallpaper.wav");

							/* Opening an audio input stream for the music */
							AudioInputStream audioIn = AudioSystem.getAudioInputStream(equipBg);

							/* Retrieving and opening the audio clip from the newly-created stream */
							vars.equipBg= AudioSystem.getClip();
							vars.equipBg.open(audioIn);

							vars.equipBg.start();
						} 

						catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

						// Sets the current dojo to the cherry blossom and sets the boolean flag to true
						vars.currentDojo = "Cherry Blossom";
						vars.hasCherryBg = true;
					}
				} 

				// Check if the user clicks on the classic dojo background
				if (e.getX() >= 150 && e.getX() <= 370 && e.getY() >= 210 & e.getY() <= 330) {

					/* Initializing the equip new wallpaper sound effect for the backgrounds section using try-catch */
					try {

						/* Initializing the file object for the music */
						File equipBg = new File("sfx/equip-new-wallpaper.wav");

						/* Opening an audio input stream for the music */
						AudioInputStream audioIn = AudioSystem.getAudioInputStream(equipBg);

						/* Retrieving and opening the audio clip from the newly-created stream */
						vars.equipBg= AudioSystem.getClip();
						vars.equipBg.open(audioIn);

						vars.equipBg.start();
					} 

					catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

					// Sets the current dojo to the classic dojo
					vars.currentDojo = "Classic Dojo";
				}
			}

			// Check if the user clicks on the backgrounds section button
			if (e.getX() >= 650 && e.getX() <= 900 && e.getY() >= 100 && e.getY() <= 170) {

				/* Initializing the click sound effect for the backgrounds section using try-catch */
				try {

					/* Initializing the file object for the music */
					File uiBtn = new File("sfx/ui-button-click.wav");

					/* Opening an audio input stream for the music */
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(uiBtn);

					/* Retrieving and opening the audio clip from the newly-created stream */
					vars.uiBtn = AudioSystem.getClip();
					vars.uiBtn.open(audioIn);

					vars.uiBtn.start();
				} 

				catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

				/* Setting the specialsSection boolean to true and the bladeSelection & bgSection booleans to false */
				vars.specialsSection = true;
				vars.bgSection = false;
				vars.bladeSection = false;
			}

			// Check if the user clicks on the background selection
			if (vars.specialsSection) {

				// Check if the user clicks on the 2x score powerup
				if (e.getX() >= 140 && e.getX() <= 265 && e.getY() >= 210 & e.getY() <= 328) {

					/* Checking if the user does not already have the 2x score powerup */
					if (!vars.has2x) {

						/* Check if user has enough points to buy the 2x score powerup */
						if (vars.arcadeTotalScore >= 2250) {

							// Confirming the purchase with a showConfirmDialog pane
							vars.confirm2x = JOptionPane.showConfirmDialog(null, "You have enough points to buy the 2x points special! \nAre you sure you want to buy the 2x points special?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);

							// Checking if the user confirms the purchase
							if (vars.confirm2x == 0) {

								// Unlocks the 2x score powerup, enables it, and subtracts 2250 points
								vars.has2x = true;
								vars.on2x = true;
								vars.arcadeTotalScore -= 2250;
							}
						}

						// Checking if the user does not have enough points for the purchase
						else if (vars.arcadeTotalScore <= 2250) {

							/* Initializing the equip locked sound effect for the powerups section using try-catch */
							try {

								/* Initializing the file object for the music */
								File lockedItem = new File("sfx/equip-locked.wav");

								/* Opening an audio input stream for the music */
								AudioInputStream audioIn = AudioSystem.getAudioInputStream(lockedItem);

								/* Retrieving and opening the audio clip from the newly-created stream */
								vars.lockedItem = AudioSystem.getClip();
								vars.lockedItem.open(audioIn);

								vars.lockedItem.start();
							} 

							catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

							// Inform the user in a showMessageDialog pane that they lack the points for the 2x score powerup
							JOptionPane.showMessageDialog(null, "You don't have enough points for the 2x points powerup. \nYou need " + Integer.toString(2250 - vars.arcadeTotalScore) + " more points in order to buy it.", "Not Enough Points", JOptionPane.ERROR_MESSAGE);
						}
					}

					// Checking if the user has already unlocked the 2x score powerup and it is on
					else if (vars.has2x && vars.on2x) {	

						// Sets the 2x score boolean flag to false
						vars.on2x = false;
					}

					// Checking if the user has already unlocked the 2x score powerup and it is not on
					else if (vars.has2x && !vars.on2x) {

						// Sets the 2x score boolean flag to true
						vars.on2x = true;
					}
				}

				// Check if the user clicks on the slower bombs powerup
				if (e.getX() >= 445 && e.getX() <= 545 && e.getY() >= 220 & e.getY() <= 320) {

					/* Checking if the user does not already have the slower bombs powerup */
					if (!vars.hasSlowBomb) {

						/* Check if user has enough points to buy the slower bombs powerup */
						if (vars.arcadeTotalScore >= 3500) {

							// Confirming the purchase with a showConfirmDialog pane
							vars.confirmSlowBomb = JOptionPane.showConfirmDialog(null, "You have enough points to buy the slow bomb special! \nAre you sure you want to buy the slow bomb special?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);

							// Checking if the user confirms the purchase
							if (vars.confirmSlowBomb == 0) {

								// Unlocks the slower bombs powerup, enables it, and subtracts 3500 points
								vars.hasSlowBomb = true;
								vars.onSlowBomb = true;
								vars.arcadeTotalScore -= 3500;
							}
						}

						// Checking if the user does not have enough points for the purchase
						else if (vars.arcadeTotalScore <= 3500) {

							/* Initializing the equip locked sound effect for the powerups section using try-catch */
							try {

								/* Initializing the file object for the music */
								File lockedItem = new File("sfx/equip-locked.wav");

								/* Opening an audio input stream for the music */
								AudioInputStream audioIn = AudioSystem.getAudioInputStream(lockedItem);

								/* Retrieving and opening the audio clip from the newly-created stream */
								vars.lockedItem = AudioSystem.getClip();
								vars.lockedItem.open(audioIn);

								vars.lockedItem.start();
							} 

							catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

							// Inform the user in a showMessageDialog pane that they lack the points for the slower bombs powerup
							JOptionPane.showMessageDialog(null, "You don't have enough points for the slow bomb powerup. \nYou need " + Integer.toString(3500 - vars.arcadeTotalScore) + " more points in order to buy it.", "Not Enough Points", JOptionPane.ERROR_MESSAGE);
						}
					}

					// Checking if the user has already unlocked the slower bombs powerup and it is on
					else if (vars.hasSlowBomb && vars.onSlowBomb) {	

						// Sets the slower bombs boolean flag to false
						vars.onSlowBomb = false;
					}

					// Checking if the user has already unlocked the slower bombs powerup and it is not on
					else if (vars.hasSlowBomb && !vars.onSlowBomb) {

						// Sets the slower bombs boolean flag to true
						vars.onSlowBomb = true;
					}
				}

				// Check if the user clicks on the tri-fruit powerup
				if (e.getX() >= 720 && e.getX() <= 820 && e.getY() >= 205 & e.getY() <= 305) {

					/* Checking if the user does not already have the tri-fruit powerup */
					if (!vars.hasTriFruit) {

						/* Check if user has enough points to buy the tri-fruit powerup */
						if (vars.arcadeTotalScore >= 5000) {

							// Confirming the purchase with a showConfirmDialog pane
							vars.confirmTriFruit = JOptionPane.showConfirmDialog(null, "You have enough points to buy the tri-fruit special! \nAre you sure you want to buy the tri-fruit special?", "Confirm Purchase", JOptionPane.YES_NO_OPTION);

							// Checking if the user confirms the purchase
							if (vars.confirmTriFruit == 0) {

								// Unlocks the tri-fruit powerup, enables it, and subtracts 5000 points
								vars.hasTriFruit = true;
								vars.onTriFruit = true;
								vars.arcadeTotalScore -= 5000;

								// Restarts the game because there are now 3 fruits instead of 6
								restartGame(false);
							}
						}

						// Checking if the user does not have enough points for the purchase
						else if (vars.arcadeTotalScore <= 5000) {

							/* Initializing the equip locked sound effect for the powerups section using try-catch */
							try {

								/* Initializing the file object for the music */
								File lockedItem = new File("sfx/equip-locked.wav");

								/* Opening an audio input stream for the music */
								AudioInputStream audioIn = AudioSystem.getAudioInputStream(lockedItem);

								/* Retrieving and opening the audio clip from the newly-created stream */
								vars.lockedItem = AudioSystem.getClip();
								vars.lockedItem.open(audioIn);

								vars.lockedItem.start();
							} 

							catch (Exception e1) {} // "e" paramater not possible, because mousePerformed method already uses "e" paramater

							// Inform the user in a showMessageDialog pane that they lack the points for the slower bombs powerup
							JOptionPane.showMessageDialog(null, "You don't have enough points for the tri-fruit powerup. \nYou need " + Integer.toString(5000 - vars.arcadeTotalScore) + " more points in order to buy it.", "Not Enough Points", JOptionPane.ERROR_MESSAGE);
						}
					}

					// Checking if the user has already unlocked the tri-fruit powerup and it is on
					else if (vars.hasTriFruit && vars.onTriFruit) {	

						// Sets the tri-fruit boolean flag to false
						vars.onTriFruit = false;
					}

					// Checking if the user has already unlocked the tri-fruit powerup and it is not on
					else if (vars.hasTriFruit && !vars.onTriFruit) {

						// Sets the tri-fruit boolean flag to true
						vars.onTriFruit = true;
					}
				}

			}
		}

		/* Checking if either the arcade mode, shop mode, or profile mode are selected */
		if (vars.arcadeMode || vars.shopMode || vars.profileMode) {

			/* Checking if the player has pressed in the bounds of the back option */
			if (e.getX() >= vars.x_pos_quit - 90 && e.getX() <= vars.x_pos_quit + 210 && e.getY() >= vars.y_pos_quit && e.getY() <= vars.y_pos_quit + 210) {

				/* Checking if the player has selected the arcade mode */
				if (vars.arcadeMode) {

					/* Restarts the game mode */
					restartGame(false);
				}

				/* Stopping the music for the game modes */
				vars.dg.stop();
				vars.gt.stop();

				/* Starting the background music for the main menu */
				vars.bg.start();
				vars.bg.loop(vars.bg.LOOP_CONTINUOUSLY);

				/* Setting the shopMode, arcadeMode, storyMode, and profileMode booleans to false */
				vars.shopMode = false;
				vars.arcadeMode = false;
				vars.storyMode = false;
				vars.profileMode = false;

				/* Renaming the title of the frame to suit the main menu */
				frame.setTitle("Fruit Ninja");

				/* Repaints the frame's components */
				repaint();
			}
		}
	}

	/* The mouseMoved method checks if the mouse's X or Y-positions have altered */
	public void mouseMoved(MouseEvent e) {

		/* Retrieving and storing the mouse's position */
		vars.mouseX = e.getX();
		vars.mouseY = e.getY();
	}

	/* The mouseDragged method checks if the mouse is moved while simultaneously held down */
	public void mouseDragged(MouseEvent e) {

		/* Retrieving and storing the mouse's position */
		vars.mouseX = e.getX();
		vars.mouseY = e.getY();

		/* Update the mask for each projectile */
		vars.melonBox = new Rectangle2D.Double(vars.xPosMelon, vars.yPosMelon, 125, 125);
		vars.pineappleBox = new Rectangle2D.Double(vars.xPosPineapple, vars.yPosPineapple, 125, 125);
		vars.coconutBox = new Rectangle2D.Double(vars.xPosCoconut, vars.yPosCoconut, 125, 125);
		vars.mangoBox = new Rectangle2D.Double(vars.xPosMango, vars.yPosMango, 125, 125);
		vars.strawberryBox = new Rectangle2D.Double(vars.xPosStrawberry, vars.yPosStrawberry, 125, 125);
		vars.appleBox = new Rectangle2D.Double(vars.xPosApple, vars.yPosApple, 125, 125);
		vars.bombBox = new Rectangle2D.Double(vars.xPosBomb, vars.yPosBomb, 125, 125);

		/* Checking if the mouse is dragging inside the mango hitbox */
		if (vars.mangoBox.contains(e.getX(), e.getY())) {

			/* Calling the fruitSliced method for the mango */
			fruitSliced("Mango");
		}

		/* Checking if the mouse is dragging inside the melon hitbox */
		if (vars.melonBox.contains(e.getX(), e.getY())) {

			/* Calling the fruitSliced method for the melon */
			fruitSliced("Melon");
		}

		/* Checking if the mouse is dragging inside the bomb hitbox */
		if (vars.bombBox.contains(e.getX(), e.getY())) {

			/* Calling the fruitSliced method for the bomb */
			fruitSliced("Bomb");
		}

		/* Checking if the mouse is dragging inside the pineapple hitbox */
		if (vars.pineappleBox.contains(e.getX(), e.getY())) {

			/* Calling the fruitSliced method for the pineapple */
			fruitSliced("Pineapple");
		}

		/* Checking if the mouse is dragging inside the coconut hitbox */
		if (vars.coconutBox.contains(e.getX(), e.getY())) {

			/* Calling the fruitSliced method for the coconut */
			fruitSliced("Coconut");
		}

		/* Checking if the mouse is dragging inside the strawberry hitbox */
		if (vars.strawberryBox.contains(e.getX(), e.getY())) {

			/* Calling the fruitSliced method for the strawberry */
			fruitSliced("Strawberry");
		}

		/* Checking if the mouse is dragging inside the apple hitbox */
		if (vars.appleBox.contains(e.getX(), e.getY())) {

			/* Calling the fruitSliced method for the apple */
			fruitSliced("Apple");
		}
	}

	/* The fruitSliced method is used when any fruit is sliced (dragged) through */
	public void fruitSliced(String fruit) {

		/* Check if the projectile (fruit) was not the bomb */
		if (fruit != "Bomb") {

			// Increase the score by a random number from 1-2 if an apple or strawberry is sliced
			if (fruit == "Apple" || fruit == "Strawberry") {

				// If 2x points is enabled, the score will be increased by 2-4 points
				if (vars.on2x) {
					vars.arcadeScore += ((int)(Math.random() * 2 + 1)) * (2);
				} else {
					vars.arcadeScore += (int)(Math.random() * 2 + 1);
				}
			} 

			// Increase the score by a random number from 2-3 if a mango is sliced
			else if (fruit == "Mango") {

				// If 2x points is enabled, the score will be increased by 4-6 points
				if (vars.on2x) {
					vars.arcadeScore += ((int)(Math.random() * 3 + 2)) * (2);
				} else {
					vars.arcadeScore += (int)(Math.random() * 3 + 2);
				}
			} 

			// Increase the score by a random number from 3-4 if a coconut or pineapple is sliced
			else if (fruit == "Coconut" || fruit == "Pineapple") {

				// If 2x points is enabled, the score will be increased by 6-8 points
				if (vars.on2x) {
					vars.arcadeScore += ((int)(Math.random() * 4 + 3)) * (2);
				} else {
					vars.arcadeScore += (int)(Math.random() * 4 + 3);
				}
			} 

			// Increase the score by a random number from 3-5 if a melon is sliced
			else if (fruit == "Melon") {

				// If 2x points is enabled, the score will be increased by 6-10 points
				if (vars.on2x) {
					vars.arcadeScore += ((int)(Math.random() * 3 + 5)) * (2);
				} else {
					vars.arcadeScore += (int)(Math.random() * 3 + 5);
				}
			}

		}

		/* Checking if the current score is greater than the high score */
		if (vars.arcadeScore > vars.arcadeHighScore) {

			/* Changing the high score to the current score */
			vars.arcadeHighScore = vars.arcadeScore;
		}

		/* Checking if the fruit projectile was the melon */
		if (fruit == "Melon") {

			/* Retreiving the coordinates at which the fruit was sliced and enabling the splatter */
			vars.xPosMelonSplatter = vars.xPosMelon;
			vars.yPosMelonSplatter = vars.yPosMelon;
			vars.drawMelonSplatter = true;

			/* Reset the Y-position of the fruit to make it appear at the bottom */
			vars.yPosMelon = 810;
			vars.xPosMelon = vars.rand.nextInt(700) + 50;

			try { // Open an audio input stream.
				File sliceMelon = new File("sfx/Impact-Watermelon.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(sliceMelon);

				vars.sliceWatermelon = AudioSystem.getClip();
				vars.sliceWatermelon.open(audioIn);
				vars.sliceWatermelon.start();	

			} catch(Exception e) {}
		}

		/* Checking if the fruit projectile was the pineapple */
		else if (fruit == "Pineapple") {

			/* Retreiving the coordinates at which the fruit was sliced and enabling the splatter */
			vars.xPosPineappleSplatter = vars.xPosPineapple;
			vars.yPosPineappleSplatter = vars.yPosPineapple;
			vars.drawPineappleSplatter = true;

			/* Reset the Y-position of the fruit to make it appear at the bottom */
			vars.yPosPineapple = 810;
			vars.xPosPineapple = vars.rand.nextInt(700) + 50;

			try { // Open an audio input stream.
				File slicePineapple = new File("sfx/Impact-Pineapple.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(slicePineapple);

				vars.slicePineapple = AudioSystem.getClip();
				vars.slicePineapple.open(audioIn);
				vars.slicePineapple.start();	

			} catch(Exception e) {}
		}

		/* Checking if the fruit projectile was the coconut */
		else if (fruit == "Coconut") {

			/* Retreiving the coordinates at which the fruit was sliced and enabling the splatter */
			vars.xPosCoconutSplatter = vars.xPosCoconut;
			vars.yPosCoconutSplatter = vars.yPosCoconut;
			vars.drawCoconutSplatter = true;

			/* Reset the Y-position of the fruit to make it appear at the bottom */
			vars.yPosCoconut = 810;
			vars.xPosCoconut = vars.rand.nextInt(700) + 50;

			try { // Open an audio input stream.
				File sliceCoconut = new File("sfx/Impact-Coconut.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(sliceCoconut);

				vars.sliceCoconut = AudioSystem.getClip();
				vars.sliceCoconut.open(audioIn);
				vars.sliceCoconut.start();	

			} catch(Exception e) {}
		}

		/* Checking if the fruit projectile was the mango */
		else if (fruit == "Mango") {

			/* Retreiving the coordinates at which the fruit was sliced and enabling the splatter */
			vars.xPosMangoSplatter = vars.xPosMango;
			vars.yPosMangoSplatter = vars.yPosMango;
			vars.drawMangoSplatter = true;

			/* Reset the Y-position of the fruit to make it appear at the bottom */
			vars.yPosMango = 810;
			vars.xPosMango = vars.rand.nextInt(700) + 50;

			try { // Open an audio input stream.
				File sliceMango = new File("sfx/Impact-Mango.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(sliceMango);

				vars.sliceMango = AudioSystem.getClip();
				vars.sliceMango.open(audioIn);
				vars.sliceMango.start();	

			} catch(Exception e) {}
		}

		/* Checking if the fruit projectile was the strawberry */
		else if (fruit == "Strawberry") {

			/* Retreiving the coordinates at which the fruit was sliced and enabling the splatter */
			vars.xPosStrawberrySplatter = vars.xPosStrawberry;
			vars.yPosStrawberrySplatter = vars.yPosStrawberry;
			vars.drawStrawberrySplatter = true;

			/* Reset the Y-position of the fruit to make it appear at the bottom */
			vars.yPosStrawberry = 810;
			vars.xPosStrawberry = vars.rand.nextInt(700) + 50;

			try { // Open an audio input stream.
				File sliceStrawberry = new File("sfx/Impact-Strawberry.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(sliceStrawberry);

				vars.sliceStrawberry = AudioSystem.getClip();
				vars.sliceStrawberry.open(audioIn);
				vars.sliceStrawberry.start();	

			} catch(Exception e) {}
		}

		/* Checking if the fruit projectile was the apple */
		else if (fruit == "Apple") {

			/* Retreiving the coordinates at which the fruit was sliced and enabling the splatter */
			vars.xPosAppleSplatter = vars.xPosApple;
			vars.yPosAppleSplatter = vars.yPosApple;
			vars.drawAppleSplatter = true;

			/* Reset the Y-position of the fruit to make it appear at the bottom */
			vars.yPosApple = 810;
			vars.xPosApple = vars.rand.nextInt(700) + 50;

			try { // Open an audio input stream.
				File sliceApple = new File("sfx/Impact-Apple.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(sliceApple);

				vars.sliceApple = AudioSystem.getClip();
				vars.sliceApple.open(audioIn);
				vars.sliceApple.start();	

			} catch(Exception e) {}
		}

		/* Checking if the fruit projectile was the bomb */
		else if (fruit == "Bomb") {

			/* Reset the Y-position of the bomb to make it appear at the bottom */
			vars.yPosBomb = 810;
			vars.xPosBomb = vars.rand.nextInt(700) + 50;

			/* Decrement the lives remaining by 1 */
			vars.livesRemaining -= 1;

			try { // Open an audio input stream.
				File bombExplode = new File("sfx/Sound/Bomb-explode.wav");
				AudioInputStream audioIn = AudioSystem.getAudioInputStream(bombExplode);

				vars.bombExplode = AudioSystem.getClip();
				vars.bombExplode.open(audioIn);
				vars.bombExplode.start();	

			} catch(Exception e) {}
		}
	}

	/* The generateFact method is used to prompt the user with a random fact after the game has been lost */
	public String generateFact() {

		/* Declaring and initializing array of size 44 and generating random index */
		String fruitFacts[] = new String[44];
		int randomFact = (int)(Math.random() * 44);

		/* This block of code initializes the entire array of fruit facts manually
		 * There is whitespace between every 3-5 lines for readability purposes
		 */
		
		fruitFacts[0] = "Unripe pineapples are poisonous.";
		fruitFacts[1] = "The passion fruit is the national flower of Paraguay.";
		fruitFacts[2] = "Japanese plums actually originated in China.";
		fruitFacts[3] = "In Kerala in South India, coconut flowers \nmust be present during a marriage ceremony.";
		fruitFacts[4] = "Early explorers used watermelons as \ncanteens to carry their water supplies.";

		fruitFacts[5] = "Strawberries are the only fruit with \ntheir seeds on the outside of the fruit.";
		fruitFacts[6] = "Mangoes can be ripened quickly by \nplacing them in a paper bag with a ripe banana.";
		fruitFacts[7] = "Kiwi fruit was originally known by \nits Chinese name, yang tao (sunny peach).";
		fruitFacts[8] = "Watermelons are 92% water!";

		fruitFacts[9] = "One third of the world's pineapples \nare produced in Hawaii.";
		fruitFacts[10] = "The average strawberry has around 200 seeds.";
		fruitFacts[11] = "The second most popular mango \nvariety grown in Australia is the R2E2!";
		fruitFacts[12] = "The kiwi fruit is declared as \nthe national fruit of China.";

		fruitFacts[13] = "Almonds are members of the peach family.";
		fruitFacts[14] = "Pineapples were once very rare, \nthus the name 'King of Fruits'.";
		fruitFacts[15] = "The watermelon is cousins with \ncucumbers, pumpkins, and squash.";
		fruitFacts[16] = "A plum is a drupe, a pitted fruit related \nto the nectarine, peach, and apricot.";

		fruitFacts[17] = "Oranges are technically a \nhesperidium, a kind of berry.";
		fruitFacts[18] = "Oranges are the most commonly grown tree in the world.";
		fruitFacts[19] = "Eating bananas between meals can \nhelp expectant mothers avoid morning sickness.";
		fruitFacts[20] = "Peaches were once known as Persian apples.";

		fruitFacts[21] = "Research shows that eating apples may \nreduce the risk of many kinds of cancer.";
		fruitFacts[22] = "The Romans used strawberries for a \nlong list of medical remedies.";
		fruitFacts[23] = "If you plant a single orange seed, \nyou will probably get more than one plant.";
		fruitFacts[24] = "In China, the peach is a symbol \nof longevity and good luck.";

		fruitFacts[25] = "Bananas are not trees, they are giant herbs.";
		fruitFacts[26] = "Mangoes have as much vitamin C as oranges.";
		fruitFacts[27] = "The largest watermelon ever \ngrown was 262 pounds, or 119 kg!";
		fruitFacts[28] = "Lemon and salt can be used to treat \nrust spots and to clean copper pots.";

		fruitFacts[29] = "Apple seeds are mildly poisonous, \nbut not enough to be dangerous to humans.";
		fruitFacts[30] = "The prickly pear is actually a type of \ncactus and considered a noxious weed in Australia.";
		fruitFacts[31] = "Apples have almost 5 times the \nantioxidant capacity of bananas.";
		fruitFacts[32] = "The purple-fruited species of \npassion fruit self-fertilizes.";

		fruitFacts[33] = "People allergic to latex are likely \nto also be allergic to kiwi fruit.";
		fruitFacts[34] = "More fresh mangoes are eaten every \nday than any other fruit in the world.";
		fruitFacts[35] = "Strawberries are a member of the rose family.";
		fruitFacts[36] = "Nothing rhymes with orange.";

		fruitFacts[37] = "Over 1,200 varieties of watermelons \nare grown worldwide in 96 countries.";
		fruitFacts[38] = "Mangoes belong to the same family \nof plants as poison ivy.";
		fruitFacts[39] = "There are more than 20 billion \ncoconuts harvested each year!";
		fruitFacts[40] = "Lemons contain more sugar than strawberries.";

		fruitFacts[41] = "Nectarines are just peaches without the fuzz.";
		fruitFacts[42] = "The peach is a member of the rose family";
		fruitFacts[43] = "Strawberries were used as sacred \nsymbols by Christian stonemasons";

		/* Creates a string for the losing message which includes the fruit fact */
		String loss =  "Good try! \nYou Scored: " + Integer.toString(vars.arcadeScore) + "\nHigh Score: " + Integer.toString(vars.arcadeHighScore) + "\nSensei's Fruit Fact: " + fruitFacts[randomFact];

		/* Returns the newly created string */
		return loss;
	}

	// Sometimes, randomly a hint for the cheat codes in the shop will be generated when the user loses arcade mode
	// This method generates a cheat code hint but most of the time it won't (24 Strings, 4 will be cheat code hints, 20 will be empty strings)
	public String generateHint() {

		/* Declaring and initializing array of size 23 and generating random index */
		String hints[] = new String[23];
		int randomHint = (int)(Math.random() * 23);

		/* Initializes the entire array of hints */
		hints[0] = "Hint: try entering \"supersecretcode\" in the Shop!";
		hints[1] = "Hint: try entering \"reset\" in the Shop!";
		hints[2] = "Hint: try entering \"rockblade\" in the Shop!";
		hints[3] = "Hint: try entering \"sunlight\" in the Shop!";

		// Creates for loop which indexes through the rest of the array
		for (int hint = 4; hint < 23; hint++) {

			// Sets the value to empty
			hints[hint] = "";
		}

		/* Creates a string for the hint message */
		String hint = "\n" + hints[randomHint];

		/* Returns the newly created string */
		return hint;
	}

	/* The restartGame method is used to re-initialize the variables and reset the game mode */
	public void restartGame(boolean playerLost) {

		// Increases the total arcade score with the current score
		vars.arcadeTotalScore += vars.arcadeScore;

		// Resets the splatter for each of the fruits
		vars.drawPineappleSplatter = false;
		vars.drawStrawberrySplatter = false;
		vars.drawCoconutSplatter = false;
		vars.drawAppleSplatter = false;
		vars.drawMangoSplatter = false;
		vars.drawMelonSplatter = false;

		/* Stops the projectile (fruit) timer */
		vars.fruitMoveTimer.stop();

		/* Checking if the player has set the playerLost parameter to true and arcade mode is selected */
		if (playerLost && vars.arcadeMode) {

			// Generates a loss message and hint
			String loss = generateFact(); 
			String hint = generateHint();

			/* Display the losing message */
			JOptionPane.showMessageDialog(null, loss + hint, "Fruit Ninja", JOptionPane.PLAIN_MESSAGE, vars.senseiCoconut);
		}

		/* Setting arcadeMode to false, resetting score, and resetting lives */
		vars.arcadeMode = false;
		vars.arcadeScore = 0;
		vars.livesRemaining = 3;

		/* Resetting the Y-positions of each fruit and bomb */
		vars.yPosMelon = 810;
		vars.yPosPineapple = 810;
		vars.yPosCoconut = 810;
		vars.yPosMango = 810;
		vars.yPosStrawberry = 810;
		vars.yPosApple = 810;
		vars.yPosBomb = 810;

		/* Update the mask for each projectile */
		vars.melonBox = new Rectangle2D.Double(vars.xPosMelon, vars.yPosMelon, 125, 125);
		vars.pineappleBox = new Rectangle2D.Double(vars.xPosPineapple, vars.yPosPineapple, 125, 125);
		vars.coconutBox = new Rectangle2D.Double(vars.xPosCoconut, vars.yPosCoconut, 125, 125);
		vars.mangoBox = new Rectangle2D.Double(vars.xPosMango, vars.yPosMango, 125, 125);
		vars.strawberryBox = new Rectangle2D.Double(vars.xPosStrawberry, vars.yPosStrawberry, 125, 125);
		vars.appleBox = new Rectangle2D.Double(vars.xPosApple, vars.yPosApple, 125, 125);
		vars.bombBox = new Rectangle2D.Double(vars.xPosBomb, vars.yPosBomb, 125, 125);

		/* Renaming the title of the frame to suit the main menu */
		frame.setTitle("Fruit Ninja");

		// Reset startGameSec back to 3 so that the "Ready?, Go" text appears when you want to play again
		vars.startGameSec = 3;
	}


	/* Unimplemented methods from MouseListener */
	public void mouseClicked(MouseEvent e) {}
	public void mouseReleased(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}

}
