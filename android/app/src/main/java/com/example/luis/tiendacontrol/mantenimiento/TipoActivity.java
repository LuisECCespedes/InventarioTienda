package com.example.luis.tiendacontrol.mantenimiento;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.luis.tiendacontrol.MenuActivity;
import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.adaptador.ClienteGridAdapter;
import com.example.luis.tiendacontrol.adaptador.TipoGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ProductoTabla;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipoActivity extends AppCompatActivity {
    @BindView(R.id.gvActTipo)
    ListView gvTipo;
    private TipoGridAdapter adaptador;
    private List<Tipo> listTipo= new ArrayList<>();
    private Tipo objTipo;
    @BindView(R.id.fabActTipoNuevo)
    FloatingActionButton fbTipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo);
        ButterKnife.bind(this);
        this.setTitle("Tipo Producto");
        cargarLista();

        gvTipo.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objTipo = listTipo.get(i);
                enviarActivity();
            }
        });
    }

    private void cargarLista() {
        Select.SelectTipo(getApplicationContext(),listTipo);
        adaptador = new TipoGridAdapter(getApplicationContext(), 0, listTipo);
        gvTipo.setAdapter(adaptador);
    }

    public void clickButtonTipo(View view)    {
        // Nuevo Cliente
        Intent intento = new Intent(getApplicationContext(), TipoDetalleActivity.class);
        //llamamos a la actividad
        startActivity(intento);
    }

    @Override
    public void onBackPressed() {
        // Salir
        Intent intento = new Intent(getApplicationContext(), MenuActivity.class);
        //llamamos a la actividad
        startActivity(intento);
        finish();
    }
    public void enviarActivity()
    {
        if (!objTipo.getTip_id().equals("1")) {
                Intent intento = new Intent(getApplicationContext(), TipoDetalleActivity.class);
                intento.putExtra("objTipo", objTipo);
                //llamamos a la actividad
                startActivity(intento);
            }
    }
}