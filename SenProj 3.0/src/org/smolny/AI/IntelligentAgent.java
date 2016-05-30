package org.smolny.AI;

import org.smolny.agent.PreyPredator.*;
import org.smolny.world.CellProjection;

/**
 * Created by dsh on 4/30/16.
 */
public class IntelligentAgent extends PreyPredator implements Prey {

    private RFLModule module;
    public static final int max = Integer.MAX_VALUE;

    public IntelligentAgent() {
        super();
    }

    public void preTick() {
        if (lifeLevel < 1 || lifeTime == max) {
            handle.die();
            System.out.println("Smarty has died cause he is hungry");
        }
        lifeLevel -= 4;
    }

    @Override
    public void onDie() {
        this.module.agentDied();
        if (getLifeLevel() > 1) {
            System.out.println("Wolf ate smarty");
        }
    }

    @Override
    public void onCreate() {
        //TODO
    }

    public void setRFLModule(RFLModule module) {
        this.module = module;
    }

    @Override
    public void tick(CellProjection[][] environment) {
        super.tick(environment);
        module.newState(this, memory); // create the state
        Action a = module.chooseAction(0.5); // choose the action according to the state
        mapAction(a); // do the action
        System.out.println("Smarty has lifelevel "  + lifeLevel + " and took the "+ a.toString());

    }

    //mapping high-level actions to the game ones
    private void mapAction(Action a) {

      if (a.equals(Action.EAT)) {
          searchForFood(memory, Grass.class, 1);
      } else if (a.equals(Action.PARTNER)) {
          searchForPartner(memory, Rabbit.class, this.getSex(), 1);
      } else {
           runAway(memory, Wolf.class, 1, 5);

      }

    }
}
