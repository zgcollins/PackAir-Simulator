package edu.ncsu.csc216.transit.airport.security;

import java.util.ArrayList;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * SecurityArea contains all the security Checkpoints
 * @author zcollins
 *
 */
public class SecurityArea implements TransitGroup {

	/** Max number of Checkpoints */
	public static final int MAX_CHECKPOINTS = 17;
	/** Min number of Checkpoints */
	public static final int MIN_CHECKPOINTS = 3;
	
	/** Error message for Checkpoint creation */
	public static final String ERROR_CHECKPOINTS = "Number of checkpoints must be at least "
			+ MIN_CHECKPOINTS + " and at most " + MAX_CHECKPOINTS + ".";
	/** Error message for wrong security area index */
	public static final String ERROR_INDEX = "Index out of range for this security area";
	
	/** Largest index for the lines reserved for Fast Track Passengers */
	private int largestFastIndex;
	/** Index of the line reserved for TSA PreCheck/Trusted Travelers */
	private int tsaPreIndex;
	/** Collection of Checkpoints */
	private ArrayList<CheckPoint> gates;
	
	/**
	 * Constructor for SecurityArea
	 * @param numCheckpoints the number of Checkpoints to create
	 */
	public SecurityArea(int numCheckpoints) {
		setGates(numCheckpoints);
	}
	
	
	/**
	 * Sets number of gates
	 * @param numGates number of gates
	 * @throws IllegalArgumentException if invalid number of checkpoints
	 */
	private void setGates(int numGates) {
		// Make sure numGates is valid
		if (numGates > MAX_CHECKPOINTS || numGates < MIN_CHECKPOINTS) {
			throw new IllegalArgumentException(ERROR_CHECKPOINTS);
		}
		
		gates = new ArrayList<CheckPoint>();
		
		// Add new checkpoints to the collection
		for (int i = 0; i < numGates; i++) {
			gates.add(new CheckPoint());
		}
		
		
		// Set the FastTrack and TsaPre index
		// One third are FastTrack lines, fractions rounded up
		int numFastTrackGates = numGates / 3;
		if (numGates % 3 != 0) {
			numFastTrackGates++; // Round up if needed
		}
		largestFastIndex = numFastTrackGates - 1;
				
		this.tsaPreIndex = numGates - 1; // Last line is TSA line
	}
	
	/** 
	 * Gets the gates
	 * @return the collection of gates
	 */
	public ArrayList<CheckPoint> getGates() {
		return this.gates;
	}
	
	/**
	 * Adds the Passenger to the line with the given index
	 * @param index the line index to add to
	 * @param p the passenger to add
	 */
	public void addToLine(int index, Passenger p) {
		try {
			gates.get(index).addToLine(p);
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Index out of range for this security area");
		}
	}
	
	/**
	 * Gets the index of the shortest security line that ordinary passengers are allowed to enter
	 * @return shortest regular line index
	 */
	public int shortestRegularLine() {
		int smallestLineSize = Integer.MAX_VALUE;
		int shortestRegularLineIdx = largestFastIndex + 1; 
		
		// Find the shortest regular line in gates
		for (int i = shortestRegularLineIdx; i < tsaPreIndex; i++) {
			if (lengthOfLine(i) < smallestLineSize) {
				smallestLineSize = lengthOfLine(i);
				shortestRegularLineIdx = i;
			}
		}
		return shortestRegularLineIdx;
	}
	
	/**
	 * Gets the shortest FastTrack line index
	 * @return shortest fast line index
	 */
	public int shortestFastTrackLine() {
		int smallestLineSize = Integer.MAX_VALUE;
		int shortestFastTrackLineIdx = 0; 
		
		// Find the shortest Fast line in gates
		for (int i = 0; i <= largestFastIndex ; i++) {
			if (lengthOfLine(i) < smallestLineSize) {
				smallestLineSize = lengthOfLine(i);
				shortestFastTrackLineIdx = i;
			}
		}
		return shortestFastTrackLineIdx;
	}
	
	/**
	 * Gets the shortest TSAPre line index
	 * @return shortest TSA line index
	 */
	public int shortestTSAPreLine() {
		return tsaPreIndex;
	}
	
	/**
	 * Gets the length of the line with the given index
	 * @param idx the index  to check for length
	 * @return the length of line at given index
	 * @throws IllegalArgumentException if the index is out of bounds
	 */
	public int lengthOfLine(int idx) {
		try{
			return gates.get(idx).size();
		} catch (IndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Index out of range for this security area");
		}
	}
	

	/**
	 * Finds the next Passenger to go from the gates
	 * @return the next Passenger, or null if there isn't one
	 */
	@Override
	public Passenger nextToGo() {
		Passenger p = null;
		// Find the front Passenger that matches this next departure time
		boolean foundNext = false;
		for (CheckPoint check: gates) {
			if (check.departTimeNext() == departTimeNext() && !foundNext) {
				p = check.nextToGo();
				foundNext = true;
			}
		}
		return p;
	}

	/**
	 * Returns time next Passenger will finish clearing security, or Integer.MAX_VALUE
	 * if all lines empty
	 * @return time of next Passenger departure
	 */
	@Override
	public int departTimeNext() {
		// Who has the earliest departure time?
		int earliestDepartureTime = Integer.MAX_VALUE;
		for (CheckPoint check: gates) {
			if (check.departTimeNext() < earliestDepartureTime) {
				earliestDepartureTime = check.departTimeNext();
			}
		}
		return earliestDepartureTime;
	}

	/**
	 * Removes and returns next Passenger to clear security
	 * @return the next Passenger to leave security
	 */
	@Override
	public Passenger removeNext() {
		try {
			int gateIdxToRemoveFrom = this.nextToGo().getLineIndex();
			return gates.get(gateIdxToRemoveFrom).removeFromLine();
		} catch (NullPointerException e) {
			return null;
		}
	}

}
