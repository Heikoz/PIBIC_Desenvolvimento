package br.com.pibic.main;

import android.app.Activity;
import android.os.Bundle;

public class ActivitySobre extends Activity{

	public static final String ACAO_EXIBIR_SOBRE = "main.ACAO_EXIBIR_SOBRE";
	public static final String CATEGORIA_SOBRE = "main.CATEGORIA_SOBRE";


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_sobre);

	}
}
	