package replacement;
import java.util.Vector;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import chromosome.Chromosome;

//If GGA is used, the best chromosome from the old population is carried over to the new population
//If SSGA 
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

        //sort both arrays by fitness descending
        Collections.sort(population, Comparator.comparingDouble(Chromosome::getFitness));
        Collections.reverse(population);
        Collections.sort(offspring, Comparator.comparingDouble(Chromosome::getFitness));
        Collections.reverse(offspring);

        //keep the top N elites from the old population
        int index = 0;
        for (int i = 0; i < eliteCount; i++) {
            nextGen.set(index++, population.get(i));
        }

        //fill remaining slots with the best offspring first
        for (int i = 0; i < offspring.size() && index < popSize; i++) {
            nextGen.set(index++, offspring.get(i));
        }

        //if not enough offspring, fill the rest with remaining old population excluding elites
        for (int i = eliteCount; i < population.size() && index < popSize; i++) {
            nextGen.set(index++, population.get(i));
        }

        return nextGen;
    }
}



