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
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;

import java.util.List;

/**
 * Created by luis on 17/12/2017.
 */

public class ProductoGridAdapter extends ArrayAdapter<Producto> {

    public ProductoGridAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Producto> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            //objeto enviado por parametro
            Producto objProducto = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
            }
            Float nSize = SessionPreferences.get(parent.getContext()).getLetraSize();

            //altermos los valores de las propiedades
            TextView tvPrecio = convertView.findViewById(R.id.tvItemProductoPrecio);
            TextView tvDescri = convertView.findViewById(R.id.tvItemProductoDescri);

            tvPrecio.setTextSize(nSize);
            tvPrecio.setText(objProducto.getProd_precio() + " | ");

            tvDescri.setTextSize(nSize);
            tvDescri.setText(objProducto.getProd_descri());

        } catch (Exception e) {

        }

        return convertView;

    }
}