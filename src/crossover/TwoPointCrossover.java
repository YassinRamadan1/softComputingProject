package crossover;

import java.util.Random;
import chromosome.Chromosome;

public class TwoPointCrossover implements Crossover {
    private Random rand = new Random();

    @Override
    public Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
        int length = parent1.getGenes().length;
        int point1 = rand.nextInt(length - 2);
        int point2 = rand.nextInt(length - point1 - 1) + point1 + 1;

        Object[] offSpringGenes1 = new Object[length];
        Object[] offSpringGenes2 = new Object[length];

        for(int i=0; i<point1; i++){
            offSpringGenes1[i] = parent1.getGenes()[i];
            offSpringGenes2[i] = parent2.getGenes()[i];
        }

        for(int i=point1; i<point2; i++){
            offSpringGenes1[i] = parent2.getGenes()[i];
            offSpringGenes2[i] = parent1.getGenes()[i];
        }

        for(int i=point2; i<length; i++){
            offSpringGenes1[i] = parent1.getGenes()[i];
            offSpringGenes2[i] = parent2.getGenes()[i];
        }

        Chromosome offSpring1 = new Chromosome(offSpringGenes1);
        Chromosome offSpring2 = new Chromosome(offSpringGenes2);

        return new Chromosome[] {offSpring1, offSpring2};
    }
}