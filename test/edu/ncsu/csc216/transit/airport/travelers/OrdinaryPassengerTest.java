package edu.ncsu.csc216.transit.airport.travelers;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Log;

/**
 * Tests the OrdinaryPassenger class
 * @author zcollins
 *
 */
public class OrdinaryPassengerTest {

	/** Field for a passenger */
	Passenger p;
	
	/**
	 * Sets up the test cases
	 * @throws Exception if exception occurs
	 */
	@Before
	public void setUp() throws Exception {
		// Construct new passenger, making sure it's Ordinary
		p = new OrdinaryPassenger(0, 20, new Log());
	}
	

	/**
	 * Test the getColor method
	 */
	@Test
	public void testGetColor() {
		p.setProcessTime(OrdinaryPassenger.MAX_EXPECTED_PROCESS_TIME / 2 - 1);
		assertEquals(p.getColor(), new Color(255, 153, 153));
		
		Passenger p2 = new OrdinaryPassenger(2, OrdinaryPassenger.MAX_EXPECTED_PROCESS_TIME / 2, new Log());
		assertEquals(p2.getColor(), Color.RED); 
	}
	
	/**
	 * Test the get in line method
	 */
	@Test
	public void testGetInLine() {
		TransitGroup lines = new SecurityArea(3);
		p.getInLine(lines);
		assertEquals(p.getLineIndex(), 1);
		
	}

}
