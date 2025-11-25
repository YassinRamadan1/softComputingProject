package genetic.replacement;
import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;
import genetic.chromosome.Chromosome;

public class GGA_Replacment<T> implements Replacement<T> {

    @Override
    public Vector<Chromosome<T>> replacement(Vector<Chromosome<T>> population, Vector<Chromosome<T>> offspring) {
        if(offspring.size() == population.size())
            return offspring;
        
        Vector<Chromosome<T>> newPopulation = new Vector<>((Collections.nCopies(population.size(), new Chromosome<T>(new Vector<>()))));

        Collections.sort(population, Comparator.comparingDouble(Chromosome::getFitness));
        for(int i = 0; i < population.size(); i++) {
            if(i < offspring.size()) {
                newPopulation.set(i, offspring.get(i));
            }
            else {
                newPopulation.set(i, population.get(i));
            }
        }
        return newPopulation;
    }    
}
