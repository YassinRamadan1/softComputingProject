package replacement;
import chromosome.Chromosome;
import java.util.Vector;
//What should it take?   -->  population list, Offspring list
//What should it return? -->  New Generation list of chromosomes
public interface Replacement<T> {

    Vector<Chromosome<T>> replacement(Vector<Chromosome<T>> population, Vector<Chromosome<T>> offspring);
}
