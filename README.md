# csc216-601-P1-03


# -------- What is PackAirSimulator? --------
#
# PackAirSimulator is an application for simulating airport security checkpoint wait times.
# It was developed for a ficticious airline named PackAir.  They had recently acquired an
# entire terminal at the airport and wanted to determine the answer to the following questions:
# 
# - How many checkpoints should the terminal have?
# - How should the checkpoints be arranged? How many fast-track security lines, TSA pre-check
#	lines should they have? How many ordinary lines?
# - How long might their customers spend waiting in line depending on the answers to the 
# 	previous two questions?


# -------- Information on using the simulator --------
#
# To use the program, run the file SecurityCheckpointViewer.java from the command line.
# The file is located in the src/ui/ folder.
# The GUI will appear. Enter number of checkpoints (3-17), number of passengers for the 
# simulation, and the % distribution of the different types of passengers. The output will 
# be the average wait time for passengers and the average time spent processing in security.
#
# A note to users: During the simulation, passengers are represented by different colored
# discs. Lighter colored passengers have shorter wait times (maybe they are faster to empty
# their bag or take their shoes off!). The blue discs represent Fast Track passengers. Red 
# discs are ordinary passengers, and green discs are TSA Pre-check passengers.
#
# The first third of the checkpoint lines is reserved for fast-track only. The middle ones
# are for any type of passenger. The final line is reserved for TSA Trusted Travelers only.


# -------- Information about design and implementation --------
#
# PackAirSimulator utilizes Java Swing, the UI libraries for Java. Java Swing utlizes a 
# a modified MVC design, i.e. separable model architecture (the model is made up of 
# separate and distinct classes and the view/controller are contained in a single UI class)
# 
# The simulation contains 2 types of events, handled with an event calendar class:
#
# 1. At the ticketing area. The event is a passenger leaving the ticketing area and entering 
# the security area. The passenger goes immediately to the back of the line of passengers for 
# the selected security checkpoint. Later, the same passenger will reach the front of the line
# and complete processing at the checkpoint.
# 2. At a security checkpoint. The event is a passenger departure from the front of a security 
# checkpoint line. The passenger at the front of each line is at a security checkpoint 
# undergoing processing. The departing passenger logs his or her information (how long the 
# passenger stood in line prior to actual security processing and how long the passenger took 
# to go through processing after making it to the front of the line), and then leaves the 
# simulation.
#
# When the simulation begins, all passengers are created with an automatically generated 
# arrival time and processing time using a random number. The passenger's wait time is 
# calculated once they leave the ticketing area and join a security checkpoint line.
#
# Refer to /project_docs/ to reference the UML diagram for this program.