package app.model;

import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;


public class ShireMap {
    private final Map<Integer, Node> nodes = new HashMap<>(); // lista wszystkich obiektów (pola, browary, karczmy)

    public void addNode(Node n) {
        nodes.put(n.getId(), n);
    }

    public void addEdge(Edge e) {
        Node fromNode = nodes.get(e.getFrom());
        Node toNode = nodes.get(e.getTo());

        if (fromNode != null) {
            fromNode.addOutgoingEdge(e);
        }

        if (toNode != null) {
            toNode.addIncomingEdge(e);
        }
    }

    public Node getNode(int id) {
        return nodes.get(id);
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public void print() {
        for (Node n : nodes.values()) {
            System.out.println(n);
        }
    }

    // metoda tworzaca kopie danych pól, browarów i karczm
    private Map<Integer, Integer> createDataSnapshot() {
        Map<Integer, Integer> snapshot = new HashMap<>();

        for (Node node : nodes.values()) {
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

    // metoda przywracajaca dane z kopii
    private void restoreFromSnapshot(Map<Integer, Integer> snapshot) {
        for (Map.Entry<Integer, Integer> entry : snapshot.entrySet()) {
            int nodeId = entry.getKey();
            int value = entry.getValue();
            Node node = nodes.get(nodeId);

            if (node instanceof Field) {
                ((Field) node).setCurrentAmountOfBarleyInTheField(value);
            } else if (node instanceof Brewery) {
                ((Brewery) node).setCurrentAmountOfBeerProduced(value);
            } else if (node instanceof Inn) {
                ((Inn) node).setAmountOfBeer(value);
            }
        }
    }

    private int[][] createCapacityMatrix() { //metoda tworzaca macierz pojemnosci dla sieci przeplywowej
        int n = nodes.size();
        int[][] capacity = new int[n][n];

        for (int i = 0; i < n; i++) {
            Arrays.fill(capacity[i], 0);
        }

        for (Node node : nodes.values()) {
            for (Edge edge : node.getOutgoingEdges()) {
                capacity[edge.getFrom()][edge.getTo()] = edge.getCapacity();
            }
        }

        return capacity;
    }

    public int[][] createActivationCostMatrix() {
        int n = nodes.size();
        int[][] activationCost = new int[n][n];
        int INF = Integer.MAX_VALUE;


        for (int i = 0; i < n; i++) {
            Arrays.fill(activationCost[i], INF);
            activationCost[i][i] = 0; // koszt przejścia do tego samego wierzchołka to 0
        }

        for (Node node : nodes.values()) {
            for (Edge edge : node.getOutgoingEdges()) {
                activationCost[edge.getFrom()][edge.getTo()] = edge.getRepairCost();
            }
        }

        return activationCost;
    }

    public int calculateFieldToBreweryFlow() {
        int n = nodes.size();
        int superSource = n;
        int superSink = n + 1;

        // rozmiar macierzy to n + 2 (bo dodajemy superSource i superSink)
        int[][] extendedCapacity = new int[n + 2][n + 2];


        int[][] capacity = createCapacityMatrix();

        // kopiowanie istniejącej macierzy
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                extendedCapacity[i][j] = capacity[i][j];
            }
        }

        // lączenie superSource z polami
        for (Node node : nodes.values()) {
            if (node instanceof Field) {
                Field field = (Field) node;
                int fieldId = field.getId();
                int barleyAvailable = field.getCurrentAmountOfBarleyInTheField();
                extendedCapacity[superSource][fieldId] = barleyAvailable;
            }
        }

        // lączenie browarów (Brewery) z superSink
        for (Node node : nodes.values()) {
            if (node instanceof Brewery) {
                int breweryId = node.getId();
                extendedCapacity[breweryId][superSink] = Integer.MAX_VALUE;
            }
        }

        return EdmondsKarp.maxFlow(extendedCapacity, superSource, superSink);
    }

    public int calculateBreweryToInnFlow() {
        int n = nodes.size();
        int superSource = n;
        int superSink = n + 1;

        int[][] capacity = createCapacityMatrix();
        int[][] extendedCapacity = new int[n + 2][n + 2];

        // kopiowanie istniejącej macierzy
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                extendedCapacity[i][j] = capacity[i][j];
            }
        }

        // lączenie superSource z browarami
        for (Node node : nodes.values()) {
            if (node instanceof Brewery) {
                Brewery brewery = (Brewery) node;
                int breweryId = brewery.getId();
                int beerAvailable = brewery.getCurrentAmountOfBeerProduced();
                extendedCapacity[superSource][breweryId] = beerAvailable;
            }
        }

