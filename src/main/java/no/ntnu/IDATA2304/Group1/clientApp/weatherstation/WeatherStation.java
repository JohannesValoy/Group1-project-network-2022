package no.ntnu.IDATA2304.Group1.clientApp.weatherstation;

/**
 * Represents a weather station providing data on temperature, atmospheric pressure,
 * rain etc.
 * This class is a <b>facade</b> to the underlying business logic.
 * See <a href="https://en.wikipedia.org/wiki/Facade_pattern"> Facade Pattern, Wikipedia</a>
 * for details.
 */
public class WeatherStation {
  private AtmosphericPressureSensor atmosSensor;
  private RainSensor rainSensor;
  private TemperatureSensor tempSensor;
  private WindSensor windSensor;

  public WeatherStation() {
    this.windSensor = new WindSensor();
    this.rainSensor = new RainSensor();
    this.atmosSensor = new AtmosphericPressureSensor();
    this.tempSensor = new TemperatureSensor();
  }

  public AtmosphericPressureSensor getAtmosSensor() {
    return atmosSensor;
  }

  public RainSensor getRainSensor() {
    return rainSensor;
  }

  public TemperatureSensor getTempSensor() {
    return tempSensor;
  }

  public WindSensor getWindSensor() {
    return windSensor;
  }
}
