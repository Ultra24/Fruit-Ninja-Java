/* Import all of the required packages */
import java.awt.Font;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import java.util.Random;
import javax.sound.sampled.Clip;
 
public class Variables {
 
	/* Declaring all of the required ImageIcon variables */
	public ImageIcon titleBackground, titleText, dojoBackground, shopBackground, cloudySkiesBackground, cherryBlossomBackground, sunlightBackground, swordBackground;
	public ImageIcon arcadeBtn, storyBtn, shopBtn, quitBtn, backBtn, nextBtn, lockedBtn;
	public ImageIcon threeLives, twoLives, oneLive, noLives, xPopup, gameOver;
	public ImageIcon[] fruitImages;
	
	public ImageIcon scoreFruit, bomb, bombSmall, senseiCoconut, senseiWatermelon, sensei;
	public ImageIcon basicBladeLogo, shinyRedLogo, iceBladeLogo, rockBladeLogo;
	public ImageIcon basicBlade, redBlade, blueBlade, rockBlade;
	public ImageIcon basicDojoLogo, cloudDojoLogo, cherryDojoLogo, codeLogo, sunlightDojoLogo;
	public ImageIcon twoxscore, triFruit, ninjaImg, ninjaWalkImg, profile, profileHead, playerBody, playerBody2;
	
	// Images and X & Y positions for the fruit splatters
	public ImageIcon redSplatterImg, yellowSplatterImg, whiteSplatterImg;
	public boolean drawPineappleSplatter, drawStrawberrySplatter, drawCoconutSplatter, drawAppleSplatter, drawMangoSplatter, drawMelonSplatter;
	public int xPosPineappleSplatter, yPosPineappleSplatter;
	public int xPosStrawberrySplatter, yPosStrawberrySplatter;
	public int xPosCoconutSplatter, yPosCoconutSplatter;
	public int xPosAppleSplatter, yPosAppleSplatter;
	public int xPosMangoSplatter, yPosMangoSplatter;
	public int xPosMelonSplatter, yPosMelonSplatter;
	
	// Images for the story mode
	public ImageIcon senseiText1, mouseDrag;
 
	// Clips for the sound effects and background music
	public static Clip bg, dg, gt, bombExplode, sliceApple, sliceCoconut, sliceMango, sliceWatermelon, slicePineapple, sliceStrawberry;
	public static Clip startSfx, gameOverSfx, quitGameSfx, equipBlade, equipBg, lockedItem, uiBtn;
	
	// Declaring fonts, all of the same font-family but different size
	public Font fBig, fLarge, fMed, fSmall;
 
	// Timers for the loading screen, "ready, go?" text before game starts, movement of fruit time, and player cutscene movement timer
	public Timer titleTimer, startGameTimer, fruitMoveTimer, xPopupTimer, movementTimer;;
	public boolean arcadeMode, storyMode, shopMode, profileMode, readyStart, namePhase, drawXPopup, storyFinished;
	public boolean bladeSection, bgSection, specialsSection, secretFound, resetFound, rockFound, sunlightFound;
	public String startGame, currentBlade, currentDojo;
	public Ellipse2D arcadeHitBox;
	
	// Hit boxes for the ninja and the blade (for story mode)
	public Rectangle2D ninjaHitBox, bladeHitBox;
	
	// Initializing Random class
	public Random rand = new Random();
	
	// X and Y pos of the mouse for the blade 
	public int mouseX, mouseY;
	
	// boolean to move the player right in the story mode cutscene
	public boolean right;
	
	// Hit boxes for the fruits
	public Rectangle2D melonBox, pineappleBox, coconutBox, mangoBox, strawberryBox, appleBox, bombBox;
	
	// Booleans for the fruits moving upwards
	public boolean melonUp, pineappleUp, coconutUp, mangoUp, strawberryUp, appleUp, bombUp;
 
