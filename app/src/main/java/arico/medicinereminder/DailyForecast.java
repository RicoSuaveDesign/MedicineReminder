package arico.medicinereminder;

/**
 * Created by starb on 5/1/2018.
 */

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DailyForecast {

    @SerializedName("Date")
    @Expose
    private String date;

    @SerializedName("AirAndPollen")
    @Expose
    private List<AirAndPollen> airAndPollen = null;


    public List<AirAndPollen> getAirAndPollen() {
        return airAndPollen;
    }

    public void setAirAndPollen(List<AirAndPollen> airAndPollen) {
        this.airAndPollen = airAndPollen;
    }


}
