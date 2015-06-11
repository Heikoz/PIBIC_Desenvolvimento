package br.com.pibic.main;

import org.json.JSONException;
import org.json.JSONObject;

import br.com.ufac.bean.User;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class ActivitySecundaria extends Activity {
	public static final String USER = "main.USER";
	public static final String ACAO_EXIBIR_SAUDACAO = "main.ACAO_EXIBIR_SAUDACAO";
	public static final String CATEGORIA_SAUDACAO = "main.CATEGORIA_SAUDACAO";
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    // Get the message from the intent
	    Intent intent = getIntent();
	    User user = (User) intent.getSerializableExtra(USER);

	    // Create the text view
	    TextView textView = new TextView(this);
	    textView.setTextSize(40);
	    textView.setText(user.getId()+"\n"+user.getName());

	    // Set the text view as the activity layout
	    setContentView(textView);
	}
}
