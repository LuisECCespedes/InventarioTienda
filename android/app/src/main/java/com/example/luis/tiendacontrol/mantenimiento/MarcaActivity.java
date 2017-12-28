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
import com.example.luis.tiendacontrol.adaptador.MarcaGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarcaActivity extends AppCompatActivity {
    @BindView(R.id.gvActMarca)
    ListView gvMarca;
    @BindView(R.id.fabActMarcaNuevo)
    FloatingActionButton fbMarca;
    private MarcaGridAdapter adaptador;
    private List<Marca> listMarca= new ArrayList<>();
    private Marca objMarca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca);
        ButterKnife.bind(this);
        this.setTitle("Marca Producto");
        cargarLista();

        gvMarca.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objMarca = listMarca.get(i);
                enviarActivity();
            }
        });
    }

    private void cargarLista() {
        Select.SelectMarca(getApplicationContext(),listMarca);
        adaptador = new MarcaGridAdapter(getApplicationContext(), 0, listMarca);
        gvMarca.setAdapter(adaptador);

    }

    public void clickButtonMarca(View view)    {
        // Nuevo Cliente
        Intent intento = new Intent(getApplicationContext(), MarcaDetalleActivity.class);
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
        if(!objMarca.getMar_id().equals("1")) {
            // Modificar Cliente
            Intent intento = new Intent(getApplicationContext(), MarcaDetalleActivity.class);
            intento.putExtra("objMarca", objMarca);
            //llamamos a la actividad
            startActivity(intento);
        }
    }
}
