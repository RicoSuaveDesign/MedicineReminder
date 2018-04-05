package arico.medicinereminder;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

/**
 * Created by starb on 3/23/2018.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>  {

    /*public interface OnItemClickListener {
        void onItemClick(Medicine med);
    }*/


    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder

    private final ArrayList<Medicine> meds;
    //private final OnItemClickListener listener;
    Context mContext;

    public RecyclerAdapter(ArrayList<Medicine> medics) {
        this.meds = medics;
        //this.listener = listen;

    }

    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int i) {

        //add a settext to show tag id and also hold it


        viewHolder.bind(meds.get(i));
    }

    @Override
    public int getItemCount() {
        return meds.size();
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

        public void bind(final Medicine med) {

            med_name.setText(med.getMedName());

           // med_desc.setText(med.getMed_desc());

            med_dosage.setText(String.valueOf(med.getDosage()));
            med_dosage.append(" ");
           // med_dosage.append(med.getDosageUnit());

            doseunit.setText(String.valueOf(med.getTag_id()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, ViewSingleMedActivity.class);

                    // Put most of the medicine into the intent for display.
                    intent.putExtra("is_new", med.getIsNewMed());
                    intent.putExtra("tag_id", med.getTag_id());
                    intent.putExtra("med_name", med.getMedName());
                    intent.putExtra("med_desc", med.getMed_desc());
                    intent.putExtra("how_often", med.getMedFreqPerTime());
                    intent.putExtra("interval", med.getMedFreqInterval());
                    intent.putExtra("dosage", med.getDosage());
                    intent.putExtra("unit", med.getDosageUnit());
                    intent.putExtra("doses_left", med.getDosesLeft());
                    intent.putExtra("in_or_out", med.getInOut());
                    intent.putExtra("lastTaken", med.getLastTimeTaken());

                    context.startActivity(intent);
                }
            });

        }
    }
}

