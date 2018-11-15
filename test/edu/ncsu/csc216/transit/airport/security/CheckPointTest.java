package edu.ncsu.csc216.transit.airport.security;

import static org.junit.Assert.*;

import java.util.NoSuchElementException;

import org.junit.Before;
import org.junit.Test;

import edu.ncsu.csc216.transit.airport.entrance.Ticketing;
import edu.ncsu.csc216.transit.airport.travelers.OrdinaryPassenger;
import edu.ncsu.csc216.transit.airport.travelers.Passenger;
import edu.ncsu.csc216.transit.simulation_utils.Log;

/**
 * Tests the Checkpoint class
 * @author zcollins
 *
 */
public class CheckPointTest {

	Passenger p;
	CheckPoint c;
	
	/**
	 * Sets up the tests
	 */
	@Before
	public void setUp() {
		p = Ticketing.generatePassenger(new Log());
		c = new CheckPoint();
	}
	
	/**
	 * Test Checkpoint creation
	 */
	@Test
	public void testCheckpoint() {
		assertEquals(c.size(), 0);
		assertEquals(c.getTimeWhenAvailable(), 0);
	}
	
	/**
	 * Tests setTimeWhenAvailable
	 */
	@Test
	public void testSetTimeWhenAvailable() {
		c.setTimeWhenAvailable(p);
		assertEquals(c.getTimeWhenAvailable(), p.getProcessTime() + p.getArrivalTime());
		
		Passenger p2 = Ticketing.generatePassenger(new Log());
		c.setTimeWhenAvailable(p2);
		assertEquals(c.getTimeWhenAvailable(), p.getProcessTime() + p.getArrivalTime() + 
				p2.getProcessTime());
	} 
	
	/**
	 * Test remove from line
	 */
	@Test
	public void testRemoveLine() {
		c.addToLine(p);
		assertEquals(c.size(), 1);
		assertTrue(c.hasNext());
		Passenger removed = c.removeFromLine();
		assertEquals(removed, p);
		assertEquals(c.size(), 0);
		assertFalse(c.hasNext());
		try {
			c.removeFromLine();
			fail();
		} catch (NoSuchElementException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Test departTimeNext()
	 */
	@Test
	public void testDepartTimeNext() {
		assertEquals(c.departTimeNext(), Integer.MAX_VALUE);
		c.addToLine(p);
		assertEquals(c.departTimeNext(), p.getArrivalTime() + p.getProcessTime() + p.getWaitTime());
		assertEquals(c.nextToGo(), p);
	}
	
	/**
	 * Test add to line
	 */
	@Test
	public void testAddToLine() {
		Log l = new Log();
		
		SecurityArea s = new SecurityArea(5);
		
		Passenger p1 = new OrdinaryPassenger(0, 20, l);
		Passenger p2 = new OrdinaryPassenger(1, 25, l);
		Passenger p3 = new OrdinaryPassenger(2, 30, l);
		Passenger p4 = new OrdinaryPassenger(3, 35, l);
		Passenger p5 = new OrdinaryPassenger(4, 40, l);
		
		p1.getInLine(s);
		l.logData(p1);
		assertEquals(2, p1.getLineIndex());
		assertEquals(0, p1.getWaitTime());
		assertEquals(20, s.getGates().get(2).getTimeWhenAvailable());
		p2.getInLine(s);
		l.logData(p2);
		assertEquals(3, p2.getLineIndex());
		assertEquals(0, p2.getWaitTime());
		assertEquals(26, s.getGates().get(3).getTimeWhenAvailable());
		p3.getInLine(s);
		l.logData(p3);
		assertEquals(2, p3.getLineIndex());
		assertEquals(18, p3.getWaitTime());
		assertEquals(50, s.getGates().get(2).getTimeWhenAvailable());
		p4.getInLine(s);
		l.logData(p4);
		assertEquals(3, p4.getLineIndex());
		assertEquals(23, p4.getWaitTime());
		assertEquals(61, s.getGates().get(3).getTimeWhenAvailable());
		p5.getInLine(s);
		l.logData(p5);
		assertEquals(2, p5.getLineIndex());
		assertEquals(46, p5.getWaitTime());
		assertEquals(90, s.getGates().get(2).getTimeWhenAvailable());
		
		assertEquals(0.29, l.averageWaitTime(), 0.029);
		
	
		
		
		
		
		
		
		
		
		
	
		
		
		
		
		
		
		
		
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