        // lączenie karczm z superSink
        for (Node node : nodes.values()) {
            if (node instanceof Inn) {
                int innId = node.getId();
                extendedCapacity[innId][superSink] = Integer.MAX_VALUE;
            }
        }

        return EdmondsKarp.maxFlow(extendedCapacity, superSource, superSink);
    }


    public void simulateWholeProcess() {
        // tworzenie kopii danych przed symulacją
        Map<Integer, Integer> originalData = createDataSnapshot();

        try {
            int maxBarleyFlow = calculateFieldToBreweryFlow();
            System.out.println("Maksymalny przepływ jęczmienia z pól do browarów: " + maxBarleyFlow + " ton");

            reduceBarleyInFields(maxBarleyFlow);

            int totalBeerProduced = 0;
            List<Brewery> breweries = new ArrayList<>();

            for (Node node : nodes.values()) {
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

            int maxBeerFlow = calculateBreweryToInnFlow();
            int actualBeerDelivered = Math.min(maxBeerFlow, totalBeerProduced);
            System.out.println("Maksymalny przepływ piwa z browarów do karczm: " + actualBeerDelivered + " jednostek");


            distributeBeerToInns(actualBeerDelivered);
        } finally {
            // przywrócenie oryginalnych danych niezależnie od wyniku działania programu
            restoreFromSnapshot(originalData);
        }
    }

    private void reduceBarleyInFields(int totalHarvestedBarley) {
        List<Field> fields = new ArrayList<>();
        for (Node node : nodes.values()) {
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
        for (Node node : nodes.values()) {
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

    public void simulateWholeProcessWithActivation() {
        // tworzenie kopii danych przed symulacją
        Map<Integer, Integer> originalData = createDataSnapshot();

        try {
            // zbieranie ID pól, browarów i karczm
            List<Integer> fieldIds = new ArrayList<>();
            List<Integer> breweryIds = new ArrayList<>();
            List<Integer> innIds = new ArrayList<>();

            Map<Integer, Integer> fieldCapacities = new HashMap<>();

            for (Node node : nodes.values()) {
                if (node instanceof Field) {
                    Field field = (Field) node;
                    fieldIds.add(node.getId());
                    fieldCapacities.put(node.getId(), field.getCurrentAmountOfBarleyInTheField());
                } else if (node instanceof Brewery) {
                    breweryIds.add(node.getId());
                } else if (node instanceof Inn) {
                    innIds.add(node.getId());
                }
            }

            int[][] capacity = createCapacityMatrix();
            int[][] activationCost = createActivationCostMatrix();

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
                    Field field = (Field) nodes.get(fieldId);
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
            //produkcja piiiwa
            int totalBeerProduced = 0;
            for (int breweryId : breweryIds) {
                int barleyReceived = barleyReceivedByBrewery.getOrDefault(breweryId, 0);
                if (barleyReceived > 0) {
                    Brewery brewery = (Brewery) nodes.get(breweryId);
                    int beerProduced = brewery.processBarley(barleyReceived);
                    totalBeerProduced += beerProduced;
                }
            }

            System.out.println("Całkowita ilość wyprodukowanego piwa: " + totalBeerProduced + " jednostek");

            // przygotowanie limitów dla ilości piwa w browarach
            Map<Integer, Integer> breweryCapacities = new HashMap<>();
            for (int breweryId : breweryIds) {
                Brewery brewery = (Brewery) nodes.get(breweryId);
                breweryCapacities.put(breweryId, brewery.getCurrentAmountOfBeerProduced());
            }

            // bliczenie przepływu z browarów do karczm
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
                    Brewery brewery = (Brewery) nodes.get(breweryId);
                    brewery.setCurrentAmountOfBeerProduced(brewery.getCurrentAmountOfBeerProduced() - sentBeer);
                }
            }

            for (int innId : innIds) {
                int receivedBeer = 0;
                for (int i = 0; i < capacity.length; i++) {
                    receivedBeer += breweryToInnResult.getFlow()[i][innId];
                }

                if (receivedBeer > 0) {
                    Inn inn = (Inn) nodes.get(innId);
                    inn.receiveBeerFromBrewery(receivedBeer);
                }
            }


        } finally {
            // przywrócenie oryginalnych danych niezależnie od wyniku działania programu
            restoreFromSnapshot(originalData);
        }
    }
}