package com.authorwjf.http_get;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity implements OnClickListener {
	
	String usuario;
	String senha; 
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        findViewById(R.id.my_button).setOnClickListener(this);
    }

	@Override
	public void onClick(View arg0) {
		Button b = (Button)findViewById(R.id.my_button);
		EditText edtUsuario = (EditText) findViewById(R.id.login);
		EditText edtSenha = (EditText) findViewById(R.id.senha);
		
		String usuario = edtUsuario.getText().toString();
		String senha = edtSenha.getText().toString();
		
		new LongRunningGetIO(usuario, senha).execute();
	}
	
	private class LongRunningGetIO extends AsyncTask <Void, Void, String> {
	
		String username;
		String password;
		
		LongRunningGetIO(String usuario, String senha) {
			username = usuario;
			password = senha;
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
			 HttpPost httpPost = new HttpPost("http://proplan.ufac.br/sieauthentication/login");
             String text = null;
             
             try {
            	 
            	 List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);	 
            	 nameValuePairs.add(new BasicNameValuePair("username", username));
            	 nameValuePairs.add(new BasicNameValuePair("password", password));
            	 httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            	 
            	 Log.i("Verificando", username+" e "+password);
            	 
                   HttpResponse response = httpClient.execute(httpPost);

                   HttpEntity entity = response.getEntity(); 
                   
                text = getASCIIContentFromEntity(entity);
                   
                   
             } catch (Exception e) {
            	 return e.getLocalizedMessage();
             }
             return text;
		}	
		
		protected void onPostExecute(String results) {
			if (results.length()>5) {
				Toast.makeText(getApplicationContext(), "Logado: "+results, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(getApplicationContext(), "Usu�rio n�o encontrado.", Toast.LENGTH_LONG).show();
			}
		}
    }
}