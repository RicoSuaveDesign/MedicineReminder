// Model class to receive multiple medicines.

package arico.medicinereminder;

/**
 * Created by starb on 3/21/2018.
 */


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
public class MedResponse {




        @SerializedName("page")
        private int page;
        @SerializedName("medicines")
        @Expose
        private List<Medicine> results;
        @SerializedName("total_results")
        private int totalResults;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<Medicine> getResults() {
            return results;
        }

        public void setResults(List<Medicine> results) {
            this.results = results;
        }

        public int getTotalResults() {
            return totalResults;
        }

        public void setTotalResults(int totalResults) {
            this.totalResults = totalResults;
        }
}
