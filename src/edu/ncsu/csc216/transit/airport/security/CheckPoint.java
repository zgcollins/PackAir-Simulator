package edu.ncsu.csc216.transit.airport.security;

import java.util.NoSuchElementException;

import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.airport.travelers.PassengerQueue;

/**
 * Checkpoint represents a security checkpoint line and its line, where the 
 * front Passenger is undergoing the actual security process.
 * @author zcollins
 *
 */
public class CheckPoint {
	/** Time that the last passenger in line will clear security and leave the line */
	private int timeWhenAvailable;
	
	/** PassengerQueue to represent the Checkpoint line */
	private PassengerQueue line;
	
	
	/**
	 * Constructor for Checkpoints
	 */
	public CheckPoint() {
		setLine(new PassengerQueue());
		setTimeWhenAvailable(null);
	}
	
	/**
	 * Setter for the Checkpoint line
	 * @param line the PassengerQueue to set as the instance variable
	 */
	public void setLine(PassengerQueue line) {
		this.line = line;
	}
	
	/**
	 * Updates timeWhenAvailable field. Initialized to 0 if line is empty
	 * @param p the next Passenger
	 */
	public void setTimeWhenAvailable(Passenger p) {
		if (p == null) {
			timeWhenAvailable = 0;
		} else if (p.getArrivalTime() >= timeWhenAvailable) {
			timeWhenAvailable =  p.getArrivalTime() + p.getProcessTime();
		} else {
			timeWhenAvailable += p.getProcessTime();
		}
	}
	
	
	/**
	 * Get timeWhenAvailable
	 * @return timeWhenAvailable
	 */
	public int getTimeWhenAvailable() {
		return this.timeWhenAvailable;
	}
	
	/**
	 * Gets the size of this Checkpoint (how many Passengers in line)
	 * @return the size of the line
	 */
	public int size() {
		return line.size();
	}
	
	/**
	 * Removes the next Passenger from the front of the Checkpoint
	 * @return the next Passenger that's removed
	 * @throws NoSuchElementException if no one left in Checkpoint
	 */
	public Passenger removeFromLine() throws NoSuchElementException {
		return line.remove();
	}
	
	/**
	 * Tells if the Checkpoint has anyone else in line
	 * @return true if anyone in line, false if not
	 */
	public boolean hasNext() {
		return !line.isEmpty();
	}
	
	/**
	 * Returns the amount of time before the next Passenger will clear security, returning
	 * Integer.MAX_VALUE if the line is empty
	 * @return amount of time before next Passenger will clear security
	 */
	public int departTimeNext() {
		if (line.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return line.front().getArrivalTime() + line.front().getProcessTime() 
					+ line.front().getWaitTime();
		}
	}
	
	/**
	 * Returns the Passenger at the front of the security line
	 * @return the next Passenger to go
	 */
	public Passenger nextToGo() {
		return line.front();
	}
	
	/**
	 * Adds the parameter to the end of the line, setting their waitTime according to
	 * timeWhenAvailable as well as their arrivalTime and processTime, and updating 
	 * timeWhenAvailable
	 * @param p the Passenger to add to the Checkpoint
	 */
	public void addToLine(Passenger p) {
		line.add(p);
		p.setWaitTime(timeWhenAvailable - p.getArrivalTime());
		if (p.getWaitTime() < 0) {
			p.setWaitTime(0);
		}
		setTimeWhenAvailable(p);
	}
}
