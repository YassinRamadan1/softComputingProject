package replacement;

import java.util.Arrays;
import java.util.Comparator;

import chromosome.Chromosome;

//If GGA is used, the best chromosome from the old population is carried over to the new population
//If SSGA 
public class Elitism_Replacement implements Replacement{

    private final int nElites;

    public Elitism_Replacement(int nElites) {
        this.nElites = nElites;
    }


    @Override
    public Chromosome[] replacement(Chromosome[] population, Chromosome[] offspring) {
        int popSize = population.length;
        int eliteCount = Math.min(nElites, popSize);
        Chromosome[] nextGen = new Chromosome[popSize];

        //sort both arrays by fitness descending
        Arrays.sort(population, Comparator.comparingDouble(Chromosome::getFitness).reversed());
        Arrays.sort(offspring, Comparator.comparingDouble(Chromosome::getFitness).reversed());

        //keep the top N elites from the old population
        int index = 0;
        for (int i = 0; i < eliteCount; i++) {
            nextGen[index++] = population[i];
        }

        //fill remaining slots with the best offspring first
        for (int i = 0; i < offspring.length && index < popSize; i++) {
            nextGen[index++] = offspring[i];
        }

        //if not enough offspring, fill the rest with remaining old population excluding elites
        for (int i = eliteCount; i < population.length && index < popSize; i++) {
            nextGen[index++] = population[i];
        }

        return nextGen;
    }
}



