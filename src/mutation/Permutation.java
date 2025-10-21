package mutation;

import chromosome.Chromosome;

import java.util.Arrays;
import java.util.Random;

import static java.util.Collections.max;
import static java.util.Collections.swap;

public class Permutation implements PermutationMutation {
    @Override
    public Chromosome mutateSwap(Chromosome chromosome) {
        Random random = new Random();

        int randomPosition1 = random.nextInt(chromosome.getGenes().length);
        int randomPosition2;

        do {
            randomPosition2 = random.nextInt(chromosome.getGenes().length);
        } while (randomPosition1 == randomPosition2);

        swap(Arrays.asList(chromosome.getGenes()),  randomPosition1, randomPosition2);

        return chromosome;
    }

    @Override
    public Chromosome mutateInsertion(Chromosome chromosome) {
        Random random = new Random();
        int length = chromosome.getGenes().length;

        int randomPosition1 = random.nextInt(length);
        int randomPosition2;

        do {
            randomPosition2 = random.nextInt(length);
        } while (Math.abs(randomPosition1 - randomPosition2) <= 1);

        int lastIdx = Math.max(randomPosition1, randomPosition2);
        int firstIdx = Math.min(randomPosition1, randomPosition2);

        for(int i=lastIdx; i>firstIdx+1;i--) {
            Object temp = chromosome.getGenes()[i];
            chromosome.getGenes()[i] = chromosome.getGenes()[i-1];
            chromosome.getGenes()[i-1] = temp;
        }

        return chromosome;
    }
}
