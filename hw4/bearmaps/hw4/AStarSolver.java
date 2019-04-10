package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;


public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private ArrayHeapMinPQ<Vertex> fringe = new ArrayHeapMinPQ();
    private List<Vertex> solution = new ArrayList<>();
    private Map<Vertex, Double> distTo = new HashMap<>();
    private Map<Vertex, Vertex> edgeTo = new HashMap<>();
    private double exploreTime = 0.0;
    private double solutionWeight = 0.0;
    private int statesExplored = 0;
    private SolverOutcome outcome = SolverOutcome.UNSOLVABLE;


    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout) {
        Stopwatch sw = new Stopwatch();
        double heuristic = input.estimatedDistanceToGoal(start, end);
        fringe.add(start, heuristic);
        distTo.put(start, 0.0);

        while (fringe.size() != 0) {
            Vertex v = (Vertex) fringe.removeSmallest();
            statesExplored++;
            exploreTime = sw.elapsedTime();

            if (v.equals(end)) {
                outcome = SolverOutcome.SOLVED;
                Vertex vertex = end;
                solutionWeight = distTo.get(vertex);
                while (vertex != null) {
                    solution.add(vertex);
                    vertex = edgeTo.get(vertex);
                }
                Collections.reverse(solution);
                return;
            } else if (sw.elapsedTime() > timeout) {
                outcome = SolverOutcome.TIMEOUT;
                return;
            }

            for (WeightedEdge e : input.neighbors(v)) {
                Vertex p = (Vertex) e.from();
                Vertex q = (Vertex) e.to();
                double w = e.weight();
                heuristic = input.estimatedDistanceToGoal(q, end);
                distTo.putIfAbsent(q, Double.POSITIVE_INFINITY);

                if (distTo.get(p) + w < distTo.get(q)) {

                    distTo.put(q, distTo.get(p) + w);
                    edgeTo.put(q, p);

                    if (fringe.contains(q)) {
                        fringe.changePriority(q, distTo.get(q) + heuristic);
                    } else {
                        fringe.add(q, distTo.get(q) + heuristic);
                    }
                }
            }
        }
    }

    @Override
    public SolverOutcome outcome() {
        return outcome;
    }

    @Override
    public List<Vertex> solution() {
        return solution;
    }

    @Override
    public double solutionWeight() {
        return solutionWeight;
    }

    @Override
    public int numStatesExplored() {
        return statesExplored;
    }

    @Override
    public double explorationTime() {
        return exploreTime;
    }
}
