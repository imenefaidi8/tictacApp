package oran.myapp.reservation.adapter;

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
public class RendezVousAdapter extends RecyclerView.Adapter<RendezVousAdapter.RendezVousViewHolder> {

    private ArrayList<RendezVous> RAL ;
    private Context context;
    private RendezVousClickListener listener ;

    public RendezVousAdapter( Context context, ArrayList<RendezVous> RAL, RendezVousClickListener listener) {
        this.context = context;
        this.RAL = RAL;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RendezVousAdapter.RendezVousViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_rdv,parent,false);
        return new RendezVousViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RendezVousViewHolder holder, int position) {
        holder.date.setText(RAL.get(position).getDate());
        holder.doctor.setText("Doctor : "+RAL.get(position).getDoctor());
        switch (RAL.get(position).getState()){
            case 2 :
                holder.statusLinear.setBackgroundColor(Color.RED);
                Log.e("case","0");
            break ;

            case 1 :
                holder.statusLinear.setBackgroundColor(Color.GREEN);
                Log.e("case","1");
                break ;

            case 0 :
                holder.statusLinear.setBackgroundColor(Color.YELLOW);
                Log.e("case","2");
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
