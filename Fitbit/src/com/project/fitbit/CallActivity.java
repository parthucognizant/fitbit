package com.project.fitbit;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.project.fitbit.parser.JSONParser;

public class CallActivity extends ActionBarActivity {

	String item;
	TextView result;
	private ProgressDialog pDialog;
	String output;
	private static String url = "https://api.fitbit.com/1/user/-/activities/date/today.json";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_call);
		item = getIntent().getExtras().getString("itemvalue");
		Toast.makeText(this, item, Toast.LENGTH_SHORT).show();
		result = (TextView)findViewById(R.id.tv_result);
		new GetData().execute();

		//Log.d("Create Response", output);

	}
	public void display() {
		result.setText(output);

	}

	class GetData extends AsyncTask<String, String, String> {
		private static final String TAG = "GetData";
		JSONParser jsonParser = new JSONParser();

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			JSONObject json1 = jsonParser.makeHttpRequest(url,
					"GET");
			Log.d("Create Response", json1.toString());

			Log.i(TAG, ""+json1.toString());
			return json1.toString();
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			output = result;
			display();

		}

	}

}
