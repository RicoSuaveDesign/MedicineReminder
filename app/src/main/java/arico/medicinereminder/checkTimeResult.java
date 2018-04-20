// This is the model class to receive multiple checktimes from the get checktimes query.

package arico.medicinereminder;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by starb on 4/16/2018.
 */

public class checkTimeResult {

    @SerializedName("times")
    private List<CheckTime> results;
    @SerializedName("total_results")
    private int totalResults;

    public List<CheckTime> getResults() {
        return results;
    }

    public void setResults(List<CheckTime> results) {
        this.results = results;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }
}
