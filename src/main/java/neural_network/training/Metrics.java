package neural_network.training;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Metrics {
    private final ArrayList<Double> lossHistory = new ArrayList<>();
    private final List<Double> accuracies = new ArrayList<>();
    public void addLoss(double loss) {
        lossHistory.add(loss);
    }
    public void addAccuracy(double accuracy) {
        accuracies.add(accuracy);
    }

    public ArrayList<Double> getLossHistory() {
        return lossHistory;
    }

    public void saveToCSV(String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("epoch,loss,accuracy\n");

            for (int i = 0; i < lossHistory.size(); i++) {
                writer.write(
                    (i + 1) + "," +
                    lossHistory.get(i) + "," +
                    accuracies.get(i) + "\n"
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
