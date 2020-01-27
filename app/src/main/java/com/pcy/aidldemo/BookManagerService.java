package com.pcy.aidldemo;

import android.app.ActivityManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.pcy.aidldemo.model.Book;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BookManagerService extends Service {
    private static final String TAG = "BookManagerService";
    private List<Book> bookList = new ArrayList<>();

    public BookManagerService() {
    }

    private IBinder binder = new IBookManager.Stub() {
        @Override
        public List<Book> getBookList() throws RemoteException {
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(TAG, "addBook() called with: book = [" + book + "]" + "threadName: " + Thread.currentThread().getName());
            Log.d(TAG, "addBook getAppName: " + getAppName(BookManagerService.this));
            bookList.add(book);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() called");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public static String getAppName(Context context) {
        int pid = android.os.Process.myPid(); // Returns the identifier of this process
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List list = activityManager.getRunningAppProcesses();
        for (Object o : list) {
            ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (o);
            try {
                if (info.pid == pid) {
                    // 根据进程的信息获取当前进程的名字
                    return info.processName;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // 没有匹配的项，返回为null
        return null;
    }

}
