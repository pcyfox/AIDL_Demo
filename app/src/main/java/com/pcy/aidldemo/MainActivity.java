package com.pcy.aidldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.pcy.aidldemo.model.Book;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private int id = 0;
    private IBookManager manager;
    private Intent startSrviceIntent;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.d(TAG, "onServiceConnected() called with: name = [" + name + "], service = [" + service + "]");
            manager = IBookManager.Stub.asInterface(service);

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected() called with: name = [" + name + "]");

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onBind(View view) {
        startSrviceIntent = new Intent().setAction("com.example.service.book").setPackage(this.getPackageName());
        bindService(startSrviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    public void onAdd(View view) {
        if (manager != null) {
            try {
                manager.addBook(new Book(id++ + "", "新书到了"));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void onStopService(View view) {
        if (startSrviceIntent == null) return;
        stopService(startSrviceIntent);
    }

    public void onUnbindService(View view) {
        if (serviceConnection == null) return;
        unbindService(serviceConnection);
    }

}
