package app.model;

import java.util.HashMap;
import java.util.Map;

public class StateManager {
    private final ShireMap shireMap;

    public StateManager(ShireMap shireMap) {
        this.shireMap = shireMap;
    }

    public Map<Integer, Integer> createDataSnapshot() {
        Map<Integer, Integer> snapshot = new HashMap<>();

        for (Node node : shireMap.getNodes().values()) {
            if (node instanceof Field) {
                Field field = (Field) node;
                snapshot.put(field.getId(), field.getCurrentAmountOfBarleyInTheField());
            } else if (node instanceof Brewery) {
                Brewery brewery = (Brewery) node;
                snapshot.put(brewery.getId(), brewery.getCurrentAmountOfBeerProduced());
            } else if (node instanceof Inn) {
                Inn inn = (Inn) node;
                snapshot.put(inn.getId(), inn.getAmountOfBeer());
            }
        }

        return snapshot;
    }

    public void restoreFromSnapshot(Map<Integer, Integer> snapshot) {
        for (Map.Entry<Integer, Integer> entry : snapshot.entrySet()) {
            int nodeId = entry.getKey();
            int value = entry.getValue();
            Node node = shireMap.getNode(nodeId);

            if (node instanceof Field) {
                ((Field) node).setCurrentAmountOfBarleyInTheField(value);
            } else if (node instanceof Brewery) {
                ((Brewery) node).setCurrentAmountOfBeerProduced(value);
            } else if (node instanceof Inn) {
                ((Inn) node).setAmountOfBeer(value);
            }
        }
    }
}