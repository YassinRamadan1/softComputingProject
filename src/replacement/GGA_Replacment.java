package replacement;
import java.util.Vector;
import java.util.Collections;
import java.util.Comparator;
import chromosome.Chromosome;

public class GGA_Replacment<T> implements Replacement<T> {

    @Override
    public Vector<Chromosome<T>> replacement(Vector<Chromosome<T>> population, Vector<Chromosome<T>> offspring) {
        if(offspring.size() == population.size())
            return offspring;
        
        //make new population array
        Vector<Chromosome<T>> newPopulation = new Vector<>((Collections.nCopies(population.size(), new Chromosome<T>(new Vector<>()))));

        //sort population by fitness descending
        Collections.sort(population, Comparator.comparingDouble(Chromosome::getFitness));
        Collections.reverse(population);
        //replace old population with offspring
        for(int i = 0; i < population.size(); i++) {
            if(i < offspring.size()) {
                newPopulation.set(i, offspring.get(i));
            }
            //if not enough offspring fill with best old population individuals
            else {
                newPopulation.set(i, population.get(i));
            }
        }
        return newPopulation;
    }
    
}
