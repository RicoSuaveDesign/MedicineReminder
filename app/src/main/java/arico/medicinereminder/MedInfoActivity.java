package arico.medicinereminder;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by starb on 3/23/2018.
 */

public class MedInfoActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MedInterface medfetch = MedClient.getClient().create(MedInterface.class);

        Call<MedResponse> call = medfetch.getAllMedicines(1);
        call.enqueue(new Callback<MedResponse>() {
            @Override
            public void onResponse(Call<MedResponse>call, Response<MedResponse> response) {

                List<Medicine> medicines = response.body().getResults();
                Log.d(TAG, "Number of medicines received: " + medicines.size());
                Log.d(TAG, "med name: " + medicines.get(0).getMedName());

            }

            @Override
            public void onFailure(Call<MedResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });



    }
}
