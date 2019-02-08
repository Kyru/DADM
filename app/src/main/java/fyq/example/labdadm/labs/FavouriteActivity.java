package fyq.example.labdadm.labs;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import fyq.example.labdadm.labs.QuotationMethods.QuotationArrayAdapter;

public class FavouriteActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        QuotationArrayAdapter quotationArrayAdapter =
                new QuotationArrayAdapter(this, R.layout.quotation_list_row, getMockQuotations());

        ListView listView = findViewById(R.id.lv_quotations);
        listView.setAdapter(quotationArrayAdapter);

    }

    public void findAuthor(View view){

        String authorName = "Albert Einstein";

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + authorName));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public ArrayList<Quotation> getMockQuotations(){

        ArrayList<Quotation> mockListQuotation = new ArrayList<>();
        Quotation mockQuotation = new Quotation("This is a mock quotation", "F");
        for (int i = 0; i < 10; i++){
            mockListQuotation.add(mockQuotation);
        }
        return mockListQuotation;
    }

}
