package chromosome;

import java.util.Vector;

public class Chromosome<T> {
    private Vector<T> genes;
    private double fitness = Double.POSITIVE_INFINITY;
    public int chromosomeLength;

    public Chromosome(Vector<T> genes){
        this.genes = genes;
    }

    public Vector<T> getGenes() { 
        return genes;
    }

    public void setGenes(Vector<T> genes) {
        this.genes = genes;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
