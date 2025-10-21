package crossover;

import java.util.Vector;

import chromosome.Chromosome;

public interface Crossover<T> {
    Vector<Chromosome<T>> crossover(Chromosome<T> parent1, Chromosome<T> parent2);
}
