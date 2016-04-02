package org.smolny.agent;

import org.smolny.utils.Point;
import org.smolny.world.CellProjection;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by dsh on 3/22/16.
 */
public class Grass extends Material {

    public Grass() {
        super();
    }

    @Override
    public void tick(CellProjection[][] environment) {
        int count = MAX;
        Point p = null;
        if (lifeLevel < 1) {
            handle.die();
        }
        if (lifeTime > 15) {
            for (int i = 0; i < environment.length; i++) {
                for (int j = 0; j < environment[i].length; j++) {
                    CellProjection cp = environment[i][j];
                    if (!(cp.getAgents().contains(Grass.class))) {
                        //free cell
                        int h = Math.abs(localPosition.getX() - cp.getLocalPoint().getX()) + Math.abs(localPosition.getY() - cp.getLocalPoint().getY());
                        if (h >= 1 && h < count) {
                            count = h;
                            p = cp.getLocalPoint();
                        }
                    }
                }
            }
            handle.createGrass(this, p);
        }
    }
}
