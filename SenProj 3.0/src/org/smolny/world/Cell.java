package org.smolny.world;

import org.smolny.agent.Agent;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by I326876 on 03.03.2016.
 */
public class Cell {

    private int x;
    private int y;
    private Set<Agent> agents;

    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        this.agents = new HashSet<>();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Set<Agent> getAgents() {

        return agents;
    }
}
