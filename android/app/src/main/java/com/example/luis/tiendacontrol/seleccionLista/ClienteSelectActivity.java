package com.example.luis.tiendacontrol.seleccionLista;

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
import com.example.luis.tiendacontrol.adaptador.ClienteGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.modelo.Inventario;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.modelo.TipoMovimiento;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.mantenimiento.ClienteActivity;
import com.example.luis.tiendacontrol.mantenimiento.ClienteDetalleActivity;
import com.example.luis.tiendacontrol.mantenimiento.ProductoInventarioRegistroActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ClienteSelectActivity extends AppCompatActivity {
    @BindView(R.id.gvActCliSelect)
    ListView gvCliente;

    @BindView(R.id.rbActCliProveedorSel)
    RadioButton rbProveedor;

    @BindView(R.id.rbActCliClienteSel)
    RadioButton rbCliente;

    private ClienteGridAdapter adaptador;
    private List<Cliente> listaCliente= new ArrayList<>();;
    private Cliente objCliente;

    private Inventario objInventario;
    private Tipo objTipo;
    private Marca objMarca;
    private TipoMovimiento objTipoMovimiento;
    private Producto objProducto;
    String vcant="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_select);

        ButterKnife.bind(this);
        this.setTitle("Clientes/Proveedor");

        if (getIntent().hasExtra("objInventario"))
        {
            objInventario = (Inventario) getIntent().getSerializableExtra("objInventario");
        }

        if (getIntent().hasExtra("objMarca"))
        {
            objMarca = (Marca) getIntent().getSerializableExtra("objMarca");
        }

        if (getIntent().hasExtra("objTipo"))
        {
            objTipo = (Tipo) getIntent().getSerializableExtra("objTipo");
        }

        if (getIntent().hasExtra("objCliente"))
        {
            objCliente = (Cliente) getIntent().getSerializableExtra("objCliente");
        }

        if (getIntent().hasExtra("objTipoMovimiento"))
        {
            objTipoMovimiento = (TipoMovimiento) getIntent().getSerializableExtra("objTipoMovimiento");
        }

        if (getIntent().hasExtra("objProducto"))
        {
            objProducto = (Producto) getIntent().getSerializableExtra("objProducto");
        }

        if (getIntent().hasExtra("cantidad"))
        {
            vcant = getIntent().getStringExtra("cantidad");
        }

        rbCliente.setChecked(objCliente.getClie_tipo().equals("C"));
        rbProveedor.setChecked(objCliente.getClie_tipo().equals("P"));
        cargarLista();
        gvCliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                objCliente = listaCliente.get(i);
                cambiarActvidad();
            }
        });
    }

    private void cargarLista() {
        listaCliente.clear();
        Select.SelectCliente(getApplicationContext(),listaCliente,rbCliente.isChecked()?"C":"P");
        adaptador = new ClienteGridAdapter(getApplicationContext(), 0, listaCliente);
        gvCliente.setAdapter(adaptador);
    }


    private void cambiarActvidad()
    {   // Salir
        Intent intento = new Intent(getApplicationContext(), ProductoInventarioRegistroActivity.class);
        intento.putExtra("objInventario",objInventario);
        intento.putExtra("objMarca",objMarca);
        intento.putExtra("objTipo",objTipo);
        intento.putExtra("objCliente",objCliente);
        intento.putExtra("objTipoMovimiento",objTipoMovimiento);
        intento.putExtra("objProducto",objProducto);
        intento.putExtra("cantidad",vcant);
        intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intento);
    }

    @Override
    public void onBackPressed() {
        cambiarActvidad();
    }

    public void onRadioClienteProveedorSelect(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbActCliClienteSel:
                cargarLista();
                // Listar los tipos Ingreso
                break;

            case R.id.rbActCliProveedorSel:
                cargarLista();
                // Listar los tipos Salida
                break;
        }
    }
}
