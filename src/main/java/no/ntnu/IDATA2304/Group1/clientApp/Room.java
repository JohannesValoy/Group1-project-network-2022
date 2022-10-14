package no.ntnu.IDATA2304.Group1.clientApp;

import no.ntnu.IDATA2304.Group1.clientApp.weatherstation.RainSensor;
import no.ntnu.IDATA2304.Group1.clientApp.weatherstation.WeatherStation;
import no.ntnu.IDATA2304.Group1.clientApp.weatherstation.WindSensor;

public class Room {

    private WeatherStation weatherStation;
    private RainSensor rainSensor;
    private WindSensor windSensor;

    public Room(){
        this.weatherStation = new WeatherStation();
    }

    public WeatherStation getWeatherStation() {
        return weatherStation;
    }

    public void setWeatherStation(WeatherStation weatherStation){
        this.weatherStation = weatherStation;
    }
}
