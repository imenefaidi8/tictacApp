package oran.myapp.reservation.screens;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.window.SplashScreen;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import oran.myapp.reservation.MainActivity;
import oran.myapp.reservation.R;
import oran.myapp.reservation.modele.patient;
/*nchofo ila rah mconnecte ou non */
public class splashScreen extends AppCompatActivity {
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    // Objet to type SPlash Screen named inst ( instance )
    private static splashScreen inst ;

    private patient UserData;

    public splashScreen() {

        inst = this;
    }

    // method de classs return object of splashScreen
    public static splashScreen getInst() {
        return inst;
    }




    private FirebaseDatabase Root = FirebaseDatabase.getInstance("https://pfelicence-615fe-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference usersRef = Root.getReference("users");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //This method will be executed once the timer is over
                // Start your app main activity

                // if mAuth.getCurrentUser ( means that the user is already logged in )
                if (mAuth.getCurrentUser() != null) {
                    usersRef.child(mAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(!snapshot.exists()) return;
                            UserData = snapshot.getValue(patient.class);
                            Intent i = new Intent(splashScreen.this, DashboardActivity.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            // close this activity
                            finish();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                } else {
                    // if not go to MainActivity ( Log in Activity )
                    Intent i = new Intent(splashScreen.this, MainActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            }
            // 3000 means this code will not run until the 3000 ms goes on
        }, 1000);

    }

    // Getters and Setters of UserData ( Static for all app or activities )
    public patient GetUserData() {
        return UserData;
    }
    public void setUserData(patient UserData){this.UserData=UserData;}
}