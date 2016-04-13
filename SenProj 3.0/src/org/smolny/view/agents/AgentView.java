package org.smolny.view.agents;

import javafx.scene.Node;

/**
 * Created by Asus on 08.04.2016.
 */
public interface AgentView {

    Node getView(int radius, int centerX, int centerY);

    int getZIndex();

}
