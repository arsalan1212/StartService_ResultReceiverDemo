package com.example.arsalankhan.startservice_resultreceiverdemo;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by Arsalan khan on 7/20/2017.
 *
 * in start service there is no mechanism to communicate with the component that start the
 * service for that we use (in case of started service)
 * 1) ResultReceiver (within the same process,means activity and service are in same process)
 * 2) BroadCastReceiver (within the same process or in different process)
 */

public class MyService extends Service {

    private ResultReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("TAG","Service is Created");
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId) {

        Log.d("TAG","OnstarTCommand");
        receiver = intent.getParcelableExtra("receiver");
        int num=intent.getIntExtra("num",1);
        MyAsynTask task=new MyAsynTask();
        task.execute(num);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("TAG","Service is Destroyed");
    }


    class MyAsynTask extends AsyncTask<Integer,Integer,Integer>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d("TAG","Thread Name: "+Thread.currentThread().getName()+" ID: "+Thread.currentThread().getId());
        }

        @Override
        protected Integer doInBackground(Integer... integers) {

            int counter=0;
            int num=integers[0];
            while(counter<num){
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(counter);
                counter++;

            }
            Log.d("TAG","Thread Name: "+Thread.currentThread().getName()+" ID: "+Thread.currentThread().getId());

            return counter;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Log.d("TAG","NUM: "+values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {

            Log.d("TAG","RESULT: "+integer);
            Bundle bundle=new Bundle();
            bundle.putString("result","Counter Stop in "+integer +" Seconds");
            receiver.send(Activity.RESULT_OK,bundle);
            super.onPostExecute(integer);
        }
    }
}
