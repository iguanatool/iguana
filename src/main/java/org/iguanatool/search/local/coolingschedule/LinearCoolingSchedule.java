package org.iguanatool.search.local.coolingschedule;

public class LinearCoolingSchedule extends CoolingSchedule {

    private double decrement;

    public LinearCoolingSchedule(double startingTemperature,
                                 double decrement) {
        this.temperature = startingTemperature;
        this.decrement = decrement;
    }

    public void cool() {
        temperature -= decrement;
        if (temperature < 0) {
            temperature = 0;
        }
    }
}
