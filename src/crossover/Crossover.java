package crossover;

import chromosome.Chromosome;

public interface Crossover {
    Chromosome[] crossover(Chromosome parent1, Chromosome parent2);
}
