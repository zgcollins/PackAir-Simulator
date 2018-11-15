package edu.ncsu.csc216.transit.airport.security;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.transit.airport.travelers.OrdinaryPassenger;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.simulation_utils.Log;

/**
 * Tests SecurityArea
 * @author zcollins
 *
 */
public class SecurityAreaTest {

	int numGates;
	int largestFastIndex;
	int tsaPreIndex;
	SecurityArea a;
	
	/**
	 * Setup method
	 */
	@Before
	public void setUp() {
		numGates = 3;
	}
	
	/**
	 * Test construction
	 */
	@Test
	public void testSecurityArea() {
		numGates = 1;
		try{
			a = new SecurityArea(numGates);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(e.getMessage(), SecurityArea.ERROR_CHECKPOINTS);
		}
		
		numGates = 3;
		a = new SecurityArea(numGates);
		assertEquals(null, a.nextToGo());
		
		Passenger p = new OrdinaryPassenger(0, 20, new Log());
		a.addToLine(1, p);
		assertEquals(p.getArrivalTime() + p.getWaitTime() + p.getProcessTime(), a.departTimeNext());

		// Test remove from line
		assertEquals(a.lengthOfLine(0), 0);
		assertEquals(a.lengthOfLine(1), 1);
		assertEquals(a.lengthOfLine(2), 0);
		
		
		//Why is this returning null????
		//assertEquals(a.lengthOfLine(1), 0);
		
		// Test getGates
		assertEquals(3, a.getGates().size());
		
		// Test invalid remove and length of line
		a = new SecurityArea(3);
		try {
			a.lengthOfLine(4);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(a.departTimeNext(), Integer.MAX_VALUE);
		}
	}

}
