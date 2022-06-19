package oran.myapp.reservation.screens;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
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
import java.util.HashMap;

import oran.myapp.reservation.Constants;
import oran.myapp.reservation.MainActivity;
import oran.myapp.reservation.R;
import oran.myapp.reservation.adapter.MedicamentAdapter;
import oran.myapp.reservation.adapter.RendezVousAdapter;
import oran.myapp.reservation.adapter.ServicesAdapter;
import oran.myapp.reservation.modele.Medicament;
import oran.myapp.reservation.modele.RendezVous;
import oran.myapp.reservation.modele.Service;
import oran.myapp.reservation.modele.medecin;
import oran.myapp.reservation.modele.patient;

public class DashboardActivity extends AppCompatActivity implements ServicesAdapter.ServiceClickListener
        , RendezVousAdapter.RendezVousClickListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView Srcv, Rrvc;
    private ArrayList<Service> SAL = new ArrayList<>();
    private ArrayList<RendezVous> RAL = new ArrayList<>();
    private ArrayList<medecin> MAL = new ArrayList<>();
    private ArrayList<Medicament> MALD = new ArrayList<>();
    private ServicesAdapter SAdapter;
    private ImageView MenuIcon;
    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private RendezVousAdapter RAdapter;
    private CardView DossierCard;

    // Firebase Objects
    private FirebaseDatabase ROOT = FirebaseDatabase.getInstance("https://pfelicence-615fe-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference rdvRef = ROOT.getReference("RendezVous");
    private DatabaseReference medRef = ROOT.getReference("medcin");
    private DatabaseReference usersRef = ROOT.getReference ( "users" );
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // User Data
    private splashScreen inst = splashScreen.getInst();
    private patient userData = inst.GetUserData();
    // Medicament



    // Recycler view tools
    private RecyclerView medRecycler ;
    private  TextView userName,userAge,dossierNote,Date,DocName;

    private MedicamentAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        init();

        MenuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        // Navigation VIew Item CLicks
        nav_view.setNavigationItemSelectedListener(this);


        GetRendezVous();

    }


    private void getClientData(){

                usersRef.child(userData.getUid()).child("dossierMedical").child("ordenance")
                        .child("medicaments").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(!snapshot.exists()){
                            Toast.makeText(DashboardActivity.this, "User not found ! ", Toast.LENGTH_SHORT).show();
                            return ;
                        }
                        getMedcin();

                        for(DataSnapshot ds : snapshot.getChildren()){
                            MALD.add(ds.getValue(Medicament.class));
                        }



                        mAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


    }


