package org.smolny.view;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Asus on 08.04.2016.
 */
public class AnimationHistory {

    private List<AnimationStep> steps;

    private AnimationHistory() {}

    public void addStep(AnimationStep step) {
        this.steps.add(step);
    }

    public List<AnimationStep> getSteps() {
        return steps;
    }

    public static AnimationHistory create(int initSize) {
        AnimationHistory hist = new AnimationHistory();
        hist.steps = new ArrayList<>(initSize);
        return hist;
    }



}
