package com.example.luis.tiendacontrol.mantenimiento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.luis.tiendacontrol.MenuActivity;
import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.adaptador.TipoMovimientoGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.TipoMovimiento;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipoMovimientoActivity extends AppCompatActivity {
    @BindView(R.id.gvActTipoMovimientoMan)
    ListView gvTipoMovimiento;

    @BindView(R.id.rbActTipMovIngresoMan)
    RadioButton rbIngreso;
    @BindView(R.id.rbActTipMovSalidaMan)
    RadioButton rbSalida;

    private TipoMovimientoGridAdapter adaptador;
    private List<TipoMovimiento> listTipoMovimientoMan= new ArrayList<>();;
    private TipoMovimiento objTipoMovimiento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_movimiento);

        ButterKnife.bind(this);
        this.setTitle("Tipo Movimiento");
        rbIngreso.setChecked(true);
        if (getIntent().hasExtra("tipMov")) {
            String VingSal = getIntent().getStringExtra("tipMov");
            rbIngreso.setChecked(VingSal.equals("I"));
            rbSalida.setChecked(VingSal.equals("S"));
        }
        cargarLista();

        gvTipoMovimiento.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objTipoMovimiento = listTipoMovimientoMan.get(i);

                if (!objTipoMovimiento.getTip_mov_id().equals("1") && !objTipoMovimiento.getTip_mov_id().equals("2")) {
                    // Modificar Cliente
                    Intent intento = new Intent(getApplicationContext(), TipoMovimientoDetalleActivity.class);
                    intento.putExtra("objTipoMovimiento", objTipoMovimiento);
                    //llamamos a la actividad
                    startActivity(intento);
                }
            }
        });
    }

    private void cargarLista() {
        listTipoMovimientoMan.clear();
        //Select.SelectTipoMovimiento(getApplicationContext(), listTipoMovimientoMan);
        Select.SelectTipoMovimiento(getApplicationContext(), listTipoMovimientoMan,rbIngreso.isChecked() ? "I" : "S");
        adaptador = new TipoMovimientoGridAdapter(getApplicationContext(), 0, listTipoMovimientoMan);
        gvTipoMovimiento.setAdapter(adaptador);
    }

    @Override
    public void onBackPressed() {
        // Salir de tipo movimiento
        Intent intento = new Intent(getApplicationContext(), MenuActivity.class);
        //llamamos a la actividad
        startActivity(intento);
        finish();
    }
    public void onRadioIngresoSalidaPrincipal(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbActTipMovIngresoMan:
                cargarLista();
                // Listar los tipos Ingreso
                break;

            case R.id.rbActTipMovSalidaMan:
                cargarLista();
                // Listar los tipos Salida
                break;
        }
    }
    public void clickButtonTipoMovimiento(View view)
    {   // Nuevo Cliente
        Intent intento = new Intent(getApplicationContext(), TipoMovimientoDetalleActivity.class);
        intento.putExtra("tipMov",rbIngreso.isChecked()?"I":"S");
        //llamamos a la actividad
        startActivity(intento);
    }
}
