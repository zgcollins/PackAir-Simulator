package edu.ncsu.csc216.transit.simulation_utils;

import edu.ncsu.csc216.transit.airport.travelers.Passenger;

/**
 * Log keeps track of Passenger data throughout the simulation
 * @author zcollins
 *
 */
public class Log implements Reporter {
	/** Number of checks completed */
	private int numCompleted;
	/** Total wait time */
	private int totalWaitTime;
	/** Total process time */
	private int totalProcessTime;
		
	
	/**
	 * Constructor for log
	 */
	public Log() {
		setNumCompleted(0);
		setTotalWaitTime(0);
		setTotalProcessTime(0);
	}
	
	
	
	/**
	 * Total wait time
	 * @return the totalWaitTime
	 */
	public int getTotalWaitTime() {
		return totalWaitTime;
	}



	/**
	 * Sets total wait time
	 * @param totalWaitTime the totalWaitTime to set
	 */
	public void setTotalWaitTime(int totalWaitTime) {
		this.totalWaitTime = totalWaitTime;
	}



	/**
	 * Gets totalProcessTime
	 * @return the totalProcessTime
	 */
	public int getTotalProcessTime() {
		return totalProcessTime;
	}



	/**
	 * Sets totalProcessTime
	 * @param totalProcessTime the totalProcessTime to set
	 */
	public void setTotalProcessTime(int totalProcessTime) {
		this.totalProcessTime = totalProcessTime;
	}



	/**
	 * Sets the numCompleted
	 * @param numCompleted the numCompleted to set
	 */
	public void setNumCompleted(int numCompleted) {
		this.numCompleted = numCompleted;
	}



	/**
	 * Get number Passengers completed
	 * @return the number Passengers processed
	 */
	@Override
	public int getNumCompleted() {
		return this.numCompleted;
	}

	/**
	 * Log data for the Passenger
	 * @param passenger the next Passenger to log
	 */
	@Override
	public void logData(Passenger passenger) {
		// Adjusts the Log field wait/process time based on the next Passenger
		totalProcessTime += passenger.getProcessTime();
		totalWaitTime += passenger.getWaitTime();
		numCompleted += 1;
	}

	/**
	 * Calculates and returns the average wait time
	 * @return average wait time
	 */
	@Override
	public double averageWaitTime() {
		if (numCompleted == 0) {
			return 0;
		}
		
		// Calculate the average wait time in minutes
		double averageWaitTime = this.totalWaitTime / (60.0 * (double)this.getNumCompleted());
		return averageWaitTime;
	}

	/**
	 * Calculates and returns average process time
	 * @return the average process time
	 */
	@Override
	public double averageProcessTime() {
		if (numCompleted == 0) {
			return 0;
		}
		
		// Calculate average process time
		double averageProcessTime = this.totalProcessTime / (60.0 * (double)this.getNumCompleted());
		return averageProcessTime;
	}
	
}
