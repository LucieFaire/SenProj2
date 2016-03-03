package org.smolny.mcagents.platform;

/**
 * Created by veter on 06.02.2016.
 */
public enum LifeCycle {

    SCREENING,          //server thread
    REASONING,          //platform thread
    ACTION_SCHEDULE,    //platform thread
    ACTION_PERFORM,     //server thread
    ACTION_DONE         //

}
