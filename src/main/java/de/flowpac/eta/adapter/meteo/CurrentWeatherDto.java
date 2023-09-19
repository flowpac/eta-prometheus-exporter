package de.flowpac.eta.adapter.meteo;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@ToString
@RegisterForReflection
public class CurrentWeatherDto {

    @JsonProperty("temperature")
    private double temperature;
    @JsonProperty("windspeed")
    private double windSpeed;
    @JsonProperty("winddirection")
    private double windDirection;
    @JsonProperty("weathercode")
    private int weatherCode;

}
