package replacement;
import java.util.Collections;
import java.util.Comparator;
import java.util.Vector;

import chromosome.Chromosome;

public class SSGA_Replacement<T> implements Replacement<T>{

    @Override
     public Vector<Chromosome<T>> replacement(Vector<Chromosome<T>> population, Vector<Chromosome<T>> offspring) {
        //sort population ascending #worst first
        Collections.sort(population, Comparator.comparingDouble(Chromosome::getFitness));

        //replace worst individuals with the best offspring
        for (int i = 0; i < offspring.size() && i < population.size(); i++) {
            if (offspring.get(i).getFitness() > population.get(i).getFitness()) {
                population.set(i, offspring.get(i));
            }
        }
        return population;
    }
    
}