	// Other X and Y positions for each of the projectiles and the buttons in the main menu
	public int titleCount, x_pos_arcade, y_pos_arcade, x_pos_story, y_pos_story, x_pos_shop, y_pos_shop, x_pos_quit, y_pos_quit; 
	public int arcadeScore, arcadeHighScore, arcadeTotalScore, shopPoints, startGameSec, livesRemaining, xPopupCount, ninjaXPos;
	public int xPosMelon, yPosFruit, xPosPineapple, xPosCoconut, xPosMango, xPosStrawberry, xPosApple, xPosBomb;
	public int yPosMelon, yPosPineapple, yPosCoconut, yPosMango, yPosStrawberry, yPosApple, yPosBomb;
	
	// More miscellaneous information for profile and story
	public String name, loss, cheatCode;
	public int nameWidth, codesCollected, storyLevel, moveDelay;
	
	// String array to hold the sensei's fruit facts
	public String[] fruitFacts;
	
	// Integer that will be random, the number that it is will be the array index used for the random fact
	public int randomFact;
	
	// Integers and booleans to check if the user has unlocked shop items
	public int confirmShinyRed, confirmIcySharp, confirmCloud, confirmCherry, confirm2x, confirmSlowBomb, confirmTriFruit;
	public boolean hasShinyRed, hasIcySharp, hasCloudBg, hasCherryBg, has2x, hasSlowBomb, on2x, onSlowBomb, hasTriFruit, onTriFruit, collided, seenShop;
 
