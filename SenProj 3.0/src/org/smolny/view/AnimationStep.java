package org.smolny.view;

import org.smolny.agent.Agent;
import org.smolny.utils.IntPoint;

/**
 * Created by Asus on 08.04.2016.
 */
public class AnimationStep {
    private Agent agent;
    private IntPoint from;
    private IntPoint to;
    private AnimationType type;

    public IntPoint getFrom() {
        return from;
    }

    public void setFrom(IntPoint from) {
        this.from = from;
    }

    public IntPoint getTo() {
        return to;
    }

    public void setTo(IntPoint to) {
        this.to = to;
    }

    public AnimationType getType() {
        return type;
    }

    public void setType(AnimationType type) {
        this.type = type;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public static AnimationStep create(IntPoint from, IntPoint to, AnimationType type) {
        AnimationStep result = new AnimationStep();
        result.from = from;
        result.to = to;
        result.type = type;
        return result;
    }

}
