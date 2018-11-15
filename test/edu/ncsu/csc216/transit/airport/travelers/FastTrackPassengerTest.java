package edu.ncsu.csc216.transit.airport.travelers;

import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.airport.entrance.Ticketing;
import edu.ncsu.csc216.transit.airport.security.SecurityArea;
import edu.ncsu.csc216.transit.simulation_utils.Log;

/**
 * Tests the FastTrackPassenger class
 * @author zcollins
 *
 */
public class FastTrackPassengerTest {

	/** Field for a passenger */
	Passenger p;
	
	/**
	 * Sets up the test cases
	 * @throws Exception if exception occurs
	 */
	@Before
	public void setUp() throws Exception {
		// Construct new passenger, making sure it's FastTrack
		p = Ticketing.generatePassenger(new Log());
		while (p instanceof OrdinaryPassenger || p instanceof TrustedTraveler) {
			p = Ticketing.generatePassenger(new Log());
		}
	}
	

	/**
	 * Test the getColor method
	 */
	@Test
	public void testGetColor() {
		p = new FastTrackPassenger(0, FastTrackPassenger.MAX_EXPECTED_PROCESS_TIME / 2 - 1, new Log());
		assertEquals(p.getColor(), new Color(153, 153, 255));
		
		Passenger p2 = new FastTrackPassenger(2, FastTrackPassenger.MAX_EXPECTED_PROCESS_TIME / 2, new Log());
		assertEquals(p2.getColor(), Color.BLUE); 
	}
	
	/**
	 * Test the get in line method
	 */
	@Test
	public void testGetInLine() {
		TransitGroup lines = new SecurityArea(3);
		p.getInLine(lines);
		assertEquals(p.getLineIndex(), 0);
	}
}
