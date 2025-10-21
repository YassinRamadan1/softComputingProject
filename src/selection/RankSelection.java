package selection;

import chromosome.Chromosome;
import java.util.*;

public class RankSelection<T> implements Selection<T> {
    private Random rand = new Random();

    @Override
    public Vector<Chromosome<T>> select(Vector<Chromosome<T>> population, int numOfSelections) {
        int size = population.size();

        Vector<Chromosome<T>> sortedPopulation = population;
        Collections.sort(sortedPopulation, Comparator.comparingDouble(Chromosome::getFitness));

        double totalRankSum = (size * (size + 1)) / 2.0;

        double[] cumulativeProb = new double[size];
        double cumulative = 0.0;

        for (int i = 1; i <= size; i++) {
            double rankProb = i / totalRankSum;
            cumulative += rankProb;
            cumulativeProb[i-1] = cumulative;
        }

        Vector<Chromosome<T>> selected = new Vector<>(Collections.nCopies(numOfSelections, new Chromosome<>(new Vector<>())));

        for(int i=0; i<numOfSelections; i++){
            double r = rand.nextDouble();
            for(int j=0; j<size; j++){
                if(r <= cumulativeProb[j]){
                    selected.set(i,sortedPopulation.get(j));
                    break;
                }
            }
        }

        return selected;
    }
}