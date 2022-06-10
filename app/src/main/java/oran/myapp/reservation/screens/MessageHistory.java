package oran.myapp.reservation.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


import oran.myapp.reservation.R;
import oran.myapp.reservation.adapter.MessageHistoryAdapter;
import oran.myapp.reservation.modele.Assistant;
import oran.myapp.reservation.modele.medecin;
import oran.myapp.reservation.modele.messages;
import oran.myapp.reservation.modele.patient;

public class MessageHistory extends AppCompatActivity implements  MessageHistoryAdapter.MessagesListener {
    private splashScreen inst = splashScreen.getInst();
    private patient UserData = inst.GetUserData();
    private FirebaseDatabase ROOT = FirebaseDatabase.getInstance("https://pfelicence-615fe-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference msgRef = ROOT.getReference("messages");
    private DatabaseReference assistRef = ROOT.getReference("Assistant");
    private DatabaseReference midtRef = ROOT.getReference("medcin");
    private RecyclerView RCV;
    private MessageHistoryAdapter mAdapter;
    private ArrayList<Assistant> PAL=new ArrayList<>();
    private ArrayList<messages> MAL=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_message_history2 );
        initiliaze ();

        getParentsID ();

    }


    private void initiliaze(){
        RCV=findViewById(R.id.Message_History_Recyler);
        mAdapter = new MessageHistoryAdapter ( this,PAL,MAL,this );
        RecyclerView.LayoutManager LM = new LinearLayoutManager (this);
        RCV.setLayoutManager(LM);
        RCV.setAdapter(mAdapter);
    }

    private void getParentsID(){
        msgRef.child(UserData.getUid ()).addChildEventListener(new ChildEventListener () {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName) {
                if(snapshot.exists()){
                    messages nmsg= snapshot.getValue(messages.class);
                    MAL.add(nmsg);

                    String key = snapshot.getKey ();
                    assert key != null;
                    assistRef.child(key).addListenerForSingleValueEvent(new ValueEventListener () {
                        @SuppressLint("NotifyDataSetChanged")
                        @Override
                        public void onDataChange( DataSnapshot snapshot) {
                            if(snapshot.exists()){

                                Assistant helper = snapshot.getValue(Assistant.class);

                                GetMedcin(helper);

                            }else {
                                Toast.makeText ( MessageHistory.this , "Assistant not exist ", Toast.LENGTH_SHORT ).show ( );
                            }
                        }

                        @Override
                        public void onCancelled( DatabaseError error) {
                            Toast.makeText ( MessageHistory.this , "error: "+error.getMessage () , Toast.LENGTH_SHORT ).show ( );
                        }
                    });


                }else {
                    Toast.makeText ( MessageHistory.this , "message not exist  " , Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onChildChanged( DataSnapshot snapshot,  String previousChildName) {

            }

            @Override
            public void onChildRemoved( DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved( DataSnapshot snapshot,  String previousChildName) {

            }

            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText ( MessageHistory.this , "error: "+error.getMessage () , Toast.LENGTH_SHORT ).show ( );
            }
        });


    }

    @Override
    public void OnMessageClick(int position) {
        Intent intent = new Intent ( MessageHistory.this,MessageActivity.class );
        intent.putExtra ( "senderID",PAL.get ( position ).getUid () );
        startActivity(intent);
    }


    private void GetMedcin(Assistant assist){
        midtRef.child(assist.getMedecin()).addListenerForSingleValueEvent(new ValueEventListener () {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange( DataSnapshot snapshot) {
                if(snapshot.exists()){

                    medecin helper =  snapshot.getValue(medecin.class);


                                assert assist != null;
                                Log.d("assistants","exist with id: "+assist.getUid());
                                assist.setNom(helper.getNom());
                                assist.setPrenom(helper.getPrenom());
                                PAL.add(assist);
                                mAdapter.notifyDataSetChanged();

                }else {
                    Toast.makeText ( MessageHistory.this , "Assistant not exist ", Toast.LENGTH_SHORT ).show ( );
                }
            }

            @Override
            public void onCancelled( DatabaseError error) {
                Toast.makeText ( MessageHistory.this , "error: "+error.getMessage () , Toast.LENGTH_SHORT ).show ( );
            }
        });

    }
}