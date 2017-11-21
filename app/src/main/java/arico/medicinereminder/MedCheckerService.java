package arico.medicinereminder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by starb on 11/14/2017.
 */

public class MedCheckerService extends Service {

    public void onCreate (){

    }

    @Override
    public IBinder onBind (Intent intent){

        return null;
    }
}
