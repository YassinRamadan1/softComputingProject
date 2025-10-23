import java.util.Random;
import java.util.Vector;
import chromosome.Chromosome;
import crossover.Crossover;
import mutation.Mutation;
import replacement.Replacement;
import selection.Selection;

public class GeneticAlgorithm<T> {
    double mutationRate;
    double crossoverRate;
    int populationSize;
    int chromosomeLength;
    int generations;
    Vector<Chromosome<T>> population;
    Vector<Double> fitnessValues;
    Random rand = new Random();
    FitnessFunction<T> func;
    InfeasibilityHandler infeasibilityHandler;
    InitializePopulation<T> initializePopulation;


    Selection<T> selection;
    Replacement<T> replacement;
    Crossover<T> cv ;
    Mutation<T> mutation;

    public void setSelection(Selection<T> selection) {
        this.selection = selection;
    }

    public void setCrossover(Crossover<T> crossover) {
        this.cv = crossover;
    }

    public void setMutation(Mutation<T> mutation) {
        this.mutation = mutation;
    }

    public void setReplacement(Replacement<T> replacement) {
        this.replacement = replacement;
    }
    
    public void setPopulationSize(int populationSize)
    {
        this.populationSize = populationSize;
        fitnessValues = new Vector<>(populationSize);
        for (int i = 0; i < populationSize; i++) {
            fitnessValues.add(0.0);
        }
    }

    public void setChromosomeLength(int chromosomeLength)
    {
        this.chromosomeLength = chromosomeLength;
    }

    
    public void setCrossoverRate(double crossoverRate)
    {
        this.crossoverRate = crossoverRate;
    }

    public void setMutationRate(double mutationRate)
    {
        this.mutationRate = mutationRate;
    }

    public void setGenerations(int generations)
    {
        this.generations = generations;
    }

    public void setFitnessFunction(FitnessFunction<T> func)
    {
        this.func = func;

    }

    public void setInitialization(InitializePopulation<T> initializePopulation){
        this.initializePopulation = initializePopulation;
    }

    public void run()
    {
        population = initializePopulation.initializePopulation();
        double maxFitness = 0;
        Chromosome bestChromosome = population.get(0);
        for(int t=0; t<generations; t++){
            for(int i=0; i<populationSize; i++){
                double fitness = func.evaluate(population.get(i));
                fitnessValues.set(i, fitness);
                if(maxFitness < fitness){
                    maxFitness = fitness;
                    bestChromosome = population.get(i);
                }
            }

            Vector<Chromosome<T>> selected = selection.select(population, (int)(population.size() * crossoverRate));
            Vector<Chromosome<T>> offSpring = new Vector<>();
            
            for (int i = 0; i < selected.size(); i += 2) {
                offSpring.addAll(cv.crossover(selected.get(i), selected.get((i+1)%selected.size())));
            }

            Vector <Chromosome<T>> mutated = new Vector<>(); 
            for (int i = 0; i < offSpring.size(); i++) {
                mutated.add(mutation.mutate(offSpring.get(i), mutationRate));
            }

            population = replacement.replacement(population, mutated);
                        
        }

        // System.out.println(maxFitness);
        // System.out.println(bestChromosome.getGenes());

        System.out.println("\nBest Fitness: " + maxFitness);
        System.out.println("Best Chromosome: " + bestChromosome.getGenes());
        System.out.println("\nExecution Order per Machine:");
        for (String s : (Vector<String>) bestChromosome.getSchedule()) {
            System.out.println(s);
        }
    }
}