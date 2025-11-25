package genetic;

import java.util.Vector;

import genetic.chromosome.Chromosome;

public interface InitializePopulation<T> {
    public Vector<Chromosome<T>> initializePopulation();
    
}
