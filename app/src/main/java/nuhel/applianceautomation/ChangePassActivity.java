package nuhel.applianceautomation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePassActivity extends AppCompatActivity {
    private SharedPreferences sharedPreferences;
    private String prevPass;

    private EditText oldPass;
    private EditText newPass;
    private EditText re_newPass;
    private String myData = "homeAutomation";
    private Button changePass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pass);
        sharedPreferences = getSharedPreferences(myData, MODE_PRIVATE);
        prevPass = sharedPreferences.getString("pass", "NUll");
        oldPass = (EditText) findViewById(R.id.oldPass);
        newPass = (EditText) findViewById(R.id.newPass);
        re_newPass = (EditText) findViewById(R.id.r_newPass);
        changePass = (Button) findViewById(R.id.changePass);
        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (prevPass.equals(oldPass.getText().toString())) {
                    if (newPass.getText().toString().equals(re_newPass.getText().toString())) {
                        sharedPreferences.edit().putString("pass", newPass.getText().toString()).commit();
                        Toast.makeText(ChangePassActivity.this, "PassWord Changed", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ChangePassActivity.this, FirstActivity.class));
                        finish();
                    }
                }
            }
        });


    }
}
