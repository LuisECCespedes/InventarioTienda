package com.example.luis.tiendacontrol.mantenimiento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.RadioButton;

import com.example.luis.tiendacontrol.MenuActivity;
import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.adaptador.ClienteGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemSelected;

public class ClienteActivity extends AppCompatActivity {
    @BindView(R.id.gvActCliente)
    ListView gvCliente;
    @BindView(R.id.rbActClieCliente)
    RadioButton rbCliente;
    @BindView(R.id.rbActClieProveedor)
    RadioButton rbProveedor;

    private ClienteGridAdapter adaptador;
    private List<Cliente> listaCliente= new ArrayList<>();;
    private Cliente objCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente);
        ButterKnife.bind(this);
        this.setTitle("Clientes/Proveedores");

        rbCliente.setChecked(true);
        if (getIntent().hasExtra("cliente"))
        {
            rbCliente.setChecked(getIntent().getStringExtra("cliente").equals("C"));
            rbProveedor.setChecked(!getIntent().getStringExtra("cliente").equals("C"));
        }

        cargarLista();

        gvCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objCliente = listaCliente.get(i);
                if (!objCliente.getClie_id().equals("1") && !objCliente.getClie_id().equals("2")){
                    // Nuevo Cliente
                    Intent intento = new Intent(ClienteActivity.this, ClienteDetalleActivity.class);
                    intento.putExtra("objCliente",objCliente);
                    intento.putExtra("cliente",rbCliente.isChecked() ? "C" : "P");
                    //llamamos a la actividad
                    intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    finish();
                    startActivity(intento);
                }
            }
        });
    }

    public void onRadioClienteProveedor(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbActClieCliente:
                cargarLista();
                    // Listar los tipos Ingreso
                    break;

            case R.id.rbActClieProveedor:
                cargarLista();
                    // Listar los tipos Salida
                    break;
        }
    }
    private void cargarLista() {
        listaCliente.clear();
        Select.SelectCliente(getApplicationContext(),listaCliente,rbCliente.isChecked() ? "C":"P");
        adaptador = new ClienteGridAdapter(getApplicationContext(), 0, listaCliente);
        gvCliente.setAdapter(adaptador);
    }

    public void clickButtonCliente(View view)    {
        // Nuevo Cliente
        Intent intento = new Intent(ClienteActivity.this, ClienteDetalleActivity.class);
        intento.putExtra("cliente",rbCliente.isChecked() ? "C" : "P");
        //llamamos a la actividad
        intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intento);
    }

    @Override
    public void onBackPressed() {
        // Salir de Cliente
        Intent intento = new Intent(ClienteActivity.this, MenuActivity.class);
        //llamamos a la actividad
        intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intento);
    }
}
