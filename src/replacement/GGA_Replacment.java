package replacement;
import java.util.Arrays;
import java.util.Comparator;
import chromosome.Chromosome;

public class GGA_Replacment implements Replacement {

    @Override
    public Chromosome[] replacement(Chromosome[] population, Chromosome[] offspring) {
        if(offspring.length == population.length)
            return offspring;
        
        //make new population array
        Chromosome[] newPopulation = new Chromosome[population.length];

        //sort population by fitness descending
        Arrays.sort(population, Comparator.comparingDouble(Chromosome::getFitness).reversed());

        //replace old population with offspring
        for(int i = 0; i < population.length; i++) {
            if(i < offspring.length) {
                newPopulation[i] = offspring[i];
            }
            //if not enough offspring fill with best old population individuals
            else {
                newPopulation[i] = population[i];
            }
        }
        return newPopulation;
    }
    
}
