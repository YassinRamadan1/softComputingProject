package genetic.selection;

import genetic.chromosome.Chromosome;

import java.util.Vector;

public interface Selection<T> {
    Vector<Chromosome<T>> select(Vector<Chromosome<T>> population, int numSelections);
}