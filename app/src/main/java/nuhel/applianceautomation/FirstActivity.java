package nuhel.applianceautomation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class FirstActivity extends AppCompatActivity {


    private EditText pass;
    private Button enterButton;


    private String password = "12345";

    private SharedPreferences sharedPreferences;

    private String myData = "homeAutomation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        sharedPreferences = getSharedPreferences(myData, MODE_PRIVATE);


        if (sharedPreferences.getBoolean("my_first_time", true)) {
            sharedPreferences.edit().putBoolean("my_first_time", false).commit();
            sharedPreferences.edit().putString("pass", password).commit();
        } else {
            findViewById(R.id.passView).setVisibility(View.GONE);
            password = sharedPreferences.getString("pass", "NUll");
        }


        if (password.equals("NUll")) {
            password = "12345";
        }

        pass = (EditText) findViewById(R.id.enterPass);

        enterButton = (Button) findViewById(R.id.enterApp);


        enterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pass.getText().length() > 0) {
                    if (password.equals(pass.getText().toString())) {
                        startActivity(new Intent(FirstActivity.this, DeviceList.class));
                    }
                }
            }
        });


        findViewById(R.id.chagePass).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FirstActivity.this, ChangePassActivity.class));
                finish();
            }
        });
    }
}
