package edu.ncsu.csc216.transit.airport.entrance;

import java.util.NoSuchElementException;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.airport.travelers.PassengerQueue;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * PreSecurity objects represent the waiting area for Passengers that haven't
 * chosen a security line yet
 * @author zcollins
 *
 */
public class PreSecurity implements TransitGroup {

	/** Field for PassengerQueue */
	private PassengerQueue outsideSecurity;
	
	/**
	 * Constructor for PreSecurity
	 * @param numPassengers the number of Passengers to initialize in the queue
	 * @param log the Reporter mechanism
	 * @throws IllegalArgumentException if illegal number of passengers
	 */
	public PreSecurity(int numPassengers, Reporter log) {
		if (numPassengers > 0) {
			this.outsideSecurity = new PassengerQueue();
			while (outsideSecurity.size() < numPassengers) {
				outsideSecurity.add(Ticketing.generatePassenger(log));
			}
		} else { 
			throw new IllegalArgumentException("Number of passengers must be positive.");
		}
	}
	
	/**
	 * Method for adding Passengers for testing
	 * @param p the passenger to add
	 */
	public void addToLine(Passenger p) {
		outsideSecurity.add(p);
	}
	
	/**
	 * PreSecurity implementation of nextToGo() determines the next Passenger to leave
	 * PreSecurity area, which is the Passenger with the earliest arrivalTime
	 * @return the next Passenger to leave PreSecurity
	 */
	@Override
	public Passenger nextToGo() {
		return outsideSecurity.front();
	}

	/**
	 * PreSecurity implementation of departTimeNext() determines the departure time of the
	 * next Passenger to leave (this is the front Passenger's arrival time or INTEGER.MAX_VALUE if
	 * there is no one in ticketing yet.
	 * @return the next Passenger departure time
	 */
	@Override
	public int departTimeNext() {
		if (outsideSecurity.isEmpty()) {
			return Integer.MAX_VALUE;
		} else {
			return outsideSecurity.front().getArrivalTime();
		}
	}

	/**
	 * Removes the next Passenger from ticketing
	 * @return the next Passenger, or null if there's no one in Ticketing
	 * @throws NoSuchElementException if no one left in line
	 */
	@Override
	public Passenger removeNext() throws NoSuchElementException {
		return outsideSecurity.remove();
	}
	
	/**
	 * Does the PreSecurity area have anyone in line?
	 * @return true if there's Passenger in line, false if not
	 */
	public boolean hasNext() {
		return !outsideSecurity.isEmpty();
	}
}
