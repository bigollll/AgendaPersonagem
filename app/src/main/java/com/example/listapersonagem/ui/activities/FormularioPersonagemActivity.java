package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;

public class FormularioPersonagemActivity extends AppCompatActivity {

    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private final PersonagemDAO dao = new PersonagemDAO();   //banco de dados do personagem
    private Personagem Personagem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        //colocando titulo
        setTitle("Formulário de Personagens");
        inicializaCampos();


        configuraBotaoSalvar();

        Intent dados = getIntent();
        if(dados.hasExtra("personagem")){
            Personagem personagem = (Personagem) dados.getSerializableExtra("personagem");
            campoNome.setText(personagem.getNome());
            campoAltura.setText(personagem.getAltura());
            campoNascimento.setText(personagem.getNascimento());
        } else {
            Personagem = new Personagem();
        }
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.button_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nome = campoNome.getText().toString();
                String altura = campoAltura.getText().toString();
                String nascimento = campoNascimento.getText().toString();

                Personagem personagemSalvo = new Personagem(nome, altura, nascimento);
                dao.salva(personagemSalvo);
                finish();

                personagemSalvo.setNome(nome);
                personagemSalvo.setAltura(altura);
                personagemSalvo.setNascimento(nascimento);
                dao.edita(personagemSalvo);
                //metodo criado para facilitar a colocação dos atributos
            }
        });
    }

    private void inicializaCampos() {
        //pegando id dos campos
        campoNome = findViewById(R.id.eddittext_nome);
        campoAltura = findViewById(R.id.edittext_altura);
        campoNascimento = findViewById(R.id.edittext_nascimento);
    }
}