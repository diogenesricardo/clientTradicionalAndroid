package com.example.diogenes.clientneeandroid;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.JsonReader;
import android.util.Log;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    OkHttpClient client = new OkHttpClient();

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            postSample();
            // Para testar o Get comente a chamada acima e retire o comentário da chamada abaixo.
            // getSample();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getSample() {

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

    private void postSample() throws IOException {

        String url = "http://192.168.137.1:8080/cores";

        Cor cor = new Cor();
        cor.setDescricao("Branco Neve");

        Gson gson = new Gson();
        String jsonObject = gson.toJson(cor);


        RequestBody body = RequestBody.create(JSON, jsonObject);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                String mMessage = e.getMessage().toString();
                Log.w("failure Response", mMessage);
                //call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String mMessage = response.body().string();
                if (response.isSuccessful()){
                    try {
                        JSONObject json = new JSONObject(mMessage);
                        final String serverResponse = json.getString("descricao");

                    } catch (Exception e){
                        e.printStackTrace();
                    }

                }
            }
        });
    }

}
