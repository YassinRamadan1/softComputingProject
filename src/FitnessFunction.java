import java.util.Vector;

public interface FitnessFunction<T> 
{
    double evaluate(T chromosome);
}

class MachineCaseStudyFitness implements FitnessFunction<Vector<Integer>> 
{
    public int NumberOfMachines;
    @Override
    public double evaluate(Vector<Integer> individual) {
       
        return 0.0;
    }
}


