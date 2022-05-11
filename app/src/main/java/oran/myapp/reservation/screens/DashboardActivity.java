package oran.myapp.reservation.screens;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import oran.myapp.reservation.MainActivity;
import oran.myapp.reservation.R;
import oran.myapp.reservation.adapter.RendezVousAdapter;
import oran.myapp.reservation.adapter.ServicesAdapter;
import oran.myapp.reservation.modele.RendezVous;
import oran.myapp.reservation.modele.Service;
import oran.myapp.reservation.modele.medecin;
import oran.myapp.reservation.modele.patient;

public class DashboardActivity extends AppCompatActivity implements ServicesAdapter.ServiceClickListener
        , RendezVousAdapter.RendezVousClickListener, NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView Srcv, Rrvc;
    private ArrayList<Service> SAL = new ArrayList<> ( );
    private ArrayList<RendezVous> RAL = new ArrayList<> ( );
    private ServicesAdapter SAdapter;
    private ImageView MenuIcon;
    private DrawerLayout drawerLayout;
    private NavigationView nav_view;
    private RendezVousAdapter RAdapter;
    // Firebase Objects
    private FirebaseDatabase ROOT = FirebaseDatabase.getInstance ( "https://pfelicence-615fe-default-rtdb.europe-west1.firebasedatabase.app/" );
    private DatabaseReference rdvRef = ROOT.getReference ( "RendezVous" );
    private FirebaseAuth mAuth = FirebaseAuth.getInstance ( );

    // User Data
    private splashScreen inst = splashScreen.getInst ( );
    private patient userData = inst.GetUserData ( );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_dashboard );


        init ( );

        MenuIcon.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                if (drawerLayout.isDrawerOpen ( GravityCompat.START ))
                    drawerLayout.closeDrawer ( GravityCompat.START );
                else drawerLayout.openDrawer ( GravityCompat.START );
            }
        } );


        // Navigation VIew Item CLicks
        nav_view.setNavigationItemSelectedListener ( this );


        GetRendezVous ( );

    }


    private void GetRendezVous() {
        rdvRef.child ( userData.getUid ( ) ).addChildEventListener ( new ChildEventListener ( ) {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot , @Nullable String previousChildName) {
                if (!snapshot.exists ( )) {
                    return;
                }
                RendezVous helper = snapshot.getValue ( RendezVous.class );

                RAL.add ( helper );

                RAdapter.notifyDataSetChanged ( );
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot , @Nullable String previousChildName) {
                if (!snapshot.exists ( )) {
                    Log.e ( "onChanged" , "false" );
                    return;
                }
                Log.e ( "onChanged" , "true" );
                RendezVous helper = snapshot.getValue ( RendezVous.class );

                for (int i = 0; i < RAL.size ( ); i++) {

                    if (RAL.get ( i ).getId ( ).equals ( helper.getId ( ) )) {

                        RAL.remove ( i );
                        RAL.add ( i , helper );
                        RAdapter.notifyDataSetChanged ( );


                    }

                }
                if (RAL.size ( ) == 0) {
                    Rrvc.setVisibility ( View.GONE );

                }

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists ( )) {
                    Log.e ( "onRemoved" , "false" );
                    return;
                }
                Log.e ( "onRemoved" , "true" );
                RendezVous helper = snapshot.getValue ( RendezVous.class );

                for (int i = 0; i < RAL.size ( ); i++) {

                    if (RAL.get ( i ).getId ( ).equals ( helper.getId ( ) )) {

                        RAL.remove ( i );


                    }

                }
                RAdapter.notifyDataSetChanged ( );
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot , @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText ( DashboardActivity.this , "error: " + error.getMessage ( ) , Toast.LENGTH_SHORT ).show ( );
            }
        } );
    }

    //open boocking dialog
    private void openBookingDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder ( this );
        View view = LayoutInflater.from ( this ).inflate ( R.layout.dialog_booking , null , false );
        AlertDialog dialog = builder.create ( );

        builder.setView ( view );
        builder.setCancelable ( true );
        builder.show ( );

    }

    // Views Initialization
    private void init() {
        Srcv = findViewById ( R.id.ServiesRecycler );
        Rrvc = findViewById ( R.id.RendezVousRecycler );
        MenuIcon = findViewById ( R.id.MenuIcon );
        drawerLayout = findViewById ( R.id.drawerLayout );
        nav_view = findViewById ( R.id.nav_view );

        SAL.add ( new Service ( "protasse" , R.drawable.doctor ) );
        SAL.add ( new Service ( "PARU & PATU" , R.drawable.doctor ) );
        SAL.add ( new Service ( "Odontologie CNSV " , R.drawable.doctor ) );
        SAL.add ( new Service ( "ODF" , R.drawable.doctor ) );


        SAdapter = new ServicesAdapter ( this , SAL , this );
        RAdapter = new RendezVousAdapter ( this , RAL , this );

        //
        ImageView profileImg = nav_view.getHeaderView ( 0 ).findViewById ( R.id.ProfileImg );
        TextView drawer_user_name = nav_view.getHeaderView ( 0 ).findViewById ( R.id.drawer_user_name );
        TextView drawer_user_mail = nav_view.getHeaderView ( 0 ).findViewById ( R.id.drawer_user_mail );

        drawer_user_name.setText ( userData.getNom ( ) + " " + userData.getPrenom ( ) );
        drawer_user_mail.setText ( userData.getEmail ( ) );


        // Service Recycler View
        LinearLayoutManager LM = new LinearLayoutManager ( this , LinearLayoutManager.HORIZONTAL , false );

        Srcv.setLayoutManager ( LM );
        Srcv.setAdapter ( SAdapter );


        // RendezVous Recycler view
        LinearLayoutManager LMR = new LinearLayoutManager ( this );
        Rrvc.setLayoutManager ( LMR );
        Rrvc.setAdapter ( RAdapter );
    }

    @Override
    public void OnServiceClick(int position) {
        Toast.makeText ( DashboardActivity.this , "Service " + position + 1 + " Clicked !" , Toast.LENGTH_SHORT ).show ( );
        Intent intent = new Intent ( DashboardActivity.this , MedcinListActivity.class );
        intent.putExtra ( "service" , position );
        startActivity ( intent);
           }

    @Override
    public void OnRendezVousClick(int position) {
        Toast.makeText ( DashboardActivity.this , "RendezVous " + position + 1 + " Clicked !" , Toast.LENGTH_SHORT ).show ( );
        // openBookingDialog();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId ( )) {

            case R.id.qrcode:
                Intent intent2 = new Intent ( DashboardActivity.this , QrCodeActivity.class );
                startActivity ( intent2 );
                break;


            case R.id.profile:

                break;
            case R.id.logout:
                mAuth.signOut ( );
                Intent intent = new Intent ( DashboardActivity.this , MainActivity.class );
                intent.setFlags ( Intent.FLAG_ACTIVITY_CLEAR_TASK );
                startActivity ( intent );


        }

        return true;
    }
}
