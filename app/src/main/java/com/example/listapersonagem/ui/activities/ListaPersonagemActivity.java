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

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class ListaPersonagemActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Lista de Personagens";
    private final PersonagemDAO dao = new PersonagemDAO();


    @Override
    protected void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_personagem);
        //colocando titulo
        setTitle(TITULO_APPBAR);
        configuraFacNovoPersonagem();


    }

    private void configuraFacNovoPersonagem() {
        //pegando o botão
        FloatingActionButton botaoNovoPersonagem = findViewById(R.id.fab_novo_personagem);
        botaoNovoPersonagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abreFormularioSalva();
            }
        });
    }

    private void abreFormularioSalva() {
        startActivity(new Intent(this, FormularioPersonagemActivity.class));
    }

    //proteção de dados para n serem apagados qnd der back
    @Override
    protected void onResume() {
        super.onResume();
        configuraLista();

    }

    private void configuraLista() {
        ListView listaDePersonagens = findViewById(R.id.lista_personagem);
        final List<Personagem> personagens = dao.todos();
        listaDePersonagens(listaDePersonagens);

        //Para persistencia de dados qnd der back
        configuraItemPorClique(listaDePersonagens);
    }

    private void configuraItemPorClique(ListView listaDePersonagens) {
        listaDePersonagens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int posicao, long id) {
                abreFormularioModoEditar(adapterView, posicao);
            }

            private void abreFormularioModoEditar(AdapterView<?> adapterView, int posicao) {
                Personagem personagem = (Personagem) adapterView.getItemAtPosition(posicao);
                Intent vaiParaFormulario = new Intent(ListaPersonagemActivity.this, FormularioPersonagemActivity.class);
                vaiParaFormulario.putExtra(CHAVE_PERSONAGEM, personagem);
                startActivity(vaiParaFormulario);
                //intent para mudar para outro lugar
                //Para pegar itens em posiçoes especificos
            }


        });
    }

    private void listaDePersonagens(ListView listaDePersonagens) {
        listaDePersonagens.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, dao.todos()));
    }

}


//control + alt + m = refatora linhas escolhidas para virar um metodo