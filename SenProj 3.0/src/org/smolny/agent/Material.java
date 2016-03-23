package org.smolny.agent;


import org.smolny.world.CellProjection;

import java.util.ArrayList;

/**
 * Created by dsh on 3/6/16.
 */
public class Material extends Agent {

   public Material() {
      super();
      this.lifeLevel = 70;
   }

   public void grow(CellProjection[][] env) {
      ArrayList<CellProjection> l = new ArrayList<>();
      for (int i = 0; i < env.length; i++) {
         for (int j = 0; j < env[i].length; j++) {
               if (!env[i][j].getAgents().containsKey("Grass")) {
                  l.add(env[i][j]);
               }
         }
      }
      int choice = rand.nextInt(l.size());
      CellProjection c = l.get(choice);
      handle.grow(c.getX(), c.getY());

   }

}
