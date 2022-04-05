package oran.myapp.reservation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText txt_fullname,txt_username,txt_email,txt_password;
    private Button btn_Registre;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance ( );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //casting views
        txt_fullname = (EditText) findViewById ( R.id.fullname );
        txt_username = (EditText) findViewById ( R.id.username );
        txt_email = (EditText) findViewById ( R.id.txtemail );
        txt_password = (EditText) findViewById ( R.id.txtpassword );
        btn_Registre = (Button) findViewById ( R.id.btn_registre);

        btn_Registre.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {



            }
        } );

    }
}