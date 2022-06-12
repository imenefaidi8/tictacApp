package oran.myapp.reservation.screens;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import oran.myapp.reservation.R;
import oran.myapp.reservation.modele.patient;

public class RegisterActivity extends AppCompatActivity {
    private EditText txt_fullname, txt_username, txt_email, txt_password, textAge, textAdress, textPhone;
    private LinearLayout btn_Registre;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    private FirebaseDatabase Root = FirebaseDatabase.getInstance("https://pfelicence-615fe-default-rtdb.europe-west1.firebasedatabase.app/");
    private DatabaseReference usersRef = Root.getReference("users");
    private splashScreen inst = splashScreen.getInst();
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registring ...");
        progressDialog.setCancelable(false);
        //casting views
        txt_fullname = (EditText) findViewById(R.id.fullname);
        txt_username = (EditText) findViewById(R.id.username);
        txt_email = (EditText) findViewById(R.id.txtemail);
        txt_password = (EditText) findViewById(R.id.txtpassword);
        textAge = (EditText) findViewById(R.id.age);
        textAdress = (EditText) findViewById(R.id.txtAdress);
        textPhone = (EditText) findViewById(R.id.tel);


        btn_Registre =  findViewById(R.id.btn_registre);

        btn_Registre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Method raha l ta7t
                SignInFromFirebase();

            }
        });

    }

    // method bach ndiru create account
    private void SignInFromFirebase() {
        // hada progress dialog bach ( ki ydir click tetla3 3nadah beli rah ydir loading ... )
        progressDialog.show();
        // njibu text mn editText w ndiruhum f variables
        String Tmail = txt_email.getText().toString();
        String TPass = txt_password.getText().toString();
        String Tname = txt_fullname.getText().toString();
        String Tprenmae = txt_username.getText().toString();
        String Tadress = textAdress.getText().toString();
        String Tphone = textPhone.getText().toString();
        String Tage = textAge.getText().toString();

        // nverifiw ila edit text rahum 3amrin wela khawyin ( ila khawyin ya3tina error )
        if (Tmail.isEmpty()) {
            // hadi dissmiss ma3netha loading kamel ( ya error ya succeed wa7da fihum )
            progressDialog.dismiss();
            txt_email.setError("required ! ");
            return;
        }
        if (TPass.isEmpty()) {
            progressDialog.dismiss();
            txt_password.setError("required !");
            return;

        }
        if (Tname.isEmpty()) {
            progressDialog.dismiss();
            txt_fullname.setError("required !");
            return;

        }
        if (Tprenmae.isEmpty()) {
            progressDialog.dismiss();
            txt_username.setError("required !");
            return;

        }
        if (Tadress.isEmpty()) {
            progressDialog.dismiss();
            textAdress.setError("required !");
            return;

        }
        if (Tphone.isEmpty()) {
            progressDialog.dismiss();
            textPhone.setError("required !");
            return;

        }
        if (Tage.isEmpty()) {
            progressDialog.dismiss();
            textAge.setError("required !");
            return;

        }
        // Hna ndiru create user with email and password ( create account bel email wel mot de pass )

        mAuth.createUserWithEmailAndPassword(Tmail, TPass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                // Njibu l ID nta3 l user li tgenera mn 3and l firebase
                String UserId = authResult.getUser().getUid();
                // n3amru ga3 data nta3 l user fel object helper ( of type patient )
                patient helper = new patient(UserId, Tname, Tprenmae, Tadress, Tmail, TPass, Tage, Tphone);
                // n posto Data fel realtime database w dayment ndiruha ta7t child userID
                usersRef.child(UserId).setValue(helper).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        inst.setUserData(helper);
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, " Sign up  successfully !", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(RegisterActivity.this, DashboardActivity.class);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                });


            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(RegisterActivity.this, "error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
}