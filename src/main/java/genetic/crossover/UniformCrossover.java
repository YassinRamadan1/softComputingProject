package genetic.crossover;
import java.util.Vector;
import java.util.Collections;
import java.util.Random;
import genetic.chromosome.Chromosome;

public class UniformCrossover<T> implements Crossover<T> {
    private Random rand = new Random();

    @Override
    public Vector<Chromosome<T>> crossover(Chromosome<T> parent1, Chromosome<T> parent2) {
        int length = parent1.getGenes().size();

        
        Vector<T> offSpringGenes1 = new Vector<T> ((Collections.nCopies(length, (T) null)));
        Vector<T> offSpringGenes2 = new Vector<T> ((Collections.nCopies(length, (T) null)));

        for(int i=0; i<length; i++){
            if(rand.nextBoolean()){
                offSpringGenes1.set(i, parent1.getGenes().get(i));
                offSpringGenes2.set(i, parent2.getGenes().get(i));
            }
            else{
                offSpringGenes1.set(i, parent2.getGenes().get(i));
                offSpringGenes2.set(i, parent1.getGenes().get(i));
            }
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