package br.com.pibic.main;

import br.com.ufac.bean.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class ActivitySecundaria extends Activity {
	public static final String USER = "main.USER";
	public static final String ACAO_EXIBIR_SAUDACAO = "main.ACAO_EXIBIR_SAUDACAO";
	public static final String CATEGORIA_SAUDACAO = "main.CATEGORIA_SAUDACAO";
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		User user = (User) intent.getSerializableExtra(USER);

		setContentView(R.layout.tela_secundaria);
		TextView txtSaudacao = new TextView(this);
		ListView lstView = new ListView(this);
		txtSaudacao = (TextView) findViewById(R.id.txtSaudacao);
		txtSaudacao.setText("Olá, "+user.getFirstName());

	}

	public void sair(View v){
		finish();
	}

}

