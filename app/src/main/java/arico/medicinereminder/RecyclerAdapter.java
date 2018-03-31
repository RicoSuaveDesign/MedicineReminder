package arico.medicinereminder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by starb on 3/23/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    private ArrayList<Medicine> meds;

    public RecyclerAdapter(ArrayList<Medicine> medics) {
        this.meds = medics;
    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int i) {

        //add a settext to show tag id and also hold it


        viewHolder.med_name.setText(meds.get(i).getMedName());
        viewHolder.med_dosage.setText(String.valueOf(meds.get(i).getDosage()));
        viewHolder.doseunit.setText(meds.get(i).getDosageUnit());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = view.getContext();
                Intent intent = new Intent(context , ViewSingleMedActivity.class);
                intent.putExtra("tag_id", 1); //CHANGE THE 1 TO A MED GET TAG LATER WHEN IT IS IN HERE
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meds.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView med_name, med_dosage, doseunit;

        public ViewHolder(View view) {
            super(view);

            med_name = (TextView) view.findViewById(R.id.med_name);
            med_dosage = (TextView) view.findViewById(R.id.med_dose);
            doseunit = (TextView) view.findViewById(R.id.dose_unit);

        }
    }
}

