package oran.myapp.reservation.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import oran.myapp.reservation.R;
import oran.myapp.reservation.adapter.RendezVousAdapter;
import oran.myapp.reservation.adapter.ServicesAdapter;
import oran.myapp.reservation.modele.RendezVous;
import oran.myapp.reservation.modele.Service;
import oran.myapp.reservation.modele.patient;

public class DashboardActivity extends AppCompatActivity implements ServicesAdapter.ServiceClickListener
        , RendezVousAdapter.RendezVousClickListener {

    private RecyclerView Srcv,Rrvc;
    private ArrayList<Service> SAL = new ArrayList<>();
    private ArrayList<RendezVous> RAL = new ArrayList<>();
    private ServicesAdapter SAdapter;
    private RendezVousAdapter RAdapter;
    // Firebase Objects
    private FirebaseDatabase ROOT = FirebaseDatabase.getInstance("https://pfelicence-615fe-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference rdvRef = ROOT.getReference("RendezVous");
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // User Data
    private splashScreen inst = splashScreen.getInst();
    private patient userData = inst.GetUserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        init();



        SAL.add(new Service("Generalist",R.drawable.doctor));
        SAL.add(new Service("Dentist",R.drawable.doctor));
        SAL.add(new Service("opticien",R.drawable.doctor));
        SAL.add(new Service("tbib l galb ",R.drawable.doctor));

        GetRendezVous();

    }

    private void GetRendezVous(){
        rdvRef.child(userData.getUid()).addChildEventListener(new ChildEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
               if(!snapshot.exists()){
                   return;
               }
               RendezVous helper = snapshot.getValue(RendezVous.class);

               RAL.add(helper);

               RAdapter.notifyDataSetChanged();
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(!snapshot.exists()){
                    Log.e("onChanged","false");
                    return;
                }
                Log.e("onChanged","true");
                RendezVous helper = snapshot.getValue(RendezVous.class);

                for (int i=0;i<RAL.size();i++){

                    if(RAL.get(i).getId().equals(helper.getId())){

                        RAL.remove(i);
                        RAL.add(i,helper);
                        RAdapter.notifyDataSetChanged();


                    }

                }
                if(RAL.size()==0){
                    Rrvc.setVisibility(View.GONE);

                }

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    Log.e("onRemoved","false");
                    return;
                }
                Log.e("onRemoved","true");
                RendezVous helper = snapshot.getValue(RendezVous.class);

                for (int i=0;i<RAL.size();i++){

                    if(RAL.get(i).getId().equals(helper.getId())){

                        RAL.remove(i);


                    }

                }
                RAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Views Initialization
    private void init(){
        Srcv = findViewById(R.id.ServiesRecycler);
        Rrvc = findViewById(R.id.RendezVousRecycler);
        SAdapter = new ServicesAdapter(this,SAL,this);
        RAdapter = new RendezVousAdapter(this,RAL,this);

        // Service Recycler View
        LinearLayoutManager LM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);

        Srcv.setLayoutManager(LM);
        Srcv.setAdapter(SAdapter);


        // RendezVous Recycler view
        LinearLayoutManager LMR = new LinearLayoutManager(this);
        Rrvc.setLayoutManager(LMR);
        Rrvc.setAdapter(RAdapter);
    }

    @Override
    public void OnServiceClick(int position) {
        Toast.makeText(DashboardActivity.this, "Service "+position+1+" Clicked !", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void OnRendezVousClick(int position) {
        Toast.makeText(DashboardActivity.this, "RendezVous "+position+1+" Clicked !", Toast.LENGTH_SHORT).show();
    }
}