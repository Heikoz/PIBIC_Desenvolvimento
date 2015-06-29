package br.com.pibic.main;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import br.com.pibic.main.R.id;
import br.com.ufac.bean.Aluno;
import br.com.ufac.bean.Horario;

public class ActivityMenu extends Activity{

	private Aluno aluno;
	private TextView txtSaudacao;
	public static final String ALUNO = "main.ALUNO";
	public static final String ACAO_EXIBIR_SAUDACAO_MENU = "main.ACAO_EXIBIR_SAUDACAO_MENU";
	public static final String CATEGORIA_SAUDACAO_MENU = "main.CATEGORIA_SAUDACAO_MENU";
	private Horario horarios;


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

		case R.id.buttonHorarios:
			novaTela();
			break;

		
		case R.id.buttonSobre:
			intent = new Intent(ActivitySobre.ACAO_EXIBIR_SOBRE);
			intent.addCategory(ActivitySobre.CATEGORIA_SOBRE);
			startActivity(intent);
			break;
			
		default:
			break;
		}

	}

	private Horario novaTela() {
		new LongRunningGetIO(this).execute();
		return null;
	}

	private class LongRunningGetIO extends AsyncTask <Void, Void, String> {
		ActivityMenu activity = null;

		LongRunningGetIO(ActivityMenu activityMenu) {
			this.activity = activityMenu;
		}

		protected String getASCIIContentFromEntity(HttpEntity entity) throws IllegalStateException, IOException {
			InputStream in = entity.getContent();
			StringBuffer out = new StringBuffer();
			int n = 1;
			while (n>0) {
				byte[] b = new byte[4096];
				n =  in.read(b);
				if (n>0) out.append(new String(b, 0, n));
			}
			return out.toString();
		}

		@Override
		protected String doInBackground(Void... params) {

			HttpClient httpClient = new DefaultHttpClient();
			String url = "http://192.168.0.168:8080/Restful/aluno/horarios";
			Log.i("Verificando URLHorarios", url);
			HttpGet httpGet = new HttpGet(url);
			String text = null;

			try {
				HttpResponse response = httpClient.execute(httpGet);
				HttpEntity entity = response.getEntity(); 
				text = getASCIIContentFromEntity(entity);

			} catch (Exception e) {
				return e.getLocalizedMessage();
			}
			return text;
		}	

		protected void onPostExecute(String results) {
			if (results.equals("{}") || results == null || results.equals("null")){
				Toast.makeText(getApplicationContext(), "Erro de sessão na aplicação logue novamente...", Toast.LENGTH_LONG).show();
				Log.i("PibicAPP", "Erro de sessão ao requisitar os horários.");
			}else{			
				if (results.length()>5 && !results.contains("refused")) {
					Toast.makeText(getApplicationContext(), "Sucesso...", Toast.LENGTH_LONG).show();
					activity.getHorarios(results);
				} else {
					Toast.makeText(getApplicationContext(), "Problemas na conexão.", Toast.LENGTH_LONG).show();
					Log.e("PibicAPP", "Erro de conexão. "+results);
				}
			}
		}
	}

	public void onClickCancel(View arg0) {
		Toast.makeText(this, "Saindo...", Toast.LENGTH_LONG).show();
		finish();
	}

	public void getHorarios(String results) {
		Log.i("results: ", results);			
		horarios = new Horario();
		JSONObject jo = null;
		try {
			jo = new JSONObject(results);
			JSONArray ja;

			//{"quarta":["Engenharia de Software II T. A30-7P","Trabalho de Conclusão de Curso - TCC I T. A30-7P","Introdução à Inteligência Artificial T. A30-7P","Estágio Supervisionado T. A30-7P",""],"quinta":["Planos de Negócios em Informática T. A30-7P","Sistemas Distribuídos T. A30-7P","Estágio Supervisionado T. A30-7P","","Cálculo Diferencial e Integral V T. A84 3P"],"sabado":["","","","",""],"segunda":["Engenharia de Software II T. A30-7P","Introdução à Inteligência Artificial T. A30-7P","Estágio Supervisionado T. A30-7P","Estágio Supervisionado T. A30-7P","Cálculo Diferencial e Integral V T. A84 3P"],"sexta":["Contabilidade e Custos T. A30-5P","Interface Homem Máquina T. A30-7P","Ética e Legislação Aplicada à Informática T. A30-7P","",""],"tempo":["07:30 - 09:10","09:20 - 11:00","11:10 - 12:50","13:30 - 15:10","17:10 - 18:50"],"terca":["Estágio Supervisionado T. A30-7P","Interface Homem Máquina T. A30-7P","Sistemas Distribuídos T. A30-7P","",""]}
			//
			ja = jo.getJSONArray("tempo");
			for (int i = 0; i < ja.length(); i++)
				horarios.getTempo().add(ja.getString(i));

			ja = jo.getJSONArray("segunda");
			for (int i = 0; i < ja.length(); i++)
				horarios.getSegunda().add(ja.getString(i));

			ja = jo.getJSONArray("terca");
			for (int i = 0; i < ja.length(); i++)
				horarios.getTerca().add(ja.getString(i));

			ja = jo.getJSONArray("quarta");
			for (int i = 0; i < ja.length(); i++)
				horarios.getQuarta().add(ja.getString(i));

			ja = jo.getJSONArray("quinta");
			for (int i = 0; i < ja.length(); i++)
				horarios.getQuinta().add(ja.getString(i));

			ja = jo.getJSONArray("sexta");
			for (int i = 0; i < ja.length(); i++)
				horarios.getSexta().add(ja.getString(i));

			ja = jo.getJSONArray("sabado");
			for (int i = 0; i < ja.length(); i++)
				horarios.getSabado().add(ja.getString(i));
			
			Intent intent = new Intent();
			intent = new Intent(ActivityHorarios.ACAO_EXIBIR_HORARIOS);
			intent.addCategory(ActivityHorarios.CATEGORIA_HORARIOS);
			intent.putExtra(ActivityHorarios.HORARIO, horarios);
			intent.putExtra(ActivityMenu.ALUNO, aluno);
			startActivity(intent);

		} catch (JSONException e) {
			Toast.makeText(this, "ERRO", Toast.LENGTH_LONG).show();
			Log.e("PibicAPP", "Erro na conversão dos dados do Json para User");
		}
	}
}