package org.smolny.AI;

import org.smolny.agent.PreyPredator.Grass;
import org.smolny.agent.PreyPredator.PreyPredator;
import org.smolny.agent.PreyPredator.Wolf;
import org.smolny.world.CellProjection;

/**
 * Created by dsh on 4/30/16.
 */
public class IntelligentAgent extends PreyPredator {

    public int reward = 0;
    protected RFLModule module;
    public Action a = null;
    public IntelligentAgent() {
        super();
    }

    @Override
    public void onDie() {
        handle.die(); //?????????
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
        Action a = module.chooseAction(0.1); // choose the action according to the state
        mapAction(a); // do the action
        module.learning( 0.5, 0.5); // learn on previous decision

    }

    //mapping high-level actions to the game ones
    private void mapAction(Action a) {

      if (a.equals(Action.DIE)) {
          onDie();
      } else if (a.equals(Action.EAT)) {
          searchForFood(memory, Grass.class, 2);
      } else {
          runAway(memory, Wolf.class, 2);
      }

    }
}
