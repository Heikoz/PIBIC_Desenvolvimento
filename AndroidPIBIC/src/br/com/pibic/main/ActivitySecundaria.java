package br.com.pibic.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Calendar;

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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.ufac.bean.Aluno;

public class ActivitySecundaria extends Activity {
	public static final String ALUNO = "main.ALUNO";
	public static final String ACAO_EXIBIR_SAUDACAO = "main.ACAO_EXIBIR_SAUDACAO";
	public static final String CATEGORIA_SAUDACAO = "main.CATEGORIA_SAUDACAO";
	private TextView txtSaudacao;
	private EditText edtTipoPdf;
	private ListView lstView;
	private ManageFile manageFile;
	private Aluno aluno; 
	public final static String[] tiposRelatorios = {"atestadoMatricula", "atestadoMatriculaEstrangeiro",
		"comprovanteMatricula", "fichaCadastral", "historicoEscolarSimplificado", "integralizacaoCurricular",
		"solicitacaoMatricula","com/comprovanteMatricula", "com/historicoEscolarCRAprovados"}; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		aluno = (Aluno) intent.getSerializableExtra(ALUNO);

		setContentView(R.layout.tela_secundaria);
		edtTipoPdf = (EditText) findViewById(R.id.edtTipoPDF);
		lstView = (ListView) findViewById(R.id.lvPdfs);
		txtSaudacao = (TextView) findViewById(R.id.txtSaudacao);
		txtSaudacao.setText("Olá, "+aluno.getFirstName());
		manageFile = new ManageFile(getApplicationContext());
	}

	public void onClick(View v) {

		try {
			switch (v.getId()) {
			case R.id.buttonReadFile: // Faz a leitura do arquivo    
				manageFile.getStateSDcard();
				// Verifica se o sdcard tem permissão para leitura
				if (manageFile.isSdCardAvailable() && 
						(manageFile.isSdCardReadableOnly() || 
								manageFile.isSdCardWritableReadable())) {

					Log.i("Leitura do arquivo: ", manageFile.readFile());                    
				}           
			}
		} catch (FileNotFoundException e) {
			Log.e("TesteStorage", e.toString());
		} catch (IOException e) {
			Log.e("TesteStorage", e.toString());
		}
	}

	public void getPDF(View v){

		if (!edtTipoPdf.getText().equals("")){
			int num = Integer.parseInt(edtTipoPdf.getText().toString());
			if (num>-1 && num <9){
				Calendar c = Calendar.getInstance(); 
				
				new HttpAsyncTask().execute("http://172.18.101.223:8080/Restful/aluno/pdf/"+edtTipoPdf.getText(), tiposRelatorios[num]+"-"+aluno.getFirstName()+getData(c)+".pdf");
			}
			else
				Toast.makeText(getApplicationContext(), "Entre com valores de 0 a 8", Toast.LENGTH_LONG).show();
		}
		else
			Toast.makeText(getApplicationContext(), "Entre com valores de 0 a 8 para recuperar um pdf", Toast.LENGTH_LONG).show();
	}

	private String getData(Calendar c) {
		String date = c.get(Calendar.DAY_OF_MONTH)+"";
		date += (c.get(Calendar.MONTH)+1)+"";
		date += c.get(Calendar.YEAR);
		return date;
	}

	public void sair(View v){
		finish();
	}

	public String GET(String url, String fileName){
		InputStream inputStream = null;
		try {

			HttpClient httpclient = new DefaultHttpClient();
			HttpResponse httpResponse = httpclient.execute(new HttpGet(url));
			inputStream = httpResponse.getEntity().getContent();
			if (savePDF(fileName, inputStream))
				return "PDF armazenado com successo";

		} catch (Exception e) {
			Log.d("InputStream", e.getLocalizedMessage());	
		}
		return "O PDF não foi armazenado com successo";
	}

	private boolean savePDF(String fileName, InputStream inputStream) {
		OutputStream outputStream = null; 

		// write the inputStream to a FileOutputStream
		try {
			File file = new File(getApplicationContext().getExternalFilesDir(null), fileName);
			outputStream = new FileOutputStream(file);
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
	public File createDir(String fileName) {
		// Get the directory for the user's public pictures directory. 
		File file = new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS), "pdfs ufac");
		file.mkdirs();

		return new File(Environment.getExternalStoragePublicDirectory(
				Environment.DIRECTORY_DOWNLOADS), fileName);
	}

	private class HttpAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... dados) {
			//[0] = url  [1] = nome do arquivo
			return GET(dados[0], dados[1]);
		}
		@Override
		protected void onPostExecute(String result) {
			Log.i("PibicApp", "Resultado: "+result);
		}
	}
}

