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
        ArrayList<Point> locs = new ArrayList<>();
        Point p = null;
        if (lifeLevel < 1) {
            handle.die();
        }
        if (lifeLevel > 10) {
            for (int i = 0; i < environment.length; i++) {
                for (int j = 0; j < environment[i].length; j++) {
                    CellProjection cp = environment[i][j];
                    if (cp != null) {
                        if ((cp.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Grass.class)) == false) {
                            //free cell
                            p = cp.getLocalPoint();
                            locs.add(p);

                        }
                    }
                }
            }
            if (!(locs.isEmpty())) {
                if (locs.size() == 1) {
                    int r = rand.nextInt(locs.size());
                    p = locs.get(r);
                } else {
                    int size = locs.size() - 1;
                    int r = rand.nextInt(size);
                    p = locs.get(r);
                }
                handle.createGrass(this, p);
            }
        }
    }
}
