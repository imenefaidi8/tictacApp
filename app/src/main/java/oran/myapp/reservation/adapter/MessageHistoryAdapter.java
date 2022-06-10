package oran.myapp.reservation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


import oran.myapp.reservation.R;
import oran.myapp.reservation.modele.Assistant;
import oran.myapp.reservation.modele.messages;

public class MessageHistoryAdapter extends RecyclerView.Adapter<MessageHistoryAdapter.ViewHolder> {
    private ArrayList<Assistant> UAL;
    private ArrayList<messages> MAL;
    private Context context;
    private MessagesListener listener;


    public MessageHistoryAdapter(Context context, ArrayList<Assistant> UAL, ArrayList<messages> MAL, MessagesListener listener) {
        this.context = context;
        this.UAL = UAL;
        this.MAL = MAL;
        this.listener = listener;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate( R.layout.message_hostory_item_list, parent, false);
        ViewHolder VH = new ViewHolder(v);
        return VH;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.Name.setText(UAL.get ( position ).getNom()+" "+UAL.get(position).getPrenom());

        


    }


    @Override
    public int getItemCount() {
        return UAL.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView profile,MsgNumber;
        private TextView Name;

        public ViewHolder(View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.ProfileInMessageHistory);
            Name = itemView.findViewById(R.id.UserFullName);
            MsgNumber = itemView.findViewById(R.id.UserMessageNumber);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnMessageClick(getAdapterPosition());
                }
            });

        }
    }



    public interface MessagesListener {
        void OnMessageClick(int position);

    }

}
