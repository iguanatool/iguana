package org.iguanatool.search.local.coolingschedule;

public class FastCoolingSchedule extends CoolingSchedule {

    private int iteration;

    public FastCoolingSchedule(double startingTemperature) {
        this.temperature = startingTemperature;
        this.iteration = 1;
    }

    public void cool() {
        iteration++;
        temperature /= iteration;
    }
}
