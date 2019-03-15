import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

/**
 * Solver for the Flight problem (#9) from CS 61B Spring 2018 Midterm 2.
 * Assumes valid input, i.e. all Flight start times are >= end times.
 * If a flight starts at the same time as a flight's end time, they are
 * considered to be in the air at the same time.
 */
public class FlightSolver {
    PriorityQueue flightSolver = new PriorityQueue();

    public FlightSolver(ArrayList<Flight> flights) {
        int passenger = flights.get(0).passengers;
        int startTime = flights.get(0).startTime;
        int endTime = flights.get(0).endTime;

        for (int i = 1; i < flights.size(); i++) {
            if (flights.get(i).startTime <= endTime && flights.get(i).startTime >= startTime) {
                passenger += flights.get(i).passengers;
                startTime = flights.get(i).startTime;
                endTime = flights.get(i).endTime;
            } else {
                flightSolver.add(-passenger);
                passenger = flights.get(i).passengers;
                startTime = flights.get(i).startTime;
                endTime = flights.get(i).endTime;
            }
        }
        flightSolver.add(-passenger);
    }

    public int solve() {
        /* FIX ME */
        return -(int) flightSolver.remove();
    }

}
