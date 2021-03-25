package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaPersonagemActivity extends AppCompatActivity {

   
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        setTitle("Lista de Personagens");



        //List<String> personagem = new ArrayList<>(Arrays.asList("Alex", "Ken", "Ryu"));

        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_novo_personagem);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class));
            }
        });


        /*TextView primeiroPersonagem = findViewById(R.id.textView);
        TextView segundoPersonagem = findViewById(R.id.textView2);
        TextView terceiroPersonagem = findViewById(R.id.textView3);
        primeiroPersonagem.setText(personagem.get(0));
        segundoPersonagem.setText(personagem.get(1));
        terceiroPersonagem.setText(personagem.get(2));*/
    }
//simplificação do código acima para n ter que ficar colocando toda vez uma linha quando quiser adicionar mais uma linha

    @Override
    protected void onResume() {
        super.onResume();

        PersonagemDAO dao = new PersonagemDAO();
        ListView listaDePersonagens = findViewById(R.id.lista_personagem);
        List<Personagem> personagens = dao.todos();
        listaDePersonagens.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dao.todos()));
        //Para persistencia de dados qnd der back

        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {
                Personagem personagemEscolhido = personagens.get(posicao);
                Log.i("Personagem", "" + personagemEscolhido);
                Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
                //intent para mudar para outro lugar
                startActivity(vaiParaFormulario);
            }



        });
        //Para pegar intens em posiçoes especificos
    }

}
