package com.ambarrojostudios.rafael.habitalias;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by rafael on 28/11/17.
 */

public class CasaList extends ArrayAdapter<Casa> {

    private Activity context;
    private List<Casa> listCasa;

    public CasaList(Activity context,List<Casa> listCasa){
        super(context,R.layout.fragment_favorites, listCasa );
        this.context = context;
        this.listCasa = listCasa;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();

        View v = inflater.inflate(R.layout.listcasa_layout, null, true);

        TextView nombre_casa = (TextView) v.findViewById(R.id.nombre_casa);
        TextView precio_casa = (TextView) v.findViewById(R.id.precio_casa);
        TextView descripcion_casa = (TextView) v.findViewById(R.id.descripcion_casa);
        TextView empresa = (TextView) v.findViewById(R.id.empresa);
        ImageView imCasa = (ImageView) v.findViewById(R.id.imCasa);

        Casa casa = listCasa.get(position);

        nombre_casa.setText(casa.getFld_titulo());
        //precio_casa.setText(String.valueOf(casa.getFld_precio()));
        //descripcion_casa.setText(casa.getFld_informacion());
        //empresa.setText(casa.getP_empresafk02());

        precio_casa.setText(String.valueOf(casa.getFld_lat()));
        descripcion_casa.setText(String.valueOf(casa.getFld_lng()));

        return v;
    }
}