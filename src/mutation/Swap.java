package mutation;

import chromosome.Chromosome;

import java.util.Arrays;
import java.util.Random;

import static java.util.Collections.max;
import static java.util.Collections.swap;

public class Swap implements Mutation<Integer> {
    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome, double mutationRate) {
        Random random = new Random();

        int randomPosition1 = random.nextInt(chromosome.getGenes().size());
        int randomPosition2;

        do {
            randomPosition2 = random.nextInt(chromosome.getGenes().size());
        } while (randomPosition1 == randomPosition2);

        swap(Arrays.asList(chromosome.getGenes()),  randomPosition1, randomPosition2);

        return chromosome;
    }


}
