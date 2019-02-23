package creatures;

import huglife.*;

import java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import static huglife.HugLifeUtils.*;

public class Clorus extends Creature {
    /**
     * red color.
     */
    private int r;
    /**
     * green color.
     */
    private int g;
    /**
     * blue color.
     */
    private int b;

    public Clorus(double e) {
        super("clorus");
        r = 0;
        g = 0;
        b = 0;
        energy = e;
    }


    /**
     * Clorus should lose 0.03 units of energy when moving. If you want to
     * to avoid the magic number warning, you'll need to make a
     * private static final variable. This is not required for this lab.
     */
    public void move() {
        // TODO
        energy -= 0.03;
    }
    /**
     * Clorus lose 0.01 energy when staying.
     */
    public void stay() {
        // TODO
        energy -= 0.01;
    }

    public void attack(Creature c) {
        energy += c.energy();
    }

    public Color color() {
        r = 34; b = 231; g = 0;
        return color(r, g, b);
    }

    public Clorus replicate() {
        return new Clorus(energy / 2);
    }

    public Action chooseAction(Map<Direction, Occupant> neighbors) {
        // Rule 1
        Deque<Direction> emptyNeighbors = new ArrayDeque<>();
        Deque<Direction> plips = new ArrayDeque<>();
        boolean anyPlips = false;

        for (Map.Entry<Direction, Occupant> neighbor : neighbors.entrySet()) {
            if (neighbor.getValue().name().equals("empty")) {
                emptyNeighbors.addLast(neighbor.getKey());
            }
            if (neighbor.getValue().name().equals("plip")) {
                plips.addLast(neighbor.getKey());
                anyPlips = true;
            }
        }
        if (emptyNeighbors.isEmpty() && !anyPlips) {
            return new Action(Action.ActionType.STAY);
        }

        // Rule 2
        // HINT: randomEntry(emptyNeighbors)
        if (anyPlips) {
            return new Action(Action.ActionType.ATTACK, randomEntry(plips));
        }


        // Rule 3
        if (energy >= 1) {
            return new Action(Action.ActionType.REPLICATE, randomEntry(emptyNeighbors));
        }

        // Rule 4
        return new Action(Action.ActionType.MOVE, randomEntry(emptyNeighbors));
    }
}
