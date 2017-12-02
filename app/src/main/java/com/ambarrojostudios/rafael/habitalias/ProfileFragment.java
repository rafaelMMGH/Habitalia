package com.ambarrojostudios.rafael.habitalias;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.math.BigInteger;
import java.security.SecureRandom;


public class ProfileFragment extends Fragment{

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;
    private ProgressDialog progressDialog;
    private StorageReference mStorage;
    private DatabaseReference mDatabase;

    public static final int CAMERA_REQUEST_CODE = 0;

    private TextView txtRecoverPass;
    private EditText txtNombre_usuario,txtCorreo,txtTelefono;
    private ImageView Image_view;
    private Button btnLogOut;

    private ProfileFragment.OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        mAuth = FirebaseAuth.getInstance();

        txtNombre_usuario = (EditText) v.findViewById(R.id.txtNombre_usuario);
        txtCorreo = (EditText) v.findViewById(R.id.txtCorreo);
        txtTelefono = (EditText) v.findViewById(R.id.txtTelefono);

        txtRecoverPass = (TextView) v.findViewById(R.id.txtRecoverPass);
        txtRecoverPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = txtCorreo.getText().toString().trim();

                if (mAuth.sendPasswordResetEmail(email).isSuccessful()){
                    Toast.makeText(getActivity(), "Ha ocurrido un error, intente más tarde", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getActivity(), "Solicitud enviada, revise su correo", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Image_view = (ImageView) v.findViewById(R.id.Image_View);
        Image_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                if(intent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(Intent.createChooser(intent, "Seleccciona una imagen para tu perfil"),CAMERA_REQUEST_CODE);
                }
            }
        });

        btnLogOut = (Button) v.findViewById(R.id.btnLogOut);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(),LoginActivity.class));

            }
        });

        progressDialog = new ProgressDialog(getActivity());
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener(){
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    mStorage = FirebaseStorage.getInstance().getReference();
                    mDatabase = FirebaseDatabase.getInstance().getReference().child("P_usuario");
                    mDatabase.child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            txtNombre_usuario.setText(String.valueOf(dataSnapshot.child("fld_nombre").getValue()));
                            txtCorreo.setText(String.valueOf(dataSnapshot.child("fld_correo").getValue()));
                            txtTelefono.setText(String.valueOf(dataSnapshot.child("fld_telefono").getValue()));
                            String imageUrl = String.valueOf(dataSnapshot.child("fld_imagen").getValue());
                            if (URLUtil.isValidUrl(imageUrl))
                                Picasso.with(getActivity().getApplicationContext()).load(Uri.parse(imageUrl)).into(Image_view);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
                else {
                    getActivity().finish();
                    startActivity(new Intent(getActivity(),LoginActivity.class));


                }
            }
        };

        return v;
    }

    public String getRandomString() {
        SecureRandom random = new SecureRandom();
        return new BigInteger(130, random).toString(32);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){

            if(mAuth.getCurrentUser() == null)
                return;
            progressDialog.setMessage("Actualizando imagen de perfil");
            progressDialog.show();
            final Uri uri = data.getData();
            if(uri == null) {
                progressDialog.dismiss();
                return;
            }
            if (mAuth.getCurrentUser() == null)
                return;

            if(mStorage == null)
                mStorage = FirebaseStorage.getInstance().getReference();
            if(mDatabase == null)
                mDatabase = FirebaseDatabase.getInstance().getReference().child("P_usuario");

            final StorageReference filepath = mStorage.child("photos").child(getRandomString());
            final DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());

            currentUserDB.child("fld_imagen").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String imagen = dataSnapshot.getValue().toString();

                    if (!imagen.equals("Default") && !imagen.isEmpty()) {
                        Task<Void> task = FirebaseStorage.getInstance().getReferenceFromUrl(imagen).delete();
                        task.addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(getContext(),"Imagen Actualizada",Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(getContext(), "No se ha podido actulizar la imagen", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    currentUserDB.child("fld_imagen").removeEventListener(this);
                    filepath.putFile(uri).addOnSuccessListener(getActivity(), new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Uri downloadUri = taskSnapshot.getDownloadUrl();
                            Toast.makeText(getContext(), "Finished", Toast.LENGTH_SHORT).show();
             /**/               Picasso.with(getActivity().getApplicationContext()).load(uri).fit().centerCrop().into(Image_view);
                            DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                            currentUserDB.child("fld_imagen").setValue(downloadUri.toString());
                        }
                    }).addOnFailureListener(getActivity(), new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
        mAuthListener = null;


    }

    public class OnFragmentInteractionListener {
    }
}
