package genetic;

import java.util.Collections;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;
import genetic.chromosome.Chromosome;
import genetic.crossover.UniformCrossover;
import genetic.mutation.Insert;
import genetic.replacement.GGA_Replacment;
import genetic.selection.RouletteWheelSelection;
public class CaseStudyApplication {

    public static void main(String[] args){
        System.out.print("Population size = 100\nCrossover rate = 0.7\nMutation rate = 0.01\nGenerations = 1000\n");
        System.out.println("Enter 1 or 2:\n1.Use default parameters\n2.Enter custom parameters");

        int popSize = 100;
        double coRate = 0.7;
        double mRate = 0.01;
        int gs = 1000;

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        if(choice == 2){
            System.out.print("Enter population size: ");
            popSize = sc.nextInt();

            System.out.print("Enter crossover rate (e.g. 0.7): ");
            coRate = sc.nextDouble();

            System.out.print("Enter mutation rate (e.g. 0.01): ");
            mRate = sc.nextDouble();

            System.out.print("Enter number of generations: ");
            gs = sc.nextInt();
        }

        int populationSize = popSize;
        double crossoverRate = coRate;
        double mutationRate = mRate;
        int generations = gs;

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

        boolean[] isFeasible = new boolean[1];

        FitnessFunction<Integer> fitnessFunction = new FitnessFunction<Integer>() {
            @Override
            public EvaluationResult evaluate(Chromosome<Integer> individual) {
                isFeasible[0] = true;
                Vector<Vector<Integer>> Machines = new Vector<Vector<Integer>>(3);
                for (int i = 0; i < 3; i++) {
                    Machines.add(new Vector<>());
                }
                Vector<Integer> genes = individual.getGenes();
                for (int i = 0; i < numberOfJobs; i++) {
                    int index = genes.get(i);
                    Machines.get(index).add(i);
                }

                Vector<String> schedule = new Vector<>();

                double finalTime = 1;
                for(int i = 0; i < Machines.size(); i++) {
                    Vector<Integer> jobs = Machines.get(i);
                    Collections.sort(jobs, (j1, j2) -> {
                        if (limits[j1] != limits[j2]) return Integer.compare(limits[j1], limits[j2]);
                        return Integer.compare(durations[j1], durations[j2]);
                    });

                    double currentTime = 0;
                    int infeasiblePenalty = 0;
                    StringBuilder machineSchedule = new StringBuilder("Machine " + (i + 1) + ": ");

                    for (Integer jobIndex : jobs) {
                        double start = currentTime;
                        double end = currentTime + durations[jobIndex];
                        currentTime = end;

                        if (end > limits[jobIndex]) {
                            isFeasible[0] = false;
                            infeasiblePenalty += (end - limits[jobIndex]);
                        }

                        machineSchedule.append(jobIndex)
                                .append("[")
                                .append((int) start)
                                .append("->")
                                .append((int) end)
                                .append("], ");
                    }

                    if (machineSchedule.length() > 2){
                        machineSchedule.setLength(machineSchedule.length() - 2);
                    }
                    schedule.add(machineSchedule.toString());

                    currentTime += infeasiblePenalty * 1000;
                    finalTime = Math.max(finalTime, currentTime);
                }

                double fitness = 1.0 / finalTime;
                if(!isFeasible[0]){
                    fitness *= 0.001; // give a low fitness to infeasible solutions
                }

                return new EvaluationResult(fitness, schedule, isFeasible[0]);
            }
        };

        InitializePopulation<Integer> initializePopulation = new InitializePopulation<Integer>() {
            @Override
            public Vector<Chromosome<Integer>> initializePopulation() {
                Random rand = new Random();
                Vector <Chromosome<Integer>> population = new Vector<Chromosome<Integer>>(populationSize);

                int maxAttempts = 10000; // Maximum attempts to find a feasible solution
                int totalAttempts = 0;
                int feasibleCount = 0;

                for (int i = 0; i < populationSize; i++) {
                    Chromosome<Integer> bestAttempt = null;
                    double bestFitness = Double.NEGATIVE_INFINITY;

                    while (totalAttempts < maxAttempts) {
                        totalAttempts++;
                        Vector <Integer> genes = new Vector<Integer>(numberOfJobs);
                        for (int j = 0; j < numberOfJobs; j++) {
                            int gene = rand.nextInt(3);
                            genes.add(gene);
                        }
                        Chromosome<Integer> chromosome = new Chromosome<Integer>(genes);
                        double fitness = fitnessFunction.evaluate(chromosome).fitness;
                        if (fitness > bestFitness) {
                            bestFitness = fitness;
                            bestAttempt = chromosome;
                        }

                        if (isFeasible[0]) {
                            feasibleCount++;
                            population.add(chromosome);
                            break;
                        }
                    }
                    totalAttempts = 0;

                    // add best infeasible solution if none feasible found
                    if (population.size() <= i) {
                        population.add(bestAttempt);
                    }
                }

                if(feasibleCount == 0){
                    System.out.println("Warning: No feasible solution found in initialization. Continuing with infeasible population...");
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
        ga_engine.setCrossover(new UniformCrossover<>());
        ga_engine.setMutation(new Insert());
        ga_engine.setSelection(new RouletteWheelSelection<>());
        ga_engine.setReplacement(new GGA_Replacment<>());

        ga_engine.run();
    }
}