	/* Creates the constructor to initiliaze variables and frame */
	public Variables() {
 
		/* Initializing all of the required ImageIcons */
		titleBackground = new ImageIcon("images/start.png");
		titleText = new ImageIcon("images/game-logo.png");
		dojoBackground = new ImageIcon("images/wood-bg.png");
		shopBackground = new ImageIcon("images/shop-bg.png");
		swordBackground = new ImageIcon("images/swordBackground.png");
		
		sunlightBackground = new ImageIcon("images/backgrounds/sunlightdojo.png");
		cloudySkiesBackground = new ImageIcon("images/cloudy-skies-bg.png");
		cherryBlossomBackground = new ImageIcon("images/sakura.png");
 
		arcadeBtn = new ImageIcon("images/arcade-btn.png");
		storyBtn = new ImageIcon("images/story-btn.png");
		shopBtn = new ImageIcon("images/shop-btn.png");
		quitBtn = new ImageIcon("images/quit-btn.png");
		backBtn = new ImageIcon("images/back-btn.png");
		nextBtn = new ImageIcon("images/next-btn.png");
		lockedBtn = new ImageIcon("images/locked.png");
 
		threeLives = new ImageIcon("images/three-lives.png");
		twoLives = new ImageIcon("images/two-lives.png");
		oneLive = new ImageIcon("images/one-live.png");
		noLives = new ImageIcon("images/no-lives.png");
		
		xPopup = new ImageIcon("images/x_popup.png");
		gameOver = new ImageIcon("images/game_over.png");
		codeLogo = new ImageIcon("images/codes.png");
		profile = new ImageIcon("images/profile.png");
		profileHead = new ImageIcon("images/profile-head.png");
		playerBody = new ImageIcon("images/playerbody.png");
		playerBody2 = new ImageIcon("images/playerbody2.png");
 
		senseiCoconut = new ImageIcon("images/sensei-coconut.png");
		senseiWatermelon = new ImageIcon("images/sensei-watermelon.png");
		sensei = new ImageIcon("images/sensei.png");
		ninjaImg = new ImageIcon("images/ninja.png");
		ninjaWalkImg = new ImageIcon("images/ninja-walk.png");
 
		fruitImages = new ImageIcon[6];
 
		// Initializing the array of fruit images using a for loop
		for (int i = 1; i <= 6; i++) {
			
			fruitImages[i-1] = new ImageIcon("images/fruits/fruit" + i + ".png");
		}
		
		// Initalizing more ImageIcons
		
		basicBladeLogo = new ImageIcon("images/basic_blade.png");
		shinyRedLogo = new ImageIcon("images/Shiny_Red.png");
		iceBladeLogo = new ImageIcon("images/Ice_Blade.png");
		rockBladeLogo = new ImageIcon("images/rockBladeLogo.png");
		
		basicDojoLogo = new ImageIcon("images/backgrounds/basic-dojo.png");
		cloudDojoLogo = new ImageIcon("images/backgrounds/cloud-bg.png");
		cherryDojoLogo = new ImageIcon("images/backgrounds/cherry-blossom.png");
		sunlightDojoLogo = new ImageIcon("images/backgrounds/sunlight-dojo-logo.png");
		
		twoxscore = new ImageIcon("images/2xscore.png");
		triFruit = new ImageIcon("images/trifruit.png");
 
		scoreFruit = new ImageIcon("images/score.png");
		bomb = new ImageIcon("images/bomb.png");
		bombSmall = new ImageIcon("images/bomb-small.png");
		
		basicBlade = new ImageIcon("images/basic_blade_game.png");
		redBlade = new ImageIcon("images/shiny_red_game.png");
		blueBlade = new ImageIcon("images/ice_blade_game.png");
		rockBlade = new ImageIcon("images/rock_blade.png");
		
		senseiText1 = new ImageIcon("images/senseitext1.png");
		mouseDrag = new ImageIcon("images/mousedrag.png");
		
		yellowSplatterImg = new ImageIcon("images/splatter/yellow-splatter.png");
		redSplatterImg = new ImageIcon("images/splatter/red-splatter.png");
		whiteSplatterImg = new ImageIcon("images/splatter/coconut-splatter.png");
		
		/* Initializing the current blade to classic steel (basic blade) */
		currentBlade = "Classic Steel";
		
		/* Initializing the current bg to classic dojo (default bg) */
		currentDojo = "Classic Dojo";
		
		/* Fun facts array of "Sensei's fruit facts" that display after the user loses arcade mode */
		String[] fruitFacts = new String[44];
	
		/* Initializing the choice for arcade mode, shop mode, story mode, and profile mode to false */
		arcadeMode = false;
		storyMode = false;
		shopMode = false;
		profileMode = false;
		
		/* Initializing the lives remaining to 3 by default */
		livesRemaining = 3;
 
		/* Initialzing the fruit to move upwards when the game mode starts */
		melonUp = true;
		pineappleUp = true;
		coconutUp = true;
		mangoUp = true;
		strawberryUp = true;
		appleUp = true;
 
		// When the shop is open, the blade is the default section
		bladeSection = true;
		bgSection = false;
		specialsSection = false;
		
		/* Initializing all of the X and Y positions for each game mode to default values */
		x_pos_arcade = 115;
		y_pos_arcade = 250; 
		
		x_pos_story = 390;
		y_pos_story = 400;
		
		x_pos_shop = 675;
		y_pos_shop = 260;
 
		x_pos_quit = 850;
		y_pos_quit = 650;
 
		// Setting Y-positions for the projectiles to standard values (750)
		yPosMelon = 750;
		yPosPineapple = 750;
		yPosCoconut = 750;
		yPosMango = 750;
		yPosStrawberry = 750;
		yPosApple = 750;
		yPosBomb = 750;
	
		/* Initializing all of the X positions for each projectile (fruit) to random values */
		xPosMelon = rand.nextInt(600) + 50;
		xPosPineapple = rand.nextInt(600) + 50;
		xPosCoconut = rand.nextInt(600) + 50;
		xPosMango = rand.nextInt(600) + 50;
		xPosStrawberry = rand.nextInt(600) + 50;
		xPosApple = rand.nextInt(600) + 50;
		xPosBomb = rand.nextInt(600) + 50;
 
		/* Initializing all of the scores to default values (0) */
		arcadeScore = 0;
		arcadeTotalScore = 0;
		shopPoints = arcadeTotalScore;
 
		/* Initializing the time counters when switching phases */
		startGameSec = 3;
		titleCount = 3;
		xPopupCount = 3;
		moveDelay = 0;
		
		// Starting position of ninja in story mode
		ninjaXPos = 50;
	
		/* Initializing the startGame string to "ready" */
		startGame = "READY?";
		
		// Setting the boolean flags for the shop items to false
		hasShinyRed = false;
		hasIcySharp = false;
		hasCloudBg = false;
		hasCherryBg = false;
		
		// Setting the boolean flags for the ninja movement cutscene to false
		right = false;
		collided = false;
	}
 
}
