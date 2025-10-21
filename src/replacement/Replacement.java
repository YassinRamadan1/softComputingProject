package replacement;
import chromosome.Chromosome;
import java.util.Vector;

public interface Replacement<T> {
    Vector<Chromosome<T>> replacement(Vector<Chromosome<T>> population, Vector<Chromosome<T>> offspring);
}
