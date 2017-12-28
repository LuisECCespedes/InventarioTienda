package com.example.luis.tiendacontrol;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.luis.tiendacontrol.data.preferencia.SessionPreferences;
import com.example.luis.tiendacontrol.data.util.Mensaje;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.etActLogCon)
    TextView pass;

    Boolean ok ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    public void clickLogin(View view)
    {
        ok = true;
        if (pass.getText().toString().toLowerCase().equals("5847"))
        {
            Intent intentoParam = new Intent(LoginActivity.this,ParametrosActivity.class);
            pass.setText("");
            finish();
            startActivity(intentoParam);
            ok = false;
        }
        // pass
        if (ok && pass.getText().length() == 0) {
            Mensaje.mensajeToas(LoginActivity.this,"Falta Contraseña");
            pass.requestFocus();
            ok = false;
        }

        if (ok )
        {
            if (pass.getText().toString().equals(SessionPreferences.get(LoginActivity.this).GetClave()))
            {
                Mensaje.mensajeToas(LoginActivity.this,"Bienvenida Cristina");
                SessionPreferences.get(LoginActivity.this).Session(true);
                Intent intentoLista = new Intent(LoginActivity.this,MenuActivity.class);
                intentoLista.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                startActivity(intentoLista);
            }else{
                Mensaje.mensajeToas(LoginActivity.this,"Contraseña incorrecta");
            }
        }
    }
}
