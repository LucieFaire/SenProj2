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
        if (this.lifeLevel < 1) {
            System.out.println("Rabbit dies.");
            handle.die();
        } else {
            System.out.print("Rabbit takes the random move: ");
            randomMove(memory, this.getLocalPosition());
        }
//        int count = MAX;
//        UUID cid = null;
//        Point lp = this.getLocalPosition();
//        CellProjection wlf = null;
//        CellProjection grass = null;
//        if (this.getLifeLevel() < 1) {
//            handle.die();
//        } else {
//            for (Point p : memory.getKSet()) {
//                CellProjection cp = memory.get(p);
//                if (cp != null) {
//                    // see wolf
//                    if (cp.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Wolf.class)) {
//                        //TODO
//                    }
//                    //see grass
//                    if (cp.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Grass.class)) {
//                        int h = Math.abs(lp.getX() - p.getX()) + Math.abs(lp.getY() - p.getY());
//                        if (h < count) {
//                            count = h;
//                            grass = cp;
//                            cid = grass.getIdOfAgent(Rabbit.class);
//                        }
//
//                    }
//                }
//            }
//
//            if (grass != null && lifeLevel < 50) {
//                pathFindTo(lp, grass.getLocalPoint(), memory);
//                if (lp.equals(grass.getLocalPoint())) {
//                    handle.eat(cid);
//                }
//            } else {
//                randomMove(memory, lp);
//            }
//        }
    }


}
