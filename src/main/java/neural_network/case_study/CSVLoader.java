package neural_network.case_study;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class CSVLoader {

    public static Dataset load(String path, boolean hasHeader) {
        List<double[]> inputs = new ArrayList<>();
        List<double[]> targets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;

            if (hasHeader) {
                br.readLine();
            }

            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");

                int featureCount = tokens.length - 1;

                double[] x = new double[featureCount];
                for (int i = 0; i < featureCount; i++) {
                    x[i] = Double.parseDouble(tokens[i]);
                }

                double[] y = new double[]{Double.parseDouble(tokens[featureCount])};

                inputs.add(x);
                targets.add(y);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to load CSV file", e);
        }

        return new Dataset(
                inputs.toArray(new double[0][]),
                targets.toArray(new double[0][])
        );
    }

    public record Dataset(double[][] inputs, double[][] targets) {}
}
