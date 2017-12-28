package com.example.luis.tiendacontrol;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Mensaje;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ParametrosActivity extends AppCompatActivity implements View.OnClickListener {
    // tomamos el control y lo asignamos a la variable
    @BindView(R.id.txtClave)
    TextView esquema;
    @BindView(R.id.txtTextoPrueba)
    TextView txtTam;
    @BindView(R.id.etAcLogParTam)
    TextView txtSize;

    private Button btnGuardar,btnMas,btnMenos;
    private Float nSize;
    private SessionPreferences session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parametros);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        ButterKnife.bind(this);

        // tomamos el control y lo asignamos a la variable
        btnGuardar= (Button)findViewById(R.id.btnActLogParGuardar);
        btnMenos= (Button)findViewById(R.id.btnMas);
        btnMas= (Button)findViewById(R.id.btnMenos);

        session = SessionPreferences.get(getApplicationContext());
        nSize = session.getLetraSize();
        txtTam.setTextSize(nSize);
        txtSize.setText(""+nSize);

        // tomamos el control y lo asignamos a la variable
        btnGuardar= (Button)findViewById(R.id.btnActLogParGuardar);
        btnMenos= (Button)findViewById(R.id.btnMas);
        btnMas= (Button)findViewById(R.id.btnMenos);

        esquema.setText(SessionPreferences.get(getApplicationContext()).GetClave());

        // mencionamos que utilizaremos los botones
        btnGuardar.setOnClickListener(this);
        btnMas.setOnClickListener(this);
        btnMenos.setOnClickListener(this);

    }

    // controlador de botones
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            //boton Login
            case R.id.btnActLogParGuardar:
                // mostrar mensaje
                final Dialog dialogActivity = Mensaje.mensajeDialogo(ParametrosActivity.this,getLayoutInflater(),"Guardando Parametros","Espere");
                dialogActivity.show();
                session.SetClave(esquema.getText().toString());
                session.setLetraSize(nSize);

                Intent intentoLista = new Intent(ParametrosActivity.this,LoginActivity.class);
                intentoLista.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(intentoLista);
                dialogActivity.cancel();
                break;

            case R.id.btnMas :
                if (nSize<51) {
                    nSize++;
                    txtTam.setTextSize(nSize);
                    txtSize.setText(""+nSize);
                }
                break;

            case R.id.btnMenos :

                if (nSize>14) {
                    nSize--;
                    txtTam.setTextSize(nSize);
                    txtSize.setText(""+nSize);
                }
                break;
        }
    }
}
