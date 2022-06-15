package oran.myapp.reservation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import oran.myapp.reservation.R;
import oran.myapp.reservation.modele.Medicament;

public class MedicamentAdapter extends RecyclerView.Adapter<MedicamentAdapter.MedicamentViewHolder>{

    private ArrayList<Medicament> MAL ;
    private Context context;

    public MedicamentAdapter(Context context, ArrayList<Medicament> MAL) {
        this.context = context;
        this.MAL = MAL;

    }
    @NonNull
    @Override
    public MedicamentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_medicament,parent,false);
        return new MedicamentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentViewHolder holder, int position) {
        holder.name.setText(MAL.get(position).getNom());
        holder.id.setText("#"+MAL.get(position).getId().substring(0,5)+"...");
    }

    @Override
    public int getItemCount() {
        return MAL.size();
    }


    public class MedicamentViewHolder extends RecyclerView.ViewHolder{

        private TextView id,name;

        public MedicamentViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById( R.id.id);

            name = itemView.findViewById( R.id.midName);


        }
    }
}
