package arico.medicinereminder;

/**
 * Created by starb on 3/21/2018.
 */
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MedInterface {

    @GET("get_all_medicines.php")
    Call<MedResponse> getAllMedicines(@Query("uid") int user_id);

    @GET("get_medicine_details.php")
    Call<Medicine> getMedicineDetails(@Query("med_id") int id);

}
