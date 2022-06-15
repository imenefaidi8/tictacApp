package oran.myapp.reservation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

    private EditText email, password;
    private LinearLayout LoginButton;
    private TextView AddAccount,forgetPassword;

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
        email = findViewById ( R.id.email );
        password = findViewById ( R.id.password );
        LoginButton = findViewById ( R.id.sign_in );
        AddAccount = findViewById ( R.id.AddAccount );
        forgetPassword = findViewById ( R.id.forgetPassword );



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

        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             openResetBottomSheet();
            }
        });
    }


    private void loginFromFirebase() {
        progressDialog.show();
        String Tmail = email.getText().toString();
        String TPass = password.getText().toString();

        if (Tmail.isEmpty()) {
            progressDialog.dismiss();
            email.setError("required ! ");
            return;
        }
        if (TPass.isEmpty()) {
            progressDialog.dismiss();
            password.setError("required !");
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


    private void openResetBottomSheet(){
        BottomSheetDialog bottomDialog = new BottomSheetDialog(this);
        View view = LayoutInflater.from(this).inflate(R.layout.reset_password,null,false);
        Button cancel , reset;
        EditText resetpasswordEdit;
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Reset....");
        progressDialog.setCancelable(false);
        cancel=view.findViewById(R.id.cancel);
        reset = view.findViewById(R.id.reset);
        resetpasswordEdit = view.findViewById(R.id.resetpasswordEdit);

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomDialog.dismiss();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String email = resetpasswordEdit.getText().toString();
                if(email.isEmpty()){
                    progressDialog.dismiss();
                    resetpasswordEdit.setError("required ! ");

                    return;
                }
                mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"error: "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                            return;
                        }
                        progressDialog.dismiss();
                        Toast.makeText(MainActivity.this, "Verifier votre email maintenant ! ", Toast.LENGTH_SHORT).show();

                    }
                });

            }
        });
        bottomDialog.setCancelable(false);
        bottomDialog.setContentView(view);
        bottomDialog.show();

    }
}