package genetic;

import genetic.chromosome.Chromosome;

import java.util.Vector;

public interface InitializePopulation<T> {
    Vector<Chromosome<T>> initializePopulation();

}
