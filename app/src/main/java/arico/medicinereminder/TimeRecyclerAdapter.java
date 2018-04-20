// Recycler Adapter clone for checktimes.

package arico.medicinereminder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by starb on 4/13/2018.
 */

public class TimeRecyclerAdapter extends RecyclerView.Adapter<TimeRecyclerAdapter.ViewHolder>{


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    private final ArrayList<CheckTime> times;
    private Context mContext;

    public TimeRecyclerAdapter(ArrayList<CheckTime> time, Context context) {
        this.times = time;
        mContext = context;

    }

    @Override
    public TimeRecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new TimeRecyclerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TimeRecyclerAdapter.ViewHolder viewHolder, int i) {

        //add a settext to show tag id and also hold it


        viewHolder.bind(times.get(i));
    }

    @Override
    public int getItemCount() {
        return times.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView med_name, med_desc, med_dosage, doseunit;

        public ViewHolder(View view) {
            super(view);

            med_name = (TextView) view.findViewById(R.id.med_name);
            med_desc = (TextView) view.findViewById(R.id.med_desc);
            med_dosage = (TextView) view.findViewById(R.id.med_dose);
            doseunit = (TextView) view.findViewById(R.id.dose_unit);

        }

        public void bind(final CheckTime time) {
            med_name.setText(time.getTime());
            med_desc.setText(time.getDate());
            med_dosage.setText("");
            doseunit.setText("");







                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Context context = view.getContext();
                        Intent intent = new Intent(context, EditTimesActivity.class);

                        // Put most of the medicine into the intent for display.
                        intent.putExtra("time_id", time.getTimeid());
                        intent.putExtra("checktime", time.getTime());
                        intent.putExtra("checkdate", time.getDate());
                        intent.putExtra("tagid", time.getTag_id());

                        context.startActivity(intent);
                    }
                });


        }
    }
}
