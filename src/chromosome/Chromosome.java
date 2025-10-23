package chromosome;

import java.util.Objects;
import java.util.Vector;

public class Chromosome<T> {
    private Vector<T> genes;
    private double fitness = Double.POSITIVE_INFINITY;
    public int chromosomeLength;
    public Chromosome<T> parent;

    public Chromosome(Vector<T> genes){
        this.genes = genes;
        this.parent = null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Chromosome)) return false;
        Chromosome<?> c = (Chromosome<?>) o;
        return Objects.equals(this.genes, c.genes);
    }

    public Chromosome<T> getParent (){
        return this.parent;
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
