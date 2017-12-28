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
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;

import java.util.List;

/**
 * Created by luis on 23/12/2017.
 */

public class MenuGridAdapter  extends ArrayAdapter<String> {
    public MenuGridAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        try {
            Object objMenu = getItem(position);

            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_menu,parent,false);
            }
            Float nSize = SessionPreferences.get(parent.getContext()).getLetraSize();
            TextView tvCliente = convertView.findViewById(R.id.tvItemMenu);

            tvCliente.setTextSize(nSize);
            tvCliente.setText(objMenu.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;
    }
}
