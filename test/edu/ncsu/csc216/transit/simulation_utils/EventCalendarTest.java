package edu.ncsu.csc216.transit.simulation_utils;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.entrance.PreSecurity;
import edu.ncsu.csc216.transit.airport.entrance.Ticketing;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.airport.travelers.OrdinaryPassenger;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * Tests the EventCalendar class
 * @author zcollins
 *
 */
public class EventCalendarTest {

	/** EventCalendar field for testing */
	EventCalendar cal;
	/** TransitGroup low priority */
	TransitGroup lowPriority;
	/** TransitGroup high priority */
	TransitGroup highPriority;
	
	/** Passengers to use */
	Passenger p;
	/** Passengers to use */
	Passenger p2;
	/** Passengers to use */
	Passenger p3;
	
	/**
	 * Sets up for testing
	 */
	@Before
	public void setUp() {
		Ticketing.resetFactory();
		lowPriority = new SecurityArea(10);
		highPriority = new PreSecurity(1, new Log());
	}
	
	
	/**
	 * Tests construction and nextToAct
	 */
	@Test
	public void testEventCalendar() {
		// Create calendar, compare nextToAct to highPriority next
		cal = new EventCalendar(highPriority, lowPriority);
		assertEquals(cal.nextToAct(), highPriority.nextToGo());
		
		// Remove passenger
		p = highPriority.removeNext();
		assertFalse(((PreSecurity) highPriority).hasNext());
		try {
			assertEquals(highPriority.removeNext(), null);
			fail();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		
		
		// Test IllegalState and tie cases
		try {
			cal.nextToAct();
			fail();
		} catch (IllegalStateException e) {
			assertFalse(((PreSecurity) highPriority).hasNext());
		}
		
		
		
		// Test tie
		p = new OrdinaryPassenger(0, 20, new Log());
		p2 = new OrdinaryPassenger(0, 20, new Log());
		((PreSecurity) highPriority).addToLine(p);
		((SecurityArea) lowPriority).addToLine(4, p2);
		assertEquals(cal.nextToAct(), highPriority.nextToGo()); // Should get from PreSecurity first
	
		
		// Test lowPriority first
		p.setArrivalTime(30);
		assertEquals(cal.nextToAct(), lowPriority.nextToGo());
	
		
	}
}
