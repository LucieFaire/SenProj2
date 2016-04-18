package org.smolny.agent;

import org.smolny.world.CellProjection;

/**
 * Created by dsh on 3/19/16.
 */
public class Wolf extends LivingEntity {

    double frequency = 0.30;

    public Wolf() {
        super();
    }


    @Override
    public void tick(CellProjection[][] environment) {
        super.tick(environment);
        if (lifeLevel < 20) {
            // search for food
            searchForFood(memory, Rabbit.class);
            //System.out.println("Wolf searches for food");
        } else if (lifeTime > 35 && rand.nextDouble() < frequency) {
            searchForPartner(memory, Wolf.class, this.getSex());
            //System.out.println("Wolf searches for love");
        } else {
                searchForFood(memory, Rabbit.class);
            //System.out.println("Wolf again searches for food");

        }
//
//        Point lp = this.getLocalPosition(); // position of the wolf
//        int count = MAX;
//        UUID cid = null;
//        CellProjection prey = null;
//        if (this.getLifeLevel() < 95) {
//            // search for food
//            for (Point p : memory.getKSet()) {
//                CellProjection cp = memory.get(p);
//                if (cp != null) {
//                    if (cp.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Rabbit.class)) {
//                        int h = Math.abs(lp.getX() - p.getX()) + Math.abs(lp.getY() - p.getY());
//                        if (h < count) {
//                            count = h;
//                            prey = cp;
//                            cid = prey.getIdOfAgent(Rabbit.class);
//                        }
//                    }
//                }
//            }
//
//            if (prey != null) {
//                // rabbit found
//                pathFindTo(lp, prey.getLocalPoint(), memory);
//
//                if (lp.equals(prey.getLocalPoint())) {
//                    handle.eat(cid);
//                }
//            } else {
//                randomMove(memory, lp);
//            }
//        } else {
//            randomMove(memory, lp);
//        }

    }


}



