package genetic.mutation;

import genetic.chromosome.Chromosome;

import java.util.Collections;
import java.util.Random;

public class Swap implements Mutation<Integer> {
    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome, double mutationRate) {
        Random random = new Random();

        if (random.nextDouble() <= mutationRate) {
            int size = chromosome.getGenes().size();

            int randomPosition1 = random.nextInt(size);
            int randomPosition2;
            do {
                randomPosition2 = random.nextInt(size);
            } while (randomPosition1 == randomPosition2);

            Collections.swap(chromosome.getGenes(), randomPosition1, randomPosition2);
        }

        return chromosome;
    }
}