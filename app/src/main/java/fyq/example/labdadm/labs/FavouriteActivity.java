package fyq.example.labdadm.labs;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URLEncoder;
import java.util.ArrayList;

import fyq.example.labdadm.labs.QuotationMethods.QuotationArrayAdapter;

public class FavouriteActivity extends AppCompatActivity {
    ListView listView;
    QuotationArrayAdapter quotationArrayAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        quotationArrayAdapter =
                new QuotationArrayAdapter(this, R.layout.quotation_list_row, getMockQuotations());

        listView = findViewById(R.id.lv_quotations);
        listView.setAdapter(quotationArrayAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Quotation quotation = (Quotation) parent.getItemAtPosition(position);
                String quotationAuthor = quotation.getQuoteAuthor();

                if (quotationAuthor.equals("") || quotationAuthor == null) {
                    Toast.makeText(FavouriteActivity.this,
                            "No se ha podido obtener la información", Toast.LENGTH_LONG).show();
                } else {
                    findAuthor(view, quotationAuthor);
                }
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(final AdapterView<?> parent, final View view, final int position, long id) {

                AlertDialog.Builder builder = new AlertDialog.Builder(FavouriteActivity.this);
                builder.setIcon(android.R.drawable.stat_sys_warning);
                builder.setMessage(R.string.st_alert_dialog);
                builder.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );
                builder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quotationArrayAdapter.remove((Quotation) parent.getItemAtPosition(position));
                    }
                });

                builder.create().show();
                return true;
            }
        });

    }

    public void findAuthor(View view, String authorName) {

        try {
            String authorNameEncoded = URLEncoder.encode(authorName, "UTF-8");

            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://en.wikipedia.org/wiki/Special:Search?search=" + authorNameEncoded));

            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }

        } catch (Exception e) {
            Log.d("Yourapp", "UnsupportedEncodingException");
        }
    }

    public ArrayList<Quotation> getMockQuotations() {

        ArrayList<Quotation> mockListQuotation = new ArrayList<>();
        Quotation mockQuotation = new Quotation("if you want to impress someone put him on your black list",
                "Johnnie Walker");
        for (int i = 0; i < 10; i++) {
            mockListQuotation.add(mockQuotation);
        }

        Quotation nullQuotation = new Quotation("This is a null quotation", "");
        mockListQuotation.add(nullQuotation);

        return mockListQuotation;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menufavquot, menu);
        if(quotationArrayAdapter.getCount()==0) menu.findItem(R.id.clearall).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:
                return super.onOptionsItemSelected(item);
            case R.id.clearall:
                AlertDialog.Builder builderClear = new AlertDialog.Builder(FavouriteActivity.this);
                builderClear.setIcon(android.R.drawable.stat_sys_warning);
                builderClear.setMessage(R.string.st_alert_clearalldialog);
                builderClear.setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }
                );
                builderClear.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        quotationArrayAdapter.clear();
                        supportInvalidateOptionsMenu();
                    }

                });
                builderClear.create().show();
                break;
        }


        return true;
    }
}
