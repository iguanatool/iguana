package org.iguanatool.search.local.coolingschedule;

public abstract class CoolingSchedule {

    protected double temperature;

    public abstract void cool();

    public double getTemperature() {
        return temperature;
    }
}
