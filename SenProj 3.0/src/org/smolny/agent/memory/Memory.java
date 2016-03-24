package org.smolny.agent.memory;

import org.smolny.utils.Point;
import org.smolny.world.CellProjection;
import sun.text.CodePointIterator;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Asus on 24.03.2016.
 */
public class Memory {

    private Map<Point, CellProjection> memoryGrid = new HashMap<>();

    public void update(CellProjection[][] env) {
        for(int i=0; i < env.length; i++) {
            for(int j=0; j < env[i].length; j++) {
                CellProjection cell = env[i][j];
                memoryGrid.put(cell.getLocalPoint(), cell);
            }
        }
    }

    public CellProjection get(Point p) {
        return memoryGrid.get(p);
    }

    public CellProjection get(int i, int j) {
        return memoryGrid.get(Point.create(i,j));
    }


}
