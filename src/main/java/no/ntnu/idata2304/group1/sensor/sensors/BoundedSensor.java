package no.ntnu.idata2304.group1.sensor.sensors;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;
import no.ntnu.idata2304.group1.data.Sensor;

/**
 * Provides sensor values within a given range
 */
public abstract class BoundedSensor extends Sensor {
    private double currentValue;
    private final double min;
    private final double max;

    private static final Random randomGenerator = new Random();

    /**
     * Create a sensor which will provide values in a given range
     *
     * @param initialValue The initial value for the sensor
     * @param minValue minimum allowed value
     * @param maxValue maximum allowed value
     */
    protected BoundedSensor(Sensor.Types type, String name, double initialValue, double minValue,
            double maxValue) {
        super(type, name);
        currentValue = initialValue;
        min = minValue;
        max = maxValue;
    }

    public double readValue() {
        changeCurrentValueRandomly();
        return currentValue;
    }

    private void changeCurrentValueRandomly() {
        double delta = generateRandomDelta();
        currentValue += delta;
        if (isCurrentValueOutOfBoundaries()) {
            currentValue -= 2 * delta;
        }
        roundCurrentValueToOneDecimal();
    }

    /**
     * Generate a random delta, which is a small fraction of the allowed value range.
     *
     * @return The delta value. Note: it can be negative
     */
    private double generateRandomDelta() {
        double maxDelta = (max - min) / 50.0;
        return randomGenerator.nextDouble(-maxDelta, maxDelta);
    }

    private boolean isCurrentValueOutOfBoundaries() {
        return currentValue > max || currentValue < min;
    }

    private void roundCurrentValueToOneDecimal() {
        // Code adapted from https://stackoverflow.com/a/21596413/1703497
        // Note: this won't work, as it will round the number down: (int) (currentValue * 10) / 10.0
        currentValue =
                BigDecimal.valueOf(currentValue).setScale(1, RoundingMode.HALF_UP).doubleValue();
    }
}
