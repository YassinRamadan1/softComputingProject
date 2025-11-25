package genetic.crossover;
import java.util.Vector;
import java.util.Random;
import genetic.chromosome.Chromosome;
import java.util.Collections;

public class OrderOneCrossover<T> implements Crossover<T> {
    private Random rand = new Random();

    private boolean containGene(Vector<T> genes, T gene){
        for(T g : genes){
            if(g != null && gene.equals(g)){
                return true;
            }
        }
        return false;
    }

    private void fillRemainingGenes(Vector<T> offSpringGenes, Chromosome<T> parent, int start, int end){
        int length = offSpringGenes.size();
        int currentPos = end % length;

        for(int i = 0; i < length; i++){
            T gene = parent.getGenes().get((end + i) % length);
            if(!containGene(offSpringGenes, gene)){
                while (offSpringGenes.get(currentPos) != null) {
                    currentPos = (currentPos + 1) % length;
                }
                offSpringGenes.set(currentPos,gene);
            }
        }
    }

    @Override
    public Vector<Chromosome<T>> crossover(Chromosome<T> parent1, Chromosome<T> parent2) {
        int length = parent1.getGenes().size();
        // Can crash if length < 2 ####################################################
        int start = rand.nextInt(length - 2);
        int end = rand.nextInt(length - start - 1) + start + 1;

        Vector<T> offSpringGenes1 = new Vector<>((Collections.nCopies(length, (T) null)));
        Vector<T> offSpringGenes2 = new Vector<>((Collections.nCopies(length, (T) null)));


        for(int i = start; i <= end; i++){
            offSpringGenes1.set(i, parent1.getGenes().get(i));
            offSpringGenes2.set(i, parent2.getGenes().get(i));
        }

        fillRemainingGenes(offSpringGenes1, parent2, start, end + 1);
        fillRemainingGenes(offSpringGenes2, parent1, start, end + 1);

        Chromosome<T> offSpring1 = new Chromosome<T>(offSpringGenes1);
        offSpring1.parent = parent1; 
        Chromosome<T> offSpring2 = new Chromosome<T>(offSpringGenes2);
        offSpring2.parent = parent2; 

        Vector<Chromosome<T>> offSpring = new Vector<Chromosome<T>>(2);
        offSpring.add(offSpring1);
        offSpring.add(offSpring2);
        return offSpring;
    }
}