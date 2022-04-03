package oran.myapp.reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText mailEdit, PassEdit;
    private Button LoginButton;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance ( );


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );

        mailEdit = findViewById ( R.id.EmailEdit );
        PassEdit = findViewById ( R.id.passwordEdit );
        LoginButton = findViewById ( R.id.loginButton );


        LoginButton.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {

                loginFromFirebase ( );

            }
        } );
    }


    private void loginFromFirebase() {
        String Tmail = mailEdit.getText ( ).toString ( );
        String TPass = PassEdit.getText ( ).toString ( );

        if(Tmail.isEmpty () ){
            mailEdit.setError ( "required ! " );
            return ;
        }
        if(TPass.isEmpty ()){
            PassEdit.setError ( "required !" );
            return;

        }

        mAuth.signInWithEmailAndPassword ( Tmail,TPass ).addOnSuccessListener ( new OnSuccessListener<AuthResult> ( ) {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText ( MainActivity.this," Sign in  successfully !",Toast.LENGTH_SHORT ).show ();
            }
        } ).addOnFailureListener ( new OnFailureListener ( ) {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText ( MainActivity.this,"error: "+e.getMessage (),Toast.LENGTH_SHORT ).show ();
            }
        } );


    }
}