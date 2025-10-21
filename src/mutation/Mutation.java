package mutation;

import chromosome.Chromosome;

public interface Mutation<T> {
    Chromosome<T> mutate(Chromosome<T> chromosome, double mutationRate);
}
