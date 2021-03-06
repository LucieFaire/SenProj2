package org.smolny.agent.memory;

import org.smolny.utils.IntPoint;
import org.smolny.world.CellProjection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Asus on 24.03.2016.
 */
public class Memory {

    private Map<IntPoint, CellProjection> memoryGrid;

    public Memory() {
        this.memoryGrid = new HashMap<>();
    }

    // update the memory by adding new env + updating the old cells
    public void update(CellProjection[][] env) {
        for(int i = 0; i < env.length; i++) {
            for(int j = 0; j < env[i].length; j++) {
                CellProjection cell = env[i][j];
                if (cell != null) {
                    memoryGrid.put(cell.getLocalPoint(), cell);
                }
            }
        }
    }

    public CellProjection get(IntPoint p) {
        return memoryGrid.get(p);
    }

    public CellProjection get(int i, int j) {
        return memoryGrid.get(IntPoint.create(i,j));
    }

    public Map<IntPoint, CellProjection> getMemory() {
        return memoryGrid;
    }

    public int getSize() {
        return memoryGrid.size();
    }

    public Set<IntPoint> getKSet() {
        return memoryGrid.keySet();
    }
}
