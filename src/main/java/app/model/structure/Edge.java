package app.model.structure;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Edge {
    static int nextId = 0;
    final int id;
    int from;
    int to;
    int capacity;
    int repairCost;

    public Edge(int from, int to, int capacity, int repairCost) {
        this.id = nextId++;
        this.from = from;
        this.to = to;
        this.capacity = capacity;
        this.repairCost = repairCost;
    }
}
