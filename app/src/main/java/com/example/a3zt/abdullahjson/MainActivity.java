package com.example.a3zt.abdullahjson;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    int pp=6;
    private final String API_URL = "http://18.194.70.123/api/users";
    private ListView listView;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        dialog = new ProgressDialog(this);
        dialog.setIndeterminate(true);
        dialog.setCancelable(false);
        dialog.setMessage("Loading. Please wait...");
        listView = findViewById(R.id.LV_User);
        new JSONTask().execute(API_URL);

    }



    public class JSONTask extends AsyncTask<String,String, List<UserDetails> > {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.show();
        }

        @Override
        protected List<UserDetails> doInBackground(String... params) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;


            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream stream = connection.getInputStream();
                reader = new BufferedReader(new InputStreamReader(stream));
                StringBuffer buffer = new StringBuffer();
                String line ="";
                while ((line = reader.readLine()) != null){
                    buffer.append(line);
                }

                String finalJson = buffer.toString();

                JSONArray parentObject = new JSONArray(finalJson);
                List<UserDetails> bookDetailsList = new ArrayList<>();

                for(int i=0; i<parentObject.length(); i++) {

                    JSONObject jsonObject = parentObject.getJSONObject(i);
                    String name=jsonObject.getString("name");
                    Log.d("A",name);
                    String email=jsonObject.getString("email");
                    UserDetails bookDetails = new UserDetails(name,email);
                    bookDetailsList.add(bookDetails);
                }
                return bookDetailsList;

            } catch (MalformedURLException e) {
                Log.d("De1",e.getMessage());
            } catch (IOException e) {
                Log.d("De2",e.getMessage());
            }
            catch (JSONException e) {
                Log.d("De3",e.getMessage());
            } finally {
                if(connection != null) {
                    connection.disconnect();
                }
                try {
                    if(reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return  null;
        }

        @Override
        protected void onPostExecute(final List<UserDetails> result) {
            super.onPostExecute(result);
            dialog.dismiss();
            if(result != null) {
                BookAdapter adapter = new BookAdapter(getApplicationContext(), R.layout.row, result);
                listView.setAdapter(adapter);
            } else {
                Toast.makeText(getApplicationContext(), "Not able to fetch data from server, please check url.", Toast.LENGTH_SHORT).show();
            }
        }
    }



}



