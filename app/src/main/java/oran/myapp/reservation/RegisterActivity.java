package oran.myapp.reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    private EditText txt_fullname,txt_username,txt_email,txt_password;
    private Button btn_Registre;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance ( );

    private FirebaseDatabase Root=FirebaseDatabase.getInstance ();
    private DatabaseReference users=Root.getReference ("users");
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Registring ...");
        progressDialog.setCancelable(false );
        //casting views
        txt_fullname = (EditText) findViewById ( R.id.fullname );
        txt_username = (EditText) findViewById ( R.id.username );
        txt_email = (EditText) findViewById ( R.id.txtemail );
        txt_password = (EditText) findViewById ( R.id.txtpassword );
        btn_Registre = (Button) findViewById ( R.id.btn_registre);

        btn_Registre.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                // Method raha l ta7t
                SignInFromFirebase ( );

            }
        } );

    }
    // method bach ndiru create account
    private void SignInFromFirebase() {
        // hada progress dialog bach ( ki ydir click tetla3 3nadah beli rah ydir loading ... )
        progressDialog.show();
        // njibu text mn editText w ndiruhum f variables
        String Tmail = txt_email.getText ( ).toString ( );
        String TPass = txt_password.getText ( ).toString ( );

         // nverifiw ila edit text rahum 3amrin wela khawyin ( ila khawyin ya3tina error )
        if(Tmail.isEmpty () ){
            // hadi dissmiss ma3netha loading kamel ( ya error ya succeed wa7da fihum )
            progressDialog.dismiss();
            txt_email.setError ( "required ! " );
            return ;
        }
        if(TPass.isEmpty ()){
            progressDialog.dismiss();
            txt_password.setError ( "required !" );
            return;

        }
        // Hna ndiru create user with email and password ( create account bel email wel mot de pass )

        mAuth.createUserWithEmailAndPassword ( Tmail,TPass ).addOnSuccessListener ( new OnSuccessListener<AuthResult>( ) {
            @Override
            public void onSuccess(AuthResult authResult) {
                progressDialog.dismiss();
                Toast.makeText ( RegisterActivity.this," Sign up  successfully !",Toast.LENGTH_SHORT ).show ();
            }
        } ).addOnFailureListener ( new OnFailureListener( ) {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText ( RegisterActivity.this,"error: "+e.getMessage (),Toast.LENGTH_SHORT ).show ();
            }
        } );


    }
}