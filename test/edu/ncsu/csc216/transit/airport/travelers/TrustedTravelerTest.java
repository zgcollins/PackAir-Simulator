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
 * Tests the OrdinaryPassenger class
 * @author zcollins
 *
 */
public class TrustedTravelerTest {

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
		Ticketing.setDistribution(10, 10);
		Ticketing.resetFactory();
		while (p instanceof FastTrackPassenger || p instanceof OrdinaryPassenger) {
			p = Ticketing.generatePassenger(new Log());
		}
	}
	

	/**
	 * Test the getColor method
	 */
	@Test
	public void testGetColor() {
		p = new TrustedTraveler(0, TrustedTraveler.MAX_EXPECTED_PROCESS_TIME / 2 - 1, new Log());
		assertEquals(p.getColor(), new Color(153, 255, 153));
		
		Passenger p2 = new TrustedTraveler(2, TrustedTraveler.MAX_EXPECTED_PROCESS_TIME / 2, new Log());
		assertEquals(p2.getColor(), Color.GREEN); 
	}
	
	/**
	 * Test the get in line method
	 */
	@Test
	public void testGetInLine() {
		TransitGroup lines = new SecurityArea(3);
		p.getInLine(lines);
		assertEquals(p.getLineIndex(), 2);
		
		Log l = new Log();
		
		// SecurityArea with 5 Checks
		SecurityArea lines2 = new SecurityArea(5);
		
		// Create Passenger and add to line 3
		Passenger p0 = new OrdinaryPassenger(10, 25, l);
		lines2.addToLine(2, p0);
		assertEquals(1, lines2.lengthOfLine(2));
		
		// Create 6 TT passengers and make them get in line
		Passenger tt0 = new TrustedTraveler(2, 20, l);
		Passenger tt1 = new TrustedTraveler(22, 20, l);
		Passenger tt2 = new TrustedTraveler(32, 20, l);
		Passenger tt3 = new TrustedTraveler(42, 20, l);
		Passenger tt4 = new TrustedTraveler(52, 20, l);
		Passenger tt5 = new TrustedTraveler(53, 20, l);
		
		tt0.getInLine(lines2);
		tt1.getInLine(lines2);
		tt2.getInLine(lines2);
		tt3.getInLine(lines2);
		tt4.getInLine(lines2);
		tt5.getInLine(lines2);
		
		assertEquals(4, tt0.getLineIndex());
		assertEquals(3, tt1.getLineIndex());
		assertEquals(4, tt2.getLineIndex());
		assertEquals(2, tt3.getLineIndex());
		assertEquals(3, tt4.getLineIndex());
		assertEquals(4, tt5.getLineIndex());
		
		
	}

}
