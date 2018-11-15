package edu.ncsu.csc216.transit.simulation_utils;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.transit.airport.travelers.OrdinaryPassenger;


/**
 * Tests the Simulator class
 * @author zcollins
 *
 */
public class SimulatorTest {

	/** Simulator for testing */
	Simulator sim;
	
	
	
	/**
	 * Setup method
	 */
	@Before
	public void setUp() {
		sim = new Simulator(5, 20, 20, 30, 50);
		
	}
	
	/**
	 * Test construction
	 */
	@Test
	public void testSimulator() {
		Reporter log = sim.getReporter();
		log.logData(new OrdinaryPassenger(0, 20, log));
		
		
		assertFalse(sim.passengerClearedSecurity());
		assertTrue(sim.moreSteps());
		
		Simulator temp = sim;
		// Try construction with illegal parameters
		try {
			sim = new Simulator(5, 100, 5, 95, 20);
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(temp, sim);
		}
	}
	
	/**
	 * Test step
	 */ 
	@Test
	public void testStep() {
		
		// Step 1 - should move a passenger into Security line
		sim.step();
		// Use color to assert proper line index for each Passenger
		if (sim.getCurrentPassengerColor() == Color.BLUE || 
				sim.getCurrentPassengerColor() == new Color(153, 153, 255)) {
			// Assert current Passenger is a FastTrackPassenger
			assertEquals(0, sim.getCurrentIndex());
			assertFalse(sim.passengerClearedSecurity());
		} else if (sim.getCurrentPassengerColor() == Color.RED ||
				sim.getCurrentPassengerColor() == new Color(255, 153, 153)) {
			// Assert current Passenger is Ordinary
			assertEquals(2, sim.getCurrentIndex());
			assertFalse(sim.passengerClearedSecurity());
		} else {
			// Assert currentPassenger is TrustedTraveler
			assertEquals(4, sim.getCurrentIndex());
			assertFalse(sim.passengerClearedSecurity());
		} 
	}
}
 