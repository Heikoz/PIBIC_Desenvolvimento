package br.com.pibic.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ufac.bean.Aluno;

public class ActivityMenu extends Activity{

	Aluno aluno;
	private TextView txtSaudacao;
	public static final String ALUNO = "main.ALUNO";
	public static final String ACAO_EXIBIR_SAUDACAO_MENU = "main.ACAO_EXIBIR_SAUDACAO_MENU";
	public static final String CATEGORIA_SAUDACAO_MENU = "main.CATEGORIA_SAUDACAO_MENU";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_menu);
		findViewById(R.id.buttonLogin);
		Intent intent = getIntent();
		aluno = (Aluno) intent.getSerializableExtra(ALUNO);
		
		txtSaudacao = (TextView) findViewById(R.id.txtSaudacao);
		txtSaudacao.setText("Olá, "+aluno.getFirstName());
		
	}
	
	public void onClick (View v) {

		Intent intent = new Intent();
		
		switch (v.getId()) {
		case R.id.buttonRelatorios:
			
			intent = new Intent(ActivityRelatorios.ACAO_EXIBIR_SAUDACAO);
			intent.addCategory(ActivityRelatorios.CATEGORIA_SAUDACAO);
			intent.putExtra(ActivityRelatorios.ALUNO, aluno);
			startActivity(intent);
			
			break;

		default:
			break;
		}
		
	}

	public void onClickCancel(View arg0) {
		Toast.makeText(this, "Saindo...", Toast.LENGTH_LONG).show();
		finish();
	}

}