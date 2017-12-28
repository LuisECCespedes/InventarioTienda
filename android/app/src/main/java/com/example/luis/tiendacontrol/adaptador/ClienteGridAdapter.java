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
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;

import java.util.List;

/**
 * Created by luis on 15/12/2017.
 */

public class ClienteGridAdapter extends ArrayAdapter<Cliente>{
    public ClienteGridAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Cliente> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            Cliente objCliente = getItem(position);

            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_cliente,parent,false);
            }
            Float nSize = SessionPreferences.get(parent.getContext()).getLetraSize();
            TextView tvCliente = convertView.findViewById(R.id.tvItemCliente);

            tvCliente.setTextSize(nSize);
            tvCliente.setText(objCliente.getClie_nombre());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
