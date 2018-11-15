package edu.ncsu.csc216.transit.simulation_utils;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * Represents the EventCalendar which decides the order of events for the simulation
 * @author zcollins
 *
 */
public class EventCalendar {
	
	/** High priority TransitGroup */
	private TransitGroup highPriority;
	/** Low priority TransitGroup */
	private TransitGroup lowPriority;
	
	/**
	 * Constructor for EventCalendar
	 * @param highPriority the PreSecurity area
	 * @param lowPriority the SecurityArea
	 */
	public EventCalendar(TransitGroup highPriority, TransitGroup lowPriority) {
		setTransitGroups(highPriority, lowPriority);
	}

	/**
	 * Sets the TransitGroups
	 * @param highPriority PreSecurity area
	 * @param lowPriority SecurityArea
	 */
	private void setTransitGroups(TransitGroup highPriority, TransitGroup lowPriority) {
		this.highPriority = highPriority;
		this.lowPriority = lowPriority;
	}
	
	/**
	 * Gets the Passenger that is going to be next to exit their line. Returns the 
	 * next highPriority Passenger if there's a tie
	 * @return the next Passenger to act
	 * @throws IllegalStateException if both TransitGroups are empty
	 */
	public Passenger nextToAct() {
		if (highPriority.nextToGo() == null && lowPriority.nextToGo() == null) {
			// There's no one in line, throw exception
			throw new IllegalStateException();
		}
		
		// Find the next Passenger to act by checking lowest departure time between groups
		if (highPriority.departTimeNext() > lowPriority.departTimeNext()) {
			return lowPriority.nextToGo();
		} else {
			return highPriority.nextToGo();
		}
	}
}
