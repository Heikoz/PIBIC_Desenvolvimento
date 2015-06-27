package br.com.pibic.main;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.com.ufac.bean.Aluno;

public class MainActivity extends Activity{
	private EditText edtUsuario;
	private EditText edtSenha;

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tela_inicial);
		edtUsuario = (EditText) findViewById(R.id.edtUsuario);
		edtSenha = (EditText) findViewById(R.id.edtSenha);
		edtUsuario.setText("95196986200");
		edtSenha.setText("lukass13");
	}

	public void onClickCancel(View arg0) {
		Toast.makeText(this, "Saindo...", Toast.LENGTH_LONG).show();
		finish();
	}

	public void onClickLogin(View arg0) {
		
		
		String usuario = edtUsuario.getText().toString();
		String senha = edtSenha.getText().toString();

		new LongRunningGetIO(usuario, senha, this).execute();
	}

	private class LongRunningGetIO extends AsyncTask <Void, Void, String> {
		MainActivity activity = null;
		String username;
		String password;

		LongRunningGetIO(String usuario, String senha, MainActivity activity) {
			username = usuario;
			password = senha;
			this.activity = activity;
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
			String url = "http://172.16.108.37:8080/Restful/aluno/"+username+"/"+password;
			Log.i("Verificando URL", url);
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
				Toast.makeText(getApplicationContext(), "Não foi encontrado nenhum usuário com esses dados...", Toast.LENGTH_LONG).show();
				Log.i("PibicAPP", "Login ou senha incorreta.");
			}else{			
				if (results.length()>5 && !results.contains("refused")) {
					Toast.makeText(getApplicationContext(), "Sucesso...", Toast.LENGTH_LONG).show();
					activity.novaTela(results);
				} else {
					Toast.makeText(getApplicationContext(), "Problemas na conexão.", Toast.LENGTH_LONG).show();
					Log.e("PibicAPP", "Erro de conexão. "+results);
				}
			}
		}
	}
	public void novaTela(String results) {
		Log.i("results: ", results);
		Intent intent = new Intent(ActivityMenu.ACAO_EXIBIR_SAUDACAO_MENU);
		intent.addCategory(ActivityMenu.CATEGORIA_SAUDACAO_MENU);
		
		Aluno aluno = new Aluno();
		JSONObject jo = null;
		try {
			//{"cpf":"951.969.862-00","deficiencia":"Sem DeficiÃªncia ","estado":"Acre ","nacionalidade":"Brasil ",
			//"nascimento":"27/02/1994","naturalidade":"Rio Branco ","nome":"Vitor Lucas Pires Cordovil ",
			//"nomeDaMae":"Rosa Maria Pinheiro Pires ","nomeDoPai":"JosÃ© Euclides Cordovil ","rg":"10491872","sexo":"Masculino "}
			jo = new JSONObject(results);
			aluno.setCpf(jo.getString("cpf"));
			aluno.setDeficiencia(jo.getString("deficiencia"));
			aluno.setEstado(jo.getString("estado"));
			aluno.setNacionalidade(jo.getString("nacionalidade"));
			aluno.setNascimento(jo.getString("nascimento"));
			aluno.setNaturalidade(jo.getString("naturalidade"));
			aluno.setNome(jo.getString("nome"));
			aluno.setNomeDaMae(jo.getString("nomeDaMae"));
			aluno.setNomeDoPai(jo.getString("nomeDoPai"));
			aluno.setRg(jo.getString("rg"));
			aluno.setSexo(jo.getString("sexo"));
			Log.i("PibicApp", aluno.toString());
			intent.putExtra(ActivityMenu.ALUNO, aluno);
			startActivity(intent);

		} catch (JSONException e) {
			Toast.makeText(this, "ERRO", Toast.LENGTH_LONG).show();
			Log.e("PibicAPP", "Erro na conversão dos dados do Json para User");
		}
	}
}