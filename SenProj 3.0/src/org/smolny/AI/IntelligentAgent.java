package org.smolny.AI;

import org.smolny.agent.LivingEntity;
import org.smolny.world.CellProjection;

/**
 * Created by dsh on 4/30/16.
 */
public class IntelligentAgent extends LivingEntity {

    public IntelligentAgent() {
        super();
    }

    @Override
    public void onDie() {
        //TODO
    }

    @Override
    public void onCreate() {
        //TODO
    }

    @Override
    public void tick(CellProjection[][] environment) {
        super.tick(environment);
        RFLModule.updateReward();
        RFLModule.newState(this, memory);
        //Action a = RFLModule.learning(????);
        //mapAction(a);

    }

    private void mapAction(Action a) {

        String action = a.getAction();
        switch (action) {
           case Action.GOUP:
               handle.goUp();
               break;
            case Action.GODOWN:
                handle.goDown();
                break;
            case Action.GOLEFT:
                handle.goLeft();
                break;
            case Action.GORIGHT:
                handle.goRight();
                break;
//            case Action.EAT:
//                handle.eat(????);
//                break;
            case Action.DIE:
                handle.die();
                break;
//            case Action.ADDAGENT:
//                handle.addAgent(????);
//                break;
//            case Action.CREATEAGENT:
//                handle.createAgent(???);
//                break;

        }
    }
}
