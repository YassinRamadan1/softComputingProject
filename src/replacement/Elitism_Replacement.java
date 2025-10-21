package replacement;
import java.util.Vector;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import chromosome.Chromosome;

public class Elitism_Replacement<T> implements Replacement<T>{

    private final int nElites;

    public Elitism_Replacement(int nElites) {
        this.nElites = nElites;
    }


    @Override
    public Vector<Chromosome<T>> replacement(Vector<Chromosome<T>> population, Vector<Chromosome<T>> offspring) {
        int popSize = population.size();
        int eliteCount = Math.min(nElites, popSize);
        Vector<Chromosome<T>> nextGen = new Vector<>(popSize);

        Collections.sort(population, Comparator.comparingDouble(Chromosome::getFitness));
        Collections.reverse(population);
        Collections.sort(offspring, Comparator.comparingDouble(Chromosome::getFitness));
        Collections.reverse(offspring);

        int index = 0;
        for (int i = 0; i < eliteCount; i++) {
            nextGen.set(index++, population.get(i));
        }

        for (int i = 0; i < offspring.size() && index < popSize; i++) {
            nextGen.set(index++, offspring.get(i));
        }

        for (int i = eliteCount; i < population.size() && index < popSize; i++) {
            nextGen.set(index++, population.get(i));
        }

        return nextGen;
    }
}



