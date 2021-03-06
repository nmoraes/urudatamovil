package com.proyecto.urudatamovil.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.proyecto.urudatamovil.R;
import com.proyecto.urudatamovil.objects.PeticionWebClient;

public class PeticionDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_peticion_detail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_peticion_detail, menu);
        return true;
    }
    @Override
    public void onStart() {
        super.onStart();
        mostrarDetalles();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void mostrarDetalles(){
        Intent i = this.getIntent();
        PeticionWebClient peticion = i.getParcelableExtra("peticion");
        TextView editText;

        Long petId=peticion.getIdPeticion();
        editText = (TextView)findViewById(R.id.value_id);
        if (petId!=null) {
            editText.setText(petId.toString());
        }

        String fechaIni=peticion.getInicio();
        editText = (TextView) findViewById(R.id.value_fecha_inicio);
        if (fechaIni!=null) {
            editText.setText(fechaIni);
        }else {
            editText.setText("");
        }

        String fechafin =peticion.getFin();
        editText = (TextView) findViewById(R.id.value_fecha_fin);
        if (fechafin!=null) {
            editText.setText(fechafin);
        }else {
            editText.setText("");
        }

        String desc=peticion.getDescripcion();
        editText = (TextView) findViewById(R.id.value_descripcion);
        if (desc!=null) {
            editText.setText(desc);
        }else {
            editText.setText("");
        }

        String estado=peticion.getEstado();
        editText = (TextView) findViewById(R.id.value_estado);
        if (estado!=null) {
            if (estado.equalsIgnoreCase("null")){
                editText.setText("Esperando Aprobación");
            }else {
                editText.setText(estado);
            }
        }else {
            editText.setText("Esperando Aprobación");
        }
    }
}
