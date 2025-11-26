package genetic;

import java.util.Vector;

public class EvaluationResult {
    public double fitness;
    public Vector<String> schedule;
    public boolean isFeasible;

    public EvaluationResult(double fitness, Vector<String> schedule, boolean isFeasible) {
        this.fitness = fitness;
        this.schedule = schedule;
        this.isFeasible = isFeasible;
    }
}