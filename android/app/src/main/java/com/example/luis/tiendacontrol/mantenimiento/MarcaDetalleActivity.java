package com.example.luis.tiendacontrol.mantenimiento;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Controles;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Delete;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Insert;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Update;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ClienteTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.MarcaTabla;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MarcaDetalleActivity extends AppCompatActivity implements View.OnClickListener{
    private Marca objMarca;

    @BindView(R.id.llActMarEdit)
    LinearLayout llModificarEliminar;

    @BindView(R.id.btActMarModificarAceptar)
    Button btAceptar;
    @BindView(R.id.btActMarEliminarCancelar)
    Button btEliminar;

    @BindView(R.id.etActMarNom)
    EditText etNombre;

    Boolean bInser = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marca_detalle);
        ButterKnife.bind(this);
        this.setTitle("Nuevo");

        if (getIntent().hasExtra("objMarca"))
        {
            objMarca = (Marca) getIntent().getSerializableExtra("objMarca");
            bInser = false;
            etNombre.setEnabled(false);
            etNombre.setText(objMarca.getMar_descri());
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
            case R.id.btActMarModificarAceptar:
                if (btAceptar.getText().equals("Aceptar")) {
                    if(!Controles.vacio(getApplicationContext(),list,new String[]{"Ingrese Nombre"}))
                    {
                        String codigo = bInser ? SessionPreferences.get(getApplicationContext()).GetMarca() : objMarca.getMar_id();
                        if(bInser)Insert.RegistrarRegistro(getApplicationContext(),new Marca(codigo,etNombre.getText().toString()), MarcaTabla.TABLA);
                        if(!bInser)Update.ActualizarRegistro(getApplicationContext(),new Marca(codigo,etNombre.getText().toString()), MarcaTabla.TABLA);
                    }else{
                        bsalir =false;
                    }

                } else {
                    btAceptar.setText("Aceptar");
                    btEliminar.setText("Cancelar");
                    etNombre.setEnabled(true);
                    bsalir = false;
                }
                break;

            case R.id.btActMarEliminarCancelar:
                if (btEliminar.getText().equals("Eliminar")) {
                    bsalir = false;
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(MarcaDetalleActivity.this);
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
                            Delete.EliminarRegistro(getApplicationContext(),objMarca.getMar_id(), MarcaTabla.TABLA);
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
        Intent intento = new Intent(getApplicationContext(), MarcaActivity.class);
        //llamamos a la actividad
        startActivity(intento);
        finish();
    }
}
