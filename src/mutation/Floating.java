package mutation;

import chromosome.Chromosome;

import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class Floating implements FloatingPointMutation {
    @Override
    public Chromosome mutateUniform(Chromosome chromosome) {
        Float[] genes = (Float[]) chromosome.getGenes();
        Random random = new Random();

        float UB = Collections.max(Arrays.asList(genes));
        float LB =  Collections.min(Arrays.asList(genes));

        for (int i = 0; i < genes.length; i++){
            float randomNumber1 = (float) Math.round(Math.random() * 100f) / 100f;

            float delta_L = genes[i] - LB;
            float delta_U = UB - genes[i];

            float delta = (randomNumber1 <= 0.5f) ? delta_L : delta_U;
            float randomNumber2 = (float) Math.round(random.nextFloat() * delta * 100f) / 100f;

            genes[i] = (delta == delta_L) ? genes[i]-randomNumber2 : genes[i]+randomNumber2;
        }

        return chromosome;
    }
}
