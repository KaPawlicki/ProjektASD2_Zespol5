package app.model;

public class Edge {
    private static int nextId = 0;
    private final int id;
    private int from;
    private int to;
    private int capacity; // ile można przewieźć daną drogą
    private int repairCost;

    public Edge(int from, int to, int capacity, int repairCost) {
        this.id = nextId;
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.repairCost = repairCost;
        nextId++;
    }

    public int getId() { return id; }
    public int getFrom() { return from; }
    public int getTo() { return to; }
    public int getCapacity() { return capacity; }
    public int getRepairCost() { return repairCost; }
    public void setFrom(int from) { this.from = from; }
    public void setTo(int to) { this.to = to; }
    public void setCapacity(int capacity) { this.capacity = capacity; }
    public void setRepairCost(int repairCost) { this.repairCost = repairCost; }

    @Override
    public String toString() {
        return "Edge{id=" + id +
                " from=" + from +
                ", to=" + to +
                ", capacity=" + capacity +
                ", repairCost=" + repairCost +
                '}';
    }
}
