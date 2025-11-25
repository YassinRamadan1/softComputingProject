package genetic.replacement;
import java.util.Vector;

import genetic.chromosome.Chromosome;

public class SSGA_Replacement<T> implements Replacement<T>{

    @Override
     public Vector<Chromosome<T>> replacement(Vector<Chromosome<T>> population, Vector<Chromosome<T>> offspring) {
        for (int i = 0; i < offspring.size(); i++) { 
            if (offspring.get(i).getFitness() > offspring.get(i).parent.getFitness()) {
                Integer x = population.indexOf(offspring.get(i).parent);
                if(x != -1){
                    population.set(x, offspring.get(i));
                }
            }
        }
        return population;
    }
    
}
