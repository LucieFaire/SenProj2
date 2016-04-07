package org.smolny.agent;

import org.smolny.utils.Point;
import org.smolny.world.CellProjection;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Created by dsh on 3/22/16.
 */
public class Grass extends Material {

    double spawnProb = 0.20;

    public Grass() {
        super();
    }

    @Override
    public void tick(CellProjection[][] environment) {
        ArrayList<Point> locs = new ArrayList<>();
        Point p;
        int center = this.getSight();
        if (surrounded(environment, center)) {
            handle.die();
        } else {
            if (lifeLevel < 8 && rand.nextDouble() < spawnProb) {
                for (int i = 0; i < environment.length; i++) {
                    for (int j = 0; j < environment[i].length; j++) {
                        CellProjection cp = environment[i][j];
                        if (cp != null) {
                            if (!(cp.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Grass.class))) {
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


    private boolean surrounded(CellProjection[][] env, int c) {
        int count = 0;
        for (int i = c - 1; i < c + 2; i++) {
            for (int j = c - 1; j < c + 2; j++) {
                if (env[i][j] != null) {
                    if (env[i][j].getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Grass.class)) {
                        count++;
                    }
                }
            }
        }
        if (count == 8) {
            return true;
        }
        return false;
    }

}
