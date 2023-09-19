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
public class WeatherDto {

    @JsonProperty("latitude")
    private double latitude;
    @JsonProperty("longitude")
    private double longitude;
    @JsonProperty("generationtime_ms")
    private double generationTime;
    @JsonProperty("utc_offset_seconds")
    private int utcOffset;
    @JsonProperty("timezone")
    private String timezone;
    @JsonProperty("elevation")
    private double elevation;
    @JsonProperty("current_weather")
    private CurrentWeatherDto currentWeather;


}
