package arico.medicinereminder;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Weather {


    @SerializedName("DailyForecasts")
    @Expose
    private List<DailyForecast> dailyForecasts = null;



    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setDailyForecasts(List<DailyForecast> dailyForecasts) {
        this.dailyForecasts = dailyForecasts;
    }

}