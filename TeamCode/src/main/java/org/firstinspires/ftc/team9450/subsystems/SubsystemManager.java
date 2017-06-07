package org.firstinspires.ftc.team9450.subsystems;

import java.util.ArrayList;

/**
 * Created by plato2000 on 6/7/17.
 */
public class SubsystemManager {
    private ArrayList<Subsystem> subsystems;

    public SubsystemManager() {
        subsystems = new ArrayList<>();
    }

    public void add(Subsystem s) {
        subsystems.add(s);
    }

    public void loopSystems() {
        for(Subsystem s : subsystems) {
            s.loop();
        }
    }

}
