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
import com.example.luis.tiendacontrol.data.modelo.Kardex;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import java.util.List;

/**
 * Created by luis on 20/12/2017.
 *
 */

public class KardexGridAdapter extends ArrayAdapter<Kardex> {
    public KardexGridAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Kardex> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            //objeto enviado por parametro
            Kardex objKarder = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_producto, parent, false);
            }
            Float nSize = SessionPreferences.get(parent.getContext()).getLetraSize();

            //altermos los valores de las propiedades
            TextView tvPrecio = convertView.findViewById(R.id.tvItemProductoPrecio);
            TextView tvDescri = convertView.findViewById(R.id.tvItemProductoDescri);

            tvPrecio.setTextSize(nSize);
            tvPrecio.setText(objKarder.getProd_cant() + " | " + objKarder.getTip_mov_ing_sal() + " | ");

            tvDescri.setTextSize(nSize);
            //nombre del producto
            tvDescri.setText(objKarder.getTip_mov_descri());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
