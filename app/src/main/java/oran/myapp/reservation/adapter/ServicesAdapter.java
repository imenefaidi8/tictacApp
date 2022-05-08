package oran.myapp.reservation.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import oran.myapp.reservation.R;
import oran.myapp.reservation.modele.Service;
import oran.myapp.reservation.modele.medecin;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServicesViewHolder> {

    private ArrayList<medecin> MAL;
    private Context context;
    private ServiceClickListener listener;

    public ServicesAdapter(Context context , ArrayList<medecin> MAL , ServiceClickListener listener) {
        this.context = context;
        this.MAL = MAL;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ServicesViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View view = LayoutInflater.from ( parent.getContext ( ) ).inflate ( R.layout.list_item_services , parent , false );
        return new ServicesViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesViewHolder holder , int position) {

        holder.Service_TextView.setText ( MAL.get ( position ).getService ( ) );
    }

    @Override
    public int getItemCount() {
        return MAL.size ( );
    }

    public class ServicesViewHolder extends RecyclerView.ViewHolder {
        private ImageView Service_ImageView;
        private TextView Service_TextView;

        public ServicesViewHolder(@NonNull View itemView) {
            super ( itemView );
            Service_ImageView = itemView.findViewById ( R.id.Service_ImageView );
            Service_TextView = itemView.findViewById ( R.id.Service_TextView );

            // Back nClicku Kul item w y3ayet l OnServiceClick mn Listener
            itemView.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View view) {
                    listener.OnServiceClick ( getAdapterPosition ( ) );
                }
            } );
        }
    }

    public interface ServiceClickListener {
        void OnServiceClick(int position);
    }
}
