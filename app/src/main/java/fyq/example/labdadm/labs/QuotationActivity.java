package fyq.example.labdadm.labs;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

    public void refreshQuote (View view){

        tv_author.setText(getResources().getString(R.string.tv_sample_author));
        tv_quote.setText(getResources().getString(R.string.tv_sample_quote));

    }


}