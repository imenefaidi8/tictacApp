package oran.myapp.reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mailEdit, PassEdit;
    private Button LoginButton;
    private TextView AddAccount;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance ( );
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Sign in  ...");
        progressDialog.setCancelable(false );
        mailEdit = findViewById ( R.id.EmailEdit );
        PassEdit = findViewById ( R.id.passwordEdit );
        LoginButton = findViewById ( R.id.loginButton );
        AddAccount = findViewById ( R.id.AddAccount );


        LoginButton.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {

                loginFromFirebase ( );

            }
        } );

        AddAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }


    private void loginFromFirebase() {
        progressDialog.show();
        String Tmail = mailEdit.getText ( ).toString ( );
        String TPass = PassEdit.getText ( ).toString ( );

        if(Tmail.isEmpty () ){
            progressDialog.dismiss();
            mailEdit.setError ( "required ! " );
            return ;
        }
        if(TPass.isEmpty ()){
            progressDialog.dismiss();
            PassEdit.setError ( "required !" );
            return;

        }

        mAuth.signInWithEmailAndPassword ( Tmail,TPass ).addOnSuccessListener ( new OnSuccessListener<AuthResult> ( ) {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressDialog.dismiss();
                Toast.makeText ( MainActivity.this," Sign in  successfully !",Toast.LENGTH_SHORT ).show ();
            }
        } ).addOnFailureListener ( new OnFailureListener ( ) {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText ( MainActivity.this,"error: "+e.getMessage (),Toast.LENGTH_SHORT ).show ();
            }
        } );


    }
}