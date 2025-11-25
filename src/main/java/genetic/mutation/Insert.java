package genetic.mutation;

import java.util.Random;

import genetic.chromosome.Chromosome;

public class Insert implements Mutation<Integer> {
    @Override
    public Chromosome<Integer> mutate(Chromosome<Integer> chromosome, double mutationRate) {
        Random random = new Random();
        
        if (random.nextDouble() > mutationRate) {
            return chromosome;
        }

        int length = chromosome.getGenes().size();

        int randomPosition1 = random.nextInt(length);
        int randomPosition2;

        do {
            randomPosition2 = random.nextInt(length);
        } while (Math.abs(randomPosition1 - randomPosition2) <= 1);

        int lastIdx = Math.max(randomPosition1, randomPosition2);
        int firstIdx = Math.min(randomPosition1, randomPosition2);

        for(int i=lastIdx; i>firstIdx+1;i--) {
            Integer temp = chromosome.getGenes().get(i);
            chromosome.getGenes().set(i, chromosome.getGenes().get(i-1));
            chromosome.getGenes().set(i-1, temp);
        }

        return chromosome;
    }
    
}
