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
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by luis on 15/12/2017.
 */

public class MarcaGridAdapter extends ArrayAdapter<Marca>{


    public MarcaGridAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Marca> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            Marca objMarca = getItem(position);


            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_marca,parent,false);
            }
            Float nSize = SessionPreferences.get(parent.getContext()).getLetraSize();
            TextView tvMarca = convertView.findViewById(R.id.tvItemMarca);

            tvMarca.setText(objMarca.getMar_descri());
            tvMarca.setTextSize(nSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
