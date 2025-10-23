import java.util.Map;
import java.util.Vector;
import chromosome.Chromosome;

public interface FitnessFunction<T>
{
    Map.Entry<Double, Vector<String>> evaluate(Chromosome<T> x);
}
