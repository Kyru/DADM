package fyq.example.labdadm.labs;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }

    @Override
    protected void onPause() {
        SharedPreferences prefs =
                PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = prefs.edit();

        EditText editTextName = findViewById(R.id.editText_Name);
        if(editTextName.getText().toString().equals("") || editTextName.getText().toString() == null ){
            editor.remove("name");
        } else {
            editor.putString("name", editTextName.getText().toString());
        }

        editor.apply();
        super.onPause();
    }


}
