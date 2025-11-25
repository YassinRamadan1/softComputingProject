package genetic.selection;

import genetic.chromosome.Chromosome;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class RouletteWheelSelection<T> implements Selection<T> {
    @Override
    public Vector<Chromosome<T>> select(Vector<Chromosome<T>> population, int numSelections){
        Vector<Chromosome<T>> selected = new Vector<>((Collections.nCopies(numSelections, new Chromosome<T>(new Vector<>()))));

        double totalFitnessSum = 0;
        for (Chromosome<T> c : population) {
            totalFitnessSum += c.getFitness();
        }

        Map<Integer, Float> chromosomeRange = new HashMap<>();

        float currentStart = 0.0f;
        for (int i=0; i<population.size(); i++) {
            float currEnd = currentStart + (float)( (population.get(i).getFitness() / totalFitnessSum) * 100);
            currEnd = Math.round(currEnd * 100f) / 100f;

            chromosomeRange.put(i, currEnd);
            currentStart = currEnd+0.01f;
        }

        double[] randomArray = new double[numSelections];

        for(int i=0; i<numSelections; i++){
            randomArray[i] = Math.random() * (totalFitnessSum-1);
        }

        int idx = 0;
        for (double i :  randomArray) {
            double probability = Math.round((( i / totalFitnessSum)*100) * 100f) / 100f;

            for (Map.Entry<Integer, Float> item : chromosomeRange.entrySet()) {
                if (probability <= item.getValue()) {
                    selected.set(idx, population.get(item.getKey()));
                    idx++;
                    break;
                }
            }
        }
        return selected;
    }
}
