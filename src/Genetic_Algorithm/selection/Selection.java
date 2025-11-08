package selection;
import java.util.Vector;
import chromosome.Chromosome;

public interface Selection<T> {
    Vector<Chromosome<T>> select(Vector<Chromosome<T>> population, int numSelections);
}