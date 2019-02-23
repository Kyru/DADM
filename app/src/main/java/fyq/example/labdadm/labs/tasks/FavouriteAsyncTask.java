package fyq.example.labdadm.labs.tasks;

import android.os.AsyncTask;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import fyq.example.labdadm.labs.FavouriteActivity;
import fyq.example.labdadm.labs.Quotation;
import fyq.example.labdadm.labs.databases.MySQLiteOpenHelper;
import fyq.example.labdadm.labs.databases.QuotationDatabase;

public class FavouriteAsyncTask extends AsyncTask<Boolean , Void, List<Quotation>> {

    private WeakReference<FavouriteActivity> weakReferenceFav;

    public FavouriteAsyncTask(FavouriteActivity fav){
        this.weakReferenceFav = new WeakReference(fav);
    }


    @Override
    protected List<Quotation> doInBackground(Boolean... booleans) {
        List<Quotation> quotationList = new ArrayList<>();

        if(weakReferenceFav != null) {
            MySQLiteOpenHelper sqlhelper = MySQLiteOpenHelper.getInstance(weakReferenceFav.get().getApplicationContext());

            int cont = booleans.length;

            for(int i = 0; i < cont; i++) {
                if (booleans[i]) {
                    quotationList = QuotationDatabase.getInstance(weakReferenceFav.get().getApplicationContext()).quotationDAO().getAllQuotation();
                } else {
                    quotationList = sqlhelper.getInstance(weakReferenceFav.get().getApplicationContext()).getQuotations();
                }
            }
        }

        return  quotationList;
    }

    @Override
    protected void onPostExecute(List<Quotation> quotations) {
        if (weakReferenceFav != null) {
            weakReferenceFav.get().fillAdapter(quotations);
        }
    }
}
