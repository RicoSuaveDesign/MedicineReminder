//model class for accuweather location
package arico.medicinereminder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by starb on 5/1/2018.
 */

public class accuLocation {

    @SerializedName("Key")
    @Expose
    private String locKey;

    String getLocKey(){
        return locKey;
    }
    void setLocKey(String key){
        locKey = key;
    }
}
