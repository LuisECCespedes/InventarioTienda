package com.example.luis.tiendacontrol;

import android.app.Dialog;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.luis.tiendacontrol.adaptador.ClienteGridAdapter;
import com.example.luis.tiendacontrol.adaptador.MenuGridAdapter;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.modelo.TipoMovimiento;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.data.util.Metodos;
import com.example.luis.tiendacontrol.esquemaSqlLite.BackupRestore;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Delete;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Insert;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ProductoTabla;
import com.example.luis.tiendacontrol.mantenimiento.ClienteActivity;
import com.example.luis.tiendacontrol.mantenimiento.MarcaActivity;
import com.example.luis.tiendacontrol.mantenimiento.MovimientosActivity;
import com.example.luis.tiendacontrol.mantenimiento.MovimientosRealizadosActivity;
import com.example.luis.tiendacontrol.mantenimiento.ProductoActivity;
import com.example.luis.tiendacontrol.mantenimiento.ProductoDetalleActivity;
import com.example.luis.tiendacontrol.mantenimiento.ProductoInventarioActivity;
import com.example.luis.tiendacontrol.mantenimiento.TipoActivity;
import com.example.luis.tiendacontrol.mantenimiento.TipoMovimientoActivity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MenuActivity extends AppCompatActivity {
    private MenuGridAdapter adaptador;
    private List<String> listaItemMenu= new ArrayList<>();

    String item="TipoMovimiento";
    @BindView(R.id.lvActMenu)
    ListView listMenu;

    private Intent intentoActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);
        listaItemMenu.add("Tipo Movimiento");       listaItemMenu.add("Marca Producto");        listaItemMenu.add("Tipo Producto");         listaItemMenu.add("Producto");
        listaItemMenu.add("Cliente/Proveedor");     listaItemMenu.add("Inventario");            listaItemMenu.add("Movimientos Realizados");
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, menu);
        adaptador = new MenuGridAdapter(getApplicationContext(), 0, listaItemMenu);
        listMenu.setAdapter(adaptador);

        listMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int posicion, long id) {
                Object _item = listMenu.getItemAtPosition(posicion);
                switch (_item.toString()) {
                    case "Tipo Movimiento":
                        intentoActivity = new Intent(MenuActivity.this,TipoMovimientoActivity.class);
                        break;

                    case "Marca Producto":

                        intentoActivity = new Intent(MenuActivity.this,MarcaActivity.class);
                        break;

                    case "Tipo Producto":
                        intentoActivity = new Intent(MenuActivity.this,TipoActivity.class);
                        break;

                    case "Producto":
                        intentoActivity = new Intent(MenuActivity.this,ProductoActivity.class);
                        intentoActivity.putExtra("objMarca",new Marca("1","Todos"));
                        intentoActivity.putExtra("objTipo",new Tipo("1","Todos"));
                        break;

                    case "Cliente/Proveedor":
                        intentoActivity = new Intent(MenuActivity.this,ClienteActivity.class);

                        break;
                    case "Inventario":
                        intentoActivity = new Intent(MenuActivity.this,ProductoInventarioActivity.class);
                        intentoActivity.putExtra("objMarca",new Marca("1","Todos"));
                        intentoActivity.putExtra("objTipo",new Tipo("1","Todos"));
                        break;
                    case "Movimientos Realizados":
                        intentoActivity = new Intent(MenuActivity.this,MovimientosActivity.class);
                        intentoActivity.putExtra("objMarca",new Marca("1","Todos"));
                        intentoActivity.putExtra("objTipo",new Tipo("1","Todos"));
                        break;
                }
                startActivity(intentoActivity);
            }
        });

    }

    //region Manejo de menus
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_cerrar_session,menu);
        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.Backup:
                Dialog m = Mensaje.mensajeDialogo(MenuActivity.this,getLayoutInflater(),"Backup","Backup");
                m.show();
                Backup();
                m.dismiss();
                return true;
            case R.id.Restore:

                Dialog n = Mensaje.mensajeDialogo(MenuActivity.this,getLayoutInflater(),"Restore","Restore");
                n.show();
                Restore();
                n.dismiss();
                return true;

            case R.id.cerrarSession:
                cerrarSession();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void Backup() {
        Select.Backup(getApplicationContext());
    }

    private void Restore() {
        //Mensaje.mensajeToas(MenuActivity.this,"A");

        try {
            String nombre = "tienda.txt";
            File tarjeta = Environment.getExternalStorageDirectory();
            File file = new File(tarjeta.getAbsolutePath(),nombre);

            FileInputStream fIn = new FileInputStream(file);
            InputStreamReader archivo = new InputStreamReader(fIn);
            BufferedReader br = new BufferedReader(archivo);
            String linea = br.readLine();
            String todo = "";
            while (linea != null)
            {
                todo = todo+"\n"+linea+"";
                linea = br.readLine();
            }
            Mensaje.mensajeToas(getApplicationContext(),todo);
            br.close();
            archivo.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void cerrarSession() {
        Mensaje.mensajeToas(MenuActivity.this,"Cerrando Sessión");
        SessionPreferences.get(MenuActivity.this).Session(false);
        Intent intentoCerrar = new Intent(MenuActivity.this,LoginActivity.class);
        intentoCerrar.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intentoCerrar);
        finish();
    }
    //endregion
}