//    RendezVous helper = snapshot.getValue ( RendezVous.class );
//
//                RAL.add ( helper );
//
//                RAdapter.notifyDataSetChanged ( );



    private void GetRendezVous() {
        rdvRef.orderByChild("pid").equalTo(userData.getUid()).addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Toast.makeText(DashboardActivity.this, "Not Exist", Toast.LENGTH_SHORT).show();
                    return;
                }
                RAL.clear();
                MAL.clear();

                for (DataSnapshot ds : snapshot.getChildren()) {
                    RendezVous helper = ds.getValue(RendezVous.class);
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
                        if (date1.before(curDate) && helper.getState()!=1) {
                       //     Toast.makeText(DashboardActivity.this, "old one ", Toast.LENGTH_SHORT).show();
                            HashMap<String,Object> hash = new HashMap<>();
                            hash.put("state",2);
                            rdvRef.child(helper.getId()).updateChildren(hash);

                        }
                        if (date1.after(curDate) || helper.getDate ( ).equals ( currentDate )) {

                            RAL.add(helper);
                            medRef.child(helper.getDid()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (!snapshot.exists()) {
                                        Toast.makeText(DashboardActivity.this, "Not Exist", Toast.LENGTH_SHORT).show();
                                        return;
                                    }
                                    medecin mHelper = snapshot.getValue(medecin.class);
                                    MAL.add(mHelper);
                                    RAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Toast.makeText(DashboardActivity.this, "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(DashboardActivity.this, "error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getMedcin(){
        medRef.child(userData.getDossierMedical().getDid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()) return;

                    medecin doc = snapshot.getValue(medecin.class);

                    DocName.setText("Dr."+doc.getNom());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    //open boocking dialog
    private void openBookingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_booking, null, false);
        AlertDialog dialog = builder.create();

        builder.setView(view);
        builder.setCancelable(true);
        builder.show();

    }



    // Views Initialization
    private void init() {
        Srcv = findViewById(R.id.ServiesRecycler);
        Rrvc = findViewById(R.id.RendezVousRecycler);
        MenuIcon = findViewById(R.id.MenuIcon);
        drawerLayout = findViewById(R.id.drawerLayout);
        nav_view = findViewById(R.id.nav_view);
        userName = findViewById(R.id.userName);
        userAge = findViewById(R.id.userAge);
        dossierNote = findViewById(R.id.dossierNote);
        Date = findViewById(R.id.Date);
        DocName = findViewById(R.id.DocName);
        medRecycler = findViewById(R.id.medRecycler);
        DossierCard = findViewById(R.id.DossierCard);

        userName.setText(userData.getNom());
        userAge.setText(userData.getAge());
        Log.e("userData",userData.getUid());
        if(userData.getDossierMedical()!=null){
            DossierCard.setVisibility(View.VISIBLE);
            dossierNote.setText(userData.getDossierMedical().getNote());
            Date.setText("#"+userData.getDossierMedical().getDate());

        }else {


        }



        SAL.add(new Service("protasse", R.drawable.doctor));
        SAL.add(new Service("PARU & PATU", R.drawable.doctor));
        SAL.add(new Service("Odontologie CNSV ", R.drawable.doctor));
        SAL.add(new Service("ODF", R.drawable.doctor));


        SAdapter = new ServicesAdapter(this, SAL, this);
        RAdapter = new RendezVousAdapter(this,RAL,MAL,this);

        //
        ImageView profileImg = nav_view.getHeaderView(0).findViewById(R.id.ProfileImg);
        TextView drawer_user_name = nav_view.getHeaderView(0).findViewById(R.id.drawer_user_name);
        TextView drawer_user_mail = nav_view.getHeaderView(0).findViewById(R.id.drawer_user_mail);

        drawer_user_name.setText(userData.getNom() + " " + userData.getPrenom());
        drawer_user_mail.setText(userData.getEmail());


        // Service Recycler View
        LinearLayoutManager LM = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        Srcv.setLayoutManager(LM);
        Srcv.setAdapter(SAdapter);


        // RendezVous Recycler view
        LinearLayoutManager LMR = new LinearLayoutManager(this);
        Rrvc.setLayoutManager(LMR);
        Rrvc.setAdapter(RAdapter);


        mAdapter = new MedicamentAdapter(this,MALD);

        LinearLayoutManager LM2 = new LinearLayoutManager(this);
        medRecycler.setLayoutManager(LM2);
        medRecycler.setAdapter(mAdapter);

        getClientData();
    }



    @Override
    public void OnServiceClick(int position) {
        Toast.makeText(DashboardActivity.this, "Service " + position + 1 + " Clicked !", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(DashboardActivity.this, MedcinListActivity.class);
        intent.putExtra("service", position);
        startActivity(intent);
    }

    @Override
    public void OnRendezVousClick(int position) {
        Toast.makeText(DashboardActivity.this, "RendezVous " + position + 1 + " Clicked !", Toast.LENGTH_SHORT).show();
        // openBookingDialog();
    }

    @Override
    public void OnRendezVousLongClick(int position) {
        openResetBottomSheet(position);
    }

    private void openResetBottomSheet(int positino){
        BottomSheetDialog bottomDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.bottom_sheet_delete_rdc,null,false);
        Button cancel , reset;

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("suppression....");
        progressDialog.setCancelable(false);
        cancel=view.findViewById(R.id.cancel);
        reset = view.findViewById(R.id.reset);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               rdvRef.child(RAL.get(positino).getId()).child("state").setValue(2);
               MAL.remove(positino);
               RAL.remove(positino);


            }
        });
        bottomDialog.setCancelable(false);
        bottomDialog.setContentView(view);
        bottomDialog.show();

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.qrcode:
                Intent intent2 = new Intent(DashboardActivity.this, QrCodeActivity.class);
                startActivity(intent2);
                break;


            case R.id.messages:
                Intent intent3 = new Intent(DashboardActivity.this, MessageHistory.class);
                startActivity(intent3);
                break;

            case R.id.history:
                Intent intent4 = new Intent ( DashboardActivity.this , RdvHistoryActivity.class );

                startActivity ( intent4 );

                break;

            case R.id.logout:
                mAuth.signOut();
                Intent intent = new Intent(DashboardActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();


        }

        return true;
    }


}
