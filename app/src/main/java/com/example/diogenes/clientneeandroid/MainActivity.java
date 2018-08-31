package com.example.diogenes.clientneeandroid;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.JsonReader;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSample();


    }

    private void getSample(){

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here

                try {
                    // 01 - Create URL
                    URL getInsumosURL = new URL("http://192.168.137.1:8080/insumos");

                    // 02 - Create connection
                    HttpURLConnection myConnection =
                            (HttpURLConnection) getInsumosURL.openConnection();

                    // 03 - InputStream do corpo da resposta
                    InputStream responseBody = myConnection.getInputStream();

                    // 04 - Leitor do Stream
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");

                    // 05 - Leitor de JSON
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    // 06 - Iterar
                    jsonReader.beginObject(); // Start processing the JSON object
                    while (jsonReader.hasNext()) { // Loop through all keys
                        String key = jsonReader.nextName(); // Fetch the next key
                        if (key.equals("totalElements")) { // Check if desired key
                            // Fetch the value as a String
                            String value = jsonReader.nextString();

                            // Do something with the value
                            // ...
                            TextView total = (TextView) findViewById(R.id.total);
                            total.setText(value);

                            break; // Break out of the loop
                        } else {
                            jsonReader.skipValue(); // Skip values of other keys
                        }
                    }

                    // 07 - Fechando o leitor de JSON
                    jsonReader.close();

                    // 08 - Fechando a conexão
                    myConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void post(){
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                // All your networking logic
                // should be here

                try {
                    // 01 - Create URL
                    URL getInsumosURL = new URL("http://192.168.137.1:8080/insumos");

                    // 02 - Create connection
                    HttpURLConnection myConnection =
                            (HttpURLConnection) getInsumosURL.openConnection();

                    // 03 - InputStream do corpo da resposta
                    InputStream responseBody = myConnection.getInputStream();

                    // 04 - Leitor do Stream
                    InputStreamReader responseBodyReader =
                            new InputStreamReader(responseBody, "UTF-8");

                    // 05 - Leitor de JSON
                    JsonReader jsonReader = new JsonReader(responseBodyReader);

                    // 06 - Iterar
                    jsonReader.beginObject(); // Start processing the JSON object
                    while (jsonReader.hasNext()) { // Loop through all keys
                        String key = jsonReader.nextName(); // Fetch the next key
                        if (key.equals("totalElements")) { // Check if desired key
                            // Fetch the value as a String
                            String value = jsonReader.nextString();

                            // Do something with the value
                            // ...
                            TextView total = (TextView) findViewById(R.id.total);
                            total.setText(value);

                            break; // Break out of the loop
                        } else {
                            jsonReader.skipValue(); // Skip values of other keys
                        }
                    }

                    // 07 - Fechando o leitor de JSON
                    jsonReader.close();

                    // 08 - Fechando a conexão
                    myConnection.disconnect();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }


}
