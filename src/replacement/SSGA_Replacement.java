package replacement;

import java.util.Arrays;
import java.util.Comparator;

import chromosome.Chromosome;

public class SSGA_Replacement implements Replacement{

    @Override
     public Chromosome[] replacement(Chromosome[] population, Chromosome[] offspring) {
        //sort population ascending #worst first
        Arrays.sort(population, Comparator.comparingDouble(Chromosome::getFitness));

        //replace worst individuals with the best offspring
        for (int i = 0; i < offspring.length && i < population.length; i++) {
            if (offspring[i].getFitness() > population[i].getFitness()) {
                population[i] = offspring[i];
            }
        }
        return population;
    }
    
}
