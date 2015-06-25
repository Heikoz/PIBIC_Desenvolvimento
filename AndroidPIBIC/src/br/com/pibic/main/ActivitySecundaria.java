package br.com.pibic.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ufac.bean.Aluno;

public class ActivitySecundaria extends Activity {
	public static final String ALUNO = "main.ALUNO";
	public static final String ACAO_EXIBIR_SAUDACAO = "main.ACAO_EXIBIR_SAUDACAO";
	public static final String CATEGORIA_SAUDACAO = "main.CATEGORIA_SAUDACAO";
	private TextView txtSaudacao;
	private ListView lstView;
	private ManageFile manageFile;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Aluno aluno = (Aluno) intent.getSerializableExtra(ALUNO);

		setContentView(R.layout.tela_secundaria);

		lstView = (ListView) findViewById(R.id.lvPdfs);
		txtSaudacao = (TextView) findViewById(R.id.txtSaudacao);
		txtSaudacao.setText("Olá, "+aluno.getFirstName());
		manageFile = new ManageFile(getApplicationContext());
	}

	public void onClick(View v) {

		try {
			switch (v.getId()) {
			case R.id.buttonReadFile: // Faz a leitura do arquivo    

				// Verifica se o sdcard tem permissão para leitura
				if (manageFile.isSdCardAvailable() && 
						(manageFile.isSdCardReadableOnly() || 
								manageFile.isSdCardWritableReadable())) {
					manageFile.getStateSDcard();
					Log.i("Leitura do arquivo: ", manageFile.readFile());                    
				}
				else {
					Toast.makeText(this, 
							"O cartão SD não está disponível, ou não permite" +
									" leitura", Toast.LENGTH_SHORT).show();
				}            
				break;

			case R.id.buttonWriteFile: // Faz a escrita do arquivo

				// Verifica se o sdcard tem permissão para escrita
				if (manageFile.isSdCardAvailable() &&
						manageFile.isSdCardWritableReadable()) {
					// Avisa o usuário se a gravação foi bem sucedida
					if(manageFile.WriteFile("Teste do Pibic") == 
							true){
						Toast.makeText(this, 
								"Texto gravado com sucesso.",
								Toast.LENGTH_SHORT).show();
					}
					else{
						Toast.makeText(this, 
								"Não foi possível escrever o texto.", 
								Toast.LENGTH_SHORT).show();
					}
				}
				else {
					Toast.makeText(this, 
							"O cartão SD não está disponível, ou não permite escrita.", 
							Toast.LENGTH_SHORT).show();                    
				}        

				break;

			default:
				break;
			}

		} catch (FileNotFoundException e) {
			Log.e("TesteStorage", e.toString());
		} catch (IOException e) {
			Log.e("TesteStorage", e.toString());
		}

	}

	public void getPDF(View v){
		new HttpAsyncTask().execute("http://192.168.0.2:8080/Restful/aluno/pdf");
	}

	public void sair(View v){
		finish();
	}

	public String GET(String url){
		InputStream inputStream = null;
		try {

			HttpClient httpclient = new DefaultHttpClient();
			Log.i("PibicAppStorage", "AQUI");
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			Log.i("PibicAppStorage", "AQUI 22");
			inputStream = httpResponse.getEntity().getContent();
			if (savePDF("teste.pdf", inputStream))
				return "PDF armazenado com successo";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());	
		}
		return "O PDF não foi armazenado com successo";
	}

	private boolean savePDF(String fileName, InputStream inputStream) {	
		/* Checks if external storage is available for read and write */
		String state = Environment.getExternalStorageState();
		Log.i("ExternalStorageState(): ", state);
		if (!Environment.MEDIA_MOUNTED.equals(state)){
			Log.e("PibicAppStorage", "armazenamento externo não está disponível.");
			return false;
		}

		OutputStream outputStream = null; 

		// write the inputStream to a FileOutputStream
		try {
			outputStream = new FileOutputStream(createDir());
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (outputStream != null) {
				try {
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return true;
	}

	//cria um diretório dentro da pasta de download da APP.
	public File createDir() {
		// Get the directory for the user's public pictures directory. 
		File file = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS), "pdfs ufac");
		file.mkdirs();

		return new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS), "teste.pdf");
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... urls) {

			return GET(urls[0]);
		}
		@Override
		protected void onPostExecute(String result) {
			Log.e("PibicApp", "Resultado: "+result);
		}
	}

}

