package crossover;

import java.util.Collections;
import java.util.Random;
import java.util.Vector;

import chromosome.Chromosome;

public class TwoPointCrossover<T> implements Crossover<T> {
    private Random rand = new Random();

    @Override
    public Vector<Chromosome<T>> crossover(Chromosome<T> parent1, Chromosome<T> parent2) {
        int length = parent1.getGenes().size();
        int point1 = rand.nextInt(length - 2);
        int point2 = rand.nextInt(length - point1 - 1) + point1 + 1;

        
        Vector<T> offSpringGenes1 = new Vector<T> ((Collections.nCopies(length, (T) null)));
        Vector<T> offSpringGenes2 = new Vector<T> ((Collections.nCopies(length, (T) null)));

        for(int i=0; i<point1; i++){
            offSpringGenes1.set(i, parent1.getGenes().get(i));
            offSpringGenes2.set(i, parent2.getGenes().get(i));
        }

        for(int i=point1; i<point2; i++){
            offSpringGenes1.set(i, parent2.getGenes().get(i));
            offSpringGenes2.set(i, parent1.getGenes().get(i));
        }

        for(int i=point2; i<length; i++){
            offSpringGenes1.set(i, parent1.getGenes().get(i));
            offSpringGenes2.set(i, parent2.getGenes().get(i));
        }

        Chromosome<T> offSpring1 = new Chromosome<T>(offSpringGenes1);
        Chromosome<T> offSpring2 = new Chromosome<T>(offSpringGenes2);
        offSpring1.parent = parent1;
        offSpring2.parent = parent2;

        Vector<Chromosome<T>> offSpring = new Vector<Chromosome<T>>(2);
        offSpring.add(offSpring1);
        offSpring.add(offSpring2);
        return offSpring;
    }
}