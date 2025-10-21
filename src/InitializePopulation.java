import java.util.Vector;

import chromosome.Chromosome;

public interface InitializePopulation<T> {

    public Vector<Chromosome<T>> initializePopulation();
    
}
