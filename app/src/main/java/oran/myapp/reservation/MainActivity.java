package oran.myapp.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import oran.myapp.reservation.modele.patient;
import oran.myapp.reservation.screens.DashboardActivity;
import oran.myapp.reservation.screens.RegisterActivity;
import oran.myapp.reservation.screens.splashScreen;

public class MainActivity extends AppCompatActivity {

    private EditText mailEdit, PassEdit;
    private Button LoginButton;
    private TextView AddAccount;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private ProgressDialog progressDialog;
    private splashScreen inst = splashScreen.getInst();



    private FirebaseDatabase Root = FirebaseDatabase.getInstance("https://pfelicence-615fe-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference usersRef = Root.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sign in  ...");
        progressDialog.setCancelable(false);
        mailEdit = findViewById(R.id.EmailEdit);
        PassEdit = findViewById(R.id.passwordEdit);
        LoginButton = findViewById(R.id.loginButton);
        AddAccount = findViewById(R.id.AddAccount);


        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loginFromFirebase();

            }
        });

        AddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    private void loginFromFirebase() {
        progressDialog.show();
        String Tmail = mailEdit.getText().toString();
        String TPass = PassEdit.getText().toString();

        if (Tmail.isEmpty()) {
            progressDialog.dismiss();
            mailEdit.setError("required ! ");
            return;
        }
        if (TPass.isEmpty()) {
            progressDialog.dismiss();
            PassEdit.setError("required !");
            return;

        }

        mAuth.signInWithEmailAndPassword(Tmail, TPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, " Sign in  successfully !", Toast.LENGTH_SHORT).show();

                // njibu Data nta3 l user mn Realtime data base


                usersRef.child(authResult.getUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    /* snapchat fiha ga3 les donne */
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (!snapshot.exists()) return;

                        patient UserData = snapshot.getValue(patient.class);

                        inst.setUserData(UserData);


                        Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                        // close this activity

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}