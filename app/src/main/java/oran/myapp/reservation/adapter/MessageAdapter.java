package oran.myapp.reservation.adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.SplashScreen;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import oran.myapp.reservation.R;
import oran.myapp.reservation.modele.messages;
import oran.myapp.reservation.modele.patient;
import oran.myapp.reservation.screens.splashScreen;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {
    private Context context;
    private ArrayList<messages> MAL;
    private splashScreen inst = splashScreen.getInst();
    private patient UserData = inst.GetUserData();

    public MessageAdapter(Context context, ArrayList<messages> MAL) {
        this.context = context;
        this.MAL = MAL;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate( R.layout.list_item_messages, parent, false);
        MessageViewHolder VH = new MessageViewHolder(v);
        return VH;
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        messages pos = MAL.get(position);

        if (!pos.getSenderID().equals(UserData.getUid ())) {
            holder.MessageContent.setText(pos.getMessage());
            holder.MessageTime.setText(pos.getTime());


        } else {
            holder.ParentLinearLayout.setBackgroundResource(R.drawable.curren_msg_shape);
            holder.ParentOfParentLayout.setGravity(Gravity.RIGHT);
            holder.MessageContent.setText(pos.getMessage());
            holder.MessageTime.setText(pos.getTime());

        }


    }

    @Override
    public int getItemCount() {
        return MAL.size();
    }

    public ArrayList<messages> getElement(){


        return MAL;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder {
        private TextView MessageContent, MessageTime;
        private ImageView profile_image;
        private LinearLayout ParentLinearLayout,ParentOfParentLayout;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            MessageContent = itemView.findViewById(R.id.messageContent);
            MessageTime = itemView.findViewById(R.id.MessageTime);
            profile_image = itemView.findViewById(R.id.messageProfile);
            ParentLinearLayout = itemView.findViewById(R.id.ParentLinearLayout);
            ParentOfParentLayout = itemView.findViewById(R.id.ParentOfParentLayout);

        }
    }

}
