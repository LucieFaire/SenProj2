package org.smolny.agent;

import org.smolny.utils.Point;
import org.smolny.world.CellProjection;

import java.util.ArrayList;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by dsh on 3/19/16.
 */
public class Rabbit extends LivingEntity {

    public Rabbit() {
        super();

    }

    @Override
    public void tick(CellProjection[][] environment) {
        super.tick(environment);
        ArrayList<Point> locs = new ArrayList<>();
        int count = MAX;
        UUID cid = null;
        Point lp = this.getLocalPosition();
        Point wlf;
        Point grass = null;
        if (this.getLifeLevel() < 1) {
            handle.die();
        } else {
            for (Point p : memory.getKSet()) {
                CellProjection cp = memory.get(p);
                if (cp != null) {
                    // see wolf
                    if (cp.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Wolf.class)) {
                        wlf = cp.getLocalPoint(); // collect all the locs of wolves
                        locs.add(wlf);
                    }
                    //see grass
                    if (cp.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Grass.class)) {
                        int h = Math.abs(lp.getX() - p.getX()) + Math.abs(lp.getY() - p.getY());
                        if (h < count) {
                            count = h;
                            grass = cp.getLocalPoint();
                            cid = cp.getIdOfAgent(Grass.class);
                        }

                    }
                }
            }
            wlf = superposition(locs, lp);

            if (grass != null && wlf == null) {
                // no danger, can eat
                pathFindTo(lp, grass, memory);
                if (lp.equals(grass)) {
                    handle.eat(cid);
                }
            } else
            if (wlf != null) {
                // wolf found, run away
                pathFindTo(lp, wlf, memory);
            } else {
                randomMove(memory, lp);
            }
        }
    }


    private Point superposition(ArrayList<Point> arr, Point lp) {
        if (arr.isEmpty()) {
            return null;
        }
        int x = 0;
        int y = 0;
        for (Point p : arr) {
            Point vector = lp.minus(p);
            double length = Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY());
            vector = Point.create((int)(vector.getX() / (length * length)),
                                  (int)(vector.getY() / (length * length)));

            x += vector.getX();
            y += vector.getY();

        }
        Point target = Point.create(Math.round(lp.getX() + x), Math.round(lp.getY() + y));
        return target;
    }

}
