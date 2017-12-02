package com.ambarrojostudios.rafael.habitalias;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{


    private Button btnReg;
    private EditText txtEmail,txtPass,txtName,txtTel;

    private TextView TvReg,TvRegis;

    //private ProgressBar progressBar;
    private ProgressDialog dialog;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onStart() {
        super.onStart();
//        firebaseAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_register);

        //progressBar = (ProgressBar) findViewById(R.id.progressBar);
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setTitle("Registrando...");
        dialog.setMessage("Por favor espere mientras se registra su cuenta!");

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }

        btnReg = (Button) findViewById(R.id.btnReg);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtName = (EditText) findViewById(R.id.txtName);
        txtTel = (EditText) findViewById(R.id.txtTel);

        TvReg = (TextView) findViewById(R.id.TvReg);

        TvRegis = (TextView) findViewById(R.id.Tvregis);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Lobster-Regular.ttf");
        TvRegis.setTypeface(typeface);

        btnReg.setOnClickListener(this);
        TvReg.setOnClickListener(this);
    }

    private void registerUser(){
        final String Name = txtName.getText().toString().trim();
        final String Tel = txtTel.getText().toString().trim();
        final String email = txtEmail.getText().toString().trim();
        final String pass = txtPass.getText().toString().trim();
        final View view = findViewById(R.id.content);


        if (TextUtils.isEmpty(email) ){
            Snackbar.make(view,"Ingrese su correo", Snackbar.LENGTH_INDEFINITE).setAction("Aceptar", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();

            return;
        }else if (TextUtils.isEmpty(pass)){
            Snackbar.make(view,"Ingrese una contraseña ", Snackbar.LENGTH_INDEFINITE).setAction("Aceptar", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
            return;
        }else if (pass.length() < 6){
            Snackbar.make(view,"La contraseña debe tener al menos 6 dígitos ", Snackbar.LENGTH_INDEFINITE).setAction("Aceptar", new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }

        else{

            final Intent intent = new Intent(this,MainActivity.class);
            //progressBar.setVisibility(View.VISIBLE);
            dialog.show();

            firebaseAuth.createUserWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                DatabaseReference mdatabase = FirebaseDatabase.getInstance().getReference().child("P_usuario");
                                DatabaseReference currentUserDB = mdatabase.child(firebaseAuth.getCurrentUser().getUid());

                                currentUserDB.child("fld_correo").setValue(email);
                                currentUserDB.child("fld_imagen").setValue("Default");
                                currentUserDB.child("fld_nombre").setValue(Name);
                                currentUserDB.child("fld_telefono").setValue(Tel);
                                Toast.makeText(RegisterActivity.this,"Registro Correcto", Toast.LENGTH_SHORT).show();
                                //                    progressBar.setVisibility(View.INVISIBLE);
                                dialog.dismiss();
                                startActivity(intent);



                            }else{
                                //Correo repetido || validacion de correo( contenga @, después del @ tenga una direccion real
                                Snackbar.make(view,"No se ha podido Registrar su cuenta", Snackbar.LENGTH_INDEFINITE).setAction("Aceptar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
                                dialog.dismiss();
                                //progressBar.setVisibility(View.INVISIBLE);

                            }
                        }
                    });


        }
    }

    @Override
    public void onClick(View v) {

        if (v == btnReg){
            registerUser();
        }else if (v == TvReg){
            //Will open login activity here
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }


    }
}

