package com.example.kuaikuait.screen;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PixelFormat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;

public class FloatView {

    private static View mView = null;

    public static void showfloatview(final Context context){

        MyAdapter adapter;
        NotesDB notesDB;
        SQLiteDatabase dbReader;
        Cursor cursor;
        ListView lvfloat;

       // lv= (ListView) findViewById(R.id.list_float);
        //notesDB = new NotesDB(this);
       // dbReader = notesDB.getReadableDatabase();

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
        View view = LayoutInflater.from(context).inflate(R.layout.activity_float_view,
                null);
        return view;
    }

}
