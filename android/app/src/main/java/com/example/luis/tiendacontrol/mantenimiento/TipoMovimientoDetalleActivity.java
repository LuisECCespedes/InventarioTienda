package com.example.luis.tiendacontrol.mantenimiento;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.TipoMovimiento;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Controles;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Delete;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Insert;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Update;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.MarcaTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.TipoMovimientoTabla;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipoMovimientoDetalleActivity extends AppCompatActivity implements View.OnClickListener{
    private TipoMovimiento objTipoMovimiento;

    @BindView(R.id.btActTipMovModificarAceptar)
    Button btAceptar;
    @BindView(R.id.btActTipMovEliminarCancelar)
    Button btEliminar;

    @BindView(R.id.etActTipMovNom)
    EditText etNombre;

    @BindView(R.id.radio_ingreso)
    RadioButton rbIngreso;
    @BindView(R.id.radio_salida)
    RadioButton rbSalida;
    @BindView(R.id.radioGrup)
    RadioGroup radioGrup;

    Boolean bInser = true;

    private String vTipoMovimiento = "I";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_movimiento_detalle);
        ButterKnife.bind(this);
        rbIngreso.setChecked(true);
        this.setTitle("Nuevo");

        if (getIntent().hasExtra("tipMov")) {
            vTipoMovimiento = getIntent().getStringExtra("tipMov");
            rbIngreso.setChecked(vTipoMovimiento.equals("I"));
            rbSalida.setChecked(vTipoMovimiento.equals("S"));
        }

        if (getIntent().hasExtra("objTipoMovimiento"))
        {
            objTipoMovimiento = (TipoMovimiento) getIntent().getSerializableExtra("objTipoMovimiento");
            bInser = false;
            etNombre.setEnabled(false);
            etNombre.setText(objTipoMovimiento.getTip_mov_descri());
            rbSalida.setEnabled(false);
            rbIngreso.setEnabled(false);
            vTipoMovimiento =objTipoMovimiento.getTip_mov_ing_sal();
            rbIngreso.setChecked(vTipoMovimiento.equals("I"));
            rbSalida.setChecked(vTipoMovimiento.equals("S"));
            this.setTitle("Detalle");
        }
        btAceptar.setText(bInser?"Aceptar":"Modificar");
        btEliminar.setText(bInser?"Cancelar":"Eliminar");
        btAceptar.setOnClickListener(this);
        btEliminar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        boolean bsalir = true;
        List<EditText> list = new ArrayList<>();
        list.add(etNombre);
        switch (view.getId())
        {
            case R.id.btActTipMovModificarAceptar:
                if (btAceptar.getText().equals("Aceptar")) {
                    if(!Controles.vacio(getApplicationContext(),list,new String[]{"Ingrese Nombre"}))
                    {
                        String codigo = bInser ? SessionPreferences.get(getApplicationContext()).GetTipoMovimiento() : objTipoMovimiento.getTip_mov_id();
                        if(bInser)Insert.RegistrarRegistro(getApplicationContext(),new TipoMovimiento(codigo,etNombre.getText().toString(),rbIngreso.isChecked() ? "I" : "S"), TipoMovimientoTabla.TABLA);
                        if(!bInser) Update.ActualizarRegistro(getApplicationContext(),new TipoMovimiento(codigo,etNombre.getText().toString(),rbIngreso.isChecked() ? "I" : "S"), TipoMovimientoTabla.TABLA);
                    }else{
                        bsalir =false;
                    }

                } else {
                    btAceptar.setText("Aceptar");
                    btEliminar.setText("Cancelar");
                    etNombre.setEnabled(true);
                    rbSalida.setEnabled(true);
                    rbIngreso.setEnabled(true);
                    bsalir = false;
                }
                break;

            case R.id.btActTipMovEliminarCancelar:
                if (btEliminar.getText().equals("Eliminar")) {
                    bsalir = false;
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(TipoMovimientoDetalleActivity.this);
                    dialogo1.setTitle("Importante");
                    dialogo1.setMessage("¿Segura que quieres eliminar?");
                    dialogo1.setCancelable(false);

                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {


                        }
                    });

                    dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Delete.EliminarRegistro(getApplicationContext(),objTipoMovimiento.getTip_mov_id(), TipoMovimientoTabla.TABLA);
                            Salir();
                        }
                    });
                    dialogo1.show();
                }

                break;
        }
        if (bsalir) Salir();
    }

    @Override
    public void onBackPressed() {
        Salir();
    }

    void Salir()
    {
        // Salir de Cliente
        Intent intento = new Intent(getApplicationContext(), TipoMovimientoActivity.class);
        intento.putExtra("tipMov",vTipoMovimiento);
        //llamamos a la actividad
        intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intento);
    }
}
