package fyq.example.labdadm.labs;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quotation);

        authorName = "Nameless One";

        tv_quote = findViewById(R.id.tv_quote);
        tv_author = findViewById(R.id.tv_author);

        String quote = tv_quote.getText().toString();
        String newQuote = quote.replace("%1s", authorName);

        tv_quote.setText(newQuote);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuquotations, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.getquot_item:
                item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        tv_author.setText(getResources().getString(R.string.tv_sample_author));
                        tv_quote.setText(getResources().getString(R.string.tv_sample_quote));
                        return true;
                    }
                });
                break;
            case R.id.addtofav_item:
                break;
        }
        return true;
    }


}
