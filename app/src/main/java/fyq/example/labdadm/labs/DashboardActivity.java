package fyq.example.labdadm.labs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Queue;

public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }

    public void goTo(View view){
        Intent intent;
        switch (view.getId()) {
            case R.id.bt_about:
                intent = new Intent(this, AboutActivity.class);
                break;
            case R.id.bt_favquot:
                intent = new Intent(this, FavouriteActivity.class);
                break;
            case R.id.bt_settings:
                intent = new Intent(this, SettingsActivity.class);
                break;
            case R.id.bt_getquot:
                intent = new Intent(this, QuotationActivity.class);
                break;
            default: intent = new Intent(this, DashboardActivity.class);
        }
        startActivity(intent);
    }
}
