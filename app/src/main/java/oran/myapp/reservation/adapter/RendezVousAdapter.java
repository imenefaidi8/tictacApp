package oran.myapp.reservation.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import oran.myapp.reservation.R;
import oran.myapp.reservation.modele.RendezVous;
import oran.myapp.reservation.modele.Service;
import oran.myapp.reservation.modele.medecin;

public class RendezVousAdapter extends RecyclerView.Adapter<RendezVousAdapter.RendezVousViewHolder> {

    private ArrayList<RendezVous> RAL ;
    private ArrayList<medecin> MAL ;
    private Context context;
    private RendezVousClickListener listener ;

    public RendezVousAdapter( Context context, ArrayList<RendezVous> RAL,ArrayList<medecin> MAL, RendezVousClickListener listener) {
        this.context = context;
        this.RAL = RAL;
        this.MAL = MAL;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RendezVousAdapter.RendezVousViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rdv,parent,false);
        return new RendezVousViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RendezVousViewHolder holder, int position) {
        holder.date.setText(RAL.get(position).getDate()+" "+RAL.get(position).getTime());
        if(MAL.size()>position)
        holder.doctor.setText("Doctor : "+MAL.get(position).getNom()+" "+MAL.get(position).getPrenom());
        switch (RAL.get(position).getState()){
            case 2 :
                holder.statusLinear.setBackgroundColor(Color.RED);
                holder.date.setTextColor(Color.WHITE);
                Log.e("case","2");
            break ;

            case 1 :
                holder.statusLinear.setBackgroundColor(Color.GREEN);
                holder.date.setTextColor(Color.WHITE);
                Log.e("case","1");
                break ;

            case 0 :
                holder.statusLinear.setBackgroundColor(Color.YELLOW);
                holder.date.setTextColor(Color.GRAY);
                Log.e("case","0");
                break ;


        }
    }



    @Override
    public int getItemCount() {
        return RAL.size();
    }


    public class RendezVousViewHolder extends RecyclerView.ViewHolder{

        private TextView date,doctor;
        private LinearLayout statusLinear;
        public RendezVousViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            statusLinear = itemView.findViewById(R.id.statusLinear);
            doctor = itemView.findViewById(R.id.doctor);

            // Back nClicku Kul item w y3ayet l OnServiceClick mn Listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.OnRendezVousClick(getAdapterPosition());
                }
            });

        }
    }

    public interface RendezVousClickListener {
        void OnRendezVousClick(int position);
    }
}
