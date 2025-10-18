import java.util.Random;
import java.util.Vector;

public class GeneticAlgorithm<T> {
    int rouletteWheelSelection()
    {

        return 0;
    }
    int rankSelection() 
    {

        return 0;
    }
    int singlePointCrossover()
    {

        return 0;
    }
    int uniformCrossover()
    {

        return 0;
    }
    int order1Crossover() 
    {

        return 0;
    }
    int swapMutation() 
    {

        return 0;
    }
    int insertionMutation()
    {

        return 0;
    }
    int flipMutation() 
    {

        return 0;
    }
    int uniformMutation()
    {

        return 0;
    }

    int mutationRate;
    int crossoverRate;
    int populationSize;
    int individualSize;
    int generations;
    Vector<T> population;
    Vector<Double> fitnessValues;
    Random rand = new Random();
    FitnessFunction<T> func;

    void initializePopulation(int populationSize, int individualSize, int crossoverRate, int mutationRate, int generations, Vector<T> population)
    {
        this.populationSize = populationSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.individualSize = individualSize;
        this.generations = generations;
        this.population = population;
        this.fitnessValues = new Vector<Double>(populationSize);

    }

    public void evaluateFitness()
    {
        for(int i = 0; i < populationSize; i++)
        {
            double fitness = func.evaluate(population.get(i));
            fitnessValues.set(i, fitness);
        }

    }

    public void run(String selectionMethod, String crossoverMethod, String mutationMethod, String fitnessFunction)
    {

        evaluateFitness();
        //check if optimal soulution is found or number of generations reached
        // selection
        // crossover
        // mutation
    }
}