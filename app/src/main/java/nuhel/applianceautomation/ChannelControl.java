package nuhel.applianceautomation;

import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.Time;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ChannelControl extends AppCompatActivity {

    //Declaring Buttons
    private Button channel_one_on;
    private Button channel_one_off;
    private Button channel_two_on;
    private Button channel_two_off;
    private Button channel_three_on;
    private Button channel_three_off;
    private Button channel_four_on;
    private Button channel_four_off;


    private String address = null;
    private ProgressDialog progress;
    private BluetoothAdapter myBluetooth = null;
    private BluetoothSocket btSocket = null;
    private boolean isBtConnected = false;
    //SPP UUID. Look for it
    static final UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private SharedPreferences sharedPreferences;
    private String myData = "homeAutomation";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.channel_control);
        initializeButtons();
        initButtonActions();
        Intent newint = getIntent();
        address = newint.getStringExtra(DeviceList.EXTRA_ADDRESS); //receive the address of the bluetooth device
        sharedPreferences = getSharedPreferences(myData, MODE_PRIVATE);
        new ConnectBT().execute();
    }

    private void initializeButtons() {
        channel_one_on = (Button) findViewById(R.id.channel_one_on);
        channel_one_off = (Button) findViewById(R.id.channel_one_off);
        channel_two_on = (Button) findViewById(R.id.channel_two_on);
        channel_two_off = (Button) findViewById(R.id.channel_two_off);
        channel_three_on = (Button) findViewById(R.id.channel_three_on);
        channel_three_off = (Button) findViewById(R.id.channel_three_off);
        channel_four_on = (Button) findViewById(R.id.channel_four_on);
        channel_four_off = (Button) findViewById(R.id.channel_four_off);

        findViewById(R.id.showBill).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ChannelControl.this,ElectricBill.class));
            }
        });
    }

    private void initButtonActions() {

        channel_one_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doButtonAction(SignalSource.CHANNEL_ONE_ON);
            }
        });

        channel_one_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doButtonAction(SignalSource.CHANNEL_ONE_OFF);
            }
        });

        channel_two_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doButtonAction(SignalSource.CHANNEL_TWO_ON);
            }
        });
        channel_two_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doButtonAction(SignalSource.CHANNEL_TWO_OFF);
            }
        });

        channel_three_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doButtonAction(SignalSource.CHANNEL_THREE_ON);
            }
        });

        channel_three_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doButtonAction(SignalSource.CHANNEL_THREE_OFF);
            }
        });

        channel_four_on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doButtonAction(SignalSource.CHANNEL_FOUR_ON);
            }
        });

        channel_four_off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doButtonAction(SignalSource.CHANNEL_FOUR_OFF);
            }
        });

    }


    private void doButtonAction(String signal) {

        //Channel One

        if (signal.equals(SignalSource.CHANNEL_ONE_ON)) {
            if (InstanceSaver.channel_one_indicator == true) {
                msg("Channel One is already On");
                return;
            } else {
                if (sendData(signal)) {
                    InstanceSaver.channel_one_indicator = true;
                    msg("Channel One Turned On");
                    Date date = new Date();
                    InstanceSaver.TIME_ONE_BEGIN = date.getTime();
                } else {
                    msg("Channel One Turned On Failed");
                }
            }

        } else if (signal.equals(SignalSource.CHANNEL_ONE_OFF)) {
            if (InstanceSaver.channel_one_indicator == false) {
                msg("Channel One is already OFF");
                return;
            } else {
                if (sendData(signal)) {
                    InstanceSaver.channel_one_indicator = false;
                    msg("Channel One Turned Off");
                    Date date = new Date();
                    InstanceSaver.TIME_ONE_END = date.getTime();
                    long s = sharedPreferences.getLong("TOTAL_TIME_ONE", 0) + InstanceSaver.TIME_ONE_END - InstanceSaver.TIME_ONE_BEGIN;
                    sharedPreferences.edit().putLong("TOTAL_TIME_ONE", s).commit();
                    Toast.makeText(this, "" + TimeUnit.MILLISECONDS.toSeconds(s), Toast.LENGTH_SHORT).show();

                } else {
                    msg("Channel One Turned Off Failed");
                }
            }

        }


        //Channel Two

        else if (signal.equals(SignalSource.CHANNEL_TWO_ON)) {
            if (InstanceSaver.channel_two_indicator == true) {
                msg("Channel Two is already On");
                return;
            } else {
                if (sendData(signal)) {
                    InstanceSaver.channel_two_indicator = true;
                    msg("Channel Two Turned On");
                    Date date = new Date();
                    InstanceSaver.TIME_TWO_BEGIN = date.getTime();
                } else {
                    msg("Channel Two Turned On Failed");
                }
            }

        } else if (signal.equals(SignalSource.CHANNEL_TWO_OFF)) {

            if (InstanceSaver.channel_two_indicator == false) {
                msg("Channel Two is already OFF");
                return;
            } else {

                if (sendData(signal)) {
                    InstanceSaver.channel_two_indicator = false;
                    msg("Channel Two Turned Off");
                    Date date = new Date();
                    InstanceSaver.TIME_TWO_END = date.getTime();
                    long s = sharedPreferences.getLong("TOTAL_TIME_TWO", 0) + InstanceSaver.TIME_TWO_END - InstanceSaver.TIME_TWO_BEGIN;
                    sharedPreferences.edit().putLong("TOTAL_TIME_TWO", s).commit();
                    Toast.makeText(this, "" + TimeUnit.MILLISECONDS.toSeconds(s), Toast.LENGTH_SHORT).show();
                } else {
                    msg("Channel Two Turned Off Failed");
                }
            }
        }


        //Channel Three


        else if (signal.equals(SignalSource.CHANNEL_THREE_ON)) {
            if (InstanceSaver.channel_three_indicator == true) {
                msg("Channel Three is already On");
                return;
            } else {
                if (sendData(signal)) {
                    InstanceSaver.channel_three_indicator = true;
                    msg("Channel Three Turned On");
                    Date date = new Date();
                    InstanceSaver.TIME_THREE_BEGIN = date.getTime();
                } else {
                    msg("Channel Three Turned On Failed");
                }
            }

        } else if (signal.equals(SignalSource.CHANNEL_THREE_OFF)) {
            if (InstanceSaver.channel_three_indicator == false) {
                msg("Channel Three is already OFF");
                return;
            } else {
                if (sendData(signal)) {
                    InstanceSaver.channel_three_indicator = false;
                    msg("Channel Three Turned Off");
                    Date date = new Date();
                    InstanceSaver.TIME_THREE_END = date.getTime();


                    long s = sharedPreferences.getLong("TOTAL_TIME_THREE", 0) + InstanceSaver.TIME_THREE_END - InstanceSaver.TIME_THREE_BEGIN;

                    sharedPreferences.edit().putLong("TOTAL_TIME_THREE", s).commit();

                    Toast.makeText(this, "" + TimeUnit.MILLISECONDS.toSeconds(s), Toast.LENGTH_SHORT).show();

                } else {
                    msg("Channel Three Turned Off Failed");
                }
            }
        }


        //Channel Four

        else if (signal.equals(SignalSource.CHANNEL_FOUR_ON)) {
            if (InstanceSaver.channel_four_indicator == true) {
                msg("Channel Four is already On");
                return;
            } else {
                if (sendData(signal)) {
                    InstanceSaver.channel_four_indicator = true;
                    msg("Channel Four Turned On");
                    Date date = new Date();
                    InstanceSaver.TIME_FOUR_BEGIN = date.getTime();
                } else {
                    msg("Channel Four Turned On Failed");
                }
            }
        } else if (signal.equals(SignalSource.CHANNEL_FOUR_OFF)) {
            if (InstanceSaver.channel_four_indicator == false) {
                msg("Channel Four is already Off");
                return;
            } else {
                if (sendData(signal)) {
                    InstanceSaver.channel_four_indicator = false;
                    msg("Channel Four Turned Off");
                    Date date = new Date();
                    InstanceSaver.TIME_FOUR_END = date.getTime();

                    long s = sharedPreferences.getLong("TOTAL_TIME_FOUR", 0) + InstanceSaver.TIME_FOUR_END - InstanceSaver.TIME_FOUR_BEGIN;

                    sharedPreferences.edit().putLong("TOTAL_TIME_FOUR", s).commit();

                    Toast.makeText(this, "" + TimeUnit.MILLISECONDS.toSeconds(s), Toast.LENGTH_SHORT).show();

                } else {
                    msg("Channel Four Turned Off Failed");
                }
            }
        }

    }


    private boolean sendData(String signal) {

        if (btSocket != null) {
            try {
                btSocket.getOutputStream().write(signal.getBytes());
                return true;
            } catch (IOException e) {
                return false;
            }
        }

        return false;
    }


    private class ConnectBT extends AsyncTask<Void, Void, Void>  // UI thread
    {

        public ConnectBT() {

        }

        private boolean ConnectSuccess = true; //if it's here, it's almost connected

        @Override
        protected void onPreExecute() {
            progress = ProgressDialog.show(ChannelControl.this, "Connecting...", "Please wait!!!");  //show a progress dialog
        }

        @Override
        protected Void doInBackground(Void... devices) //while the progress dialog is shown, the connection is done in background
        {
            try {
                if (btSocket == null || !isBtConnected) {
                    myBluetooth = BluetoothAdapter.getDefaultAdapter();//get the mobile bluetooth device
                    BluetoothDevice dispositivo = myBluetooth.getRemoteDevice(address);//connects to the device's address and checks if it's available
                    btSocket = dispositivo.createInsecureRfcommSocketToServiceRecord(myUUID);//create a RFCOMM (SPP) connection
                    BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                    btSocket.connect();//start connection


                }
            } catch (IOException e) {
                ConnectSuccess = false;//if the try failed, you can check the exception here
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) //after the doInBackground, it checks if everything went fine
        {
            super.onPostExecute(result);

            if (!ConnectSuccess) {
                msg("Failed to connect with device");
                finish();
            } else {
                msg("Connected to device");
                isBtConnected = true;
            }
            progress.dismiss();
        }
    }

    // fast way to call Toast
    private void msg(String s) {
        Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT).show();
    }
}
