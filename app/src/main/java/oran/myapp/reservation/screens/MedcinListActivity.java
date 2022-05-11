package oran.myapp.reservation.screens;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Locale;

import oran.myapp.reservation.R;
import oran.myapp.reservation.adapter.MedcinListAdapter;
import oran.myapp.reservation.modele.medecin;
import oran.myapp.reservation.modele.patient;

public class MedcinListActivity extends AppCompatActivity implements MedcinListAdapter.onMedcinListListener {

    private RecyclerView rcv;
    private ArrayList<medecin> MAL = new ArrayList<> ( );
    private MedcinListAdapter mAdapter;
    // Firebase Objects
    private FirebaseDatabase ROOT = FirebaseDatabase.getInstance ( "https://pfelicence-615fe-default-rtdb.europe-west1.firebasedatabase.app/" );
    private DatabaseReference medcinRef = ROOT.getReference ( "medcin" );
    // private FirebaseAuth mAuth = FirebaseAuth.getInstance ( );

    // User Data
    private splashScreen inst = splashScreen.getInst ( );
    private patient userData = inst.GetUserData ( );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_medcin_list );

        rcv = findViewById ( R.id.recyclerMedcinList );
        mAdapter = new MedcinListAdapter ( this , MAL , this );
        int service = getIntent ( ).getIntExtra ( "service" , 0 );
        LinearLayoutManager LM = new LinearLayoutManager ( this );

        rcv.setLayoutManager ( LM );
        rcv.setAdapter ( mAdapter );

        getDataFromFB (service);

    }

    private void getDataFromFB(int service) {

        medcinRef.orderByChild ( "service" ).equalTo(service).addListenerForSingleValueEvent ( new ValueEventListener ( ) {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(!snapshot.exists ()){
                    Toast.makeText ( MedcinListActivity.this , "is Empty", Toast.LENGTH_SHORT ).show ( );
                    return;

                }

                for(DataSnapshot ds : snapshot.getChildren ()){

                    medecin helper= ds.getValue (medecin.class);
                    MAL.add ( helper );
                    mAdapter.notifyDataSetChanged ();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( MedcinListActivity.this , "error: "+error.getMessage () , Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }


    @Override
    public void onMedcinMessageCLick(int position) {
     // handle messages
        Toast.makeText ( MedcinListActivity.this,"soon" ,Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void onMedcinLocationCLick(int position) {
        String geoUri = "http://maps.google.com/maps?q=loc:" +  MAL.get ( position ).getLat () + "," + MAL.get ( position ).getLang ()
                + " (" + MAL.get ( position ).getNom()+ ")";
      //  String uri = String.format( Locale.ENGLISH, "geo:%f,%f", MAL.get ( position ).getLat (), MAL.get ( position ).getLang ());
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoUri));
        startActivity(intent);
    }
}