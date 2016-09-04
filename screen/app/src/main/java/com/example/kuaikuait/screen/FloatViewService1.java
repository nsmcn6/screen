package com.example.kuaikuait.screen;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

public class FloatViewService1 extends Service {
    private WindowManager wManager;// 窗口管理者
    private WindowManager.LayoutParams mParams;// 窗口的属性
    private boolean flag = true;
    private Activity myView;
    private ListView lvFloat;

    MyAdapter adapter;
    NotesDB notesDB;
    SQLiteDatabase dbReader;
    Cursor cursor;

    private static View mView = null;
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onCreate(Bundle savedInstanceState) {
        wManager = (WindowManager) getApplicationContext().getSystemService(
                Context.WINDOW_SERVICE);

        LayoutInflater inflater=LayoutInflater.from(getApplicationContext());
        View view = inflater.inflate(R.layout.service_float_view,null);


        lvFloat= (ListView) view.findViewById(R.id.list_float);
        notesDB = new NotesDB(this);
        dbReader = notesDB.getReadableDatabase();



        mParams = new WindowManager.LayoutParams();
        mParams.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;// 系统提示window
        mParams.format = PixelFormat.TRANSLUCENT;// 支持透明
        //mParams.format = PixelFormat.RGBA_8888;
        mParams.flags |= WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL ;// 焦点
        mParams.width =  WindowManager.LayoutParams.WRAP_CONTENT;//窗口的宽和高
        mParams.height =  WindowManager.LayoutParams.WRAP_CONTENT;
        mParams.x = 0;//窗口位置的偏移量
        mParams.y = 0;
        mParams.alpha = 0.1f;//窗口的透明度

        super.onCreate();
    }
    public int onStartCommand(Intent intent, int flags, int startId) {

        if (flag) {
            flag = false;
            wManager.addView(lvFloat, mParams);//添加窗口
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public static void showfloatview(final Context context){




        // 获取应用的Context 和windowManager
        WindowManager wm = (WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();

        mView = setUpView(context);
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        params.format = PixelFormat.TRANSLUCENT;// // 不设置这个弹出框的透明遮罩显示为黑色
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        //设置是否收到用户的操作
        params.setTitle("Toast");

        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height =WindowManager.LayoutParams.WRAP_CONTENT;

        params.gravity = Gravity.CENTER;//排版居中对齐
        wm.addView(mView, params);
    }

    private static View setUpView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_float_view,
                null);
        return view;
    }
    public void selectDB() {
        cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null, null,
                null, null);
        adapter = new MyAdapter(this, cursor);
        lvFloat.setAdapter(adapter);
    }


}
