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

public class QuotationActivity extends AppCompatActivity {

    TextView tv_quote;
    TextView tv_author;
    String authorName;
    int num_frases=0;
    boolean add_visible=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);

        authorName = prefs.getString("edit_text_preference_username", "Nameless One");

        tv_quote = findViewById(R.id.tv_quote);
        tv_author = findViewById(R.id.tv_author);

        String quote = tv_quote.getText().toString();
        String newQuote = quote.replace("%1s", authorName);

        tv_quote.setText(newQuote);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuquotations, menu);
        menu.findItem(R.id.addtofav_item).setVisible(true);
        add_visible=true;
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
                return super.onOptionsItemSelected(item);
            case R.id.addtofav_item:
                item.setVisible(false);
                add_visible=false;
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
            super.onSaveInstanceState(savedInstanceState);
            savedInstanceState.putString(tv_quote);
            savedInstanceState.putString(tv_author);
            savedInstanceState.putInt(num_frases);
            savedInstanceState.putBoolean(add_visible);

    }


}
