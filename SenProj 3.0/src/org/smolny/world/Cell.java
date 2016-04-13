package org.smolny.world;

import org.smolny.agent.Agent;
import org.smolny.utils.IntPoint;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by I326876 on 03.03.2016.
 */
public class Cell {

    private IntPoint point;
    private Set<Agent> agents;

    public Cell(int x, int y) {
        this.point = IntPoint.create(x, y);
        this.agents = new HashSet<>();
    }

   public IntPoint getPoint() {
       return point;
   }

    public Set<Agent> getAgents() {
        return agents;
    }


}
