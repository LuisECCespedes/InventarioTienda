package com.example.luis.tiendacontrol.mantenimiento;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Marca;
import com.example.luis.tiendacontrol.data.modelo.Producto;
import com.example.luis.tiendacontrol.data.modelo.Tipo;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Controles;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Delete;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Insert;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Select;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Update;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.MarcaTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ProductoTabla;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.TipoTabla;
import com.example.luis.tiendacontrol.seleccionLista.MarcaSelectActivity;
import com.example.luis.tiendacontrol.seleccionLista.TipoSelectActivity;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProductoDetalleActivity extends AppCompatActivity implements View.OnClickListener{

    private final String TEMPORAL_PICTURE_NAME = "temporal.jpg";
    private final String APP_DIRECTORY = "myPictureApp/";
    private final String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;
    private String pathUri = "";

    @BindView(R.id.btActProdEliminarCancelar)
    Button btEliminar;
    @BindView(R.id.btActProdModificarAceptar)
    Button btModificar;

    @BindView(R.id.btActProDetMarca)
    Button btMarca;
    @BindView(R.id.btActProDetTipo)
    Button btTipo;

    @BindView(R.id.etActProdNombre)
    EditText etNombre;
    @BindView(R.id.etActProdPrecio)
    EditText etPrecio;

    @BindView(R.id.ivActProdFoto)
    ImageView imProducto;

    private Marca objMarca,objMarcaDetalle;
    private Tipo objTipo,objTipoDetalle;
    private Producto objProducto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_detalle);
        ButterKnife.bind(this);

        if (getIntent().hasExtra("objMarca"))
        {
            objMarca = (Marca) getIntent().getSerializableExtra("objMarca");
        }

        if (getIntent().hasExtra("objTipo"))
        {
            objTipo = (Tipo) getIntent().getSerializableExtra("objTipo");
        }

        if (getIntent().hasExtra("objProducto"))
        {
            objProducto = (Producto) getIntent().getSerializableExtra("objProducto");
            objTipoDetalle = (Tipo) Select.BuscaRegistro(getApplicationContext(),objProducto.getTip_id(), TipoTabla.TABLA);
            objMarcaDetalle = (Marca) Select.BuscaRegistro(getApplicationContext(),objProducto.getMar_id(), MarcaTabla.TABLA);
            bloquearDesbloquear(!objProducto.getProd_id().equals("0"));
            String precio = String.format("%.2f",objProducto.getProd_precio()).replace(",",".");
            etPrecio.setText(precio+"");
            etNombre.setText(objProducto.getProd_descri());
            imProducto.setEnabled(objProducto.getProd_id().equals("0"));
            pathUri = objProducto.getRutaFoto();

            if (pathUri.length() > 0) {
                imProducto.setImageURI(Uri.parse(objProducto.getRutaFoto()));
            }
        }

        btModificar.setText(objProducto.getProd_id().equals("0") ? "Aceptar":"Modificar");
        btEliminar.setText(objProducto.getProd_id().equals("0") ? "Cancelar":"Eliminar");

        if (getIntent().hasExtra("objMarcaDetalle"))
        {
            objMarcaDetalle = (Marca) getIntent().getSerializableExtra("objMarcaDetalle");
            bloquearDesbloquear(false);
            btModificar.setText("Aceptar");
            btEliminar.setText("Cancelar");
        }

        if (getIntent().hasExtra("objTipoDetalle"))
        {
            objTipoDetalle = (Tipo) getIntent().getSerializableExtra("objTipoDetalle");
        }

        btMarca.setText(objMarcaDetalle.getMar_descri());
        btTipo.setText(objTipoDetalle.getTip_descri());

        btEliminar.setOnClickListener(this);
        btModificar.setOnClickListener(this);

    }

    //region Imagen del producto
    public void seleccionarImagens(View view)
    {

        //Mensaje.mensajeToas(getApplicationContext(),"ClickPerra");
        final CharSequence[] options = {"Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(ProductoDetalleActivity.this);
        builder.setTitle("Elege una Opcion");
        //segun la opcion , los datos de charsequence
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int seleccion) {
                if (options[seleccion] == "Elegir de Galeria") {
                    //Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    Intent selectImagen = new Intent(Intent.ACTION_PICK,  android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    selectImagen.setType("image/*");
                    startActivityForResult(Intent.createChooser(selectImagen,"Seleciona app de imagen"),SELECT_PICTURE);

                }else
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void abrirCamara() {
        File foto = new File(Environment.getExternalStorageDirectory(),MEDIA_DIRECTORY);
        foto.mkdir();

        String pathFoto = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;

        File newFile = new File(pathFoto);

        Intent intenAbrirCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intenAbrirCamara.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
        startActivityForResult(intenAbrirCamara,PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case PHOTO_CODE:
                if (resultCode == RESULT_OK)
                {
                    String dir = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY + File.separator + TEMPORAL_PICTURE_NAME;
                    decodeBitmap(dir);
                }
                break;
            case SELECT_PICTURE:
                if (resultCode == RESULT_OK)
                {
                    Uri path = data.getData();
                    pathUri = path.toString();
                    imProducto.setImageURI(path);

                    //Uri pathFoto = data.getData();
                    //imCliente.setImageURI(pathFoto);
                }
        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        imProducto.setImageBitmap(bitmap);
    }
    //endregion

    @OnClick(R.id.btActProDetMarca)
    public void clickMarca(View view)
    {
        llamarTipoMarca(false);
    }

    @OnClick(R.id.btActProDetTipo)
    public void clickTipo(View view)
    {
        llamarTipoMarca(true);
    }

    @Override
    public void onClick(View view) {
        boolean bsalir = true;
        List<EditText> list = new ArrayList<>();
        list.add(etNombre);
        list.add(etPrecio);
        switch (view.getId())
        {
            case R.id.btActProdModificarAceptar:

                if (btModificar.getText().equals("Aceptar")) {
                    if(!Controles.vacio(getApplicationContext(),list,new String[]{"Ingrese Nombre","Ingrese Precio"}))
                    {
                        //if (!objMarcaDetalle.getMar_id().equals("1") && !objTipoDetalle.getTip_id().equals("1") ) {
                        if (!btTipo.getText().toString().toLowerCase().equals("todos") && !btMarca.getText().toString().toLowerCase().equals("todos") ) {
                            String codigo = objProducto.getProd_id().equals("0") ? SessionPreferences.get(getApplicationContext()).GetProducto() : objProducto.getProd_id();
                            Double precio = Double.parseDouble(new DecimalFormat("#.00").format(Double.parseDouble(etPrecio.getText().toString())).replace(",","."));

                            if(objProducto.getProd_id().equals("0"))
                                Insert.RegistrarRegistro(getApplicationContext(),new Producto(codigo,etNombre.getText().toString(),
                                                precio,objMarcaDetalle.getMar_id().toString(),objTipoDetalle.getTip_id().toString(),pathUri),
                                        ProductoTabla.TABLA);

                            if(!objProducto.getProd_id().equals("0"))
                                Update.ActualizarRegistro(getApplicationContext(),new Producto(codigo,etNombre.getText().toString(),
                                                precio,objMarcaDetalle.getMar_id().toString(),objTipoDetalle.getTip_id().toString(),pathUri),
                                    ProductoTabla.TABLA);

                        } else {
                            Mensaje.mensajeToas(getApplicationContext(),(btMarca.getText().toString().toLowerCase().equals("todos") ? "Indique la marca" : "Indique el tipo" ));
                            bsalir =false;
                        }
                    }else{
                        bsalir =false;
                    }
                } else {
                    bloquearDesbloquear(false);
                    btModificar.setText("Aceptar");
                    btEliminar.setText("Cancelar");
                    bsalir=false;
                }
                break;

            case R.id.btActProdEliminarCancelar:
                if (btEliminar.getText().equals("Eliminar")) {
                    bsalir = false;
                    android.support.v7.app.AlertDialog.Builder dialogo1 = new android.support.v7.app.AlertDialog.Builder(ProductoDetalleActivity.this);
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
                            Delete.EliminarRegistro(getApplicationContext(),objProducto.getProd_id(), ProductoTabla.TABLA);
                            cambiarActividad();
                        }
                    });
                    dialogo1.show();
                } else {
                    bsalir=true;
                }

                break;

        }
        if (bsalir) cambiarActividad();
    }

    @Override
    public void onBackPressed() {
        cambiarActividad();
    }

    void cambiarActividad(){

        Intent intentoActivity = new Intent(getApplicationContext(),ProductoActivity.class);
        intentoActivity.putExtra("objMarca",objMarca);
        intentoActivity.putExtra("objTipo",objTipo);
        startActivity(intentoActivity);

    }

    void bloquearDesbloquear(boolean bblo){
        btMarca.setEnabled(!bblo);
        btTipo.setEnabled(!bblo);
        etPrecio.setEnabled(!bblo);
        etNombre.setEnabled(!bblo);
        imProducto.setEnabled(!bblo);
    }

    void llamarTipoMarca(boolean btipo){
        Intent intTipo = new Intent(getApplicationContext(),btipo ? TipoSelectActivity.class : MarcaSelectActivity.class);
        intTipo.putExtra("objTipoDetalle",objTipoDetalle);
        intTipo.putExtra("objTipo",objTipo);
        intTipo.putExtra("objMarcaDetalle",objMarcaDetalle);
        intTipo.putExtra("objMarca",objMarca);
        intTipo.putExtra("actividad","ProductoDetalle");
        objProducto = new Producto(objProducto.getProd_id(),etNombre.getText().toString(),Double.parseDouble(etPrecio.getText().toString()),objMarcaDetalle.getMar_id(),objTipoDetalle.getTip_id(),pathUri);
        intTipo.putExtra("objProducto",objProducto);
        startActivity(intTipo);
        finish();
    }
}
