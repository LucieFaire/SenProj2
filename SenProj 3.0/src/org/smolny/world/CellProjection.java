package org.smolny.world;

import org.smolny.agent.Agent;
import org.smolny.agent.AgentProjection;
import org.smolny.utils.IntPoint;


import java.util.*;

/**
 * Created by dsh on 3/17/16.
 */
public class CellProjection {

   // public static final int DIAGONAL_COST = 14;
    public static final int V_H_COST = 10;

    //private Point globalPoint = null;
    private Set<AgentProjection> agents;
    private int hCost;
    private int cost;
    private CellProjection parent = null;

    private IntPoint localPoint = null;


    public CellProjection() {
        this.agents = new HashSet<>();
        this.cost = 0;
        this.hCost = 0;
    }

    public void createCopy(Cell cell) {
        if (cell == null) {
            return;
        }
        //this.globalPoint = Point.create(cell.getPoint().getX(), cell.getPoint().getY());
        for (Agent a : cell.getAgents()) {
            agents.add(AgentProjection.create(a));

        }

    }

//    public Point getGlobalPoint() {
//        return this.globalPoint;
//    }

    public Set<AgentProjection> getAgents() {
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

    public IntPoint getLocalPoint() {
        return localPoint;
    }

    public void setLocalPoint(IntPoint point) {
        this.localPoint = point;
    }


    public UUID getRelevantAgent(Class c, char s) {
        for (AgentProjection a : agents) {
            if (a.getC().equals(c) && a.getSex() != s) {
                return a.getId();
            }
        }
        return null;
    }

    public UUID getRelevantAgent(Class c) {
        for (AgentProjection a : agents) {
            if (a.getC().equals(c)) {
                return a.getId();
            }
        }
        return null;
    }




}
