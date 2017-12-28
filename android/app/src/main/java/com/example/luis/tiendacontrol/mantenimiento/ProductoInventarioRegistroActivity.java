package com.example.luis.tiendacontrol.mantenimiento;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.modelo.Inventario;
import com.example.luis.tiendacontrol.data.modelo.Kardex;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.modelo.TipoMovimiento;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Insert;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.KardexTabla;
import com.example.luis.tiendacontrol.seleccionLista.ClienteSelectActivity;
import com.example.luis.tiendacontrol.seleccionLista.TipoMovimientoSelectActivity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductoInventarioRegistroActivity extends AppCompatActivity {
    @BindView(R.id.btActProInvRegAgregar)
    Button btAgregar;
    @BindView(R.id.btActProInvRegCancelar)
    Button btCancelar;
    @BindView(R.id.btActProInvRegClienteProveedor)
    Button btCliente;
    @BindView(R.id.btActProInvRegMovimiento)
    Button btMovimiento;
    @BindView(R.id.btActProInvRegMas)
    Button btMas;
    @BindView(R.id.btActProInvRegMenos)
    Button btMenos;

    @BindView(R.id.tvActProInvRegClienteProveedor)
    TextView tvCliente;
    @BindView(R.id.tvActProInvRegMovimiento)
    TextView tvMovimiento;
    @BindView(R.id.tvActProInvRegNombreProducto)
    TextView tvNombre;

    @BindView(R.id.etActProInvRegObservacion)
    EditText tvObservacion;
    @BindView(R.id.etActProInvRegCantidad)
    EditText tvCantidad;
    @BindView(R.id.etActProInvRegPrecio)
    EditText tvPrecio;

    private Inventario objInventario;
    private Tipo objTipo;
    private Marca objMarca;
    private Cliente objCliente;
    private TipoMovimiento objTipoMovimiento;
    private Producto objProducto;
    private boolean bsalir = false;
    private String vcant="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_inventario_registro);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("cantidad"))
        {
            vcant = getIntent().getStringExtra("cantidad");
        }

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

        // cargar datos a los controles
        tvCantidad.setText(vcant);
        tvCliente.setText(objCliente.getClie_nombre());
        tvMovimiento.setText(objTipoMovimiento.getTip_mov_descri());
        tvNombre.setText(objInventario.getProd_descri());

        tvPrecio.setText(objProducto.getProd_precio()+"");
    }


    @OnClick(R.id.btActProInvRegMovimiento)
    public void clickTipoMovimiento(View view)
    {
        llamarActividad(TipoMovimientoSelectActivity.class);
    }

    @OnClick(R.id.btActProInvRegClienteProveedor)
    public void clickCliente(View view)
    {
        llamarActividad(ClienteSelectActivity.class);
    }

    void llamarActividad(Class<?> activity){
        Intent intento = new Intent(getApplicationContext(),activity);
        intento.putExtra("objInventario",objInventario);
        intento.putExtra("objMarca",objMarca);
        intento.putExtra("objTipo",objTipo);
        intento.putExtra("objProducto",objProducto);
        intento.putExtra("cantidad",tvCantidad.getText().toString());
        if (!bsalir) {
            intento.putExtra("objCliente",objCliente);
            intento.putExtra("objTipoMovimiento",objTipoMovimiento);
            objProducto.setProd_precio(Double.parseDouble(new DecimalFormat("#.00").format(Double.parseDouble(tvPrecio.getText().toString())).replace(",",".")));
        }
        intento.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        finish();
        startActivity(intento);
    }

    @Override
    public void onBackPressed() {
        bsalir=true;
        llamarActividad(ProductoInventarioDetalleActivity.class);
    }

    @OnClick(R.id.btActProInvRegAgregar)
    public void clickRegistrar(View view)
    {
        Date myDate = new Date();
        String codigoKardex = SessionPreferences.get(getApplicationContext()).GetKardex(),
                hora = new SimpleDateFormat("HH:mm").format(myDate) , fecha = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(myDate);

        Double precio =Double.parseDouble(new DecimalFormat("#.00").format(Double.parseDouble(tvPrecio.getText().toString())).replace(",","."));
        int cantidad = Integer.parseInt(tvCantidad.getText().toString());

        Kardex objKarex = new Kardex(codigoKardex,objCliente.getClie_id(),objProducto.getProd_id(),precio,cantidad,tvObservacion.getText().toString(),
                fecha,hora,objTipoMovimiento.getTip_mov_ing_sal(),objTipoMovimiento.getTip_mov_descri());

        //Registramos
        Insert.RegistrarRegistro(getApplicationContext(),objKarex, KardexTabla.TABLA);

        // Actividad detalle
        llamarActividad(ProductoInventarioActivity.class);
    }
    @OnClick(R.id.btActProInvRegCancelar)
    public void clickCancelar(View view)
    {
        llamarActividad(ProductoInventarioDetalleActivity.class);
    }

    public void ClickMasMenos(View view) {
        // Is the button now checked?
        //boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.btActProInvRegMenos:
                AumentarDisminuir(false);
                // Listar los tipos Ingreso
                break;

            case R.id.btActProInvRegMas:
                AumentarDisminuir(true);
                // Listar los tipos Salida
                break;
        }
    }

    private void AumentarDisminuir(boolean bmas) {
        int num = Integer.parseInt(tvCantidad.getText().toString());
        if (bmas) {num++;} else {num--;}
        num = (num < 1 ? 1 : num);
        tvCantidad.setText(num + "");
    }
}
