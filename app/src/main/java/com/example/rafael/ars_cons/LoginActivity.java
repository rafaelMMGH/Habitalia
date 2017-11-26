package com.example.rafael.ars_cons;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity implements OnClickListener {

    private Button btnSign;
    private EditText txtEmail;
    private EditText txtPass;
    private TextView TvReg,TvInicio;


    //private ProgressBar progressBar;
    private ProgressDialog dialog;

    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);

//        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        dialog = new ProgressDialog(this);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setIndeterminate(true);
        dialog.setTitle("Ingresando...");
        dialog.setMessage("Por favor espere mientras ingresa a su cuenta!");


        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
        }


        btnSign = (Button) findViewById(R.id.btnSign);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPass = (EditText) findViewById(R.id.txtPass);


        TvInicio = (TextView) findViewById(R.id.TvInicio);
        Typeface typeface = Typeface.createFromAsset(getAssets(),"fonts/Lobster-Regular.ttf");
        TvInicio.setTypeface(typeface);

        TvReg = (TextView) findViewById(R.id.TvReg);

        btnSign.setOnClickListener(this);
        TvReg.setOnClickListener(this);

    }

    public void loginUser(){
        String email = txtEmail.getText().toString().trim();
        String pass = txtPass.getText().toString().trim();
        final View view = findViewById(R.id.content);

        if (TextUtils.isEmpty(email) ){
            Snackbar.make(view,"Ingrese su correo", Snackbar.LENGTH_INDEFINITE).setAction("Aceptar", new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
            return;
        }else if (TextUtils.isEmpty(pass)){
            Snackbar.make(view,"Ingrese una contraseña ", Snackbar.LENGTH_INDEFINITE).setAction("Aceptar", new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
            return;
        }else if (pass.length() < 6){
            Snackbar.make(view,"La contraseña debe tener al menos 6 dígitos ", Snackbar.LENGTH_INDEFINITE).setAction("Aceptar", new OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            }).show();
        }      else{

            //progressBar.setVisibility(View.VISIBLE);
            dialog.show();

            firebaseAuth.signInWithEmailAndPassword(email,pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                Toast.makeText(LoginActivity.this,"Sesión Iniciada", Toast.LENGTH_SHORT).show();
            //                    progressBar.setVisibility(View.INVISIBLE);
                                dialog.dismiss();
                                finish();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            }else{
                                //Correo repetido || validacion de correo( contenga @, después del @ tenga una direccion real
                                Snackbar.make(view,"No se ha podido Iniciar Sesión", Snackbar.LENGTH_INDEFINITE).setAction("Aceptar", new OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                }).show();
            //                    progressBar.setVisibility(View.INVISIBLE);
                                dialog.dismiss();
                            }
                        }
                    });


        }
    }


    @Override
    public void onClick(View v) {

        if (v == btnSign){
            loginUser();
        }else if (v == TvReg){
            //Will open login activity here

            finish();
            startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
        }
    }
}

