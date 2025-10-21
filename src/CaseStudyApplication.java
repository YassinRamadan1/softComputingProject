import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import chromosome.Chromosome;
import java.util.concurrent.atomic.AtomicBoolean;
public class CaseStudyApplication {

// [[1], [1,3], [2]]

// [0, 1, 2, 1]
// [1, 3, 2, 4]
// 10 15 5 1 
// 15 20 30 5
// sort by time limit for every machine
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter population size: ");
        int populationSize = sc.nextInt();

        System.out.print("Enter crossover rate (e.g. 0.7): ");
        double crossoverRate = sc.nextDouble();

        System.out.print("Enter mutation rate (e.g. 0.02): ");
        double mutationRate = sc.nextDouble();

        System.out.print("Enter number of generations: ");
        int generations = sc.nextInt();
        System.out.print("Enter number of jobs: ");
        Integer numberOfJobs = sc.nextInt();
        Integer [] durations = new Integer[numberOfJobs];
        Integer [] limits = new Integer[numberOfJobs];
        for(int i=0; i<numberOfJobs; ++i){
            System.out.println("Enter duration and limit for job " + i + ": ");   
            Integer duration = sc.nextInt();
            Integer limit = sc.nextInt();
            durations[i] = duration;
            limits[i] = limit;
        }

        boolean [] isFeasable = new boolean[1];
        FitnessFunction<Integer> fitnessFunction = new FitnessFunction<Integer>() {
            @Override
            public double evaluate(Chromosome<Integer> individual) {
                isFeasable[0] = true;
                Vector<Vector<Integer>> Machines = new Vector<Vector<Integer>>(3); 
                for (int i = 0; i < 3; i++) {
                    Vector<Integer> machine = new Vector<>();
                    Machines.add(machine);
                }
                Vector<Integer> genes = individual.getGenes();
                for (int i = 0; i < individual.chromosomeLength; i++) {
                    int index = genes.get(i);
                    Machines.get(index).add(i);
                }

                double finalTime = 1;
                for(int i = 0; i < Machines.size(); i++) {
                    Vector<Integer> jobs = Machines.get(i);
                    Collections.sort(jobs);
                    double totalTime = 0;
                    int infeasible = 0;
                    for (Integer jobIndex : jobs) {
                        totalTime += durations[jobIndex];
                        if (totalTime > limits[jobIndex]) {
                            isFeasable[0] = false;
                            infeasible += totalTime - limits[jobIndex];
                        }
                    }
                    totalTime += infeasible * 1000;
                    finalTime = Math.max(finalTime, totalTime);
                }
                finalTime = 1.0 / finalTime;
                return finalTime;
            }
        };

        InitializePopulation<Integer> initializePopulation = new InitializePopulation<Integer>() {
            @Override
            public Vector<Chromosome<Integer>> initializePopulation() {
                Random rand = new Random();
                Vector <Chromosome<Integer>> population = new Vector<Chromosome<Integer>>(populationSize);
                for (int i = 0; i < populationSize; i++) {
                    Vector <Integer> genes = new Vector<>(numberOfJobs);
                    Chromosome<Integer> c = new Chromosome<>(genes); 
                    population.add(c);
                }
                for (int i = 0; i < populationSize; i++) {
                    Vector <Integer> genes = new Vector<Integer>(numberOfJobs);
                    for (int j = 0; j < numberOfJobs; j++) {
                        genes.add(0);
                    }
                    for(int j=0; j<numberOfJobs; ++j){
                        int gene = (int) Math.floor((rand.nextDouble() * 3));
                        genes.set(j, gene);
                   }
                    Chromosome<Integer> chromosome = new Chromosome<Integer>(genes);
                    
                    double fitness = fitnessFunction.evaluate(chromosome);
                    if(isFeasable[0]){
                        population.set(i, chromosome);
                    }
                    else {
                        i--;
                    }                    
                }
                return population;
            }
        };
        
        GeneticAlgorithm<Integer> ga_engine = new GeneticAlgorithm<Integer>();
        ga_engine.setPopulationSize(populationSize);
        ga_engine.setChromosomeLength(numberOfJobs);
        ga_engine.setFitnessFunction(fitnessFunction);
        ga_engine.setInitialization(initializePopulation);
        ga_engine.setCrossoverRate (crossoverRate);
        ga_engine.setMutationRate(mutationRate);
        ga_engine.setGenerations(generations);
        ga_engine.run();
    }
}
