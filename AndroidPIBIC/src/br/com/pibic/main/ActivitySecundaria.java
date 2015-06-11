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
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get the message from the intent
		Intent intent = getIntent();
		User user = (User) intent.getSerializableExtra(MainActivity.EXTRA_MESSAGE);

		// Create the text view
		TextView textView = new TextView(this);
		textView.setTextSize(40);
		textView.setText(user.getId()+"\n"+user.getName());

		// Set the text view as the activity layout
		setContentView(textView);
	}
}
