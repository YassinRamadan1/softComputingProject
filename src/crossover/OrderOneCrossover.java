package crossover;

import java.util.Random;
import chromosome.Chromosome;

public class OrderOneCrossover implements Crossover {
    private Random rand = new Random();

    private boolean containGene(Object[] genes, Object gene){
        for(Object g : genes){
            if(g != null && gene.equals(g)){
                return true;
            }
        }
        return false;
    }

    private void fillRemainingGenes(Object[] offSpringGenes, Chromosome parent, int start, int end){
        int length = offSpringGenes.length;
        int currentPos = end % length;

        for(int i = 0; i < length; i++){
            Object gene = parent.getGenes()[(end + i) % length];
            if(!containGene(offSpringGenes, gene)){
                while (offSpringGenes[currentPos] != null) {
                    currentPos = (currentPos + 1) % length;
                }
                offSpringGenes[currentPos] = gene;
            }
        }
    }

    @Override
    public Chromosome[] crossover(Chromosome parent1, Chromosome parent2) {
        int length = parent1.getGenes().length;
        // Can crash if length < 2 ####################################################
        int start = rand.nextInt(length - 2);
        int end = rand.nextInt(length - start - 1) + start + 1;

        Object[] offSpringGenes1 = new Object[length];
        Object[] offSpringGenes2 = new Object[length];

        // Copy segment between start and end (inclusive)
        for(int i = start; i <= end; i++){
            offSpringGenes1[i] = parent1.getGenes()[i];
            offSpringGenes2[i] = parent2.getGenes()[i];
        }

        // Fill remaining genes
        fillRemainingGenes(offSpringGenes1, parent2, start, end + 1);
        fillRemainingGenes(offSpringGenes2, parent1, start, end + 1);

        Chromosome offSpring1 = new Chromosome(offSpringGenes1);
        Chromosome offSpring2 = new Chromosome(offSpringGenes2);

        return new Chromosome[] { offSpring1, offSpring2 };
    }
}