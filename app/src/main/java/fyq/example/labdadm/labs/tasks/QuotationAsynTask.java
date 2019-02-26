package fyq.example.labdadm.labs.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;

import fyq.example.labdadm.labs.Quotation;
import fyq.example.labdadm.labs.QuotationActivity;
import fyq.example.labdadm.labs.R;

public class QuotationAsynTask extends AsyncTask<String, Void, Quotation> {

    private WeakReference<QuotationActivity> weakReferenceQuotation;

    public QuotationAsynTask(QuotationActivity quotationActivity){
        weakReferenceQuotation = new WeakReference<>(quotationActivity);
    }

    @Override
    protected Quotation doInBackground(String... strings) {

        Quotation quotation = null;
        String postParameters="";

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https");
        builder.authority("api.forismatic.com");
        builder.appendPath("api");
        builder.appendPath("1.0");
        builder.appendPath("");

        String lang = weakReferenceQuotation.get().getResources().getString(R.string.st_english);
        String method_http_post = weakReferenceQuotation.get().getResources().getString(R.string.st_post);
        String method_http_get = weakReferenceQuotation.get().getResources().getString(R.string.st_get);

        if(strings[1].equals(method_http_get)) {
            builder.appendQueryParameter("method", "getQuote");
            builder.appendQueryParameter("format", "json");
            if (strings[0].equals(lang)) {
                builder.appendQueryParameter("lang", "en");
            } else builder.appendQueryParameter("lang", "ru");
        }
        else{
            postParameters = "method=getQuote&format=json&lang=";

            if (strings[0].equals(lang)) {
                postParameters+="en";
            } else postParameters+="ru";
        }
        try {
            URL url = new URL(builder.build().toString());
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            connection.setRequestMethod(strings[1]);
            connection.setDoInput(true);

            if(strings[1].equals(method_http_post)){
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(postParameters);
                writer.flush();
                writer.close();
            }
            if(connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                Gson gson = new Gson();
                quotation = gson.fromJson(reader,Quotation.class);
                reader.close();
            }
            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return quotation;
    }

    @Override
    protected void onPreExecute() {
        if(weakReferenceQuotation != null){
            weakReferenceQuotation.get().showProgressBar();
        }
    }

    @Override
    protected void onPostExecute(Quotation quotation) {

        if(weakReferenceQuotation != null){
            weakReferenceQuotation.get().hideProgressBar(quotation);
        }
    }

}
