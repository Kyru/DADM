package fyq.example.labdadm.labs;

import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import fyq.example.labdadm.labs.databases.MySQLiteOpenHelper;
import fyq.example.labdadm.labs.databases.QuotationDatabase;
import fyq.example.labdadm.labs.tasks.QuotationAsynTask;

public class QuotationActivity extends AppCompatActivity {

    TextView tv_quote;
    TextView tv_author;
    ScrollView scrollViewQuotation;
    ProgressBar progressBar;
    String authorName;
    boolean add_visible;
    Menu menu;
    Quotation q;
    MySQLiteOpenHelper sqlhelper = MySQLiteOpenHelper.getInstance(this);
    Handler handler;
    String database_method;
    String language;
    String http_method;
    QuotationAsynTask quotationAsynTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        tv_quote = findViewById(R.id.tv_quote);
        tv_author = findViewById(R.id.tv_author);
        scrollViewQuotation = findViewById(R.id.scrollViewQuotation);
        progressBar = findViewById(R.id.progressBarQuotation);
        add_visible = false;

        handler = new Handler();

        if(savedInstanceState==null) {

            authorName = prefs.getString("edit_text_preference_username", "Nameless One");
            database_method = prefs.getString("list_preference_database_methods", "Room");
            language = prefs.getString("list_preference_languages", getResources().getString(R.string.st_english));
            http_method = prefs.getString("list_preference_methods_http", "GET");

            String quote = tv_quote.getText().toString();
            String newQuote = quote.replace("%1s", authorName);

            tv_quote.setText(newQuote);
        }else{
            tv_quote.setText(savedInstanceState.getString("tv_quote"));
            tv_author.setText(savedInstanceState.getString("tv_author"));
            add_visible = savedInstanceState.getBoolean("add_visible");

            database_method = savedInstanceState.getString("list_preference_database_methods");
            language = savedInstanceState.getString("list_preference_languages");
            http_method = savedInstanceState.getString("list_preference_methods_http");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menuopt) {
        getMenuInflater().inflate(R.menu.menuquotations, menuopt);
        menuopt.findItem(R.id.addtofav_item).setVisible(add_visible);
        menu = menuopt;
        return true;
    }

    public void showProgressBar(){
        tv_author.setVisibility(View.INVISIBLE);
        tv_quote.setVisibility(View.INVISIBLE);

        menu.findItem(R.id.addtofav_item).setVisible(false);
        menu.findItem(R.id.getquot_item).setVisible(false);

        progressBar.setVisibility(View.VISIBLE);
    }

    public void hideProgressBar(Quotation quotation){
        tv_author.setVisibility(View.VISIBLE);
        tv_author.setText(quotation.getQuoteAuthor());

        tv_quote.setVisibility(View.VISIBLE);
        tv_quote.setText(quotation.getQuoteText());

        menu.findItem(R.id.getquot_item).setVisible(true);

        progressBar.setVisibility(View.INVISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                switch(database_method){
                    case "Room":
                        add_visible = QuotationDatabase.getInstance(QuotationActivity.this).quotationDAO().getQuotation(tv_quote.getText().toString()) == null;
                        break;
                    case "SQLiteOpenHelper":
                        add_visible= !sqlhelper.isInDatabase(tv_quote.getText().toString());
                        break;
                }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        menu.findItem(R.id.addtofav_item).setVisible(add_visible);
                    }
                });
            }

        }).start();


    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home: return super.onOptionsItemSelected(item);
            case R.id.getquot_item:

                quotationAsynTask = new QuotationAsynTask(QuotationActivity.this);
                String [] args = {language, http_method};
                quotationAsynTask.execute(args);

                add_visible = false;
                if(isNetworkConnected()) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            switch (database_method) {
                                case "Room":
                                    add_visible = QuotationDatabase.getInstance(QuotationActivity.this).quotationDAO().getQuotation(tv_quote.getText().toString()) != null;
                                    break;
                                case "SQLiteOpenHelper":
                                    add_visible = sqlhelper.isInDatabase(tv_quote.getText().toString());
                                    break;
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    menu.findItem(R.id.addtofav_item).setVisible(add_visible);
                                }
                            });
                        }

                    }).start();
                }

                return super.onOptionsItemSelected(item);

            case R.id.addtofav_item:
                add_visible=false;
                item.setVisible(false);
                q = new Quotation(tv_quote.getText().toString(),tv_author.getText().toString());

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        switch (database_method){
                            case "Room":
                                QuotationDatabase.getInstance(QuotationActivity.this).quotationDAO().addQuotation(q);
                                break;
                            case "SQLiteOpenHelper":
                                sqlhelper.addQuotation(q);
                                break;
                        }
                    }
                }).start();

                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString("tv_quote",tv_quote.getText().toString());
            savedInstanceState.putString("tv_author", tv_author.getText().toString());
            savedInstanceState.putBoolean("add_visible",add_visible);

            savedInstanceState.putString("list_preference_database_methods", database_method);
            savedInstanceState.putString("list_preference_languages", language);
            savedInstanceState.putString("list_preference_methods_http", http_method);
    }

    public boolean isNetworkConnected() {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        return ((info != null) && (info.isConnected()));
    }

    @Override
    public void onDestroy() {
        if(quotationAsynTask!=null && quotationAsynTask.getStatus()== AsyncTask.Status.RUNNING){
            quotationAsynTask.cancel(true);
        }
        super.onDestroy();
    }
}
