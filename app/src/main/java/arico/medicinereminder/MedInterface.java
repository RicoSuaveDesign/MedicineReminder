package arico.medicinereminder;

/**
 * Created by starb on 3/21/2018.
 */
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import java.util.ArrayList;

public interface MedInterface {

    @GET("get_all_medicines.php")
    Call<MedResponse> getAllMedicines(@Query("uid") int user_id);

    @GET("get_medicine_details.php")
    Call<Medicine> getMedicineDetails(@Query("med_id") int id);


    //@FormUrlEncoded
    @POST("create_medicine.php")
    Call<Medicine> postMedicine(@Body Medicine med);
   /* Call<Medicine> postMedicine(@Field("uid") int user_id,
                                @Field ("name") String med_name,
                                @Field ("medFreqPerTime") int medFreqPerTime,
                                @Field("medFreqInterval") long medFreqInterval,
                                @Field ("dosage") float dosage,
                                @Field ("unit") String unit,
                                @Field ("expiration") String expiration,
                                @Field ("dosesLeft") int dosesLeft,
                                @Field ("checkTime[]") ArrayList<String> checkTimes);*/


}
