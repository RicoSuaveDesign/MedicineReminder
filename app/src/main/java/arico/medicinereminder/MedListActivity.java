// Lists out every medicine registered to a user.
// Queries database for full medicine, but only displays name, dosage, and purpose.

package arico.medicinereminder;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by starb on 1/29/2018.
 */

public class MedListActivity extends Activity {
    private RecyclerView recyclerView;
    private ArrayList<Medicine> medicines;
    private RecyclerAdapter adapter;
    private Context context;

    private TextView nomed;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medlist);
        nomed = (TextView) findViewById(R.id.nomed);
        context = this.getApplicationContext();
        initViews();
    }

    private void initViews(){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
       // recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON();
    }

    private void loadJSON() {
        MedInterface medfetch = MedClient.getClient().create(MedInterface.class);

        Call<MedResponse> call = medfetch.getAllMedicines(1);
        call.enqueue(new Callback<MedResponse>() {
            @Override
            public void onResponse(Call<MedResponse>call, Response<MedResponse> response) {

                medicines = new ArrayList<>(response.body().getResults());
                Log.d(TAG, "Number of medicines received: " + medicines.size());
                Log.d(TAG, "med name: " + medicines.get(0).getMedName());

                if(medicines.size() > 0) {
                    adapter = new RecyclerAdapter(medicines, context);
                    recyclerView.setAdapter(adapter);
                }
                else{
                    nomed.setText("No medicines yet. Try scanning an RFID tag.");
                }


            }

            @Override
            public void onFailure(Call<MedResponse>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    @Override
    public void onBackPressed(){
        // it's bad to press back and be able to see stuff thats been deleted or input so lets just go back to main activity
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

}
