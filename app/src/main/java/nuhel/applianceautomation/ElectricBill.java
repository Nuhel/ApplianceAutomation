package nuhel.applianceautomation;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class ElectricBill extends AppCompatActivity {
    long timeone, timetwo, timethree, timefour;
    Button btn;

    long totaltime, finalone, finaltwo, finalthree, finalfour, finaltotal;
    TextView txt1, txt2, txt3, txt4, txt5, txt6;

    private SharedPreferences sharedPreferences;
    private String myData = "homeAutomation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        sharedPreferences = getSharedPreferences(myData, MODE_PRIVATE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_electric_bill);


        timeone = sharedPreferences.getLong("TOTAL_TIME_ONE", 0);
        timetwo = sharedPreferences.getLong("TOTAL_TIME_TWO", 0);
        timethree = sharedPreferences.getLong("TOTAL_TIME_THREE", 0);
        timefour = sharedPreferences.getLong("TOTAL_TIME_FOUR", 0);

        finalone = TimeUnit.MILLISECONDS.toSeconds(timeone);
        finaltwo = TimeUnit.MILLISECONDS.toSeconds(timetwo);
        finalthree = TimeUnit.MILLISECONDS.toSeconds(timethree);
        finalfour = TimeUnit.MILLISECONDS.toSeconds(timefour);


        totaltime = finalone + finaltwo + finalthree + finalfour;
        String showone = Long.toString(finalone);
        String showtwo = Long.toString(finaltwo);
        String showthree = Long.toString(finalthree);
        String showfour = Long.toString(finalfour);


        float mul = (float) finalone / (float) 3600;

        float finalunitone = 0.1f * mul;

        mul = (float) finaltwo / (float) 3600;
        float finalunittwo = 0.1f * mul;

        mul = (float) finalthree / (float) 3600;
        float finalunitthree = 0.1f * mul;

        mul = (float) finalfour / (float) 3600;
        float finalunitfour = 0.1f * mul;
        float totalunit = finalunitone + finalunittwo + finalunitthree + finalunitfour;

        float taka = (float) (totalunit * .13);
        //float taka = (float) (totalunit * 3.33) + 50;

        String showtotalunit = String.format("%.5f", totalunit) ;

        String showtaka = Float.toString(taka);

        txt1 = (TextView) findViewById(R.id.textView6);
        txt1.setText(showone);
        txt2 = (TextView) findViewById(R.id.textView7);
        txt2.setText(showtwo);
        txt3 = (TextView) findViewById(R.id.textView8);
        txt3.setText(showthree);
        txt4 = (TextView) findViewById(R.id.textView9);
        txt4.setText(showfour);
        txt5 = (TextView) findViewById(R.id.textView11);
        txt5.setText(showtotalunit);
        txt6 = (TextView) findViewById(R.id.textView12);
        txt6.setText(showtaka);
    }
}
