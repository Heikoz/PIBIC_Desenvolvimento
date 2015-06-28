package br.com.pibic.main;

import java.io.File;
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
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import br.com.ufac.bean.Aluno;

public class ActivityRelatorios extends Activity {
	public static final String ALUNO = "main.ALUNO";
	public static final String ACAO_EXIBIR_SAUDACAO = "main.ACAO_EXIBIR_SAUDACAO";
	public static final String CATEGORIA_SAUDACAO = "main.CATEGORIA_SAUDACAO";
	private TextView txtSaudacao;
	private Aluno aluno; 
	public final static String[] tiposRelatorios = {"atestadoMatricula", "atestadoMatriculaEstrangeiro",
		"comprovanteMatricula", "fichaCadastral", "historicoEscolarSimplificado", "integralizacaoCurricular",
		"solicitacaoMatricula","com/comprovanteMatricula", "com/historicoEscolarCRAprovados"}; 
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		aluno = (Aluno) intent.getSerializableExtra(ALUNO);

		setContentView(R.layout.tela_relatorios);
		txtSaudacao = (TextView) findViewById(R.id.txtSaudacao);
		txtSaudacao.setText("Olá, "+aluno.getFirstName());
		Log.i("Aviso","Aluno Aqui"+aluno);
	}

	public void onClick(View v) {
		
		Log.i("ERRO", "ENTROU AQUI?!");
		
			switch (v.getId()) {
				
			case R.id.buttonAtestados:
				getPDF(0);
				break;
			
			case R.id.buttonAtestadosEstrangeiros:
				getPDF(1);
				break;
			
			case R.id.buttonComprovanteMatricula:
				getPDF(2);
				break;
			
			case R.id.buttonFichaCadastral:
				getPDF(3);
				break;
			
			case R.id.buttonHistoricoEscolar:
				getPDF(4);
				break;
			
			case R.id.buttonHistoricoIntegralizacao:
				getPDF(5);
				break;
			
			case R.id.buttonSolicitacaoMatricula:
				getPDF(6);
				break;
				
			case R.id.buttonComprovanteMatriculaSIE:
				getPDF(7);		
				break;
					
			case R.id.buttonHistoricoEscolarCR:
				getPDF(8);
				break;
			
			}
	}

	public void getPDF(int num){
			Calendar c = Calendar.getInstance(); 
			new HttpAsyncTask().execute("http://192.168.0.5:8080/Restful/aluno/pdf/"+num, tiposRelatorios[num]+"-"+aluno.getFirstName()+getData(c)+".pdf");
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
		//FileOutputStream fos = null;
		 //write the inputStream to a FileOutputStream
		try {
			//File file = new File(getApplicationContext().getExternalFilesDir(null), fileName);
			//Salvando na pasta de downloads
			if (!isExternalStorageWritable())
				return false;
			
			outputStream = new FileOutputStream(getDownloadDir(fileName));
			Log.i("STORAGE", Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"");
			 //fos = openFileOutput(fileName, Context.MODE_WORLD_WRITEABLE);
			 //Log.i("STORAGE: ", "File= "+fileName+"\n"+fos.toString()+"\n"+fos.getFD().toString());
			int read = 0;
			byte[] bytes = new byte[1024];

			while ((read = inputStream.read(bytes)) != -1) {
				outputStream.write(bytes, 0, read);
			}

		} catch (IOException e) {
			e.printStackTrace();
			return false;
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			//if (outputStream != null) {
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
//		File file = new File(Environment.getExternalStoragePublicDirectory(
//				Environment.DIRECTORY_DOWNLOADS), "pdfs ufac");
//		file.mkdirs();
//
//		return new File(Environment.getExternalStoragePublicDirectory(
//				Environment.DIRECTORY_DOWNLOADS), fileName);
		return Environment.getExternalStorageDirectory();
	}
	
	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}
	
	public File getDownloadDir(String fileName) {
	    File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
	    return file;
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

