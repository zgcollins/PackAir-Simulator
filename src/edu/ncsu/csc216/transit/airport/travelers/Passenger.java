package edu.ncsu.csc216.transit.airport.travelers;

import java.awt.Color;

import edu.ncsu.csc216.transit.airport.TransitGroup;
import edu.ncsu.csc216.transit.simulation_utils.Reporter;

/**
 * Passenger class constructs and manages Passenger objects
 * @author zcollins
 *
 */
abstract public class Passenger {
	/** Arrival time field */
	private int arrivalTime;
	/** Wait time field */
	private int waitTime;
	/** Process time field */
	private int processTime;
	/** Place in line field */
	private int lineIndex;
	/** Are they waiting in processing or not */
	private boolean waitingProcessing;
	/** Log for this Passenger */
	private Reporter myLog;
	
	/** Minimum process time for all passengers */
	public static final int MIN_PROCESS_TIME = 20;
	
	/**
	 * Constructor for Passenger objects
	 * @param arrivalTime the Passenger's arrival time
	 * @param processTime the Passenger's process time
	 * @param myLog the reporting mechanism for the simulation
	 * @throws IllegalArgumentException if process time invalid
	 */
	public Passenger(int arrivalTime, int processTime, Reporter myLog) {
		setArrivalTime(arrivalTime);
		setProcessTime(processTime);
		waitingProcessing = false;
		setLog(myLog);
		setLineIndex(-1);
	}
	
	/**
	 * Sets the log for this Passenger
	 * @param myLog the log to set
	 */
	private void setLog(Reporter log) {
		this.myLog = log;
	}

	/**
	 * Setter for arrivalTime
	 * @param arrivalTime the arrival time of the Passenger
	 */
	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * Setter for wait time
	 * @param waitTime this Passenger's wait time
	 */
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	/**
	 * Getter for arrival time
	 * @return arrival time
	 */
	public int getArrivalTime() {
		return this.arrivalTime;
	}
	
	/**
	 * Getter for wait time
	 * @return the wait time
	 */
	public int getWaitTime() {
		return this.waitTime;
	}

	/**
	 * Getter process time
	 * @return the processTime
	 */
	public int getProcessTime() {
		return processTime;
	}

	/**
	 * Setter process time
	 * @param processTime the processTime to set
	 */
	public void setProcessTime(int processTime) {
		if (processTime < MIN_PROCESS_TIME) {
			throw new IllegalArgumentException();
		}
		this.processTime = processTime;
	}

	/**
	 * Getter line index
	 * @return the lineIndex
	 */
	public int getLineIndex() {
		return lineIndex;
	}

	/**
	 * Setter line index
	 * @param lineIndex the lineIndex to set
	 */
	protected void setLineIndex(int lineIndex) {
		this.lineIndex = lineIndex;
		if (lineIndex != -1) { 
			this.waitingProcessing = true;
		}
	}
	
	/**
	 * Are they waiting in security line
	 * @return true if in security line, false if not
	 */
	public boolean isWaitingInSecurityLine() {
		return this.waitingProcessing;
	}
	
	/**
	 * Resets Passenger's security status
	 */
	public void clearSecurity() { 
		if (waitingProcessing) {
			myLog.logData(this);
		}
		waitingProcessing = false;
		
	}
	
	/**
	 * Abstract method for adding Passenger to appropriate security line
	 * @param securityLines the lines to get into
	 */
	public abstract void getInLine(TransitGroup securityLines);
	
	/**
	 * Abstract method for getting the Passenger's color
	 * @return the Passenger's color
	 */
	public abstract Color getColor();
	
}