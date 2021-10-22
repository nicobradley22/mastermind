/*
 * Game.java
 * 
 * Class to model Mastermind game and game mechanics.
 *
 * @author Nico Bradley
 * Aug 5, 2021
 */

import java.util.HashMap; //for keyboard input
import java.util.Random; //for random pattern
import java.util.Scanner; //for keyboard input

public class Game {
	
	/*
	 * Tells whether the game is currently being played.
	 */
	public boolean playing;
	
	/*
	 * Four pegs to represent the secret pattern.
	 */
	private Peg secretPeg1;
	private Peg secretPeg2;
	private Peg secretPeg3;
	private Peg secretPeg4;
	
	/*
	 * Four pegs to represent the user's current guess.
	 */
	private Peg userPeg1;
	private Peg userPeg2;
	private Peg userPeg3;
	private Peg userPeg4;
	
	/*
	 * What guess number the user is on.
	 */
	private int currentGuess;
	
	/*
	 * The total number of allowed guesses.
	 */
	private int totalGuesses;
	
	/*
	 * How many red pins the user receives for feedback based on their guess.
	 */
	private int redPins;
	
	/*
	 * How many white pins the user receives for feedback based on their guess.
	 */
	private int whitePins;
	
	/*
	 * A list of possible peg colors.
	 */
	private String[] colorList = new String[]{"red", "green", "blue", "yellow", "white", "orange"};
	
	/*
	 * Map to map different potential user inputs to corresponding color.
	 */
	private HashMap<String, String> colors;
	
	/*
	 * A Scanner for user input.
	 */
	private Scanner input;
	
	/*
     * Constructor.
	 * POSTCONDITION: The colors map has been populated.
	 * POSTCONDITION: The secret pegs have been created randomly.
     */
	public Game(){
		playing = true;
		currentGuess = 0;
		totalGuesses = 10;
		secretPeg1 = new Peg(randomColor());
		secretPeg2 = new Peg(randomColor());
		secretPeg3 = new Peg(randomColor());
		secretPeg4 = new Peg(randomColor());
		colors = new HashMap<String, String>();
		populateHashMap();
		input = new Scanner(System.in);
	}

	/*
	 * Prints an intro to welcome the user and explain the rules.
	 */
	public void intro() {
		System.out.println("Welcome to Mastermind!");
		System.out.println("I've created a four peg code and it's your job to figure out what my code is.");
		System.out.println("Each peg can be red, green, blue, yellow, white, orange.");
		System.out.println("You get 10 guesses to figure out my code!");
		System.out.println("After each guess, you will recieve feedback.");
		System.out.println("A red pin means that you have guessed the correct color in the correct place.");
		System.out.println("A white pin means that you have guessed the correct color but it is NOT in the correct place.");
		System.out.println("Let's get started!");
		System.out.println();
		}
	
	/*
	 * Prompts the user for a guess and stores guess in four user pegs, then increments currentGuess.
	 * POSTCONDITION: The user's guess is stored in the user peg instance variables.
	 */
	public void getGuess() {
		System.out.println("You have made " + currentGuess + " guesses.");
		System.out.println("What is your guess? (red, green, blue, yellow, white, or orange)");
		System.out.println("1st peg?");
		String firstPeg = input.nextLine().toLowerCase().trim();
		userPeg1 = new Peg(colors.get(firstPeg));
		System.out.println("2nd peg?");
		String secondPeg = input.nextLine().toLowerCase().trim();
		userPeg2 = new Peg(colors.get(secondPeg));
		System.out.println("3rd peg?");
		String thirdPeg = input.nextLine().toLowerCase().trim();
		userPeg3 = new Peg(colors.get(thirdPeg));
		System.out.println("4th peg?");
		String fourthPeg = input.nextLine().toLowerCase().trim();
		userPeg4 = new Peg(colors.get(fourthPeg));
		
		currentGuess++;
	}
	
	/*
	 * Evaluates the user guess and gives feedback using a series of helper methods.
	 */
	public void evalGuess() {
		redPins = 0;
		whitePins = 0;
		
		resetSecretPegs();
		checkForRed();
		checkForWhite();
		giveFeedback();	
	}
	
