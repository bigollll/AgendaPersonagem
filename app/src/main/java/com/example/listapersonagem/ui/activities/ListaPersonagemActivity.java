package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class ListaPersonagemActivity extends AppCompatActivity {

    private final PersonagemDAO dao = new PersonagemDAO();

   
    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        //colocando titulo
        setTitle("Lista de Personagens");

        dao.salva(new Personagem("Ken", "1,80", "02041979"));
        dao.salva(new Personagem("Ryu", "1,80", "02041979"));
        configuraFacNovoPersonagem();


    }

    private void configuraFacNovoPersonagem() {
        //pegando o botão
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_novo_personagem);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class));
            }
        });
    }

    //proteção de dados para n serem apagados qnd der back
    @Override
    protected void onResume() {
        super.onResume();


        ListView listaDePersonagens = findViewById(R.id.lista_personagem);
        List<Personagem> personagens = dao.todos();
        listaDePersonagens.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dao.todos()));
        //Para persistencia de dados qnd der back

        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int posicao, long id) {
                Personagem personagemEscolhido = personagens.get(posicao);
                Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
                vaiParaFormulario.putExtra("personagem", personagemEscolhido);
                startActivity(vaiParaFormulario);
                //intent para mudar para outro lugar
                //Para pegar itens em posiçoes especificos
            }



        });

    }

}


//control + alt + m = refatora linhas escolhidas para virar um metodo