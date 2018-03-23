package arico.medicinereminder;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.net.HttpURLConnection; // time to use something not deprecated
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;

/**
 * Created by starb on 1/29/2018.
 */

public class MedListActivity extends ListActivity {
    // Progress Dialog
    private ProgressDialog pDialog;
    


    Context context;

    // honestly? let's just try access the actual webpage
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
