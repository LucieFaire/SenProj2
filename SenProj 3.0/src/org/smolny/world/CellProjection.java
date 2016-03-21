package org.smolny.world;

import org.smolny.agent.Agent;

import java.lang.Math;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by dsh on 3/17/16.
 */
public class CellProjection {

    public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;

    private int x;
    private int y;
    private Set<String> agents;
    private int hCost;
    private int cost;
    private CellProjection parent;


    public CellProjection() {
        this.agents = new HashSet<>();
        this.cost = 0;
        this.hCost = 0;
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

    public int getCost() {
        return cost;
    }

    public int getHCost() {
        return hCost;
    }

    public CellProjection getParent() {
        return parent;
    }

    public void setCost(int f) {
        this.cost = f;
    }

    public void setHCost(int h) {
        this.hCost = h;
    }

    public void setParent(CellProjection c) {
        this.parent = c;
    }

}
