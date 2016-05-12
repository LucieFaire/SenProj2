package org.smolny.agent.PreyPredator;

import org.smolny.world.CellProjection;

/**
 * Created by dsh on 3/19/16.
 */
public class Rabbit extends PreyPredator {

    private double frequency = 0.24;
    public Rabbit() {
        super();

    }

    @Override
    public void tick(CellProjection[][] environment) {
        super.tick(environment);
        double r = rand.nextDouble();
        if (lifeLevel < 15) {
            searchForFood(memory, Grass.class, 1);
            //System.out.println("Rabbit searches for food");
        } else if (lifeTime > 30 && r < frequency){
            searchForPartner(memory, Rabbit.class, this.getSex(), 1);
            //System.out.println("Rabbit searches for love");
        } else  {
            runAway(memory, Wolf.class, 1);
            //System.out.println("Rabbit runs away");
        }
//        ArrayList<Point> locs = new ArrayList<>();
//        int count = MAX;
//        UUID cid = null;
//        Point lp = this.getLocalPosition();
//        Point wlf;
//        Point grass = null;
//           for (Point p : memory.getKSet()) {
//                CellProjection cp = memory.get(p);
//                if (cp != null) {
//                    // see wolf
//                    if (cp.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Wolf.class)) {
//                        wlf = cp.getLocalPoint(); // collect all the locs of wolves
//                        locs.add(wlf);
//                    }
//                    //see grass
//                    if (cp.getAgents().stream().map(ap -> ap.getC()).collect(Collectors.toSet()).contains(Grass.class)) {
//                        int h = Math.abs(lp.getX() - p.getX()) + Math.abs(lp.getY() - p.getY());
//                        if (h < count) {
//                            count = h;
//                            grass = cp.getLocalPoint();
//                            cid = cp.getIdOfAgent(Grass.class);
//                        }
//
//                    }
//                }
//            }
//            wlf = superposition(locs, lp);
//
//            if (grass != null && wlf == null && lifeLevel < 75) {
//                // no danger, can eat
//                pathFindTo(lp, grass, memory);
//                if (lp.equals(grass)) {
//                    handle.eat(cid);
//                }
//            } else
//            if (wlf != null) {
//                // wolf found, run away
//                pathFindTo(lp, wlf, memory);
//            } else {
//                randomMove(memory, lp);
//            }
    }



//    private Point superposition(ArrayList<Point> arr, Point lp) {
//        if (arr.isEmpty()) {
//            return null;
//        }
//
//        int x = 0;
//        int y = 0;
//        for (Point p : arr) {
//            Point vector = lp.minus(p);
//            double length = Math.sqrt(vector.getX()*vector.getX() + vector.getY()*vector.getY());
//            vector = Point.create((int)(vector.getX() / (length * length)),
//                                  (int)(vector.getY() / (length * length)));
//
//            x += vector.getX();
//            y += vector.getY();
//
//        }
//        Point target = Point.create(Math.round(lp.getX() + x), Math.round(lp.getY() + y));
//        return target;
//    }

}
