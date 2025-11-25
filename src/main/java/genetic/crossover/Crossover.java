package genetic.crossover;

import java.util.Vector;

import genetic.chromosome.Chromosome;

public interface Crossover<T> {
    Vector<Chromosome<T>> crossover(Chromosome<T> parent1, Chromosome<T> parent2);
}
