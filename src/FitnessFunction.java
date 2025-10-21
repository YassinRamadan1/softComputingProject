import java.util.Vector;
import chromosome.Chromosome;

public interface FitnessFunction<T>
{
    double evaluate(Chromosome<T> x);
}

// class MachineCaseStudyFitness implements FitnessFunction<Vector<Integer>> 
// {
//     public int NumberOfMachines;
//     @Override
//     public double evaluate(Vector<Integer> individual) {    
//         return 0.0;
//     }
// }


