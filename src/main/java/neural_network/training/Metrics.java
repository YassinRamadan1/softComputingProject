package neural_network.training;

import java.util.ArrayList;

public class Metrics {
    private final ArrayList<Double> lossHistory = new ArrayList<>();

    public void addLoss(double loss) {
        lossHistory.add(loss);
    }

    public ArrayList<Double> getLossHistory() {
        return lossHistory;
    }
}
