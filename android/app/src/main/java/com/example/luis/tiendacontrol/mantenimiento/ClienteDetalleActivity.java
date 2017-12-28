package com.example.luis.tiendacontrol.mantenimiento;

import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.luis.tiendacontrol.R;
import com.example.luis.tiendacontrol.data.modelo.Cliente;
import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Controles;
import com.example.luis.tiendacontrol.data.util.Mensaje;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Delete;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Insert;
import com.example.luis.tiendacontrol.esquemaSqlLite.crud.Update;
import com.example.luis.tiendacontrol.esquemaSqlLite.tablas.ClienteTabla;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.CAMERA;

public class ClienteDetalleActivity extends AppCompatActivity implements View.OnClickListener{
    private Cliente objCliente;

    private final String TEMPORAL_PICTURE_NAME = "temporal.jpg";
    private final String APP_DIRECTORY = "myPictureApp/";
    private final String MEDIA_DIRECTORY = APP_DIRECTORY + "media";
    private final int PHOTO_CODE = 100;
    private final int SELECT_PICTURE = 200;
    private String pathUri = "";

    @BindView(R.id.llActCliEdit)
    LinearLayout llModificarEliminar;

    @BindView(R.id.btActClieEliminarCancelar)
    Button btEliminar;
    @BindView(R.id.btActClieModificarAceptar)
    Button btModificar;

    @BindView(R.id.etActClieNombre)
    EditText etNombre;
    @BindView(R.id.etActClieNumCel)
    EditText etNumClie;
    @BindView(R.id.etActClieReferencia)
    EditText etRefe;

    @BindView(R.id.rbActDetClieCliente)
    RadioButton rbCliente;
    @BindView(R.id.rbActDetClieProveedor)
    RadioButton rbProveedor;

    @BindView(R.id.ivActClieFoto)
    ImageView imCliente;
    Boolean bInser = true;
    private String vcliente = "C";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_detalle);
        ButterKnife.bind(this);
        this.setTitle("Nuevo");

        if (getIntent().hasExtra("cliente"))
        {
            vcliente = getIntent().getStringExtra("cliente");
            rbCliente.setChecked(vcliente.equals("C"));
            rbProveedor.setChecked(!vcliente.equals("C"));
        }
        if (getIntent().hasExtra("objCliente"))
        {
            objCliente = (Cliente) getIntent().getSerializableExtra("objCliente");
            bInser = false;
            etRefe.setEnabled(bInser);              etNombre.setEnabled(bInser);
            etNumClie.setEnabled(bInser);           imCliente.setEnabled(bInser);
            rbProveedor.setEnabled(bInser);         rbCliente.setEnabled(bInser);
            etRefe.setText(objCliente.getClie_referencia());
            etNombre.setText(objCliente.getClie_nombre());
            etNumClie.setText(objCliente.getClie_num_tel());
            pathUri = objCliente.getClie_rut_foto();

            if (pathUri.length() > 0) {
                imCliente.setImageURI(Uri.parse(objCliente.getClie_rut_foto()));
            }
            this.setTitle("Detalle");
        }

        //llModificarEliminar.setVisibility(bInser ? View.INVISIBLE : View.VISIBLE);
        btModificar.setText(bInser?"Aceptar":"Modificar");
        btEliminar.setText(bInser?"Cancelar":"Eliminar");
        btEliminar.setOnClickListener(this);
        btModificar.setOnClickListener(this);

    }
    //region Imagen del Cliente
    public void seleccionarImagens(View view)
    {

        //Mensaje.mensajeToas(getApplicationContext(),"ClickPerra");
        final CharSequence[] options = {"Elegir de Galeria","Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(ClienteDetalleActivity.this);
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
                    imCliente.setImageURI(path);

                    Mensaje.mensajeToas(getApplicationContext(),path);
                    //Uri pathFoto = data.getData();
                    //imCliente.setImageURI(pathFoto);
                }
        }
    }

    private void decodeBitmap(String dir) {
        Bitmap bitmap;
        bitmap = BitmapFactory.decodeFile(dir);
        imCliente.setImageBitmap(bitmap);

    }
    //endregion

    @Override
    public void onClick(View view) {
        boolean bsalir = true;
        List<EditText> list = new ArrayList<>();
        list.add(etNombre);
        switch (view.getId())
        {
            case R.id.btActClieModificarAceptar:

                if (btModificar.getText().equals("Aceptar")) {
                    if(!Controles.vacio(getApplicationContext(),list,new String[]{"Ingrese Nombre"}))
                    {
                        String codigo = bInser ? SessionPreferences.get(getApplicationContext()).GetCliente() : objCliente.getClie_id();

                        if(bInser)Insert.RegistrarRegistro(getApplicationContext(),new Cliente(codigo,etNombre.getText().toString(),etNumClie.getText().toString(),etRefe.getText().toString(),pathUri,rbCliente.isChecked() ? "C" : "P"), ClienteTabla.TABLA);
                        if(!bInser)Update.ActualizarRegistro(getApplicationContext(),new Cliente(codigo,etNombre.getText().toString(),etNumClie.getText().toString(),etRefe.getText().toString(),pathUri,rbCliente.isChecked() ? "C" : "P"), ClienteTabla.TABLA);
                    }else{
                        bsalir =false;
                    }
                } else {
                    btModificar.setText("Aceptar");     btEliminar.setText("Cancelar");
                    etRefe.setEnabled(true);            etNombre.setEnabled(true);
                    etNumClie.setEnabled(true);         imCliente.setEnabled(true);
                    rbProveedor.setEnabled(true);     rbCliente.setEnabled(true);
                    bsalir=false;
                }
                break;

            case R.id.btActClieEliminarCancelar:
                if (btEliminar.getText().equals("Eliminar")) {
                    bsalir = false;
                    android.support.v7.app.AlertDialog.Builder dialogo1 = new android.support.v7.app.AlertDialog.Builder(ClienteDetalleActivity.this);
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
                            Delete.EliminarRegistro(getApplicationContext(),objCliente.getClie_id(),ClienteTabla.TABLA);
                            Salir();
                        }
                    });
                    dialogo1.show();
                } else {
                }

                break;

        }
        if (bsalir) Salir();
    }
    public void onRadioClienteProveedorDetalle(View view)
    {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbActClieCliente:
                if (checked)
                    // Listar los tipos Ingreso
                    break;

            case R.id.rbActClieProveedor:
                if (checked)
                    // Listar los tipos Salida
                    break;
        }
    }
    void Salir()
    {   // Salir de Cliente
        Intent intento = new Intent(getApplicationContext(), ClienteActivity.class);
        intento.putExtra("cliente",vcliente);
        //llamamos a la actividad
        startActivity(intento);
        finish();
    }
}
