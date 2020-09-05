package com.ihfazh.dicodingmyquote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {
    private final String TAG = MainActivity.class.getSimpleName();

    TextView tvQuote, tvAuthor;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvAuthor = findViewById(R.id.tvAuthor);
        tvQuote = findViewById(R.id.tvQuote);
        mProgressBar = findViewById(R.id.progressBar);

        getRandomQuote();
    }

    private void getRandomQuote() {
        mProgressBar.setVisibility(ProgressBar.VISIBLE);
        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://programming-quotes-api.herokuapp.com/quotes/random";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);

                String result = new String(responseBody);
                Log.d(TAG, result);

                try {
                    JSONObject responseObject = new JSONObject(result);

                    String quote = responseObject.getString("en");
                    String author = responseObject.getString("author");

                    tvQuote.setText(quote);
                    tvAuthor.setText(author);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                mProgressBar.setVisibility(ProgressBar.INVISIBLE);

                String errorMessage;

                switch (statusCode){
                    case 401:
                        errorMessage = statusCode + " Bad Request";
                        break;
                    case 403:
                        errorMessage = statusCode + " Forbidden";
                        break;
                    case 404:
                        errorMessage = statusCode + " Not found";
                        break;
                    default:
                        errorMessage = statusCode + " " + error.getMessage();
                        break;
                }

                Toast.makeText(MainActivity.this, errorMessage, Toast.LENGTH_SHORT).show();

            }
        });

    }
}