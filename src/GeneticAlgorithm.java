import java.util.Random;
import java.util.Vector;

import javax.swing.plaf.basic.BasicMenuUI.ChangeHandler;

import chromosome.Chromosome;
import crossover.Crossover;
import crossover.OrderOneCrossover;
import crossover.SinglePointCrossover;
import crossover.TwoPointCrossover;
import crossover.UniformCrossover;
import mutation.Flip;
import mutation.Insert;
import mutation.Mutation;
import replacement.GGA_Replacment;
import replacement.Replacement;
import selection.RouletteWheelSelection;
import selection.Selection;

public class GeneticAlgorithm<T> {
    // int rouletteWheelSelection()
    // {

    //     return 0;
    // }
    // int rankSelection() 
    // {

    //     return 0;
    // }
    // int singlePointCrossover()
    // {

    //     return 0;
    // }
    // int uniformCrossover()
    // {

    //     return 0;
    // }
    // int order1Crossover() 
    // {

    //     return 0;
    // }
    // int swapMutation() 
    // {

    //     return 0;
    // }
    // int insertionMutation()
    // {

    //     return 0;
    // }
    // int flipMutation() 
    // {

    //     return 0;
    // }
    // int uniformMutation()
    // {

    //     return 0;
    // }

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




    // void initializePopulation(int populationSize, int individualSize, int crossoverRate, int mutationRate, int generations, VectorT population)
    // {
    //     this.populationSize = populationSize;
    //     this.crossoverRate = crossoverRate;
    //     this.mutationRate = mutationRate;
    //     this.individualSize = individualSize;
    //     this.generations = generations;
    //     this.population = population;
    //     this.fitnessValues = new Vector<Double>(populationSize);

    // }
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

    public void setInfeasibilityHandler(InfeasibilityHandler handler) {
        this.infeasibilityHandler = handler;
    }

    public void setInitialization(InitializePopulation<T> initializePopulation){
        this.initializePopulation = initializePopulation;
    }

    // public void evaluateFitness()
    // {
    //     for(int i = 0; i < populationSize; i++)
    //     {
    //         double fitness = func.evaluate(population.get(i));
    //         fitnessValues.set(i, fitness);
    //     }

    // }

    // public void run(String selectionMethod, String crossoverMethod, String mutationMethod, String fitnessFunction)
    // {

    //     evaluateFitness();
    //     //check if optimal soulution is found or number of generations reached
    //     // selection
    //     // crossover
    //     // mutation
    // }

    Selection<T> selection = new RouletteWheelSelection<>();
    Replacement<T> replacement = new GGA_Replacment<>();
    Crossover<T> cv = new UniformCrossover<>();
    Mutation mutation = new Insert();

    public void run()
    {
        population = initializePopulation.initializePopulation();
        for (int i = 0; i < population.size(); i++) {
            System.out.println(population.get(i).getGenes());            
        }
        double maxFitness = 0;
        Chromosome bestChromosome = population.get(0);
        for(int t=0; t<generations; t++){
        // fittness
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
                // [1,2]
            }

            Vector <Chromosome<T>> mutated = new Vector<>(); 
            for (int i = 0; i < offSpring.size(); i++) {
                mutated.add(mutation.mutate(offSpring.get(i), mutationRate));
            }

            population = replacement.replacement(population, mutated);
            
            // for (int i = 0; i < offSpring.size(); i++) {
            //     System.out.println(offSpring.get(i).getGenes());
            // }
            
        }

        System.out.println(maxFitness);
        System.out.println(bestChromosome.getGenes());
        // // // Vector<Integer> g1 = new Vector<>();
        // // // g1.add(1);
        // // // g1.add(2);
        // // // g1.add(3);
        // // // g1.add(4);
        // // // Chromosome<Integer> c1 = new Chromosome<>(g1);
        // // // Vector<Integer> g2 = new Vector<>();
        // // // g2.add(4);
        // // // g2.add(3);
        // // // g2.add(1);
        // // // g2.add(2);
        // // // Chromosome<Integer> c2 = new Chromosome<>(g2);
        
        // // // Vector<Chromosome<Integer>> offSpring = cv.crossover(c1, c2);

        // // // System.out.println(offSpring.get(0).getGenes());
        // // // System.out.println(offSpring.get(1).getGenes());

        // // // Vector <Boolean> v = new Vector<>();
        // // // v.add(false);
        // // // v.add(false);
        // // // v.add(false);
        // // // v.add(false);
        // // // v.add(false);

        // // // Chromosome<Boolean> c = new Chromosome<>(v);
        // // // Chromosome res = m.mutate(c, .5);
        // // // System.out.println(res.getGenes());


        // Vector<Integer> g1 = new Vector<>();
        // g1.add(1);
        // g1.add(0);
        // g1.add(1);
        // g1.add(0);
        // Chromosome<Integer> c1 = new Chromosome<>(g1);       
        // System.out.println(func.evaluate(c1));
        // Vector<Integer> g2 = new Vector<>();
        // g2.add(0);
        // g2.add(0);
        // g2.add(1);
        // g2.add(2);
        // Chromosome<Integer> c2 = new Chromosome<>(g2);       
        // System.out.println(func.evaluate(c2));
        
    }
}