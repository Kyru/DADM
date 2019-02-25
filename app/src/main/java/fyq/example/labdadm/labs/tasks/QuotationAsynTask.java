package fyq.example.labdadm.labs.tasks;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import fyq.example.labdadm.labs.Quotation;
import fyq.example.labdadm.labs.QuotationActivity;

public class QuotationAsynTask extends AsyncTask<String, Void, Quotation> {

    private WeakReference<QuotationActivity> weakReferenceQuotation;

    public QuotationAsynTask(QuotationActivity quotationActivity){
        weakReferenceQuotation = new WeakReference<>(quotationActivity);
    }

    @Override
    protected Quotation doInBackground(String... strings) {
        //Quotation quotation = new Quotation("test", "test");
        Quotation quotation = null;
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("http");
        builder.authority("api.forismatic.com");
        builder.appendPath("api");
        builder.appendPath("1.0");
        builder.appendQueryParameter("method", "getQuote");
        builder.appendQueryParameter("format", "json");
        if(strings[0].equals("English")) {
            builder.appendQueryParameter("lang", "en");
        } else builder.appendQueryParameter("lang", "ru");

        try {
            URL url = new URL(builder.build().toString());
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            Log.d("1. ",""+connection.getResponseCode());

            Log.d("2. ",""+HttpURLConnection.HTTP_OK);
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
        // Cita de prueba para comprobar que funciona correctamente
        // borrar cuando empecemos con el ej3
        //quotation = new Quotation("Verdades como panes", "Ferran");
        if(weakReferenceQuotation != null){
            weakReferenceQuotation.get().hideProgressBar(quotation);
        }
    }
}
