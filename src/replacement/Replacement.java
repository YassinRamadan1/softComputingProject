package replacement;
import chromosome.Chromosome;

//What should it take?   -->  population list, Offspring list
//What should it return? -->  New Generation list of chromosomes
public interface Replacement {

    Chromosome[] replacement(Chromosome[] population, Chromosome[] offspring);
}
