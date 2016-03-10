package java.smolny.agent;

import java.smolny.agent.types.MaterialType;

/**
 * Created by dsh on 3/6/16.
 */
public class Material extends Agent {

    public Material() {
        super();
        setLifeLevel();

    }

    private void setLifeLevel() {
        //these 3 covers the whole grid according to the season
        if (type.equals(MaterialType.GRASS) || type.equals(MaterialType.GROUND)
                                            || type.equals(MaterialType.SNOW)) {
            lifeLevel = 300;
        } else {
            lifeLevel = 20;
        }

    }

    public void deleteItem() {

    }

}
