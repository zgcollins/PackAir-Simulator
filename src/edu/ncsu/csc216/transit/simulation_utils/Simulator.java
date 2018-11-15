package edu.ncsu.csc216.transit.simulation_utils;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.entrance.PreSecurity;
import edu.ncsu.csc216.transit.airport.entrance.Ticketing;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * Simulator implements the backend process for the simulation
 * @author zcollins
 *
 */
public class Simulator {

	/** Number of Passengers to create */
	private int numPassengers;
	
	/** EventCalendar for the simulation */
	private EventCalendar myCalendar;
	
	/** Log for the simulation */
	private Log log;
	
	/** TransitGroup that hasn't entered security yet */
	private TransitGroup inTicketing;
	/** TransitGroup in security */
	private TransitGroup inSecurity;
	/** Passenger most recently returned by myCalendar.nextToAct() */
	private Passenger currentPassenger;
	
	
	/**
	 * Constructor for Simulator
	 * @param numberOfCheckpoints number checkpoints to make
	 * @param numberOfPassengers number passengers to make
	 * @param trustPct percentage of TrustedTravelers
	 * @param fastPct percentage of FastTrackPassengers
	 * @param ordPct percentage of OrdinaryPassengers
	 * @throws IllegalArgumentException if any parameters are out of bounds
	 */
	public Simulator(int numberOfCheckpoints, int numberOfPassengers, int trustPct, int fastPct, 
			int ordPct)	throws IllegalArgumentException {
		if (numberOfPassengers < 1) {
			throw new IllegalArgumentException("There must be at least one passenger.");
		}
		this.numPassengers = numberOfPassengers;
		
		if (numberOfCheckpoints < 3 || numberOfCheckpoints > 17) {
			throw new IllegalArgumentException("Number of checkpoints must be at least 3 and at "
					+ "most 17.");
		}
		
		if (trustPct < 0 || fastPct < 0 || ordPct < 0) {
			throw new IllegalArgumentException("Percents must be positive.");
		}
		
		if (trustPct + fastPct + ordPct != 100) {
			throw new IllegalArgumentException("Percents must sum to 100.");
		}
		
		setLog(new Log());
		Ticketing.setDistribution(trustPct, fastPct);
		setInSecurity(new SecurityArea(numberOfCheckpoints));
		setInTicketing(new PreSecurity(numPassengers, log));
		setMyCalendar(new EventCalendar(inTicketing, inSecurity));
		setCurrentPassenger(null); // initialize currentPassenger to null
		
	}
	


	/**
	 * Sets the EventCalendar for the simulation
	 * @param myCalendar the myCalendar to set
	 */
	private void setMyCalendar(EventCalendar myCalendar) {
		this.myCalendar = myCalendar;
	}




	/**
	 * Sets the inTicketing TransitGroup
	 * @param inTicketing the inTicketing to set
	 */
	private void setInTicketing(TransitGroup inTicketing) {
		this.inTicketing = inTicketing;
	}




	/**
	 * Sets the inSecurity TransitGroup
	 * @param inSecurity the inSecurity to set
	 */
	private void setInSecurity(TransitGroup inSecurity) {
		this.inSecurity = inSecurity;
	}



	/**
	 * Sets the currentPassenger
	 * @param currentPassenger the currentPassenger to set
	 */
	private void setCurrentPassenger(Passenger currentPassenger) {
		this.currentPassenger = currentPassenger;
	}


	/**
	 * Gets the Simulators reporting mechanism
	 * @return the reporter
	 */
	public Reporter getReporter() {
		return this.log;
	}

	/**
	 * Are there any more steps to complete?
	 * @return true if more steps, false if none
	 */
	public boolean moreSteps() {
		return ((PreSecurity) inTicketing).hasNext() || inSecurity.nextToGo() != null;
	}

	/**
	 * Goes through and executes next step in EventCalendar
	 * @throws IllegalStateException if there is no next Passenger
	 */
	public void step() throws IllegalStateException {
		// Find out the next Passenger to act
		currentPassenger = myCalendar.nextToAct(); // Throws IllegalStateException if no one left
		if (currentPassenger.equals(inTicketing.nextToGo())) {
			// Current passenger is from ticketing
			currentPassenger.getInLine(inSecurity);
			inTicketing.removeNext();
		} else {
			// Current passenger is from security lines
			currentPassenger.clearSecurity();
			inSecurity.removeNext();
		}
	}

	/**
	 * Gets the current index for the current passenger
	 * @return the current passenger's index
	 */
	public int getCurrentIndex() {
		if (currentPassenger == null) {
			return -1;
		} else {
			return currentPassenger.getLineIndex();
		}
	}

	/**
	 * True if Passenger just cleared a security line and finished processing, false
	 * if not
	 * @return true if cleared security, false otherwise
	 */
	public boolean passengerClearedSecurity() {
		// If Passenger is null, return false
		if (currentPassenger == null) {
			return false;
		}
		return !currentPassenger.isWaitingInSecurityLine();
	}
	

	/**
	 * Gets the color of the current passenger
	 * @return the color of the current passenger
	 */
	public Color getCurrentPassengerColor() {
		if (currentPassenger == null) {
			return null;
		} else {
			return currentPassenger.getColor();
		}	
	}


	/**
	 * Sets the simulation log
	 * @param log the log to set
	 */
	private void setLog(Log log) {
		this.log = log;
	}
}
