package bearmaps.hw4;

import bearmaps.proj2ab.ArrayHeapMinPQ;
import edu.princeton.cs.algs4.Stopwatch;

import java.util.List;

public class AStarSolver<Vertex> implements ShortestPathsSolver<Vertex> {
    private ArrayHeapMinPQ fringe = new ArrayHeapMinPQ();
    private double exploreTime;
    private SolverOutcome outcome;
    private List<Vertex> solution;
    private double solutionWeight;
    private int statesExplored;



    public AStarSolver(AStarGraph<Vertex> input, Vertex start, Vertex end, double timeout){
        Stopwatch sw = new Stopwatch();
        if (explorationTime() > timeout) {
            outcome = SolverOutcome.UNSOLVABLE;
        }

        fringe.add(start, 0);

        while (fringe != null) {
            Vertex v = (Vertex) fringe.removeSmallest();
            solution.add(v);

            for (WeightedEdge e : input.neighbors(v)){
                Vertex p = (Vertex) e.from();
                Vertex q = (Vertex) e.to();
                double w = e.weight();

                fringe.add(e.to(), e.weight() + e.weight());


            }
        }
    }
    @Override
    public SolverOutcome outcome() { return outcome; }
    @Override
    public List<Vertex> solution(){ return solution; }
    @Override
    public double solutionWeight(){ return solutionWeight; }
    @Override
    public int numStatesExplored(){ return statesExplored; }
    @Override
    public double explorationTime() { return exploreTime; }
