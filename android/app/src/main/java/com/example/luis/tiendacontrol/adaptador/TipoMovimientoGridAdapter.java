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
import com.example.luis.tiendacontrol.data.modelo.TipoMovimiento;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;

import java.util.List;

/**
 * Created by luis on 16/12/2017.
 */

public class TipoMovimientoGridAdapter extends ArrayAdapter<TipoMovimiento> {
    public TipoMovimientoGridAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<TipoMovimiento> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            TipoMovimiento objTipoMovimiento = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_tipo_movimiento, parent, false);
            }
            Float nSize = SessionPreferences.get(parent.getContext()).getLetraSize();
            TextView tvTipoMov = convertView.findViewById(R.id.tvItemMovimientoTipo);
            TextView tvTipoMovIngSql = convertView.findViewById(R.id.tvItemMovimientoTipoIngSal);

            tvTipoMov.setTextSize(nSize);
            tvTipoMov.setText(objTipoMovimiento.getTip_mov_descri());

            tvTipoMovIngSql.setTextSize(nSize);
            tvTipoMovIngSql.setText(objTipoMovimiento.getTip_mov_ing_sal() + " - ");
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}