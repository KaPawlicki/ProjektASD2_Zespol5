package app.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SimulationEngine {
    private final ShireMap shireMap;
    private final NetworkFlowManager flowManager;
    private final StateManager stateManager;

    public SimulationEngine(ShireMap shireMap) {
        this.shireMap = shireMap;
        this.flowManager = new NetworkFlowManager(shireMap);
        this.stateManager = new StateManager(shireMap);
    }


    public void simulateWholeProcess() {
        // tworzenie kopii danych przed symulacją
        Map<Integer, Integer> originalData = stateManager.createDataSnapshot();

        try {
            int maxBarleyFlow = flowManager.calculateFieldToBreweryFlow();
            System.out.println("Maksymalny przepływ jęczmienia z pól do browarów: " + maxBarleyFlow + " ton");

            reduceBarleyInFields(maxBarleyFlow);

            int totalBeerProduced = 0;
            List<Brewery> breweries = new ArrayList<>();

            for (Node node : shireMap.getNodes().values()) {
                if (node instanceof Brewery) {
                    breweries.add((Brewery) node);
                }
            }

            int remainingBarley = maxBarleyFlow;
            for (Brewery brewery : breweries) {
                if (remainingBarley > 0) {
                    int barleyToProcess = remainingBarley;
                    int beerProduced = brewery.processBarley(barleyToProcess);
                    remainingBarley -= barleyToProcess;
                    totalBeerProduced += beerProduced;
                }
            }
            System.out.println("Całkowita ilość wyprodukowanego piwa: " + totalBeerProduced + " jednostek");

            int maxBeerFlow = flowManager.calculateBreweryToInnFlow();
            int actualBeerDelivered = Math.min(maxBeerFlow, totalBeerProduced);
            System.out.println("Maksymalny przepływ piwa z browarów do karczm: " + actualBeerDelivered + " jednostek");

            distributeBeerToInns(actualBeerDelivered);
        } finally {
            // przywrócenie oryginalnych danych niezależnie od wyniku działania programu
            stateManager.restoreFromSnapshot(originalData);
        }
    }


    public void simulateWholeProcessWithActivation() {
        // tworzenie kopii danych przed symulacją
        Map<Integer, Integer> originalData = stateManager.createDataSnapshot();

        try {
            // zbieranie ID pól, browarów i karczm
            Map<String, Object> simulationData = flowManager.prepareActivationSimulationData();
            List<Integer> fieldIds = (List<Integer>) simulationData.get("fieldIds");
            List<Integer> breweryIds = (List<Integer>) simulationData.get("breweryIds");
            List<Integer> innIds = (List<Integer>) simulationData.get("innIds");
            Map<Integer, Integer> fieldCapacities = (Map<Integer, Integer>) simulationData.get("fieldCapacities");

            int[][] capacity = flowManager.createCapacityMatrix();
            int[][] activationCost = flowManager.createActivationCostMatrix();

            MaxFlowWithActivation.FlowResult fieldToBreweryResult =
                    MaxFlowWithActivation.minCostMaxFlow(capacity, activationCost, fieldIds, breweryIds, fieldCapacities);

            int barleyFlow = fieldToBreweryResult.getMaxFlow();
            System.out.println("Maksymalny przepływ jęczmienia z pól do browarów: " + barleyFlow + " ton");
            System.out.println("Całkowity koszt aktywacji dróg dla transportu jęczmienia: " + fieldToBreweryResult.getTotalCost());

            // redukcja jęczmienia z pól na podstawie przepływu
            Map<Integer, Integer> barleyHarvestedFromField = new HashMap<>();
            for (int fieldId : fieldIds) {
                int harvested = 0;
                for (int j = 0; j < capacity.length; j++) {
                    harvested += fieldToBreweryResult.getFlow()[fieldId][j];
                }

                if (harvested > 0) {
                    Field field = (Field) shireMap.getNode(fieldId);
                    field.setCurrentAmountOfBarleyInTheField(field.getCurrentAmountOfBarleyInTheField() - harvested);
                    barleyHarvestedFromField.put(fieldId, harvested);
                }
            }

            // zliczanie jęczmienia otrzymanego przez każdy browar
            Map<Integer, Integer> barleyReceivedByBrewery = new HashMap<>();
            for (int breweryId : breweryIds) {
                int received = 0;
                for (int i = 0; i < capacity.length; i++) {
                    received += fieldToBreweryResult.getFlow()[i][breweryId];
                }
                barleyReceivedByBrewery.put(breweryId, received);
            }

            //produkcja piwa
            int totalBeerProduced = 0;
            for (int breweryId : breweryIds) {
                int barleyReceived = barleyReceivedByBrewery.getOrDefault(breweryId, 0);
                if (barleyReceived > 0) {
                    Brewery brewery = (Brewery) shireMap.getNode(breweryId);
                    int beerProduced = brewery.processBarley(barleyReceived);
                    totalBeerProduced += beerProduced;
                }
            }

            System.out.println("Całkowita ilość wyprodukowanego piwa: " + totalBeerProduced + " jednostek");

            // przygotowanie limitów dla ilości piwa w browarach
            Map<Integer, Integer> breweryCapacities = new HashMap<>();
            for (int breweryId : breweryIds) {
                Brewery brewery = (Brewery) shireMap.getNode(breweryId);
                breweryCapacities.put(breweryId, brewery.getCurrentAmountOfBeerProduced());
            }

            // obliczenie przepływu z browarów do karczm
            MaxFlowWithActivation.FlowResult breweryToInnResult =
                    MaxFlowWithActivation.minCostMaxFlow(capacity, activationCost, breweryIds, innIds, breweryCapacities);

            int beerFlow = breweryToInnResult.getMaxFlow();
            System.out.println("Maksymalny przepływ piwa z browarów do karczm: " + beerFlow + " jednostek");
            System.out.println("Całkowity koszt aktywacji dróg dla transportu piwa: " + breweryToInnResult.getTotalCost());
            System.out.println("Całkowity koszt aktywacji dróg: " + (fieldToBreweryResult.getTotalCost() + breweryToInnResult.getTotalCost()));

            // redukcja piwa z browarów na podstawie przepływu
            for (int breweryId : breweryIds) {
                int sentBeer = 0;
                for (int j = 0; j < capacity.length; j++) {
                    sentBeer += breweryToInnResult.getFlow()[breweryId][j];
                }

                if (sentBeer > 0) {
                    Brewery brewery = (Brewery) shireMap.getNode(breweryId);
                    brewery.setCurrentAmountOfBeerProduced(brewery.getCurrentAmountOfBeerProduced() - sentBeer);
                }
            }

            for (int innId : innIds) {
                int receivedBeer = 0;
                for (int i = 0; i < capacity.length; i++) {
                    receivedBeer += breweryToInnResult.getFlow()[i][innId];
                }

                if (receivedBeer > 0) {
                    Inn inn = (Inn) shireMap.getNode(innId);
                    inn.receiveBeerFromBrewery(receivedBeer);
                }
            }
        } finally {
            // przywrócenie oryginalnych danych niezależnie od wyniku działania programu
            stateManager.restoreFromSnapshot(originalData);
        }
    }


    private void reduceBarleyInFields(int totalHarvestedBarley) {
        List<Field> fields = new ArrayList<>();
        for (Node node : shireMap.getNodes().values()) {
            if (node instanceof Field) {
                fields.add((Field) node);
            }
        }

        int remainingToHarvest = totalHarvestedBarley;
        for (Field field : fields) {
            if (remainingToHarvest <= 0) break;

            int harvestedFromField = field.harvest(remainingToHarvest);
            remainingToHarvest -= harvestedFromField;
            System.out.println("Zebrano " + harvestedFromField + " ton jęczmienia z pola " + field.getType());
        }
    }

    private void distributeBeerToInns(int totalBeer) {
        List<Inn> inns = new ArrayList<>();
        for (Node node : shireMap.getNodes().values()) {
            if (node instanceof Inn) {
                inns.add((Inn) node);
            }
        }

        int innsCount = inns.size();
        if (innsCount == 0) return;

        int beerPerInn = totalBeer / innsCount;
        int remainder = totalBeer % innsCount;

        for (int i = 0; i < innsCount; i++) {
            Inn inn = inns.get(i);
            int beerToDeliver = beerPerInn + (i < remainder ? 1 : 0);
            inn.receiveBeerFromBrewery(beerToDeliver);
            System.out.println("Dostarczono " + beerToDeliver + " jednostek piwa do karczmy " + inn.getType());
        }
    }
}