package edu.ncsu.csc216.transit.airport.entrance;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.simulation_utils.Log;

/**
 * Tests the PreSecurity class
 * @author zcollins
 *
 */
public class PreSecurityTest {

	/** TransitGroup for testing */
	TransitGroup ps;
	
	/**
	 * Setup method
	 */
	@Before
	public void setUp() {
		// Create PreSecurity wit one passenger for testing
		ps = new PreSecurity(1, new Log());
		
	}
	
	/**
	 * Tests construction
	 */
	@Test
	public void testPreSecurity() {
		// Test invalid case then check other methods
		try {
			ps = new PreSecurity(0, new Log());
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals(ps.departTimeNext(), ((PreSecurity) ps).nextToGo().getArrivalTime());
			assertTrue(((PreSecurity) ps).hasNext());
			ps.removeNext();
			assertEquals(ps.departTimeNext(), Integer.MAX_VALUE);
			assertFalse(((PreSecurity) ps).hasNext());
			try {
				assertEquals(ps.removeNext(), null);
				fail();
			} catch (NoSuchElementException e2) {
				e.printStackTrace();
			}
			assertEquals(ps.nextToGo(), null);
		}
		
	}
	
}
