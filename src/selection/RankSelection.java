package selection;

import chromosome.Chromosome;
import java.util.*;

public class RankSelection implements Selection {
    private Random rand = new Random();

    @Override
    public Chromosome[] select(Chromosome[] population, int numOfSelections) {
        int size = population.length;

        // Step 1: Sort population by fitness ascending
        Chromosome[] sortedPopulation = Arrays.copyOf(population, size);
        Arrays.sort(sortedPopulation, Comparator.comparingDouble(Chromosome::getFitness));

        // Step 2: Assign ranks (highest fitness → highest rank)
        double totalRankSum = (size * (size + 1)) / 2.0;

        // Step 3: Calculate selection probability for each chromosome
        double[] cumulativeProb = new double[size];
        double cumulative = 0.0;

        for (int i = 1; i <= size; i++) {
            double rankProb = i / totalRankSum;
            cumulative += rankProb;
            cumulativeProb[i-1] = cumulative;
        }

        // Step 4: Select parents based on probabilities
        Chromosome[] selected = new Chromosome[numOfSelections];

        for(int i=0; i<numOfSelections; i++){
            double r = rand.nextDouble();
            for(int j=0; j<size; j++){
                if(r <= cumulativeProb[j]){
                    selected[i] = sortedPopulation[j];
                    break;
                }
            }
        }

        return selected;
    }
}