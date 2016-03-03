package org.smolny.agent_world;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by I326876 on 03.03.2016.
 */
public class Cell {

    private int x;
    private int y;
    private List<Agent> itemStack;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.itemStack = new ArrayList<Agent>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public List<Agent> getItemStack() {
        return itemStack;
    }
}
