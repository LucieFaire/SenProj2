package org.smolny.world;

import org.smolny.agent.Agent;
import java.lang.Math;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dsh on 3/17/16.
 */
public class CellProjection {

    private int x;
    private int y;
    private Set<String> agents;
    private int[][] heuristics;


    public CellProjection() {
        this.agents = new HashSet<>();


    }

    public void createCopy(Cell cell) {
        this.x = cell.getX();
        this.y = cell.getY();
        for (Agent a : cell.getAgents()) {
            String name = a.getName();
            agents.add(name);
        }

    }



    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public Set<String> getAgents() {
        return this.agents;
    }


}
