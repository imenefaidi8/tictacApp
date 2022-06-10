package oran.myapp.reservation.screens;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import oran.myapp.reservation.R;
import oran.myapp.reservation.adapter.MessageAdapter;
import oran.myapp.reservation.modele.messages;
import oran.myapp.reservation.modele.patient;


public class MessageActivity extends AppCompatActivity {

    private EditText MsgWritting;
    private ImageView SendButton;
    private RecyclerView RCV;
    private final ArrayList<messages> MAL = new ArrayList<>();
    private MessageAdapter messageAdapter;
    private FirebaseDatabase ROOT = FirebaseDatabase.getInstance("https://pfelicence-615fe-default-rtdb.europe-west1.firebasedatabase.app/");
    private final DatabaseReference MsgRef = ROOT.getReference("messages");


    //User Data
    private final splashScreen inst = splashScreen.getInst();
    private final patient UserData = inst.GetUserData();
    private String PosterID;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        initialize();
        messageAdapter = new MessageAdapter(this, MAL);
        RecyclerView.LayoutManager LM = new LinearLayoutManager(this);
        RCV.setLayoutManager(LM);
        RCV.setAdapter(messageAdapter);

        PosterID = getIntent().getStringExtra("senderID");
        GetMessages();
        SendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Message = MsgWritting.getText().toString();
                if (Message.isEmpty()) {
                    MsgWritting.setError("Empty Message");
                    return;
                }
                SendMessage(Message);
            }
        });

    }








    private void initialize() {
        MsgWritting = findViewById(R.id.commentSection);
        SendButton = findViewById(R.id.commentConfermation);

        RCV = findViewById(R.id.ChatRecycler);


    }

    private void SendMessage(String Message) {
        MsgWritting.setText("");


        String currentDateTimeString = java.text.DateFormat.getDateTimeInstance().format(new Date());
        Long timestamp = System.currentTimeMillis();
        messages message = new messages(String.valueOf(timestamp), UserData.getUid(), PosterID, Message, currentDateTimeString);
        MsgRef.child(UserData.getUid()).child(PosterID).child(String.valueOf(timestamp)).setValue(message).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                MsgRef.child(PosterID).child(UserData.getUid()).child(String.valueOf(timestamp)).setValue(message);


            }
        });

    }

    /*
       messages helper = snapshot.getValue(messages.class);
                    MAL.add(helper);
                    messageAdapter.notifyDataSetChanged();
     */
    private void GetMessages() {

        MsgRef.child(UserData.getUid()).child(PosterID).addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                if (snapshot.exists()) {
                    messages helper = snapshot.getValue(messages.class);
                    MAL.add(helper);
                    messageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onChildRemoved(DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

    }
}