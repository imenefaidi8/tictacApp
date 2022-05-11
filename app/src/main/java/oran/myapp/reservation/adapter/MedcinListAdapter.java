package oran.myapp.reservation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import oran.myapp.reservation.R;
import oran.myapp.reservation.modele.medecin;

public class MedcinListAdapter extends RecyclerView.Adapter<MedcinListAdapter.MedcinListViewHolder> {

     private  ArrayList<medecin> MAL ;
     private Context context ;
     private onMedcinListListener listener;

    public MedcinListAdapter( Context context ,ArrayList<medecin> MAL,onMedcinListListener Listener ) {
        this.MAL = MAL;
        this.context = context;
        this.listener=Listener;
    }

    @NonNull
    @Override
    public MedcinListViewHolder onCreateViewHolder(@NonNull ViewGroup parent , int viewType) {
        View view = LayoutInflater.from ( parent.getContext () ).inflate ( R.layout.list_item_medcin_list,parent,false );
        return new MedcinListViewHolder ( view );
    }

    @Override
    public void onBindViewHolder(@NonNull MedcinListViewHolder holder , int position) {
        holder.DocName.setText ( MAL.get ( position ).getNom ());
    }

    @Override
    public int getItemCount() {
        return MAL.size ();
    }

    public class MedcinListViewHolder extends RecyclerView.ViewHolder {
        private ImageView Location;
        private TextView DocName;
        private Button Message;

        public MedcinListViewHolder(@NonNull View itemView) {
            super ( itemView );
            Location = itemView.findViewById ( R.id.locationDoctor );
            DocName = itemView.findViewById ( R.id.doctorName );
            Message = itemView.findViewById ( R.id.MessageButton );

            // Back nClicku Kul item w y3ayet l OnServiceClick mn Listener
            Message.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View view) {
                    listener.onMedcinMessageCLick ( getAdapterPosition () );
                }
            } );
            Location.setOnClickListener ( new View.OnClickListener ( ) {
                @Override
                public void onClick(View view) {
                    listener.onMedcinLocationCLick ( getAdapterPosition () );
                }
            } );

        }
    }

    public interface onMedcinListListener{
        void onMedcinMessageCLick(int position);
        void onMedcinLocationCLick(int position);
    }
}
