package edu.ncsu.csc216.transit.airport.travelers;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * OrdinaryPassengers can only enter ordinary security lines
 * @author zcollins
 *
 */
public class OrdinaryPassenger extends Passenger {

	/** Max process time for an OrdinaryPassenger */
	public static final int MAX_EXPECTED_PROCESS_TIME = 150;
	
	/** Field for OrdinaryPassenger color */
	private Color color;

	/**
	 * Constructor for OrdinaryPassenger objects
	 * @param arrivalTime arrival time
	 * @param processTime process time
	 * @param myLog reporter mechanism
	 * @throws IllegalArgumentException if process time invalid
	 */
	public OrdinaryPassenger(int arrivalTime, int processTime, Reporter myLog) {
		super(arrivalTime, processTime, myLog);
		setColor(processTime);
	}
	
	/**
	 * Sets the color for the OrdinaryPassenger
	 * @param processTime the actual process time for the Passenger
	 */
	private void setColor(int processTime) {
		// If less than the midway point of process time range, OrdinaryPassengers
		// are light red. If greater or equal to halfway in range, they are red
		if (processTime < MAX_EXPECTED_PROCESS_TIME / 2) {
			this.color = new Color(255, 153, 153);
		} else if (processTime >= MAX_EXPECTED_PROCESS_TIME / 2){
			this.color = Color.RED;
		}
	}

	/**
	 * Places the OrdinaryPassenger in the shortest available security line
	 * @param securityLines the lines to check
	 */
	@Override
	public void getInLine(TransitGroup securityLines) {
		// Get in line
		super.setLineIndex(((SecurityArea) securityLines).shortestRegularLine());
		((SecurityArea) securityLines).addToLine(this.getLineIndex(), this);
		
	}

	/**
	 * Get the OrdinaryPassenger color
	 * @return the Passenger color
	 */
	@Override
	public Color getColor() {
		return this.color;
	}

}