	/*
	 * Private helper method to determine how many red pins the user's guess should receive.
	 * POSTCONDITION: redPins instance variable is set for correct amount of red pins user should receive.
	 * POSTCONDIGION: Secret and user pegs that have been accounted for by red pins are setChecked() to true.
	 */
	private void checkForRed() {
		//create two Peg arrays, one containing secret pegs and one containing user pegs
		Peg[] secretPegs = new Peg[] {secretPeg1, secretPeg2, secretPeg3, secretPeg4};
		Peg[] userPegs = new Peg[] {userPeg1, userPeg2, userPeg3, userPeg4};
		//loop through two arrays simultaneously checking if user peg color is same as corresponding secret peg color
		for(int i = 0; i < secretPegs.length; i++) {
			if(secretPegs[i].getColor().equals(userPegs[i].getColor())) {
				redPins++;
				secretPegs[i].setChecked(true);
				userPegs[i].setChecked(true);
			}
		}
	}
	
	/*
	 * Private helper method to determine how many white pins the user's guess should receive.
	 * POSTCONDITION: whitePins instance variable is set for correct amount of white pins user should receive.
	 */
	private void checkForWhite() {
		//create two Peg arrays, one containing secret pegs and one containing user pegs
		Peg[] secretPegs = new Peg[] {secretPeg1, secretPeg2, secretPeg3, secretPeg4};
		Peg[] userPegs = new Peg[] {userPeg1, userPeg2, userPeg3, userPeg4};
		//nested loop to check if each secretPeg is accounted for by any userPeg that has not been setChecked() to true
		for(int i = 0; i < secretPegs.length; i++) {
			for(int j = 0; j < userPegs.length; j++) {
				if(secretPegs[i].getColor().equals(userPegs[j].getColor()) && !secretPegs[i].isChecked() && !userPegs[j].isChecked()) {
					whitePins++;
				}
			}
		}
	}
	
	/*
	 * Returns a random color from the colorList.
	 * @return a random color
	 */
	private String randomColor() {
		int rnd = new Random().nextInt(colorList.length);
	    return colorList[rnd];
	}
	
	/*
	 * secretPegs are reset to be not checked yet.
	 * POSTCONDITION: Each secrePeg's checked instance varible is false.
	 */
	private void resetSecretPegs() {
		Peg[] secretPegs = new Peg[] {secretPeg1, secretPeg2, secretPeg3, secretPeg4};
		for(int i = 0; i < secretPegs.length; i++) {
			secretPegs[i].setChecked(false);
		}
	}
	
	/*
	 * Helper method to give user feedback based on red and white pins and also checks to see if user has run out of guesses.
	 */
	private void giveFeedback() {
		if(redPins == 4) {
			System.out.println("Congratulations! You won!");
			playAgain();
		} else if (currentGuess == totalGuesses) {
			System.out.println("Sorry! You're all out of guesses!");
			playAgain();
		} else {
			System.out.println("You recieve " + redPins + " red pins and " + whitePins + " white pins.");
			System.out.println();
		}
	}
	
	/*
	 * Helper method to see if the user wants to play again after a game ends.
	 */
	private void playAgain() {
		System.out.println("Would you like to play again? (y,n)");
		String playAgain = input.nextLine();
		if(playAgain.equals("y")) {
			resetGame();
		} else {
			playing = false;
		}
	}
	
	/*
	 * Helper method to reset the game if the user decides to playAgain.
	 */
	private void resetGame() {
		currentGuess = 0;
		totalGuesses = 10;
		secretPeg1 = new Peg(randomColor());
		secretPeg2 = new Peg(randomColor());
		secretPeg3 = new Peg(randomColor());
		secretPeg4 = new Peg(randomColor());
		colors = new HashMap<String, String>();
		populateHashMap();
		
	}

	/*
	 * Helper method to populate colorList hashMap with potential user inputs and map them to certain colors.
	 */
	private void populateHashMap() {
		for(int i = 0; i < colorList.length; i++) {
			colors.put(colorList[i], colorList[i]);
			colors.put(colorList[i].substring(0, 1), colorList[i]);
		}
	}

}
	
