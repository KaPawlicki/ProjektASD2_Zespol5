package app.model.structure;

import lombok.ToString;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import java.awt.*;

@ToString(callSuper = true)
@FieldDefaults(level = AccessLevel.PROTECTED)
public class Intersection extends Node {

    public Intersection(int id, String type, Point position) {
        super(id, type, position);
    }
}
