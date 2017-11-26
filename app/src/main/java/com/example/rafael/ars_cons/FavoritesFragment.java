package com.example.rafael.ars_cons;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FavoritesFragment extends Fragment {

    private FirebaseAuth firebaseAuth ;

    DatabaseReference dbCasa;

    private TextInputEditText nombre_neg,desc_negocio;
    private EditText txtlng,txtlat,txtPrecio;
    private Spinner spinner_Tipo,spinner_Tipo_construccion;

    private Button btnLogOut,btnSave;

    private FavoritesFragment.OnFragmentInteractionListener mListener;

    public FavoritesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_favorites, container, false);

        dbCasa = FirebaseDatabase.getInstance().getReference("negocios");

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() == null){
            getActivity().finish();
            startActivity(new Intent(getActivity(),LoginActivity.class));
        }

        btnLogOut = (Button) v.findViewById(R.id.btnLogOut);
        btnSave = (Button) v.findViewById(R.id.btnSave);

        nombre_neg = v.findViewById(R.id.nombre_negocio);
        desc_negocio = (TextInputEditText) v.findViewById(R.id.desc_negocio);
        txtlat =  (EditText) v.findViewById(R.id.txtlat);
        txtlng = (EditText)  v.findViewById(R.id.txtlng);
        txtPrecio =  (EditText)  v.findViewById(R.id.txtPrecio);



        spinner_Tipo = (Spinner) v.findViewById(R.id.spinner_Tipo);
        spinner_Tipo_construccion = (Spinner) v.findViewById(R.id.spinner_Tipo_construccion);
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                getActivity().finish();
                startActivity(new Intent(getActivity(),LoginActivity.class));

            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agregarCasa();
            }
        });
        return v;
    }

    private void agregarCasa(){

        String nombre_negocio = nombre_neg.getText().toString().trim();
        String des_negocio = desc_negocio.getText().toString().trim();

        String latitud = txtlat.getText().toString().trim();
        String longuitud = txtlng.getText().toString().trim();
        String tPrecio = txtPrecio.getText().toString().trim();

        String Tipo = spinner_Tipo.getSelectedItem().toString();
        String Tipo_construccion = spinner_Tipo_construccion.getSelectedItem().toString();

        if (!TextUtils.isEmpty(des_negocio) && !TextUtils.isEmpty(nombre_negocio) ){

            String Id = dbCasa.push().getKey();

            Casa negocio = new Casa(Id,nombre_negocio,des_negocio,Tipo,Tipo_construccion,latitud,longuitud,tPrecio);
            dbCasa.child(Id).setValue(negocio);

            Snackbar.make(getView(),"Operación Completada Exitosamente ", Snackbar.LENGTH_SHORT).setAction("Action",null).show();


        }else{
            Snackbar.make(getView(),"Complete todos los campos", Snackbar.LENGTH_SHORT).setAction("Action",null).show();
        }

    }



    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            // Acción al inicar el fragment
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
