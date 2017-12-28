package com.example.luis.tiendacontrol.mantenimiento;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Controles;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Delete;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Insert;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Update;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.MarcaTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.TipoTabla;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TipoDetalleActivity extends AppCompatActivity implements View.OnClickListener {
    private Tipo objTipo;
    @BindView(R.id.llActTipEdit)
    LinearLayout llModificarEliminar;

    @BindView(R.id.btActTipModificarAceptar)
    Button btAceptar;
    @BindView(R.id.btActTipEliminarCancelar)
    Button btCancelar;

    @BindView(R.id.etActTipNombre)
    EditText etNombre;
    Boolean bInser = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tipo_detalle);
        ButterKnife.bind(this);
        this.setTitle("Nuevo");

        if (getIntent().hasExtra("objTipo"))
        {
            objTipo = (Tipo) getIntent().getSerializableExtra("objTipo");
            bInser = false;
            etNombre.setEnabled(false);
            etNombre.setText(objTipo.getTip_descri());
            this.setTitle("Detalle");
        }
        btAceptar.setText(bInser?"Aceptar":"Modificar");
        btCancelar.setText(bInser?"Cancelar":"Eliminar");
        btAceptar.setOnClickListener(this);
        btCancelar.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        boolean bsalir = true;
        List<EditText> list = new ArrayList<>();
        list.add(etNombre);
        switch (view.getId())
        {
            case R.id.btActTipModificarAceptar:
                if (btAceptar.getText().equals("Aceptar")) {
                    if(!Controles.vacio(getApplicationContext(),list,new String[]{"Ingrese Nombre"}))
                    {
                        String codigo = bInser ? SessionPreferences.get(getApplicationContext()).GetTipo() : objTipo.getTip_id();
                        if(bInser)Insert.RegistrarRegistro(getApplicationContext(),new Tipo(codigo,etNombre.getText().toString()), TipoTabla.TABLA);
                        if(!bInser)Update.ActualizarRegistro(getApplicationContext(),new Tipo(codigo,etNombre.getText().toString()), TipoTabla.TABLA);
                    }else{
                        bsalir =false;
                    }
                } else {
                    btAceptar.setText("Aceptar");
                    btCancelar.setText("Cancelar");
                    etNombre.setEnabled(true);
                    bsalir = false;
                }
                break;

            case R.id.btActTipEliminarCancelar:

                if (btCancelar.getText().equals("Eliminar")) {
                    bsalir = false;
                    android.support.v7.app.AlertDialog.Builder dialogo1 = new android.support.v7.app.AlertDialog.Builder(TipoDetalleActivity.this);
                    dialogo1.setTitle("Importante");
                    dialogo1.setMessage("¿Segura que quieres eliminar?");
                    dialogo1.setCancelable(false);

                    dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // si se cancela y reimprime , restaurar los valores

                        }
                    });

                    dialogo1.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Delete.EliminarRegistro(getApplicationContext(),objTipo.getTip_id(), TipoTabla.TABLA);
                            Salir();
                        }
                    });
                    dialogo1.show();
                }

                break;
        }
        if (bsalir) Salir();
    }

    void Salir()
    {
        // Salir de Cliente
        Intent intento = new Intent(getApplicationContext(), TipoActivity.class);
        //llamamos a la actividad
        startActivity(intento);
        finish();
    }
}
