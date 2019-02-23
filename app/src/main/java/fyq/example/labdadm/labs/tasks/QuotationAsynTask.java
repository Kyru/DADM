package fyq.example.labdadm.labs.tasks;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.List;

import fyq.example.labdadm.labs.Quotation;
import fyq.example.labdadm.labs.QuotationActivity;

public class QuotationAsynTask extends AsyncTask<Void, Void, Quotation> {

    private WeakReference<QuotationActivity> weakReferenceQuotation;

    public QuotationAsynTask(QuotationActivity quotationActivity){
        weakReferenceQuotation = new WeakReference<>(quotationActivity);
    }

    @Override
    protected Quotation doInBackground(Void... voids) {
        return null;
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
        quotation = new Quotation("Verdades como panes", "Ferran");

        if(weakReferenceQuotation != null){
            weakReferenceQuotation.get().hideProgressBar(quotation);
        }
    }
}
