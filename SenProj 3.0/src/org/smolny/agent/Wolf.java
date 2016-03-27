package org.smolny.agent;

import org.smolny.utils.Point;
import org.smolny.world.Cell;
import org.smolny.world.CellProjection;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by dsh on 3/19/16.
 */
public class Wolf extends LivingEntity {

    public Wolf() {
        super();
    }


    @Override
    public void tick(CellProjection[][] environment) {
        Point lp = this.getLocalPosition(); // position of the wolf
        super.tick(environment);
        int count = MAX;
        UUID cid = null;
        CellProjection prey = null;
        if (this.getLifeLevel() < 1) {
            handle.die();
        } else
        if (this.getLifeLevel() < 50) {
            // search for food
            for (Point p : memory.getKSet()) {
                CellProjection cp = memory.get(p);
                 if (cp != null) {
                      if (cp.getAgents().stream().map( ap -> ap.getC()).collect(Collectors.toSet()).contains(Rabbit.class)) {
                           int h = Math.abs(lp.getX() - p.getX()) + Math.abs(lp.getY() - p.getY());
                           if (h < count) {
                               count = h;
                               prey = cp;
                               cid = prey.getIdOfAgent(cp.getAgents(), Rabbit.class);
                           }
                      }
                 }
            }

            if (prey != null) {
                // rabbit found
                pathFindTo(lp, prey.getLocalPoint(), memory);
                if (lp.equals(prey.getLocalPoint())) {
                    handle.eat(cid);
                }
            } else {
                randomMove(memory, lp);
            }
        } else {
            randomMove(memory, lp);
        }
    }

}
