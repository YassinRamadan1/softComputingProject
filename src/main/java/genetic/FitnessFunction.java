package genetic;

import genetic.chromosome.Chromosome;

public interface FitnessFunction<T> {
    EvaluationResult evaluate(Chromosome<T> x);
}
