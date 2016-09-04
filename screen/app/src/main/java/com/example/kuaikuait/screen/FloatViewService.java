package com.example.kuaikuait.screen;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

public class FloatViewService extends Service {
    private WindowManager wManager;// 窗口管理者
    private WindowManager.LayoutParams mParams;// 窗口的属性
    private boolean flag = true;
    private ListView listFloat;

    private MyAdapter adapter;
    private NotesDB notesDB;
    private SQLiteDatabase dbReader;
    private Cursor cursor;

    private static View flview = null;


    public void onCreate() {
        System.out.println("服务的onCreat方法");
        wManager = (WindowManager) getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);
        mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
        mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
        //mParams.format = PixelFormat.RGBA_8888;
        mParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;//不获取焦点
        mParams.width =  WindowManager.LayoutParams.WRAP_CONTENT;//窗口的宽和高
        mParams.height =  WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.gravity= Gravity.CENTER;
    /*    mParams.x = 0;//窗口位置的偏移量
        mParams.y = 0;*/
        mParams.alpha = 0.5f;//窗口的透明度

        LayoutInflater inflater=LayoutInflater.from(getApplicationContext());
        flview = inflater.inflate(R.layout.service_float_view,null);

        wManager.addView(flview, mParams);//添加窗口

        listFloat= (ListView) flview.findViewById(R.id.list_float);

        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();
        setDB();


        super.onCreate();
    }
    public int onStartCommand(Intent intent, int flags, int startId) {

        System.out.println("服务的onStartCommand方法");

        if (flag) {
            flag = false;
        }
        setDB();//每次更新显示用的
      //  wManager.updateViewLayout(flview, mParams);//添加窗口
        return super.onStartCommand(intent, flags, startId);
    }

    public void setDB() {
        cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null, null,
                null, null);
        adapter = new MyAdapter(this, cursor);
        listFloat.setAdapter(adapter);
    }

    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        System.out.println("float的onDestroy");
        wManager.removeView(flview);//添加窗口

        super.onDestroy();
    }

}
