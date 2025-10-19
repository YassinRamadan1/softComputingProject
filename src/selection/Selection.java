package selection;

import chromosome.Chromosome;

public interface Selection {
    Chromosome[] select(Chromosome[] population, int numSelections);
}