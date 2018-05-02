package arico.medicinereminder;

/**
 * Created by starb on 5/1/2018.
 */

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import java.util.ArrayList;


public interface AccuInterface {

    String apikey = "kJg45AjDEXOIksWs8ZcS8nab7kVGU0Or";

    @GET("locations/v1/cities/geoposition/search?apikey=kJg45AjDEXOIksWs8ZcS8nab7kVGU0Or")
    Call<accuLocation> getLocKey(@Query ("q") String latlong);

    @GET("/forecasts/v1/daily/1day/{locationKey}")
    Call<Weather> getPollutants(@Path ("locationKey") String lockey, @Query("apikey") String key, @Query("details") boolean det);
}
