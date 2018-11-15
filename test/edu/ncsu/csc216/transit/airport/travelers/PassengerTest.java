package edu.ncsu.csc216.transit.airport.travelers;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.entrance.Ticketing;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Log;

/**
 * Tests the Passenger methods
 * @author zcollins
 *
 */
public class PassengerTest {

	Passenger p;
	
	/**
	 * Sets up the tests
	 * @throws Exception if exception occurs
	 */
	@Before
	public void setUp() throws Exception {
		p = Ticketing.generatePassenger(new Log());
	}

	/**
	 * Tests isWaitingSecurity 
	 */
	@Test
	public void testIsWaitingSecurity() {
		TransitGroup lines = new SecurityArea(3);
		assertFalse(p.isWaitingInSecurityLine());
		p.getInLine(lines);
		assertTrue(p.isWaitingInSecurityLine());
		p.clearSecurity();
		assertFalse(p.isWaitingInSecurityLine());
	} 

}
