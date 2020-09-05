package com.ihfazh.dicodingmyquote;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class QuotesListActivity extends AppCompatActivity {
    private RecyclerView rvQuotes;
    private ProgressBar progressBar;
    private ArrayList<QuoteData> quotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotes_list);

        progressBar = findViewById(R.id.progressBar);
        rvQuotes = findViewById(R.id.quotes_list);


        getQuotesList();
    }

    private void getQuotesList() {
        AsyncHttpClient client = new AsyncHttpClient();
        progressBar.setVisibility(ProgressBar.VISIBLE);
        String url = "https://programming-quotes-api.herokuapp.com/quotes/page/1";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);

                quotes = new ArrayList<>();

                String result = new String(responseBody);
                try {
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length() ; i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        QuoteData quoteData = new QuoteData(jsonObject.getString("en"), jsonObject.getString("author"));
                        quotes.add(quoteData);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                QuotesAdapter quotesAdapter = new QuotesAdapter(quotes);
                rvQuotes.setLayoutManager(new LinearLayoutManager(QuotesListActivity.this));
                rvQuotes.setAdapter(quotesAdapter);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                progressBar.setVisibility(ProgressBar.INVISIBLE);

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

                Toast.makeText(QuotesListActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
            }
        });

    }
}