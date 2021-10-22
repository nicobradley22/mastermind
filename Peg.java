/*
 * Peg.java
 * 
 * Class to model pegs in Mastermind.
 *
 * @author Nico Bradley
 * Aug 5, 2021
 */
public class Peg {
	
	/*
	 * The color of the peg.
	 */
	private String color;
	
	/*
	 * Whether the peg has been checked and accounted for by a red or white pin.
	 */
	private boolean checked;
	
	/*
	 * Constructor.
	 * @param color A String of the color that the peg should be.
	 */
	public Peg(String color) {
		this.color = color;
		this.checked = false;
	}

	/*
	 * Returns the color of a peg.
	 * @return color A String of the color that a peg is.
	 */
	public String getColor() {
		return color;
	}
	
	/*
	 * Returns the value of the checked instance variable.
	 * @return checked A boolean of whether the peg has been checked and accounted for by a red or white pin.
	 */
	public boolean isChecked() {
		return checked;
	}
	
	/*
	 * Sets the checked instance variable.
	 * @param a boolean to set checked to.
	 */
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
}



