package org.smolny.agent.PreyPredator;

import org.smolny.agent.AgentProjection;
import org.smolny.agent.LivingEntity;
import org.smolny.agent.memory.Memory;
import org.smolny.utils.IntPoint;
import org.smolny.world.CellProjection;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by dsh on 4/30/16.
 */
public abstract class PreyPredator extends LivingEntity {


    public PreyPredator() {
        super();
    }
    public void searchForPartner(Memory memory, Class c, char s) {
        IntPoint lp = this.getLocalPosition();
        int count = MAX;
        UUID cid = null;
        UUID ccid = null;
        CellProjection found = null;
        for (IntPoint p : memory.getKSet()) {
            CellProjection cp = memory.get(p);
            if (cp != null) {
                ccid = cp.getRelevantAgent(c, s);
                if (ccid != null) {
                    int h = Math.abs(lp.getX() - p.getX()) + Math.abs(lp.getY() - p.getY());
                    if (h < count) {
                        count = h;
                        found = cp;
                        cid = ccid;
                    }
                }
            }
        }
        if (found != null) {
            pathFindTo(lp, found.getLocalPoint(), memory);
            List<AgentProjection> l;
            if (lp.equals(found.getLocalPoint())) {
                l = found.agentsoOfTheSameClass(c);
                if (l.size() < 3) {
                    handle.createAgent(c, this);
                    //System.out.println("Worked");
                }
            }
        } else {
            randomStep(memory, lp);
        }

    }

    public void searchForFood(Memory memory, Class c) {
        IntPoint lp = this.getLocalPosition();
        int count = MAX;
        UUID cid = null;
        UUID ccid = null;
        CellProjection found = null;
        for (IntPoint p : memory.getKSet()) {
            CellProjection cp = memory.get(p);
            if (cp != null) {
                ccid = cp.getRelevantAgent(c);
                if (ccid != null) {
                    int h = Math.abs(lp.getX() - p.getX()) + Math.abs(lp.getY() - p.getY());
                    if (h < count) {
                        count = h;
                        found = cp;
                        cid = ccid;
                    }
                }
            }
        }
        if (found != null) {
            pathFindTo(lp, found.getLocalPoint(), memory);

            if (lp.equals(found.getLocalPoint())) {
                handle.eat(cid);
            }
        } else {
            randomStep(memory, lp);
        }
    }


    public void runAway(Memory memory, Class c) {
        IntPoint lp = this.getLocalPosition();
        List<IntPoint> locs = new ArrayList<>();
        IntPoint wlf;
        for (IntPoint p : memory.getKSet()) {
            CellProjection cp = memory.get(p);
            if (cp != null) {
                if (cp.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(c)) {
                    wlf = cp.getLocalPoint(); // collect all the locs of wolves
                    locs.add(wlf);
                }
            }
        }
        wlf = superposition(locs, lp);
        if (wlf != null) {
            // wolf found, run away
            pathFindTo(lp, wlf, memory);
        } else {
            randomStep(memory, lp);
        }
    }


    private IntPoint superposition(List<IntPoint> arr, IntPoint lp) {
        if (arr.isEmpty()) {
            return null;
        }

        int x = 0;
        int y = 0;
        for (IntPoint p : arr) {
            IntPoint vector = lp.minus(p);
            double length = Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY());
            vector = IntPoint.create((int)(vector.getX() / (length * length)),
                    (int)(vector.getY() / (length * length)));

            x += vector.getX();
            y += vector.getY();

        }
        IntPoint target = IntPoint.create(Math.round(lp.getX() + x), Math.round(lp.getY() + y));
        return target;
    }
}
