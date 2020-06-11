package com.kaan.currencyconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.icu.text.StringSearch;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    TextView tryText;
    TextView usdText;
    TextView gbpText;
    TextView eurText;
    TextView jpyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tryText = findViewById(R.id.tryText);
        gbpText = findViewById(R.id.gbpText);
        usdText = findViewById(R.id.usdText);
        jpyText = findViewById(R.id.jpyText);
        eurText = findViewById(R.id.eurText);
    }


    public void getRates(View view) {

        DownloadData downloadData = new DownloadData();

        try {

            String url = "http://data.fixer.io/api/latest?access_key=734e0aa1f039dc853ae48cbdd8afd2a1";

            downloadData.execute(url);


        } catch (Exception e) {

        }


    }

    private class DownloadData extends AsyncTask<String, Void, String> {


        @Override
        protected String doInBackground(String... strings) {

            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try {

                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);

                int data = inputStreamReader.read();

                while (data > 0) {

                    char character = (char) data;
                    result += character;

                    data = inputStreamReader.read();

                }


                return result;

            } catch (Exception e) {
                return null;
            }

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            //System.out.println("alınan data:" + s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                //System.out.println("base:" + base);

                String rates = jsonObject.getString("rates");


                JSONObject jsonObject1 = new JSONObject(rates);
                String turkishlira = jsonObject1.getString("TRY");
                tryText.setText("TRY: " + turkishlira);

                String usd = jsonObject1.getString("USD");
                usdText.setText("USD: " + usd);

                String gbp = jsonObject1.getString("CAD");
                gbpText.setText("GBP: " + gbp);

                String eur = jsonObject1.getString("CHF");
                eurText.setText("EUR: " + eur);

                String jpy = jsonObject1.getString("JPY");
                jpyText.setText("JPY: " + jpy);

            } catch (Exception e) {

            }


        }
    }



}
