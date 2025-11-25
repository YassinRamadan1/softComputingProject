package genetic.mutation;

import genetic.chromosome.Chromosome;

public interface Mutation<T> {
    Chromosome<T> mutate(Chromosome<T> chromosome, double mutationRate);
}
