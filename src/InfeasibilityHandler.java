import chromosome.Chromosome;

public interface InfeasibilityHandler {
    boolean isFeasible(Chromosome chromosome);
}