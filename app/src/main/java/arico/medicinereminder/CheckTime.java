// This is the model class for check times, so I can work with the checktime table in the db.

package arico.medicinereminder;

/**
 * Created by starb on 4/6/2018.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CheckTime {

    @SerializedName("timeid")
    @Expose
    private int timeid;

    @SerializedName("thetime")
    @Expose
    private String time;

    @SerializedName("thedate")
    @Expose
    private String date;

    @SerializedName("tagid")
    @Expose
    private int tag_id;

    public CheckTime()
    {
        timeid = 0;
        time = "";
        date ="";
        tag_id = 0;
    }

    public int getTimeid()
    {
        return timeid;
    }
    public void setTimeid(int id){
        timeid = id;
    }

    public String getTime() {
        return time;
    }
    public void setTime(String thetime){
        time = thetime;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String theDate){
        date = theDate;
    }

    public int getTag_id() {
        return tag_id;
    }
    public void setTag_id(int theid) {
        tag_id = theid;
    }
}
