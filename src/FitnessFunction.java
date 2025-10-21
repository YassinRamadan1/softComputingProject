import java.util.Vector;
import chromosome.Chromosome;

public interface FitnessFunction<T>
{
    double evaluate(Chromosome<T> x);
}
