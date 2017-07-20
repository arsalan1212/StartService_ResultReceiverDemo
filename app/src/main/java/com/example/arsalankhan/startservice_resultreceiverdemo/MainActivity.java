package com.example.arsalankhan.startservice_resultreceiverdemo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView= (TextView) findViewById(R.id.textView);
    }

    public void StartService(View view){

        EditText editText= (EditText) findViewById(R.id.editText);
        int result=Integer.valueOf(editText.getText().toString());

        ResultReceiver receiver=new MyResultReceiver(null);

        Intent intent=new Intent(this,MyService.class);
        intent.putExtra("num",result);
        intent.putExtra("receiver",receiver);   //put receiver into Extra
        startService(intent);

    }

    public void stopService(View view){
        Intent intent=new Intent(this,MyService.class);
        stopService(intent);
    }

    /**
     * In order to Communicate with startService we use ResultReceiver
     */
    class MyResultReceiver extends ResultReceiver{

        public MyResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);

            if(resultCode==RESULT_OK && resultData!=null){
                String result=resultData.getString("result");

                textView.setText(result);
            }
        }
    }
}
