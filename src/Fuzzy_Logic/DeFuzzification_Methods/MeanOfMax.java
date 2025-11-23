package DeFuzzification_Methods;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import java.util.AbstractMap.SimpleEntry;

import variables.FuzzySet;
import variables.FuzzyVariable;

public class MeanOfMax implements DeFuzzificationMethod {
    
    private FuzzyVariable outputVariable;
    private final Map<String, Double> Memberships = new LinkedHashMap();

    public MeanOfMax(FuzzyVariable outputVariable, Map<String, Double> memberships) {
        this.outputVariable = outputVariable;
        this.Memberships.putAll(memberships);
    }

    SimpleEntry<Double, Double> calculateIntersection(SimpleEntry<Double, Double> line1Point1,
     SimpleEntry<Double, Double> line1Point2, SimpleEntry<Double, Double> line2Point1, SimpleEntry<Double, Double> line2Point2)
    {
        double x1 = line1Point1.getKey();
        double y1 = line1Point1.getValue();
        double x2 = line1Point2.getKey();
        double y2 = line1Point2.getValue();

        double x3 = line2Point1.getKey();
        double y3 = line2Point1.getValue();
        double x4 = line2Point2.getKey();
        double y4 = line2Point2.getValue();

        double denom = (x1 - x2) * (y3 - y4) - (y1 - y2) * (x3 - x4);
        if (denom == 0) {
            return null;
        }

        double px = ((x1 * y2 - y1 * x2) * (x3 - x4) - (x1 - x2) * (x3 * y4 - y3 * x4)) / denom;
        double py = ((x1 * y2 - y1 * x2) * (y3 - y4) - (y1 - y2) * (x3 * y4 - y3 * x4)) / denom;

        return new SimpleEntry<>(px, py);
    }

    public Vector<SimpleEntry<Double, Double>> getAggregatedShape()
    {
        Vector<SimpleEntry<Double, Double>> aggregated = new Vector<>();
        double lastMembership = 0.0;
        for(Map.Entry<String, Double> entry : Memberships.entrySet())
        {
            FuzzySet set = outputVariable.getSet(entry.getKey());
            Vector<Double> fuzzySetPoints = set.getPoints();
            Vector<Double> newFuzzySetPoints = set.getInverse(entry.getValue());
            if(aggregated.size() > 0)
            {
                SimpleEntry<Double, Double> fuzzySetsIntersection = calculateIntersection(
                    aggregated.get(aggregated.size() - 2), aggregated.get(aggregated.size() - 1),
                    new SimpleEntry<Double, Double>(fuzzySetPoints.get(0), 0.0),
                    new SimpleEntry<Double, Double>(newFuzzySetPoints.get(0), entry.getValue()));
                if(fuzzySetsIntersection.getValue() < entry.getValue() && fuzzySetsIntersection.getValue() < lastMembership)
                {
                    aggregated.set(aggregated.size() - 1, fuzzySetsIntersection);
                    aggregated.add(new SimpleEntry<Double, Double>(newFuzzySetPoints.get(0), entry.getValue()));
                    if(newFuzzySetPoints.get(0) != newFuzzySetPoints.get(1))
                        aggregated.add(new SimpleEntry<Double, Double>(newFuzzySetPoints.get(1), entry.getValue()));
                    aggregated.add(new SimpleEntry<Double, Double>(fuzzySetPoints.get(fuzzySetPoints.size() - 1), 0.0));
                }
                else if(fuzzySetsIntersection.getValue() > entry.getValue() && fuzzySetsIntersection.getValue() < lastMembership)
                {
                    aggregated.set(aggregated.size() - 1, calculateIntersection(
                        aggregated.get(aggregated.size() - 2), aggregated.get(aggregated.size() - 1),
                        new SimpleEntry<Double, Double>(0.0, entry.getValue()),
                        new SimpleEntry<Double, Double>(1.0, entry.getValue())));
                    aggregated.add(new SimpleEntry<Double, Double>(newFuzzySetPoints.get(1), entry.getValue()));
                    aggregated.add(new SimpleEntry<Double, Double>(fuzzySetPoints.get(fuzzySetPoints.size() - 1), 0.0));
                }
                else if(fuzzySetsIntersection.getValue() < entry.getValue() && fuzzySetsIntersection.getValue() > lastMembership)
                {
                    aggregated.set(aggregated.size() - 2, calculateIntersection(
                        new SimpleEntry<Double, Double>(fuzzySetPoints.get(0), 0.0),
                        new SimpleEntry<Double, Double>(fuzzySetPoints.get(1), 1.0),
                        new SimpleEntry<Double, Double>(0.0, lastMembership),
                        new SimpleEntry<Double, Double>(1.0, lastMembership)));
                    
                    aggregated.set(aggregated.size() - 1, new SimpleEntry<Double, Double>(newFuzzySetPoints.get(0), entry.getValue()));
                    if(newFuzzySetPoints.get(0) != newFuzzySetPoints.get(1))
                        aggregated.add(new SimpleEntry<Double, Double>(newFuzzySetPoints.get(1), entry.getValue()));
                    aggregated.add(new SimpleEntry<Double, Double>(fuzzySetPoints.get(fuzzySetPoints.size() - 1), 0.0));
                }
                else
                {
                    aggregated.set(aggregated.size() - 2, new SimpleEntry<Double, Double>(newFuzzySetPoints.get(1), entry.getValue()));
                    aggregated.set(aggregated.size() - 1, new SimpleEntry<Double, Double>(fuzzySetPoints.get(fuzzySetPoints.size() - 1), 0.0));
                }
                lastMembership = entry.getValue();
                continue;
            }

            aggregated.add(new SimpleEntry<Double, Double>(fuzzySetPoints.get(0), 0.0));
            aggregated.add(new SimpleEntry<Double, Double>(newFuzzySetPoints.get(0), entry.getValue()));
            if(newFuzzySetPoints.get(0) != newFuzzySetPoints.get(1))
                aggregated.add(new SimpleEntry<Double, Double>(newFuzzySetPoints.get(1), entry.getValue()));
            aggregated.add(new SimpleEntry<Double, Double>(fuzzySetPoints.get(fuzzySetPoints.size() - 1), 0.0));
            lastMembership = entry.getValue();
        }

        return aggregated;
    }

    @Override
    public double getCrispOutput() {

        double maxMembership = Double.NEGATIVE_INFINITY;
        Vector<SimpleEntry<Double, Double>> aggregatedShape = getAggregatedShape();
        for(SimpleEntry<Double, Double> entry : aggregatedShape)
        {
            if(entry.getValue() > maxMembership)
            {
                maxMembership = entry.getValue();
            }
        }

        double sum = 0.0;
        int count = 0;
        for(SimpleEntry<Double, Double> entry : aggregatedShape)
        {
            if(entry.getValue() == maxMembership)
            {
                sum += entry.getKey();
                count++;
            }
        }
        return sum / count;
    }
}
