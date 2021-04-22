package com.example.listapersonagem.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.listapersonagem.R;
import com.example.listapersonagem.dao.PersonagemDAO;
import com.example.listapersonagem.model.Personagem;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import static com.example.listapersonagem.ui.activities.ConstantesActivities.CHAVE_PERSONAGEM;

public class FormularioPersonagemActivity extends AppCompatActivity {


    private static final String TITULO_APPBAR_EDITA_PERSONAGEM = "Editar Personagem" ;
    private static final String TITULO_APPBAR_NOVO_PERSONAGEM = "Novo Personagem";
    private EditText campoNome;
    private EditText campoAltura;
    private EditText campoNascimento;
    private final PersonagemDAO dao = new PersonagemDAO();   //banco de dados do personagem
    private Personagem personagem;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_formulario_personagem_menu_salvar, menu);
        return super.onCreateOptionsMenu(menu);
        //pegando a imagem do check e colocando onde ela deveria estar
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.activity_formulario_personagem_menu_salvar){
            finalizaFormulario();
        }
        return super.onOptionsItemSelected(item);
        //dar função de salvar ao botão check
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_personagem);
        //colocando titulo


        inicializaCampos();
        configuraBotaoSalvar();        //pegando os metodos que foram criados
        carregaPersonagem();
        //metodos criados para otimização do código
    }

    private void carregaPersonagem() {
        Intent dados = getIntent();
        if (dados.hasExtra(CHAVE_PERSONAGEM)) {
            setTitle(TITULO_APPBAR_EDITA_PERSONAGEM);
            personagem = (Personagem) dados.getSerializableExtra(CHAVE_PERSONAGEM);
            preencheCampos();
        } else {
            setTitle(TITULO_APPBAR_NOVO_PERSONAGEM);
            personagem = new Personagem();
        }
        //carraga o personagem qnd clickar nele
    }

    private void preencheCampos() {
        campoNome.setText(personagem.getNome());
        campoAltura.setText(personagem.getAltura());
        campoNascimento.setText(personagem.getNascimento());
        //preenchendo os campos do app
    }

    private void configuraBotaoSalvar() {
        Button botaoSalvar = findViewById(R.id.button_salvar);
        botaoSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalizaFormulario();//metodo que encerra e volta pro inicio
            }
        });
    }

    private void finalizaFormulario() {
        preechePersonagem();

        if (personagem.idValido()) {
            dao.edita(personagem);
        } else {
            dao.salva(personagem);
        }
        finish();
        //metodo criado para facilitar a colocação dos atributos e para voltar para a tela principal com o finish
    }

    private void inicializaCampos() {

        //inicializa no xml
        campoNome = findViewById(R.id.eddittext_nome);
        campoAltura = findViewById(R.id.edittext_altura);   //pegando id de cada campo
        campoNascimento = findViewById(R.id.edittext_nascimento);

        //Configura a formatação
        SimpleMaskFormatter smfAltura = new SimpleMaskFormatter("N,NN"); //espaçamento no campo com virgula
        MaskTextWatcher mtwAltura = new MaskTextWatcher(campoAltura, smfAltura); //mascara para o espaçamento
        campoAltura.addTextChangedListener(mtwAltura); //vincula as informações

        //Configura a formatação
        SimpleMaskFormatter smfNascimento = new SimpleMaskFormatter("NN/NN/NNNN"); //espaçamento no campo com barra
        MaskTextWatcher mtwNascimento = new MaskTextWatcher(campoNascimento, smfNascimento); //mascara para o espaçamento
        campoNascimento.addTextChangedListener(mtwNascimento); //vincula as informações
    }

    private void preechePersonagem() {

        String nome = campoNome.getText().toString();
        String altura = campoAltura.getText().toString();
        String nascimento = campoNascimento.getText().toString();

        personagem.setNome(nome);
        personagem.setAltura(altura);
        personagem.setNascimento(nascimento);

        //preenchendo com string os campos de personagem

    }
}