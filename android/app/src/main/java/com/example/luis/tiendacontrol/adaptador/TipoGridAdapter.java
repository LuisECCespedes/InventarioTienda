package com.example.luis.tiendacontrol.adaptador;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;

import java.util.List;

/**
 * Created by luis on 15/12/2017.
 */
/*

public class MesaItemGridAdapter extends ArrayAdapter<Mesa>{

    public MesaItemGridAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Mesa> objects) {
        super(context, resource, objects);
    }

* */
public class TipoGridAdapter extends ArrayAdapter<Tipo> {


    public TipoGridAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Tipo> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            //objeto enviado por parametro
            Tipo objTipo = getItem(position);

            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tipo,parent,false);
            }
            Float nSize = SessionPreferences.get(parent.getContext()).getLetraSize();

            //altermos los valores de las propiedades
            TextView tvTipo = convertView.findViewById(R.id.tvItemTipo);

            tvTipo.setTextSize(nSize);
            tvTipo.setText(objTipo.getTip_descri());

        } catch (Exception e) {

        }

        return convertView;

    }
}
