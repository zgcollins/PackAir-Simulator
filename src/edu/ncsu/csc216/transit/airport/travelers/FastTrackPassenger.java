/**
 * Packages and imports
 */
package edu.ncsu.csc216.transit.airport.travelers;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * Creates and manages FastTrackPassenger objects
 * @author zcollins
 */
public class FastTrackPassenger extends Passenger {

	/** Field for FastTrackPassenger color */
	private Color color;
	
	/** Max expected process time */
	public static final int MAX_EXPECTED_PROCESS_TIME =  330;
	
	/**
	 * Constructor for FastTrackPassengers
	 * @param arrivalTime the Passenger's arrival time
	 * @param processTime the Passenger's process time
	 * @param myLog the reporting mechanism for the simulation
	 * @throws IllegalArgumentException if process time invalid
	 * 
	 */
	public FastTrackPassenger(int arrivalTime, int processTime, Reporter myLog) {
		super(arrivalTime, processTime, myLog);
		setColor(processTime);
		
	}

	/**
	 * Sets the FastTrackPassenger color based on their actual process time
	 * @param processTime the process time used to define the color
	 */
	private void setColor(int processTime) {
		// If less than the midway point of process time range, FastTrackPassengers
		// are light blue. If greater or equal to halfway in range, they are blue
		if (processTime < MAX_EXPECTED_PROCESS_TIME / 2) {
			this.color = new Color(153, 153, 255);
		} else if (processTime >= MAX_EXPECTED_PROCESS_TIME / 2){
			this.color = Color.BLUE;
		} 
	}

	/**
	 * Gets the FastTrackPassenger's color
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}


	/**
	 * Method for FastTrackPassengers to enter the line
	 * @param securityLines the lines to check and enter
	 */
	@Override
	public void getInLine(TransitGroup securityLines) {
		super.setLineIndex(pickLine(securityLines));
		((SecurityArea) securityLines).addToLine(this.getLineIndex(), this);
	}
	
	/**
	 * Private helper for choosing shortest available line
	 * @param securityLines the lines to check
	 * @return shortest available line to FastTrackPassengers
	 */
	private int pickLine(TransitGroup securityLines) {
		int shortestFastTrackLine = ((SecurityArea) securityLines).shortestFastTrackLine();
		int shortestRegularLine = ((SecurityArea) securityLines).shortestRegularLine();
		
		if (((SecurityArea) securityLines).lengthOfLine(shortestFastTrackLine) > 
		((SecurityArea) securityLines).lengthOfLine(shortestRegularLine)) {
			return shortestRegularLine;
		} else {
			return shortestFastTrackLine;
		}
	}
}
