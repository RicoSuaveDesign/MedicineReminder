package arico.medicinereminder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by starb on 5/1/2018.
 */
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
public class AllergyResponse {

    @SerializedName("AirAndPollen")
    @Expose
    private List<AirAndPollen> allergies;

    public List<AirAndPollen> getResults() {
        return allergies;
    }
    public void setResults(List<AirAndPollen> results) {
        this.allergies = results;
    }
}
