package genetic.crossover;

import genetic.chromosome.Chromosome;

import java.util.Vector;

public interface Crossover<T> {
    Vector<Chromosome<T>> crossover(Chromosome<T> parent1, Chromosome<T> parent2);
}
