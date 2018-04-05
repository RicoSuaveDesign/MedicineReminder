package arico.medicinereminder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by starb on 3/31/2018.
 */

public class ViewSingleMedActivity extends AppCompatActivity {

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medlist);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("tag_id");
            //The key argument here must match that used in the other activity
            System.out.println(value);
        }

    }


}
