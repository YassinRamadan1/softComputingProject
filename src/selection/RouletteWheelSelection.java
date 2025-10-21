package selection;

import chromosome.Chromosome;
import java.util.HashMap;
import java.util.Map;

public class RouletteWheelSelection implements Selection {
    @Override
    public Chromosome[] select(Chromosome[] population, int numSelections){
        Chromosome[] selected = new Chromosome[numSelections];

        double totalFitnessSum = 0;
        for (Chromosome c : population) {
            totalFitnessSum += c.getFitness();
        }

        Map<Integer, Float> chromosomeRange = new HashMap<>();

        float currentStart = 0.0f;
        for (int i=0; i<population.length; i++) {
            float currEnd = currentStart + (float)( (population[i].getFitness() / totalFitnessSum) * 100);
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
                    selected[idx] = population[item.getKey()];
                    idx++;
                    break;
                }
            }
        }
        return  selected;
    }
}
