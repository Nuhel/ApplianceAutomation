package nuhel.applianceautomation;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class GetWatt extends AppCompatActivity {
     EditText e1,e2,e3,e4;
     int flag=1;
     Button next;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_watt);
        next=(Button)findViewById(R.id.next) ;


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e1=(EditText)findViewById(R.id.one);
                String s1=e1.getText().toString();
                e2=(EditText)findViewById(R.id.two);
                String s2=e2.getText().toString();
                e3=(EditText)findViewById(R.id.three);
                String s3=e3.getText().toString();
                e4=(EditText)findViewById(R.id.four);
                String s4=e4.getText().toString();


                if(s1.equals("")||s2.equals("")||s3.equals("")||s4.equals("")){

                    flag=0;
                }
                else{
                    InstanceSaver.wat1=Integer.parseInt(s1);
                    InstanceSaver.wat2=Integer.parseInt(s2);
                    InstanceSaver.wat3=Integer.parseInt(s3);
                    InstanceSaver.wat4=Integer.parseInt(s4);
                    flag=1;
                }
                if(flag==0) {
                    Toast.makeText(GetWatt.this, "Fill All Fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    Intent intent = new Intent(GetWatt.this,ElectricBill.class);
                    startActivity(intent);
                }
            }
        });
    }
}
