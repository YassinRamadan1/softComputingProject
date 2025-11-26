package genetic.mutation;

import genetic.chromosome.Chromosome;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

public class FloatingUniform implements Mutation<Float> {
    @Override
    public Chromosome<Float> mutate(Chromosome<Float> chromosome, double mutationRate) {
        Vector<Float> genes = chromosome.getGenes();
        Random random = new Random();

        float UB = Collections.max(genes);
        float LB = Collections.min(genes);

        for (int i = 0; i < genes.size(); i++) {
            if (random.nextDouble() < mutationRate) {
                float randomNumber1 = (float) Math.round(Math.random() * 100f) / 100f;

                float delta_L = genes.get(i) - LB;
                float delta_U = UB - genes.get(i);

                float delta = (randomNumber1 <= 0.5f) ? delta_L : delta_U;
                float randomNumber2 = (float) Math.round(random.nextFloat() * delta * 100f) / 100f;

                genes.set(i, (delta == delta_L) ? genes.get(i) - randomNumber2 : genes.get(i) + randomNumber2);
            }
        }

        return chromosome;
    }
}
