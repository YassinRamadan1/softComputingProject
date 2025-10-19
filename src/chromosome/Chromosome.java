package chromosome;

public class Chromosome {
    private Object[] genes;

    public Chromosome(Object[] genes){
        this.genes = genes;
    }

    public Object[] getGenes() { 
        return genes;
    }

    // Need to be  implemented #################################
    public double getFitness(){
        return 0.0;
    }
}
