package edu.ncsu.csc216.transit.airport.travelers;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * TrustedTravelers only enter the TSA lines and ordinary lines
 * @author zcollins
 *
 */
public class TrustedTraveler extends Passenger {

	/** Max process time for a TrustedTraveler */
	public static final int MAX_EXPECTED_PROCESS_TIME = 180;
	/** Field for TrustedTraveler color */
	private Color color;

	/**
	 * Constructor for TrustedTraveler objects
	 * @param arrivalTime arrival time
	 * @param processTime process time
	 * @param myLog reporter mechanism
	 * @throws IllegalArgumentException if process time invalid
	 */
	public TrustedTraveler(int arrivalTime, int processTime, Reporter myLog) {
		super(arrivalTime, processTime, myLog);
		setColor(processTime);
	}

	/**
	 * Sets the color for the TrustedTraveler
	 * @param processTime the actual process time for the Passenger
	 */
	private void setColor(int processTime) {
		// If less than the midway point of process time range, TrustedTravelers
		// are light green. If greater or equal to halfway in range, they are green
		if (processTime < MAX_EXPECTED_PROCESS_TIME / 2) {
			this.color = new Color(153, 255, 153); // light green
		} else if (processTime >= MAX_EXPECTED_PROCESS_TIME / 2){
			this.color = Color.GREEN;
		}
	}

	/**
	 * Places the TrustedTraveler in the shortest available security line
	 * @param securityLines the lines to check
	 */
	@Override
	public void getInLine(TransitGroup securityLines) {
		// Get in line
		super.setLineIndex(pickLine(securityLines));
		((SecurityArea) securityLines).addToLine(this.getLineIndex(), this);
		
	}

	/**
	 * Private helper to pick the shortest available line for TrusterTravelers
	 * @param securityLines lines to check
	 * @return shortest line a TSA customer can enter
	 */
	private int pickLine(TransitGroup securityLines) {
		int shortestTrustedTravelerLine = ((SecurityArea) securityLines).shortestTSAPreLine();
		int shortestRegularLine = ((SecurityArea) securityLines).shortestRegularLine();
		
		if (((SecurityArea) securityLines).lengthOfLine(shortestTrustedTravelerLine) == 0
				|| ((SecurityArea) securityLines).lengthOfLine(shortestTrustedTravelerLine)
				< ((SecurityArea) securityLines).lengthOfLine(shortestRegularLine) * 2) {
			return shortestTrustedTravelerLine;
		} else {
			return shortestRegularLine;
		}
	}

	/**
	 * Get the TrustedTraveler color
	 * @return the Passenger color
	 */
	@Override
	public Color getColor() {
		return this.color;
	}

}
