package oran.myapp.reservation.screens;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import oran.myapp.reservation.R;
import oran.myapp.reservation.adapter.RendezVousAdapter;
import oran.myapp.reservation.modele.RendezVous;
import oran.myapp.reservation.modele.medecin;
import oran.myapp.reservation.modele.patient;


public class RdvHistoryActivity extends AppCompatActivity implements  RendezVousAdapter.RendezVousClickListener {

    private RecyclerView Rrvc;

    private ArrayList<RendezVous> RAL = new ArrayList<> ( );
    private ArrayList<medecin> MAL = new ArrayList<>();
    private RendezVousAdapter RAdapter;

    // Firebase Objects
    private FirebaseDatabase ROOT = FirebaseDatabase.getInstance ( "https://pfelicence-615fe-default-rtdb.europe-west1.firebasedatabase.app/" );
    private DatabaseReference rdvRef = ROOT.getReference ( "RendezVous" );
    private DatabaseReference userRef = ROOT.getReference ( "users" );
    // User Data
    private splashScreen inst = splashScreen.getInst ( );
    private patient userData = inst.GetUserData ( );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_rdv_history);
        Rrvc = findViewById ( R.id.RendezVousRecycler );
        RAdapter = new RendezVousAdapter( this , RAL , MAL , this );
        // RendezVous Recycler view
        LinearLayoutManager LMR = new LinearLayoutManager ( this );
        Rrvc.setLayoutManager ( LMR );
        Rrvc.setAdapter ( RAdapter );
        GetRendezVous();
    }

    private void GetRendezVous() {
        rdvRef.orderByChild ( "pid" ).equalTo ( userData.getUid () ).addValueEventListener ( new ValueEventListener( ) {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists ( )) {
                    Toast.makeText ( RdvHistoryActivity.this , "Not Exist" , Toast.LENGTH_SHORT ).show ( );
                    return;
                }


                RAL.clear ( );
                for (DataSnapshot ds : snapshot.getChildren ( )) {
                    RendezVous helper = ds.getValue ( RendezVous.class );

                    String sDate1 = helper.getDate() + " " + helper.getTime();
                    //current date
                    Calendar cal = Calendar.getInstance();
                    @SuppressLint("SimpleDateFormat")
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                    String currDate = sdf.format(cal.getTime());
                    SimpleDateFormat sdf2 = new SimpleDateFormat ( "dd-MM-yyyy hh:mm" );
                    String currentDate = sdf2.format ( cal.getTime ( ) );


                    try {
                        Date curDate = sdf.parse(currDate);
                        Date date1 = sdf.parse(sDate1);
                        assert date1 != null;
                        if(date1.before(curDate)){
                            RAL.add ( helper );
                            userRef.child ( helper.getPid ( ) ).addListenerForSingleValueEvent ( new ValueEventListener ( ) {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.exists ( )) {
                                        Toast.makeText ( RdvHistoryActivity.this , "Not Exist" , Toast.LENGTH_SHORT ).show ( );
                                        return;
                                    }
                                    medecin mHelper = snapshot.getValue ( medecin.class );
                                    MAL.add ( mHelper );
                                    RAdapter.notifyDataSetChanged ( );
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText ( RdvHistoryActivity.this , "error: " + error.getMessage ( ) , Toast.LENGTH_SHORT ).show ( );
                                }
                            } );

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }





                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        } );
    }
    @Override
    public void OnRendezVousClick(int position) {

    }

    @Override
    public void OnRendezVousLongClick(int position) {

    }
}