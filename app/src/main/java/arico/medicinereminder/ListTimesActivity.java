// Allows user to view times when the server will check if they have taken their medicine.
// From here they can select a time to edit or delete.
// They can also add a time from here.

package arico.medicinereminder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by starb on 4/13/2018.
 */

public class ListTimesActivity extends Activity {


    private RecyclerView recyclerView;
    private ArrayList<CheckTime> times;
    private TimeRecyclerAdapter adapter;
    private Context context;

    private int id;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle extras = getIntent().getExtras();
        setContentView(R.layout.activity_medlist);
        context = this.getApplicationContext();
        id = extras.getInt("mid");
        initViews(id);
    }

    private void initViews(int id){
        recyclerView = (RecyclerView)findViewById(R.id.card_recycler_view);
        // recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        loadJSON(id);
    }

    private void loadJSON(int id) {
        MedInterface medfetch = MedClient.getClient().create(MedInterface.class);

        Call<checkTimeResult> call = medfetch.getMedicineDetails(id);
        call.enqueue(new Callback<checkTimeResult>() {
            @Override
            public void onResponse(Call<checkTimeResult>call, Response<checkTimeResult> response) {

                times = new ArrayList<>((response.body().getResults()));
                Log.d(TAG, "Number of times received: " + times.size());
                Log.d(TAG, "Time 1: " + times.get(0).getTime());


                adapter = new TimeRecyclerAdapter(times, context);
                recyclerView.setAdapter(adapter);


            }

            @Override
            public void onFailure(Call<checkTimeResult>call, Throwable t) {
                // Log error here since request failed
                Log.e(TAG, t.toString());
            }
        });

    }

    @Override
    public void onBackPressed(){
        // it's bad to press back and be able to see stuff thats been deleted or input so lets just go back to main activity
        Intent intent = new Intent(context, MedListActivity.class);
        context.startActivity(intent);
    }

}


