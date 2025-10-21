package mutation;

import chromosome.Chromosome;

public interface PermutationMutation {
    Chromosome mutateSwap(Chromosome chromosome);
    Chromosome mutateInsertion(Chromosome chromosome);
}
