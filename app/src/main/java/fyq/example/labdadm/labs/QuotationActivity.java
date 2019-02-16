package fyq.example.labdadm.labs;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import fyq.example.labdadm.labs.databases.MySQLiteOpenHelper;

public class QuotationActivity extends AppCompatActivity {

    TextView tv_quote;
    TextView tv_author;
    String authorName;
    int num_frases=0;
    boolean add_visible;
    Menu menu;
    Quotation q;
    MySQLiteOpenHelper sqlhelper = MySQLiteOpenHelper.getInstance(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        tv_quote = findViewById(R.id.tv_quote);
        tv_author = findViewById(R.id.tv_author);
        add_visible = false;
        if(savedInstanceState==null) {

            authorName = prefs.getString("edit_text_preference_username", "Nameless One");

            String quote = tv_quote.getText().toString();
            String newQuote = quote.replace("%1s", authorName);

            tv_quote.setText(newQuote);
        }else{
            tv_quote.setText(savedInstanceState.getString("tv_quote"));
            tv_author.setText(savedInstanceState.getString("tv_author"));
            num_frases = savedInstanceState.getInt("num_frases");
            add_visible = savedInstanceState.getBoolean("add_visible");
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menuopt) {
        getMenuInflater().inflate(R.menu.menuquotations, menuopt);
        menuopt.findItem(R.id.addtofav_item).setVisible(add_visible);
        menu = menuopt;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){
            case android.R.id.home: return super.onOptionsItemSelected(item);
            case R.id.getquot_item:
                tv_author.setText(getResources().getString(R.string.tv_sample_author).replace("%1$d"," "+num_frases));
                tv_quote.setText(getResources().getString(R.string.tv_sample_quote).replace("%1$d"," "+num_frases));
                num_frases++;
                menu.findItem(R.id.addtofav_item).setVisible(true);
                add_visible= !sqlhelper.isInDatabase(tv_quote.getText().toString());
                return super.onOptionsItemSelected(item);
            case R.id.addtofav_item:
                add_visible=false;
                item.setVisible(false);
                q = new Quotation(tv_quote.getText().toString(),tv_author.getText().toString());
                sqlhelper.addQuotation(q);
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString("tv_quote",tv_quote.getText().toString());
            savedInstanceState.putString("tv_author", tv_author.getText().toString());
            savedInstanceState.putInt("num_frases",num_frases);
            savedInstanceState.putBoolean("add_visible",add_visible);

    }


}
