package genetic.mutation;

import genetic.chromosome.Chromosome;
import java.util.Random;
import java.util.Vector;

public class Flip implements Mutation<Boolean> {
    private final Random rand = new Random();

    @Override
    public Chromosome<Boolean> mutate(Chromosome<Boolean> chromosome, double mutationRate) {
        Vector<Boolean> genes = chromosome.getGenes();

        for (int i = 0; i < genes.size(); i++) {
            if (rand.nextDouble() < mutationRate) {
                boolean current = genes.get(i);
                genes.set(i, !current);
            }
        }

        return chromosome;
    }
}
