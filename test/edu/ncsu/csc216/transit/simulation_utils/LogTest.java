package edu.ncsu.csc216.transit.simulation_utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.transit.airport.travelers.OrdinaryPassenger;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * Tests the Log class
 * @author zcollins
 *
 */
public class LogTest {

	/** Log to use for testing */
	Log log;
	
	/**
	 * Setup method
	 */
	@Before
	public void setUp() {
		log = new Log();
		
	}
	
	/**
	 * Test construction
	 */
	@Test
	public void testLog() {
		assertEquals(0, log.getNumCompleted());
		assertEquals(0, log.getTotalWaitTime());
		assertEquals(0, log.getTotalProcessTime());
		assertEquals(0, (int)log.averageProcessTime());
		assertEquals(0, (int)log.averageWaitTime());
		
	}
	
	/**
	 * Test average wait time
	 */
	public void testAverageWaitTime() {
		@SuppressWarnings("unused")
		Passenger p1 = new OrdinaryPassenger(0, 0, log);
	}

}
