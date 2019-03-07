package com.ajedrez.Vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import com.ajedrez.R;

public class Principal extends AppCompatActivity {

    Spinner listaBlanco;
    Spinner listaNegro;
    ImageView jugar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        listaBlanco = findViewById(R.id.spinner);
        listaNegro = findViewById(R.id.spinner2);
        jugar = findViewById(R.id.jugar);

        ArrayAdapter<CharSequence> adapterBlanco = ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_dropdown_item);
        listaBlanco.setAdapter(adapterBlanco);

        ArrayAdapter<CharSequence> adapterNegro = ArrayAdapter.createFromResource(this,R.array.opciones,android.R.layout.simple_spinner_dropdown_item);
        listaNegro.setAdapter(adapterNegro);


        jugar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Principal.this,MainActivity.class);
                intent.putExtra("jugadorblanco",listaBlanco.getSelectedItem().toString());
                intent.putExtra("jugadornegro",listaNegro.getSelectedItem().toString());
                startActivity(intent);
                finish();
            }
        });
    }
}
