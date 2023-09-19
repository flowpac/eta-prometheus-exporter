package de.flowpac.eta.adapter.meteo;

import com.oracle.svm.core.annotate.AutomaticFeature;
import org.graalvm.nativeimage.hosted.Feature;
import org.graalvm.nativeimage.hosted.RuntimeReflection;

@AutomaticFeature
public class MeteoNativeImageFeature implements Feature {

    public void beforeAnalysis(BeforeAnalysisAccess access) {
        try {
            RuntimeReflection.registerForReflectiveInstantiation(CurrentWeatherDto.class);
            RuntimeReflection.register(CurrentWeatherDto.class);
            RuntimeReflection.register(CurrentWeatherDto.class.getDeclaredField("temperature"));
            RuntimeReflection.register(CurrentWeatherDto.class.getDeclaredField("windspeed"));
            RuntimeReflection.register(CurrentWeatherDto.class.getDeclaredField("winddirection"));
            RuntimeReflection.register(CurrentWeatherDto.class.getDeclaredField("weathercode"));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}


