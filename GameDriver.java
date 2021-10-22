/*
 * GameDriver.java
 * 
 * A driver for the Mastermind Game.
 *
 * @author Nico Bradley
 * Aug 5, 2021
 */
public class GameDriver {
	public static void main(String[] args) {
		Game game = new Game();
		game.intro();
		while (game.playing) {
			game.getGuess();
			game.evalGuess();
		}
	}
	
}
